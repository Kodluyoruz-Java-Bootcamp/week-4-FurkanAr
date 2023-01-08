package com.emlakcepte.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emlakcepte.model.Packet;
import com.emlakcepte.model.Realty;
import com.emlakcepte.model.enums.RealtyType;

public interface RealtyRepository extends JpaRepository<Realty, Integer> {

	Optional<List<Realty>> findAllByUserId(int id);
	
	Optional<List<Realty>> findAllByPacketId(Integer id);

	Optional<List<Realty>> findAllByStatus(RealtyType active);

	Integer countByProvince(String province);

	Optional<List<Realty>> findAllByProvince(String province);

	Optional<List<Realty>> findAllByProvinceAndDistrict(String province, String district);

	@Query(value = "SELECT COUNT(*) from realty WHERE province = :province AND type = 'HOUSING' AND category = 'SALE'", nativeQuery = true)
	Integer countByHousingOnSale(@Param("province") String province);

	Optional<Realty> findByNo(Integer no);

	Optional<List<Realty>> findByUserId(Integer userId);

	@Query(value = "SELECT * from realty WHERE user_id = :userId AND status = 'ACTIVE'", nativeQuery = true)
	Optional<List<Realty>> findAllByUserIdAndActiveRealty(@Param("userId") Integer userId);

	@Query(value = "SELECT * from realty WHERE user_id = :userId AND status = 'PASSIVE'", nativeQuery = true)
	Optional<List<Realty>> findAllByUserIdAndPassiveRealty(@Param("userId") Integer userId);


}
