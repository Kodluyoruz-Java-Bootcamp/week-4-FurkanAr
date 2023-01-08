package com.emlakcepte.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.emlakcepte.exception.packet.PacketNotFoundException;
import com.emlakcepte.exception.packet.PacketRealtyListFullException;
import com.emlakcepte.exception.packet.PacketTimeExpiredException;
import com.emlakcepte.exception.packet.UserPacketDoesntMatchException;
import com.emlakcepte.exception.packet.UserPacketFoundException;
import com.emlakcepte.exception.packet.UserPacketNotFoundException;

@ControllerAdvice
public class PacketExceptionHandler  extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(value = { UserPacketNotFoundException.class })
	public ResponseEntity<Object> handleUserPacketNotFoundException(UserPacketNotFoundException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST,
				LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(value = { PacketNotFoundException.class })
	public ResponseEntity<Object> handlePacketNotFoundException(PacketNotFoundException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND,
				LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(value = { UserPacketDoesntMatchException.class })
	public ResponseEntity<Object> handleUserPacketDoesntMatchException(UserPacketDoesntMatchException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND,
				LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = { PacketTimeExpiredException.class })
	public ResponseEntity<Object> handlePacketTimeExpiredException(PacketTimeExpiredException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST,
				LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { PacketRealtyListFullException.class })
	public ResponseEntity<Object> handlePacketRealtyListFullException(PacketRealtyListFullException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST,
				LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { UserPacketFoundException.class })
	public ResponseEntity<Object> handleUserPacketFoundException(UserPacketFoundException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.FOUND,
				LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.FOUND);
	}
}
