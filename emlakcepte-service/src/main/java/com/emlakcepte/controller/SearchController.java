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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emlakcepte.request.SearchRequest;
import com.emlakcepte.response.SearchResponse;
import com.emlakcepte.service.SearchService;

@RestController
@RequestMapping(value = "/searches")
public class SearchController {

	@Autowired
	private SearchService searchService;

	Logger logger = Logger.getLogger(SearchController.class.getName());

	// Returns all searches
	@GetMapping
	public ResponseEntity<List<SearchResponse>> getAll() {
		List<SearchResponse> searchResponses = searchService.getAll();
		logger.log(Level.INFO, "[SearchController] -- searches, number of searches: "+ searchResponses.size());
		return new ResponseEntity<>(searchResponses, HttpStatus.OK);

	}

	// Returns added search
	@PostMapping
	public ResponseEntity<SearchResponse> create(@RequestBody SearchRequest searchRequest) {
		SearchResponse searchResponse = searchService.create(searchRequest);
		logger.log(Level.INFO, "[SearchController] -- search created: "+ searchRequest);
		return new ResponseEntity<>(searchResponse, HttpStatus.CREATED);
	}

	// Deletes user searches
	@DeleteMapping(value = "/{userId}")
	public ResponseEntity<String> delete(@PathVariable Integer userId) {
		Integer id = searchService.deleteSearch(userId);
		logger.log(Level.INFO, "[SearchController] -- search deleted, userId: "+ userId);
		return new ResponseEntity<>("Message Deleted userId:" + id, HttpStatus.OK);
	}

	// Returns user searches
	@GetMapping(value = "/{userId}")
	public ResponseEntity<List<SearchResponse>> getByUserId(@PathVariable Integer userId) {
		List<SearchResponse> searchResponse = searchService.getAllById(userId);
		logger.log(Level.INFO, "[SearchController] -- user searches, userId: "+ userId);
		return new ResponseEntity<>(searchResponse, HttpStatus.OK);
	}

}
