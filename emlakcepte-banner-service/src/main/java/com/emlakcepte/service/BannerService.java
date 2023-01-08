package com.emlakcepte.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emlakcepte.converter.BannerConverter;
import com.emlakcepte.exception.BannerRequestNotValidException;
import com.emlakcepte.model.Banner;
import com.emlakcepte.repository.BannerRepository;
import com.emlakcepte.request.BannerRequest;
import com.emlakcepte.response.BannerResponse;

@Service
public class BannerService {

	@Autowired
	private BannerRepository bannerRepository;

	@Autowired
	private BannerConverter converter;
	
	Logger logger = Logger.getLogger(BannerService.class.getName());

	
	public BannerResponse create(BannerRequest bannerRequest) {
		if(bannerRequest.getBannerNo() != null) {
			Banner banner = converter.convert(bannerRequest);
			Banner savedBanner = bannerRepository.save(banner);
			logger.log(Level.INFO, "[BannerService] -- banner created bannerRequest: " + bannerRequest);
			BannerResponse bannerResponse = converter.convert(savedBanner);
			return bannerResponse;
		}

		logger.log(Level.WARNING, "[BannerService] -- Banner request is invalid: : " + bannerRequest);

		throw new BannerRequestNotValidException("Banner request is invalid: " + bannerRequest);
	}
	
	public List<BannerResponse> getAll() {
		List<Banner> banners = bannerRepository.findAll();
		logger.log(Level.INFO, "[BannerService] -- banner list: " + banners);
		return converter.convert(banners);
	}

}
