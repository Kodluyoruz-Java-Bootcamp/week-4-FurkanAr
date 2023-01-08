package com.emlakcepte.service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emlakcepte.converter.SearchConverter;
import com.emlakcepte.exception.User.UserNotFoundException;
import com.emlakcepte.exception.search.PlaceHolderCantNotBeBlankOrEmpty;
import com.emlakcepte.model.Search;
import com.emlakcepte.model.User;
import com.emlakcepte.repository.SearchRepository;
import com.emlakcepte.request.SearchRequest;
import com.emlakcepte.response.SearchResponse;

@Service
public class SearchService {

	@Autowired
	private SearchRepository searchRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private SearchConverter searchConverter;

	Logger logger = Logger.getLogger(SearchService.class.getName());

	// Checks if the searcher is in the system,
	// saves if the search object satisfies the conditions
	public SearchResponse create(SearchRequest searchRequest) {
		Optional<User> optionalUser = userService.getById(searchRequest.getUserId());
		if (optionalUser.isPresent()) {

			if (!searchRequest.getPlaceHolder().isBlank() || searchRequest.getPlaceHolder().isEmpty()) {
				Search search = searchConverter.convert(searchRequest);
				search.setUser(optionalUser.get());
				Search savedSearch = searchRepository.save(search);
				logger.log(Level.WARNING, "[SearchService] -- search created: "+ searchRequest);

				return searchConverter.convert(savedSearch);
			}
			logger.log(Level.WARNING, "[SearchService] -- placeholder cant be blank or empty request: "+ searchRequest);
			throw new PlaceHolderCantNotBeBlankOrEmpty("placeholder cant be blank or empty request: " + searchRequest);

		}
		logger.log(Level.WARNING, "[SearchService] -- User not found with given id: "+ searchRequest.getUserId());
		throw new UserNotFoundException("User not found with given id: " + searchRequest.getUserId());
	}

	// Returns all searches
	public List<SearchResponse> getAll() {
		List<Search> searches = searchRepository.findAll();
		logger.log(Level.INFO, "[SearchService] -- searches, number of searches: "+ searches.size());
		return searchConverter.convert(searches);
	}

	// Deletes user searches
	public Integer deleteSearch(Integer userId) {
		Optional<User> optionalUser = userService.getById(userId);
		if (optionalUser.isPresent()) {
			logger.log(Level.INFO, "[SearchService] -- user searches deleted userId: "+ userId);
			searchRepository.deleteByUserId(userId);
			return userId;
		}
		logger.log(Level.WARNING, "[SearchService] -- User not found with given id: "+ userId);
		throw new UserNotFoundException("User not found with given id: " + userId);
	}

	// Returns user searches
	public List<SearchResponse> getAllById(Integer userId) {
		Optional<User> optionalUser = userService.getById(userId);
		if (optionalUser.isPresent()) {
			logger.log(Level.INFO, "[SearchService] -- user searches, userId: "+ userId);
			return searchConverter.convert(searchRepository.findAllByUserId(userId));
		}
		logger.log(Level.WARNING, "[SearchService] -- User not found with given id: "+ userId);
		throw new UserNotFoundException("User not found with given id: " + userId);
	}

}
