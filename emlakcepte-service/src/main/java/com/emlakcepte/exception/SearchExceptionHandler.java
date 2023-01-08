package com.emlakcepte.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.emlakcepte.exception.search.PlaceHolderCantNotBeBlankOrEmpty;

@ControllerAdvice
public class SearchExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { PlaceHolderCantNotBeBlankOrEmpty.class })
	public ResponseEntity<Object> handlePlaceHolderCantNotBeBlankOrEmpty(PlaceHolderCantNotBeBlankOrEmpty exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST,
				LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
}
