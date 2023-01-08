package com.emlakcepte.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emlakcepte.request.PaymentRequest;
import com.emlakcepte.response.PaymentResponse;
import com.emlakcepte.service.PaymentService;

@RestController
@RequestMapping(value = "/payments")
public class PaymentController {
	Logger logger = Logger.getLogger(PaymentController.class.getName());

	@Autowired
	private PaymentService paymentService;

	@PostMapping
	public ResponseEntity<PaymentResponse> create(@RequestBody PaymentRequest paymentRequest) {
		PaymentResponse paymentResponse = paymentService.create(paymentRequest);
		logger.log(Level.INFO, "[PaymentController] -- payment created: " + paymentResponse);
		return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<PaymentResponse>> getAll() {
		List<PaymentResponse> paymentResponses = paymentService.getAll();
		logger.log(Level.INFO, "[PaymentController] -- payments: " + paymentResponses);
		return new ResponseEntity<>(paymentResponses, HttpStatus.OK);
	}

}
