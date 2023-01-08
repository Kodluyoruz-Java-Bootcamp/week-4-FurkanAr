package com.emlakcepte.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.emlakcepte.client.model.PaymentRequest;
import com.emlakcepte.client.model.PaymentResponse;

@FeignClient(value = "emlakcepte-payment", url = "http://localhost:8082")
public interface PaymentServiceClient {
	
	@PostMapping(value = "/payments")
	PaymentResponse create(@RequestBody PaymentRequest paymentRequest);

}
