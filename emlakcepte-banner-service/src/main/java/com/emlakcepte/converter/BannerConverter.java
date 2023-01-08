package com.emlakcepte.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.emlakcepte.model.Banner;
import com.emlakcepte.request.BannerRequest;
import com.emlakcepte.response.BannerResponse;

@Component
public class BannerConverter {
	
	public BannerResponse convert(Banner banner) {
		BannerResponse response = new BannerResponse();
		response.setId(banner.getId());
		response.setBannerNo(banner.getBannerNo());
		response.setPiece(banner.getPiece());
		response.setPhone1(banner.getPhone1());
		response.setPhone2(banner.getPhone2());
		response.setAddress(banner.getAddress());
		return response;
	}
	
	
	public Banner convert(BannerRequest bannerRequest) {
		Banner banner = new Banner();
		banner.setBannerNo(bannerRequest.getBannerNo());
		banner.setPiece(bannerRequest.getPiece());
		banner.setPhone1(bannerRequest.getPhone1());
		banner.setPhone2(bannerRequest.getPhone2());
		banner.setAddress(bannerRequest.getAddress());
		return banner;
	}
	
	public List<BannerResponse> convert(List<Banner> bannerList){
		List<BannerResponse> bannerResponses = new ArrayList<>();
		bannerList.stream().forEach(banner -> bannerResponses.add(convert(banner)));
		return bannerResponses;
	}

}
