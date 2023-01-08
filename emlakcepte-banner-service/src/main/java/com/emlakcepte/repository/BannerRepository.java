package com.emlakcepte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emlakcepte.model.Banner;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Integer> {


}
