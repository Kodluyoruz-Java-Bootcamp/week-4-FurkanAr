package com.emlakcepte.converter;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.emlakcepte.client.BannerServiceClient;
import com.emlakcepte.client.model.BannerRequest;
import com.emlakcepte.client.model.BannerResponse;
import com.emlakcepte.request.RealtyRequest;

@Component
public class BannerConverter {
	@Autowired
	private BannerServiceClient bannerServiceClient;

	Logger logger = Logger.getLogger(BannerConverter.class.getName());

	// When user creates a new realty, system gives a free banner
	public BannerResponse createBanner(RealtyRequest realtyRequest) {
		BannerRequest bannerRequest = new BannerRequest();
		bannerRequest.setBannerNo(String.valueOf(realtyRequest.getNo()));
		bannerRequest.setPiece(1);
		bannerRequest.setPhone1("123123123");
		bannerRequest.setPhone2("5251235");
		bannerRequest.setAddress(realtyRequest.getProvince());
		BannerResponse bannerResponse = bannerServiceClient.create(bannerRequest);
		logger.log(Level.INFO, "[RealtyService] -- bannerResponse: " + bannerResponse);
		return bannerResponse;
	}

}
