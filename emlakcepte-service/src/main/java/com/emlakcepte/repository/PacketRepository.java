package com.emlakcepte.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emlakcepte.model.Packet;
import com.emlakcepte.model.enums.PacketStatus;

@Repository
public interface PacketRepository extends JpaRepository<Packet, Integer> {


	Optional<Packet> findByUserId(Integer userId);

	List<Packet> findAllByUserId(Integer userId);

	List<Packet> findAllByStatus(PacketStatus active);


}
