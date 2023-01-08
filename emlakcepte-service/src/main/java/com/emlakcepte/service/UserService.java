package com.emlakcepte.service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emlakcepte.configuration.RabbitMQUserConfiguration;
import com.emlakcepte.converter.UserConverter;
import com.emlakcepte.exception.User.UserEmailAlreadyInUseException;
import com.emlakcepte.exception.User.UserEmailNotValidException;
import com.emlakcepte.exception.User.UserInfoNotBlankException;
import com.emlakcepte.exception.User.UserNotFoundException;
import com.emlakcepte.exception.User.UserPasswordIncorrectException;
import com.emlakcepte.exception.User.UserPasswordNotValidException;
import com.emlakcepte.model.User;
import com.emlakcepte.repository.UserRepository;
import com.emlakcepte.request.UserDeleteRequest;
import com.emlakcepte.request.UserRequest;
import com.emlakcepte.request.UserUpdateRequest;
import com.emlakcepte.response.UserResponse;
import com.emlakcepte.validator.UserValidator;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserConverter converter;

	@Autowired
	private UserValidator validator;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private RabbitMQUserConfiguration rabbitMQUserConfiguration;

	Logger logger = Logger.getLogger(UserService.class.getName());

	/*
	 * First of all, it is checked by email whether the entered user is in the
	 * system. If the conditions of the password and e-mail entered by the user are
	 * met, the user is created.
	 * 
	 * Email, Name, Password and Cash field cannot be blank or empty
	 */
	public UserResponse createUser(UserRequest userRequest) {
		if (validator.checkName(userRequest) && validator.checkEmail(userRequest)
				&& validator.checkPassword(userRequest) && validator.checkCash(userRequest)) {
			Optional<User> optionalUser = getByEmail(userRequest.getEmail());

			if (optionalUser.isPresent()) {
				logger.log(Level.WARNING, "[UserService] -- User already has account by given email: "+
						userRequest.getEmail());
				throw new UserEmailAlreadyInUseException(
						"User already has account by given email: " + userRequest.getEmail());

			} else if (validator.isPasswordValid(userRequest.getPassword())) {

				if (validator.isEmailValid(userRequest.getEmail())) {
					User savedUser = userRepository.save(converter.convert(userRequest));

					logger.log(Level.INFO, "[UserService] -- user created by given information userId: "+ savedUser.getId());
					rabbitTemplate.convertAndSend(rabbitMQUserConfiguration.getQueueName(), userRequest);

					logger.log(Level.WARNING,
							"[EmlakCepte UserService] -- Sending Message to the Queue : {0}, sent to : {1}"+
							new Object[] { userRequest.toString(),
									rabbitMQUserConfiguration.getQueueName().toString() });

					return converter.convert(savedUser);
				}

				logger.log(Level.WARNING, "[UserService] -- Invalid email - email:  "+ userRequest.getEmail());
				throw new UserEmailNotValidException(
						"User email is not valid, it should be tihs format : user@example.com , yours: "
								+ userRequest.getEmail());

			}

			logger.log(Level.WARNING, "[UserService] -- User password is not valid: "+ userRequest.getPassword());
			throw new UserPasswordNotValidException(
					"User password is not valid,it should include this format: A-Z a-z /@- 1-9 your email: "
							+ userRequest.getEmail());
		}
		logger.log(Level.WARNING, "[UserService] -- There cant be blank empty fields, check fields: "+
				userRequest.getName());
		throw new UserInfoNotBlankException("There cant be blank empty fields, check fields: " + userRequest.getName());

	}

	// Returns users
	public List<UserResponse> getAll() {
		List<User> users = userRepository.findAll();
		logger.log(Level.INFO, "[UserService] -- Number of users: "+ users.size());
		return converter.convert(users);
	}

	// Updates user password
	public UserResponse updatePassword(UserUpdateRequest userUpdateRequest) {
		Optional<User> optionalUser = getByEmail(userUpdateRequest.getEmail());
		if (optionalUser.isPresent()) {
			if (validator.isPasswordValid(userUpdateRequest.getPassword())) {
				optionalUser.get().setPassword(userUpdateRequest.getPassword());
				logger.log(Level.INFO, "[UserService] --  User updated userId: "+ optionalUser.get().getId());
				return converter.convert(userRepository.save(optionalUser.get()));
			}
			logger.log(Level.WARNING,
					"[UserService] -- User password is not valid: " + userUpdateRequest.getPassword());
			throw new UserPasswordNotValidException("User password is not valid userId: " + optionalUser.get().getId());
		}
		logger.log(Level.WARNING, "[UserService] -- User not found with given email: " + userUpdateRequest.getEmail());
		throw new UserNotFoundException("User not found with given email: " + userUpdateRequest.getEmail());
	}

	// Deletes user
	public UserResponse deleteUser(UserDeleteRequest userDeleteRequest) {
		Optional<User> optionalUser = getByEmail(userDeleteRequest.getEmail());
		if (optionalUser.isPresent()) {
			if (userDeleteRequest.getPassword().equals(optionalUser.get().getPassword())) {
				userRepository.delete(optionalUser.get());
				logger.log(Level.INFO, "[UserService] -- User deleted userId:"+ optionalUser.get().getId());
				return converter.convert(optionalUser.get());
			}
			logger.log(Level.WARNING,
					"[UserService] -- User password is incorrect: " + userDeleteRequest.getPassword());
			throw new UserPasswordIncorrectException(
					"User password is incorrect userId: " + optionalUser.get().getId());
		}
		logger.log(Level.WARNING, "[UserService] -- User not found by given email: " + userDeleteRequest.getEmail());
		throw new UserNotFoundException("User not found by given email: " + userDeleteRequest.getEmail());
	}

	// Return suer by given email
	public UserResponse findByEmail(String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (optionalUser.isPresent()) {
			logger.log(Level.INFO, "[UserService] -- User found with email: "+ email);
			return converter.convert(optionalUser.get());
		}
		logger.log(Level.WARNING, "[UserService] -- User not found by given email: " + email);
		throw new UserNotFoundException("User not found by given email: " + email);

	}

	// Return suer by given name
	public UserResponse getByName(String name) {
		Optional<User> optionalUser = userRepository.findByName(name);
		if (optionalUser.isPresent()) {
			logger.log(Level.INFO, "[UserService] -- User found by given name: "+ name);
			return converter.convert(optionalUser.get());
		}
		logger.log(Level.WARNING, "[UserService] -- User not found by given name: " + name);
		throw new UserNotFoundException("User not found by given name: " + name);
	}

	// Returns user by given email
	public Optional<User> getByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	// Returns user by given userID
	public Optional<User> getById(Integer userId) {
		return userRepository.findById(userId);
	}

}
