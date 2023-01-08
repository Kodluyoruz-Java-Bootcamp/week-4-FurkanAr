package com.emlakcepte.listener;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.emlakcepte.client.model.PaymentResponse;
import com.emlakcepte.converter.PacketConverter;
import com.emlakcepte.exception.User.UserNotFoundException;
import com.emlakcepte.exception.payment.PaymentResponseIsNullException;
import com.emlakcepte.model.Packet;
import com.emlakcepte.model.User;
import com.emlakcepte.repository.PacketRepository;
import com.emlakcepte.service.PacketService;
import com.emlakcepte.service.UserService;

@Component
public class PaymentListener {

	@Autowired
	private PacketRepository packetRepository;

	@Autowired
	private PacketService packetService;

	@Autowired
	private PacketConverter packetConverter;

	@Autowired
	private UserService userService;
	Logger logger = Logger.getLogger(PaymentListener.class.getName());

	/*
	 * Listens payment queue from rabbitmq and checks requirements if user hasnt a
	 * packet it and user fits all requirements defines a packet for user If user
	 * has packet and it fits requirements it renew expire date for the user packet
	 */

	@RabbitListener(queues = "emlakcepte.notification.payment")
	public void notificationListener(PaymentResponse paymentResponse) {
		try {
			if (paymentResponse != null) {

				Optional<User> foundUser = userService.getById(paymentResponse.getUserId());
				
				Optional<Packet> packet = packetService.getById(paymentResponse.getPacketId());


				if (foundUser.isPresent() && packet.get().getUser() != null) {
					System.out.println("paymentlistener: renew packet" + packet.get());
					logger.log(Level.INFO, "[Payment NotificationListener] -- renew user packet, userId: "+
							paymentResponse.getUserId());
					renewPacket(packet.get());

				} else if (foundUser.isPresent() && packet.get().getUser() == null) {
					System.out.println("paymentlistener: create packet" + packet.get());
					logger.log(Level.INFO, "[Payment NotificationListener] -- createa new packet for user , userId: "+
							paymentResponse.getUserId());
					createPacket(packet.get(), foundUser.get());

				} else {

					logger.log(Level.WARNING, "[Payment NotificationListener] --  user not found with this userId: "
							+ paymentResponse.getUserId());
					throw new UserNotFoundException("User not found  with this userId: " + paymentResponse.getUserId());
				}
				
			}else {

				logger.log(Level.WARNING, "[Payment NotificationListener] payment refused");
				throw new PaymentResponseIsNullException("Payment refused");
			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		logger.log(Level.INFO, "[Payment NotificationListener] is listening ");
	}

	// It sets user and packet and saves them.
	private void createPacket(Packet packet, User user) {
		Packet updatePacket = packetConverter.addDates(packet);
		updatePacket.setUser(user);
		packetRepository.save(updatePacket);
		System.out.println("paymentlistener 2: create packet" + updatePacket);

		logger.log(Level.INFO, "[PaymentListener] -- createa new packet for user , userId: "+ user.getId());
	}

	// Extends user packet expiring date.
	private void renewPacket(Packet packet) {
		Packet renewPacket = packetConverter.update(packet);
		logger.log(Level.INFO, "[PaymentListener] -- createa new packet for user , userId: "+ packet.getUser().getId());
		packetRepository.save(renewPacket);
		System.out.println("paymentlistener 2: renewPacket packet" + renewPacket);

	}

}
