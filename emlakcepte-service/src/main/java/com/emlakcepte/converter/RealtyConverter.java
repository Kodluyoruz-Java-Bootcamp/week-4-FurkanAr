package com.emlakcepte.converter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.emlakcepte.model.Realty;
import com.emlakcepte.model.enums.RealtyType;
import com.emlakcepte.request.RealtyRequest;
import com.emlakcepte.response.RealtyResponse;

@Component
public class RealtyConverter {

	public RealtyResponse convert(Realty realty) {
		RealtyResponse response = new RealtyResponse();
		response.setId(realty.getId());
		response.setNo(realty.getNo());
		response.setTitle(realty.getTitle());
		response.setCreateDate(realty.getCreateDate());
		response.setProvince(realty.getProvince());
		response.setDistrict(realty.getDistrict());
		response.setCategory(realty.getCategory());
		response.setStatus(realty.getStatus());
		response.setType(realty.getType());
		response.setUserId(realty.getUser().getId());
		response.setPacketId(realty.getPacket().getId());
		return response;
	}

	public Realty convert(RealtyRequest realtyRequest) {
		Realty realty = new Realty();
		realty.setNo(realtyRequest.getNo());
		realty.setCreateDate(LocalDateTime.now());
		realty.setStatus(RealtyType.IN_REVIEW);
		realty.setTitle(realtyRequest.getTitle());
		realty.setCategory(realtyRequest.getCategory());
		realty.setDistrict(realtyRequest.getDistrict());
		realty.setProvince(realtyRequest.getProvince());
		realty.setType(realtyRequest.getType());
		return realty;
	}

	public List<RealtyResponse> convert(List<Realty> realtyList) {
		List<RealtyResponse> realtyResponses = new ArrayList<>();
		realtyList.stream().forEach(realty -> realtyResponses.add(convert(realty)));
		return realtyResponses;
	}

}
