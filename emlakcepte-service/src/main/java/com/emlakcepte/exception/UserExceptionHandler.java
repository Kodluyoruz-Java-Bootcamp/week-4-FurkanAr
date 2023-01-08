package com.emlakcepte.exception;

import java.time.LocalDateTime;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.emlakcepte.exception.User.UserEmailAlreadyInUseException;
import com.emlakcepte.exception.User.UserEmailNotValidException;
import com.emlakcepte.exception.User.UserInfoNotBlankException;
import com.emlakcepte.exception.User.UserNotFoundException;
import com.emlakcepte.exception.User.UserPasswordIncorrectException;
import com.emlakcepte.exception.User.UserPasswordNotValidException;

@ControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { UserNotFoundException.class })
	public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND,
				LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = { UserPasswordIncorrectException.class })
	public ResponseEntity<Object> handleUserPasswordIncorrectException(UserPasswordIncorrectException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST,
				LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(value = { UserPasswordNotValidException.class })
	public ResponseEntity<Object> handleUserPasswordNotValidException(UserPasswordNotValidException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST,
				LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { UserEmailNotValidException.class })
	public ResponseEntity<Object> handleUUserEmailNotValidException(UserEmailNotValidException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST,
				LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { UserEmailAlreadyInUseException.class })
	public ResponseEntity<Object> handleUserEmailAlreadyInUseException(UserEmailAlreadyInUseException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST,
				LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { UserInfoNotBlankException.class })
	public ResponseEntity<Object> handleUserInfoNotBlankException(UserInfoNotBlankException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST,
				LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
}
