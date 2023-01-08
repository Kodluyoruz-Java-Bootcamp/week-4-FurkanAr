package com.emlakcepte.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emlakcepte.configuration.EmlakCeptePaymentConfiguration;
import com.emlakcepte.converter.PaymentConverter;
import com.emlakcepte.exception.PaymentResponseIsNullException;
import com.emlakcepte.model.Payment;
import com.emlakcepte.repository.PaymentRepository;
import com.emlakcepte.request.PaymentRequest;
import com.emlakcepte.response.PaymentResponse;

@Service
public class PaymentService {
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private PaymentConverter converter;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private EmlakCeptePaymentConfiguration emlakCeptePaymentConfiguration;
	
	Logger logger = Logger.getLogger(PaymentService.class.getName());
	
	
	public PaymentResponse create(PaymentRequest paymentRequest) {
		if (paymentRequest.getCash() >= paymentRequest.getPrice()) {
			Payment payment = converter.convert(paymentRequest);
			Payment savedPayment = paymentRepository.save(payment);
			PaymentResponse paymentResponse = converter.convert(savedPayment);
			rabbitTemplate.convertAndSend(emlakCeptePaymentConfiguration.getQueueName(), paymentResponse);
			logger.log(Level.INFO, "[PaymentService create] payment accepted userId: " + paymentRequest.getUserId());
			return paymentResponse;
		}
		logger.log(Level.WARNING, "[PaymentService create] payment refused cash must be mininmum 50, userId: " + paymentRequest.getUserId());
		throw new PaymentResponseIsNullException("Payment refused cash must be mininmum 50, userId: " + paymentRequest.getUserId());
	}

	public List<PaymentResponse> getAll() {
		List<Payment> payments = paymentRepository.findAll();
		logger.log(Level.INFO, "[PaymentService] payment list: " + payments);
		return converter.convert(payments); 
	}

}
