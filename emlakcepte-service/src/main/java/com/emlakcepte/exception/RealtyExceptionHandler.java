package com.emlakcepte.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.emlakcepte.exception.realty.NoCityShowCaseException;
import com.emlakcepte.exception.realty.NoRealtyException;
import com.emlakcepte.exception.realty.RealtyAlreadyCreatedException;
import com.emlakcepte.exception.realty.RealtyNotFoundException;
import com.emlakcepte.exception.realty.RealtyRequesIsNullException;
import com.emlakcepte.exception.realty.RealtyRequestIsInvalidException;
import com.emlakcepte.exception.realty.UnknownRealtyStatusException;
import com.emlakcepte.exception.realty.UserAndRealtyDoesntMatchException;

@ControllerAdvice
public class RealtyExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { UnknownRealtyStatusException.class })
	public ResponseEntity<Object> handleUnknownRealtyStatusException(UnknownRealtyStatusException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.UNAUTHORIZED,
				LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(value = { RealtyNotFoundException.class })
	public ResponseEntity<Object> handleRealtyNotFoundException(RealtyNotFoundException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST,
				LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { UserAndRealtyDoesntMatchException.class })
	public ResponseEntity<Object> handleUserAndRealtyDoesntMatchException(UserAndRealtyDoesntMatchException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST,
				LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { NoCityShowCaseException.class })
	public ResponseEntity<Object> handleNoCityShowCaseException(NoCityShowCaseException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST,
				LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { NoRealtyException.class })
	public ResponseEntity<Object> handleNNoRealtyException(NoRealtyException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST,
				LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { RealtyAlreadyCreatedException.class })
	public ResponseEntity<Object> handleNRealtyAlreadyCreatedException(RealtyAlreadyCreatedException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST,
				LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { RealtyRequesIsNullException.class })
	public ResponseEntity<Object> handleRealtyRequesIsNullException(RealtyRequesIsNullException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.NO_CONTENT,
				LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.NO_CONTENT);
	}
	
	
	@ExceptionHandler(value = { RealtyRequestIsInvalidException.class })
	public ResponseEntity<Object> handleRealtyRequestIsInvalidException(RealtyRequestIsInvalidException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST,
				LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	

}
