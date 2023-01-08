package com.emlakcepte.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emlakcepte.configuration.RabbitMQRealtyConfiguration;
import com.emlakcepte.converter.BannerConverter;
import com.emlakcepte.converter.RealtyConverter;
import com.emlakcepte.exception.User.UserNotFoundException;
import com.emlakcepte.exception.packet.PacketRealtyListFullException;
import com.emlakcepte.exception.packet.PacketTimeExpiredException;
import com.emlakcepte.exception.packet.UserPacketNotFoundException;
import com.emlakcepte.exception.realty.NoCityShowCaseException;
import com.emlakcepte.exception.realty.NoRealtyException;
import com.emlakcepte.exception.realty.RealtyAlreadyCreatedException;
import com.emlakcepte.exception.realty.RealtyNotFoundException;
import com.emlakcepte.exception.realty.RealtyRequestIsInvalidException;
import com.emlakcepte.exception.realty.UnknownRealtyStatusException;
import com.emlakcepte.exception.realty.UserAndRealtyDoesntMatchException;
import com.emlakcepte.model.Packet;
import com.emlakcepte.model.Realty;
import com.emlakcepte.model.User;
import com.emlakcepte.model.enums.RealtyType;
import com.emlakcepte.repository.RealtyRepository;
import com.emlakcepte.request.RealtyRequest;
import com.emlakcepte.request.RealtyUpdateRequest;
import com.emlakcepte.response.RealtyResponse;

@Service
public class RealtyService {

	@Autowired
	private RealtyRepository realtyRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private PacketService packetService;

	@Autowired
	private RealtyConverter converter;

	@Autowired
	private BannerConverter bannerConverter;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private RabbitMQRealtyConfiguration rabbitMQRealtyConfiguration;

	Logger logger = Logger.getLogger(RealtyService.class.getName());

	private static final int MAX_REALTY_NUMBER = 10;

	private static final int MIN_DAY = 1;

	/*
	 * Creates new a realty by given request information. It sends to information
	 * rabbitmq to invoke realty status change operation.
	 * 
	 */
	public RealtyResponse create(RealtyRequest realtyRequest) {
		Optional<User> optionalUser = userService.getById(realtyRequest.getUserId());
		Optional<Packet> optionalPacket = packetService.getById(realtyRequest.getPacketId());
		if (checkStatus(realtyRequest)) {
			Optional<Realty> matchingRealty = getByNo(realtyRequest.getNo());
			if (matchingRealty.isEmpty()) {
				logger.log(Level.INFO, "[RealtyService] -- User: {0}, -- realtyRequest: {1}",
						new Object[] { optionalUser.get(), realtyRequest });
				return checkUserPacket(optionalPacket.get(), realtyRequest);
			}
			logger.log(Level.WARNING,
					"[RealtyService] -- Realty already create with given no: " + realtyRequest.getNo());

			throw new RealtyAlreadyCreatedException("Realty already create with this no:" + realtyRequest.getNo());
		}

		logger.log(Level.WARNING, "[RealtyService] -- Realty request is invalid: : " + realtyRequest);

		throw new RealtyRequestIsInvalidException("Realty request is invalid: " + realtyRequest);

	}

	// Checks user's number of realty and packet expire time
	private RealtyResponse checkUserPacket(Packet packet, RealtyRequest realtyRequest) {
		long intervalDays = ChronoUnit.DAYS.between(LocalDate.now(), packet.getExpireDate());
		Optional<List<Realty>> realtyList = realtyRepository.findAllByPacketId(packet.getId());
		int numberOfRealty = realtyList.get().size();

		if (intervalDays >= MIN_DAY) {
			if (numberOfRealty < MAX_REALTY_NUMBER) {
				logger.log(Level.INFO, "[RealtyService] -- User: {0}, -- realty: {1}",
						new Object[] { packet.getUser().getId(), realtyRequest });
				return createRealty(packet, realtyRequest);
			}
			logger.log(Level.WARNING,
					"[RealtyService] -- User packet can have 10 realty userId: " + packet.getUser().getId());
			throw new PacketRealtyListFullException(
					"User packet can have maximum 10 realty userid: " + packet.getUser().getId());
		}
		logger.log(Level.WARNING, "[RealtyService] -- User packet time expired userId: " + packet.getUser().getId());
		throw new PacketTimeExpiredException("User packet time expired userid: " + packet.getUser().getId());

	}

	// It helps create realty steps,
	// a free banner is sent to the banner service when an banner is created
	private RealtyResponse createRealty(Packet packet, RealtyRequest realtyRequest) {
		bannerConverter.createBanner(realtyRequest);
		Realty realty = converter.convert(realtyRequest);
		realty.setUser(packet.getUser());
		realty.setPacket(packet);
		Realty savedRealty = realtyRepository.save(realty);
		logger.log(Level.INFO, "[RealtyService] -- Realty created id: {0} by user: {1}",
				new Object[] { savedRealty.getId(), savedRealty.getUser().getId() });

		rabbitTemplate.convertAndSend(rabbitMQRealtyConfiguration.getQueueName(), realtyRequest);
		logger.log(Level.WARNING,
				"[EmlakCepte Realty Service]  Sending Message to the Queue, realtyRequest: {0}, sent to : {1}",
				new Object[] { realtyRequest.getTitle(), rabbitMQRealtyConfiguration.getQueueName() });

		return converter.convert(savedRealty);
	}

	// Returns all realties
	public List<RealtyResponse> getAll() {
		List<Realty> realty = realtyRepository.findAll();
		logger.log(Level.INFO, "[RealtyService] -- realty list, number of realty: " + realty.size());
		return converter.convert(realty);
	}

	// Returns user realtyes
	public List<RealtyResponse> findAllByUserId(int id) {
		Optional<List<Realty>> optionalRealty = realtyRepository.findAllByUserId(id);
		if (optionalRealty.isPresent()) {
			logger.log(Level.INFO, "[RealtyService] --  user realty list: " + optionalRealty.get());
			return converter.convert(optionalRealty.get());
		}
		logger.log(Level.WARNING, "[RealtyService] -- User not found by given userId: " + id);
		throw new UserNotFoundException("User not found by given userId: " + id);

	}

	// Users can only change realty status PASSIVE to ACTIVE or opppsite of that
	public RealtyResponse updateRealty(Integer userId, Integer realtyId, RealtyUpdateRequest realtyRequest) {
		Optional<User> optionalUser = userService.getById(userId);
		if (optionalUser.isPresent()) {
			Optional<Realty> optionalRealty = realtyRepository.findById(realtyId);
			if (optionalRealty.isPresent()) {
				if (optionalUser.get().equals(optionalRealty.get().getUser())) {
					RealtyType type = realtyRequest.getStatus();
					switch (type) {
					case ACTIVE:
						optionalRealty.get().setStatus(RealtyType.ACTIVE);
						logger.log(Level.INFO, "[RealtyService] --  update Status: " + realtyRequest.getStatus());
						return converter.convert(realtyRepository.save(optionalRealty.get()));
					case PASSIVE:
						optionalRealty.get().setStatus(RealtyType.PASSIVE);
						logger.log(Level.INFO, "[RealtyService] -- Update Status: " + realtyRequest.getStatus());
						return converter.convert(realtyRepository.save(optionalRealty.get()));
					case IN_REVIEW:
						logger.log(Level.WARNING,
								"[RealtyService] -- User cannot change status to: " + RealtyType.IN_REVIEW);
						throw new UnknownRealtyStatusException("User cannot change status to: " + RealtyType.IN_REVIEW);
					default:
						logger.log(Level.WARNING,
								"[RealtyService] --unknown realty status: : " + realtyRequest.getStatus());
						throw new UnknownRealtyStatusException(
								"User can change status two of them: " + RealtyType.ACTIVE + RealtyType.PASSIVE);
					}
				}
				logger.log(Level.WARNING, "[RealtyService] -- user and realty doesnt match: " + userId + realtyId);
				throw new UserAndRealtyDoesntMatchException("Realty doesnt belong to user: " + userId + realtyId);
			}

			logger.log(Level.WARNING, "[RealtyService] -- realty dnot found with id:: " + realtyId);
			throw new RealtyNotFoundException("Realty not found with id: " + realtyId);

		}
		logger.log(Level.WARNING, "[RealtyService] -- user not found userId: " + userId);
		throw new UserNotFoundException("User not found with userId: " + userId);

	}

	// Deletes user's realty
	public Integer deleteRealty(Integer userId, Integer realtyId) {
		Optional<User> optionalUser = userService.getById(userId);
		if (optionalUser.isPresent()) {
			Optional<Realty> optionalRealty = realtyRepository.findById(realtyId);
			if (optionalUser.get().equals(optionalRealty.get().getUser())) {
				if (optionalRealty.isPresent()) {
					realtyRepository.delete(optionalRealty.get());
					logger.log(Level.INFO, "[RealtyService] -- realty deleted realtyId: " + realtyId);
					return realtyId;
				}
				logger.log(Level.WARNING, "[RealtyService] -- realty not found with realtyId: " + realtyId);
				throw new RealtyNotFoundException("Realty not found with  realtyId: " + realtyId);
			}
			logger.log(Level.WARNING,
					"[RealtyService] --  user and realty doesnt match: userId, realtyId " + userId + realtyId);
			throw new UserAndRealtyDoesntMatchException(
					"Realty doesnt belong to user: userId, realtyId" + userId + realtyId);
		}
		logger.log(Level.WARNING, "[RealtyService] -- user not found userId: " + userId);
		throw new UserNotFoundException("User not found with this userId: " + userId);

	}

	public Optional<Realty> getByNo(Integer realtyNo) {
		logger.log(Level.INFO, "[RealtyService] --  realty no:" + realtyNo);
		System.out.println("realty no rrealty no: " + realtyRepository.findByNo(realtyNo));
		return realtyRepository.findByNo(realtyNo);
	}

	// Returns number of realty by province
	public Integer getNumberOfProvinceRealty(String province) {
		Integer count = realtyRepository.countByProvince(province);
		if (count > 0) {
			logger.log(Level.INFO, "[RealtyService] -- number of realty by province: " + count);
			return count;
		}
		logger.log(Level.WARNING, "[RealtyService] -- cant find realty in: " + province);
		throw new NoRealtyException("Cant find realty in: " + province);
	}

	// Returns realty list for cities
	public List<RealtyResponse> getAllProvinceRealty(String province) {
		Optional<List<Realty>> responses = realtyRepository.findAllByProvince(province);
		if (responses.isPresent()) {
			logger.log(Level.INFO, "[RealtyService] -- realty list by province:  size: " + responses.get().size());
			return converter.convert(responses.get());
		}
		logger.log(Level.WARNING, "[RealtyService] --  cant find realty in: " + province);
		throw new NoRealtyException("Cant find realty in: " + province);
	}

	// Returns all listings by city and district
	public List<RealtyResponse> getAllByProvinceAndDistrict(String province, String district) {
		Optional<List<Realty>> responses = realtyRepository.findAllByProvinceAndDistrict(province, district);
		if (responses.isPresent()) {
			logger.log(Level.INFO,
					"[RealtyService] --  realty list by province and district: size:" + responses.get().size());
			return converter.convert(responses.get());
		}
		logger.log(Level.WARNING, "[RealtyService] -- cant find realty in province: {0}, district : {1}"
				+ new Object[] { province, district });
		throw new NoRealtyException("Cant find realty in: " + province + " " + district);
	}

	// Returns number of housing on sale
	public Integer getNumberOfHousingOnSale(String province) {
		Integer count = realtyRepository.countByHousingOnSale(province);
		if (count > 0) {
			logger.log(Level.INFO, "[RealtyService] -- number of housing on sale public: " + count);
			return count;
		}
		logger.log(Level.WARNING, "[RealtyService] -- cant find realty in: " + province);
		throw new NoRealtyException("Cant find realty in: " + province);

	}

	// If the number of realty in a city is more than 10,
	// the city showcase is created and return it.
	public List<RealtyResponse> getCityShowCase(String province) {
		if (getNumberOfProvinceRealty(province) >= 10) {
			logger.log(Level.INFO, "[RealtyService] --  city show case: " + province);
			return getAllProvinceRealty(province);
		}
		logger.log(Level.WARNING, "[RealtyService] --   no city showcase: " + province);
		throw new NoCityShowCaseException("No city showcase in: " + province);
	}

	public List<RealtyResponse> findUserActiveRealty(Integer userId) {
		Optional<List<Realty>> optionalRealty = realtyRepository.findAllByUserIdAndActiveRealty(userId);
		if (optionalRealty.isPresent()) {
			logger.log(Level.INFO, "[RealtyService] --  user active realty list: " + optionalRealty.get());
			return converter.convert(optionalRealty.get());
		}
		logger.log(Level.WARNING, "[RealtyService] --  user not found with tihs userId: " + userId);
		throw new UserNotFoundException("User not found with this userId: " + userId);
	}

	public List<RealtyResponse> findUserPassiveRealty(Integer userId) {
		Optional<List<Realty>> optionalRealty = realtyRepository.findAllByUserIdAndPassiveRealty(userId);
		if (optionalRealty.isPresent()) {
			logger.log(Level.INFO, "[RealtyService] --  user active realty list: " + optionalRealty.get());
			return converter.convert(optionalRealty.get());
		}
		logger.log(Level.WARNING, "[RealtyService] --  user not found with this userId: " + userId);
		throw new UserNotFoundException("User not found with  this userId: " + userId);
	}

	private boolean checkStatus(RealtyRequest realtyRequest) {
		Optional<User> optionalUser = userService.getById(realtyRequest.getUserId());
		Optional<Packet> optionalPacket = packetService.getById(realtyRequest.getPacketId());
		if (optionalUser.isPresent()) {

			if (optionalPacket.isPresent()) {
				System.out.println("test1:" + optionalPacket.get().getUser() + " " + optionalUser.get());
				if (optionalUser.get().equals(optionalPacket.get().getUser())) {

					return true;
				}

				logger.log(Level.WARNING,
						"[RealtyService] -- User and packet doesnt match by given userId {0} packetId: {1}",
						new Object[] { realtyRequest.getUserId(), realtyRequest.getPacketId() });
				throw new UserAndRealtyDoesntMatchException("Realty doesnt belong to user: " + realtyRequest.getUserId()
						+ " " + realtyRequest.getPacketId());
			}
			logger.log(Level.WARNING,
					"[RealtyService] -- user packet not found with this packetId: " + realtyRequest.getPacketId());
			throw new UserPacketNotFoundException(
					"User packet not found with this packetId: " + realtyRequest.getPacketId());
		}
		logger.log(Level.WARNING, "[RealtyService] -- User not found by given userId: " + realtyRequest.getUserId());
		throw new UserNotFoundException("User not found by given userId: " + realtyRequest.getUserId());

	}

}
