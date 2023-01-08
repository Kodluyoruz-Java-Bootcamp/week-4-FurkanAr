package com.emlakcepte.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.emlakcepte.exception.User.UserInfoNotBlankException;
import com.emlakcepte.request.UserRequest;

@Component
public class UserValidator {
	// It is checked whether the entered email meets the conditions.
	public boolean isEmailValid(String email) {
		final Pattern EMAIL_REGEX = Pattern.compile(
				"[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
				Pattern.CASE_INSENSITIVE);
		if (email == null) {
			return false;
		}
		return EMAIL_REGEX.matcher(email).matches();
	}

	// It is checked whether the entered password meets the conditions.
	public boolean isPasswordValid(String password) {
		final Pattern STRING_REGEX = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,15})");
		if (password == null) {
			return false;
		}
		return STRING_REGEX.matcher(password).matches();
	}

	// Email cannot be blaknk or empty
	public boolean checkEmail(UserRequest userRequest) {
		if (userRequest.getEmail().isBlank() || userRequest.getEmail().isEmpty()) {
			throw new UserInfoNotBlankException("Email field cannot be blank: " + userRequest.getEmail());
		}
		return true;
	}

	// Name cannot be blaknk or empty
	public boolean checkName(UserRequest userRequest) {
		if (userRequest.getName().isBlank() || userRequest.getName().isEmpty()) {
			throw new UserInfoNotBlankException("Name field cannot be blank: " + userRequest.getName());
		}
		return true;
	}

	// Password cannot be blaknk or empty
	public boolean checkPassword(UserRequest userRequest) {
		if (userRequest.getPassword().isBlank() || userRequest.getPassword().isEmpty()) {
			throw new UserInfoNotBlankException("Password field cannot be blank: ");
		}
		return true;
	}

	// Cash cannot be blaknk or empty
	public boolean checkCash(UserRequest userRequest) {
		if (userRequest.getCash().equals(null) || userRequest.getCash() < 0) {
			throw new UserInfoNotBlankException("Cash field enter valid value: " + userRequest.getCash());
		}
		return true;
	}

}
