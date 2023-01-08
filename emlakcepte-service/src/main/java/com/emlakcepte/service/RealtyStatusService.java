package com.emlakcepte.service;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emlakcepte.exception.realty.RealtyNotFoundException;
import com.emlakcepte.exception.realty.RealtyRequesIsNullException;
import com.emlakcepte.model.Realty;
import com.emlakcepte.model.enums.RealtyType;
import com.emlakcepte.repository.RealtyRepository;
import com.emlakcepte.request.RealtyRequest;

@Service
public class RealtyStatusService {

	@Autowired
	private RealtyService realtyService;

	@Autowired
	private RealtyRepository realtyRepository;
	Logger logger = Logger.getLogger(RealtyStatusService.class.getName());

	/*
	 * It listens realty queue. When a new realty created it goes to rabbitmq and it
	 * comes to this listener. It finds realty with realtyNo and convert realty
	 * status to IN_REVIEW to ACTINVE status.
	 */
	@RabbitListener(queues = "emlakcepte.notification.realty")
	public void notificationListener(RealtyRequest realtyRequest) {
		try {
			System.out.println("notification-0: " + realtyRequest);

			if (realtyRequest != null) {
				Optional<Realty> realty = realtyService.getByNo(realtyRequest.getNo());
				System.out.println("notification-1: " + realty.get());

				if (realty.isPresent()) {
					realty.get().setStatus(RealtyType.ACTIVE);
					realtyRepository.save(realty.get());
					System.out.println("notification-2: " + realty.get().getStatus());

					logger.log(Level.INFO,
							"Realty notification listener invoked - Consuming Message with realtyRequest : "
									+ realtyRequest);

				} else {
					logger.log(Level.WARNING,
							"[Realty notification] -- realty not found with realtyNo: " + realtyRequest.getNo());
					throw new RealtyNotFoundException("Realty not found with realtyNo: " + realtyRequest.getNo());
				}
			} else {
				logger.log(Level.WARNING, "[Realty notification] -- realtyRequest queue is null: " + realtyRequest);
				throw new RealtyRequesIsNullException("RealtyRequest queue is null: " + realtyRequest);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.log(Level.INFO, "[Realty notification] -- working: " + realtyRequest);

	}

}
