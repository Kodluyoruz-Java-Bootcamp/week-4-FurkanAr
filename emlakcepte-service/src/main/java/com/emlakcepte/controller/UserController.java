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

import com.emlakcepte.request.UserDeleteRequest;
import com.emlakcepte.request.UserRequest;
import com.emlakcepte.request.UserUpdateRequest;
import com.emlakcepte.response.UserResponse;
import com.emlakcepte.service.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	Logger logger = Logger.getLogger(UserController.class.getName());

	@Autowired
	private UserService userService;

	// Returns all users
	@GetMapping
	public ResponseEntity<List<UserResponse>> getAll() {
		List<UserResponse> users = userService.getAll();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	// Creates a new user
	@PostMapping
	public ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest) {
		logger.log(Level.INFO, "[UserController] -- user created: "+ userRequest);
		return new ResponseEntity<>(userService.createUser(userRequest), HttpStatus.CREATED);
	}

	// Updates user password
	@PutMapping
	public ResponseEntity<UserResponse> updatePassword(@RequestBody UserUpdateRequest userUpdateRequest) {
		UserResponse userResponse = userService.updatePassword(userUpdateRequest);
		logger.log(Level.INFO, "[UserController] -- user password updated: "+ userUpdateRequest);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}

	// Deletes an user
	@DeleteMapping
	public ResponseEntity<UserResponse> deleteUser(@RequestBody UserDeleteRequest userDeleteRequest) {
		UserResponse userResponse = userService.deleteUser(userDeleteRequest);
		logger.log(Level.INFO, "[UserController] -- user deleted: "+ userDeleteRequest);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}

	// Returns user by email
	@GetMapping(value = "/email/{email}")
	public ResponseEntity<UserResponse> getByEmail(@PathVariable String email) {
		UserResponse userResponse = userService.findByEmail(email);
		logger.log(Level.INFO, "[UserController] -- user found by given email: "+ email);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}

	// Returns user by name
	@GetMapping(value = "/name/{name}")
	public ResponseEntity<UserResponse> getByName(@PathVariable String name) {
		UserResponse userResponse = userService.getByName(name);
		logger.log(Level.INFO, "[UserController] -- user found by given name: "+ name);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}

}
