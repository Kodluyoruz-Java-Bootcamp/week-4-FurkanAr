package com.emlakcepte.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emlakcepte.request.RealtyRequest;
import com.emlakcepte.request.RealtyUpdateRequest;
import com.emlakcepte.response.RealtyResponse;
import com.emlakcepte.service.RealtyService;

@RestController
@RequestMapping(value = "/realtyes")
public class RealtyController {
	Logger logger = Logger.getLogger(RealtyController.class.getName());

	@Autowired
	private RealtyService realtyService;

	// Create new realty and return added realty
	@PostMapping
	public ResponseEntity<RealtyResponse> create(@RequestBody RealtyRequest realtyRequest) {
		RealtyResponse realtyResponse = realtyService.create(realtyRequest);
		logger.log(Level.INFO, "[RealtyController] -- realty created: "+ realtyResponse);
		return new ResponseEntity<>(realtyResponse, HttpStatus.CREATED);

	}

	// Returns all realtyes
	@GetMapping
	public ResponseEntity<List<RealtyResponse>> getAll() {
		List<RealtyResponse> realtyResponses = realtyService.getAll();
		logger.log(Level.INFO, "[RealtyController] -- realtyes size: "+ realtyResponses.size());
		return new ResponseEntity<>(realtyResponses, HttpStatus.OK);

	}

	// Returns user realtyes
	@GetMapping(value = "/users/{userId}")
	public ResponseEntity<List<RealtyResponse>> findByUserId(@PathVariable Integer userId) {
		List<RealtyResponse> realtyResponses = realtyService.findAllByUserId(userId);
		logger.log(Level.INFO, "[RealtyController] -- user realties: "+ realtyResponses);
		return new ResponseEntity<>(realtyResponses, HttpStatus.OK);

	}

	// Updates user realty status
	@PutMapping(value = "/users/{userId}/{realtyId}")
	public ResponseEntity<RealtyResponse> update(@PathVariable Integer userId, @PathVariable Integer realtyId,
			@RequestBody RealtyUpdateRequest realtyRequest) {
		RealtyResponse realtyResponse = realtyService.updateRealty(userId, realtyId, realtyRequest);
		logger.log(Level.INFO, "[RealtyController] -- user after realty status update: "+ realtyResponse);
		return new ResponseEntity<>(realtyResponse, HttpStatus.OK);

	}

	// Deletes user realty
	@DeleteMapping(value = "/users/{userId}/{realtyId}")
	public ResponseEntity<String> delete(@PathVariable Integer userId, @PathVariable Integer realtyId) {
		realtyService.deleteRealty(userId, realtyId);
		logger.log(Level.INFO, "[RealtyController] -- realty deleted, realtyId:"+ realtyId);
		return new ResponseEntity<>("Realty deleted realtyId: "+ realtyId, HttpStatus.OK);

	}

	// Returns user active realtyes
	@GetMapping(value = "/users/{userId}/active")
	public ResponseEntity<List<RealtyResponse>> findUserActiveRealty(@PathVariable Integer userId) {
		List<RealtyResponse> realtyResponses = realtyService.findUserActiveRealty(userId);
		logger.log(Level.INFO, "[RealtyController] -- user realties: "+ realtyResponses);
		return new ResponseEntity<>(realtyResponses, HttpStatus.OK);

	}
	

	// Returns user passive realtyes
	@GetMapping(value = "/users/{userId}/passive")
	public ResponseEntity<List<RealtyResponse>> findUserPassiveRealty(@PathVariable Integer userId) {
		List<RealtyResponse> realtyResponses = realtyService.findUserPassiveRealty(userId);
		logger.log(Level.INFO, "[RealtyController] -- user realties: "+ realtyResponses);
		return new ResponseEntity<>(realtyResponses, HttpStatus.OK);

	}
	

	// Returns the number of realtyes in the city
	@GetMapping(value = "/{province}")
	public ResponseEntity<Integer> getNumberOfProvinceRealty(@PathVariable String province) {
		Integer number = realtyService.getNumberOfProvinceRealty(province);
		logger.log(Level.INFO, "[RealtyController] -- number of realty by province: "+ number);
		return new ResponseEntity<>(number, HttpStatus.OK);
	}

	// Returns realtyes in the city
	@GetMapping(value = "/cities/{province}")
	public ResponseEntity<List<RealtyResponse>> getAllProvinceRealty(@PathVariable String province) {
		List<RealtyResponse> realtyResponses = realtyService.getAllProvinceRealty(province);
		logger.log(Level.INFO, "[RealtyController] -- realtyes by province: "+ realtyResponses.size());
		return new ResponseEntity<>(realtyResponses, HttpStatus.OK);

	}

	// Returns realtyes in cities and district
	@GetMapping(value = "/{province}/{district}")
	public ResponseEntity<List<RealtyResponse>> findByProvinceAndDistrict(@PathVariable String province,
			@PathVariable String district) {
		List<RealtyResponse> realtyResponses = realtyService.getAllByProvinceAndDistrict(province, district);
		logger.log(Level.INFO, "[RealtyController] -- realtyes by province and district: "+ realtyResponses.size());
		return new ResponseEntity<>(realtyResponses, HttpStatus.OK);

	}

	// Returns the number of housing for sale in the city
	@GetMapping(value = "/housings/{province}")
	public ResponseEntity<Integer> findNumberOfHousingOnSale(@PathVariable String province) {
		Integer number = realtyService.getNumberOfHousingOnSale(province);
		logger.log(Level.INFO, "[RealtyController] -- number of realty by province: "+ number);
		return new ResponseEntity<>(number, HttpStatus.OK);
	}

	// Returns city showcase
	@GetMapping(value = "/showcases/{province}")
	public ResponseEntity<List<RealtyResponse>> getCityShowCase(@PathVariable String province) {
		List<RealtyResponse> realtyResponses = realtyService.getCityShowCase(province);
		logger.log(Level.INFO, "[RealtyController] -- city showcase: "+ province);
		return new ResponseEntity<>(realtyResponses, HttpStatus.OK);

	}

}
