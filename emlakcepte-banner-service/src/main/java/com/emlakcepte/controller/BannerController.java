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

import com.emlakcepte.request.BannerRequest;
import com.emlakcepte.response.BannerResponse;
import com.emlakcepte.service.BannerService;

@RestController
@RequestMapping(value = "/banners")
public class BannerController {
	
	Logger logger = Logger.getLogger(BannerController.class.getName());


	@Autowired
	private BannerService bannerService;
	
	// Create new banner and return it
	// FeignClient
	@PostMapping
	public ResponseEntity<BannerResponse> create(@RequestBody BannerRequest bannerRequest) {
		BannerResponse bannerResponse = bannerService.create(bannerRequest);
		logger.log(Level.INFO, "[BannerController] -- banner created: "+ bannerResponse);
		return new ResponseEntity<>(bannerResponse, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<BannerResponse>> getAll() {
		List<BannerResponse> bannerResponses = bannerService.getAll();
		logger.log(Level.INFO, "[BannerController] -- banners: "+ bannerResponses);
		return new ResponseEntity<>(bannerResponses, HttpStatus.OK);
	}
}
