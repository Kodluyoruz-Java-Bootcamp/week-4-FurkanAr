package com.emlakcepte.controller;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emlakcepte.client.model.PaymentResponse;
import com.emlakcepte.request.PacketRenewRequest;
import com.emlakcepte.request.PacketRequest;
import com.emlakcepte.response.PacketResponse;
import com.emlakcepte.service.PacketService;


@RestController
@RequestMapping(value = "/packets")
public class PacketController {
	
	@Autowired
	private PacketService packetService;
	
	Logger logger = Logger.getLogger(PacketController.class.getName());

	// Gives user a new packet and returns payment response
	@PostMapping
	public ResponseEntity<PaymentResponse> create(@RequestBody PacketRequest packetRequest) {
		PaymentResponse paymentResponse = packetService.create(packetRequest);
		logger.log(Level.INFO, "[PacketController] -- packet created payment: "+ paymentResponse);
		return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
	}
	
	// Returns all packets
	@GetMapping
	public ResponseEntity<List<PacketResponse>> getAll(){
		List<PacketResponse> packetResponses = packetService.getAll();
		logger.log(Level.INFO, "[PacketController] -- number of packets, size: "+ packetResponses.size());
		return new ResponseEntity<>(packetResponses, HttpStatus.OK);
	}
	
	// Renew the user packet, returns payment response
	@PostMapping(value = "/renew")
	public ResponseEntity<PaymentResponse> renew(@RequestBody PacketRenewRequest packetRenewRequest) {
		PaymentResponse paymentResponse = packetService.renew(packetRenewRequest);
		logger.log(Level.INFO, "[PacketController] -- renew the packet: "+ paymentResponse);

		return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
	}
	
	// Returns user's packets 
	@GetMapping(value = "/{userId}")
	public ResponseEntity<List<PacketResponse>> findByUser(@PathVariable  Integer userId){
		List<PacketResponse> packetResponses = packetService.findByUserId(userId);
		logger.log(Level.INFO, "[PacketController] -- user  packet: "+ packetResponses);
		return new ResponseEntity<>(packetResponses, HttpStatus.OK);
	}

	

}
