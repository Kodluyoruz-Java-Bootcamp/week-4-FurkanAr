package com.emlakcepte.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.emlakcepte.model.Payment;
import com.emlakcepte.request.PaymentRequest;
import com.emlakcepte.response.PaymentResponse;

@Component
public class PaymentConverter {
	

	public PaymentResponse convert(Payment payment) {
		PaymentResponse response = new PaymentResponse();
		response.setId(payment.getId());
		response.setEmail(payment.getEmail());
		response.setName(payment.getName());
		response.setPacketId(payment.getPacketId());
		response.setPrice(payment.getPrice());
		response.setUserId(payment.getUserId());
		return response;

	}

	public Payment convert(PaymentRequest paymentRequest) {
		Payment payment = new Payment();
		payment.setUserId(paymentRequest.getUserId());
		payment.setName(paymentRequest.getName());
		payment.setEmail(paymentRequest.getEmail());
		payment.setPrice(paymentRequest.getPrice());
		payment.setPacketId(paymentRequest.getPacketId());
		return payment;
	}
	
	public List<PaymentResponse> convert(List<Payment> paymentList){
		List<PaymentResponse> paymentResponses = new ArrayList<>();
		paymentList.stream().forEach(payment -> paymentResponses.add(convert(payment)));
		return paymentResponses;
	}

}
