package com.emlakcepte.repository;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emlakcepte.model.Search;

@Repository
public interface SearchRepository extends JpaRepository<Search, Integer>  {
	
	List<Search> findAllByUserId(int id);
	@Transactional 
	void deleteByUserId(int id);
	
}
