package com.emlakcepte.exception.User;

public class UserEmailAlreadyInUseException extends RuntimeException {

	public UserEmailAlreadyInUseException(String message) {
		super(message);
	}
	
}