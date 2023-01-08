package com.emlakcepte.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.emlakcepte.model.Search;
import com.emlakcepte.request.SearchRequest;
import com.emlakcepte.response.SearchResponse;

@Component
public class SearchConverter {

	public SearchResponse convert(Search search) {
		SearchResponse searchResponse = new SearchResponse();
		searchResponse.setPlaceHolder(search.getPlaceHolder());
		searchResponse.setUserId(search.getUser().getId());
		return searchResponse;
	}

	public Search convert(SearchRequest searchRequest) {
		Search search = new Search();
		search.setPlaceHolder(searchRequest.getPlaceHolder());
		return search;
	}

	public List<SearchResponse> convert(List<Search> searchList) {
		List<SearchResponse> searchResponses = new ArrayList<>();
		searchList.stream().forEach(search -> searchResponses.add(convert(search)));
		return searchResponses;
	}

}
