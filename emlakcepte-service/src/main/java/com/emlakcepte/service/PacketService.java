package com.emlakcepte.service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emlakcepte.client.PaymentServiceClient;
import com.emlakcepte.client.model.PaymentRequest;
import com.emlakcepte.client.model.PaymentResponse;
import com.emlakcepte.converter.PacketConverter;
import com.emlakcepte.converter.PaymentConverter;
import com.emlakcepte.exception.User.UserNotFoundException;
import com.emlakcepte.exception.packet.UserPacketNotFoundException;
import com.emlakcepte.exception.payment.PaymentResponseIsNullException;
import com.emlakcepte.model.Packet;
import com.emlakcepte.model.User;
import com.emlakcepte.repository.PacketRepository;
import com.emlakcepte.request.PacketRenewRequest;
import com.emlakcepte.request.PacketRequest;
import com.emlakcepte.response.PacketResponse;

@Service
public class PacketService {

	@Autowired
	private PacketRepository packetRepository;

	@Autowired
	private PacketConverter packetConverter;

	@Autowired
	private PaymentConverter paymentConverter;

	@Autowired
	private UserService userService;

	@Autowired
	private PaymentServiceClient paymentServiceClient;

	Logger logger = Logger.getLogger(PacketService.class.getName());

	/*
	 * When user request a packet first a new packet created and it sent to
	 * EmlakCeptePayment service. If payment requirements is okay it returns a
	 * response and send to payment information to rabbit mq.
	 */
	public PaymentResponse create(PacketRequest packetRequest) {
		Optional<User> foundUser = userService.getById(packetRequest.getUserId());

		if (foundUser.isPresent()) {
			Packet packet = packetConverter.convert();
			Packet savedPacket = packetRepository.save(packet);

			PaymentRequest paymentRequest = paymentConverter.convert(foundUser.get(), savedPacket);
			PaymentResponse paymentResponse = paymentServiceClient.create(paymentRequest);

			if (paymentResponse != null) {
				logger.log(Level.INFO,
						"[PaymentService] -- payment is accepted, userId: " + paymentRequest.getUserId());

				return paymentResponse;
			}
			logger.log(Level.WARNING, "[PaymentService] -- payment refused cash must be mininmum 50, userId: "
					+ paymentRequest.getUserId());

			throw new PaymentResponseIsNullException(
					"Payment refused cash must be mininmum 50, userId: " + paymentRequest.getUserId());
		}

		logger.log(Level.WARNING, "[PaymentService] -- user not found with userId: " + packetRequest.getUserId());
		throw new UserNotFoundException("User not found with this userId: " + packetRequest.getUserId());
	}

	public List<PacketResponse> getAll() {
		List<Packet> packets = packetRepository.findAll();
		logger.log(Level.INFO, "[PaymentService] -- all packets, number of packets: " + packets.size());
		return packetConverter.convert(packets);
	}

	/*
	 * If user has a packet and user want to extend packet's time it adds 1 months
	 * to expiring date.
	 */

	public PaymentResponse renew(PacketRenewRequest packetRenewRequest) {
		Optional<User> foundUser = userService.getById(packetRenewRequest.getUserId());
		Optional<Packet> packet = getById(packetRenewRequest.getPacketId());

		if (foundUser.isPresent()) {
			
			if (packet.isPresent()) {
				PaymentRequest paymentRequest = paymentConverter.convert(foundUser.get(), packet.get());
				PaymentResponse paymentResponse = paymentServiceClient.create(paymentRequest);
				logger.log(Level.INFO,
						"[PaymentService] -- renew user packet, userId: " + packetRenewRequest.getUserId());

				return paymentResponse;
			}
			logger.log(Level.WARNING,
					"[PaymentService] -- user packet not found with userid: " + foundUser.get().getId());
			throw new UserPacketNotFoundException("user packet not found with userid:  " + foundUser.get().getId());
		}
		logger.log(Level.WARNING,
				"[PaymentService] --  user not found with this userId: " + packetRenewRequest.getUserId());
		throw new UserNotFoundException("User not found with this userId: " + packetRenewRequest.getUserId());
	}

	// Finds user packets
	public List<PacketResponse> findByUserId(Integer userId) {
		Optional<User> foundUser = userService.getById(userId);
		List<Packet> packets = findUserPacketByUserId(userId);
		if (foundUser.isPresent()) {
			if (!packets.isEmpty()) {
				logger.log(Level.INFO, "[PaymentService] --  user packet, userId: " + userId);
				return packetConverter.convert(packets);
			}
			logger.log(Level.WARNING, "[PaymentService] -- user packet not found with this userid: " + userId);
			throw new UserPacketNotFoundException("User packet not found with this userid: " + userId);
		}
		logger.log(Level.WARNING, "[PaymentService] -- user not found with this userId: " + userId);
		throw new UserNotFoundException("User not found with this userId: " + userId);

	}

	// Finds packets by packetId
	public Optional<Packet> getById(Integer packetId) {
		logger.log(Level.INFO, "[PaymentService] -- packet found, packetId: " + packetId);
		return packetRepository.findById(packetId);
	}

	// Returns user packet
	public List<Packet> findUserPacketByUserId(Integer userId) {
		List<Packet> packets = packetRepository.findAllByUserId(userId);
		return packets;

	}

}
