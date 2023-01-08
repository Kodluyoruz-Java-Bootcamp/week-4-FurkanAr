package com.emlakcepte.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emlakcepte.model.User;

public interface  UserRepository  extends JpaRepository<User, Integer> {


	Optional<User>  findByName(String name);

	Optional<User> findByEmail(String email);



}
