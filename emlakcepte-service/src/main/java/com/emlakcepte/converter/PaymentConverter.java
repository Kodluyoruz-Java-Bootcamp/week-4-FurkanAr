package com.emlakcepte.converter;


import org.springframework.stereotype.Component;

import com.emlakcepte.client.model.PaymentRequest;
import com.emlakcepte.model.Packet;
import com.emlakcepte.model.User;

@Component
public class PaymentConverter {


	public PaymentRequest convert(User user, Packet packet) {
		PaymentRequest paymentRequest = new PaymentRequest();
		paymentRequest.setUserId(user.getId());
		paymentRequest.setName(user.getName());
		paymentRequest.setEmail(user.getEmail());
		paymentRequest.setCash(user.getCash());
		paymentRequest.setPacketId(packet.getId());
		paymentRequest.setPrice(packet.getPrice());
		return paymentRequest;
	}

	
	
}
