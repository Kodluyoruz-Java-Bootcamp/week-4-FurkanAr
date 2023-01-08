package com.emlakcepte.listener;

import java.util.logging.Logger;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.emlakcepte.model.User;
import com.emlakcepte.repository.UserRepository;
import com.emlakcepte.request.UserRequest;

@Component
public class NotificationListener {
	
	// It liststen emlakcepte service when a new user created it sends to rabbitmq
	// and notification service listener listens rabbitmq, and saves the userrequest 
	// to database
	
	@Autowired
	private UserRepository userRepository;
	Logger logger = Logger.getLogger(NotificationListener.class.getName());

	@RabbitListener(queues = "emlakcepte.notification.user")
	public void notificationListener(UserRequest userRequest) {
		User user = new User();
		user.setName(userRequest.getName());
		user.setEmail(userRequest.getEmail());
		user.setPassword(userRequest.getPassword());
		user.setCash(userRequest.getCash());
		userRepository.save(user);
        logger.info("Notification listener invoked - Consuming Message with UserRequest Email : " + userRequest.getEmail());

	}

	
}
