package com.emlakcepte.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class BannerExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(value = { BannerRequestNotValidException.class })
	public ResponseEntity<Object> handleBannerRequestNotValidException(BannerRequestNotValidException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST,
				LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
}
