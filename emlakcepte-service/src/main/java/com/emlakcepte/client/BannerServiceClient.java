package com.emlakcepte.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.emlakcepte.client.model.BannerRequest;
import com.emlakcepte.client.model.BannerResponse;


@FeignClient(value = "emlakcepte-banner", url = "http://localhost:8081")
public interface BannerServiceClient {

	@PostMapping(value = "/banners")
	BannerResponse create(@RequestBody BannerRequest bannerRequest);
	
	@GetMapping(value = "/banners")
	List<BannerResponse> getAll();



}
	
