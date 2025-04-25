package com.example.demo.sendigMovies;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TMDBClient {
	final String API_KEY = "62972d61e70eda6830e96befdca61fff"; 
	final String BASE_URL = "https://api.themoviedb.org/3";
	public List<JsonNode> sendRequestForMovies(String genreId, String minYear, String maxYear, String region, 
			String minRating, String runtimeMin, String runtimeMax) {		
		int totalPages = 1;
		int currentPage = 1;
		List<JsonNode> movies = new ArrayList<>();

		
		WebClient webClient = WebClient.builder()
				.baseUrl(BASE_URL)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
		
		while (currentPage <= totalPages) {
			final int page = currentPage;
		String response = webClient.get()
				.uri(uriBuilder -> {
					uriBuilder.path("/discover/movie")
					.queryParam("api_key", API_KEY)
					.queryParam("language", "ru")
					.queryParam("with_genres", genreId)
					.queryParam("page", page);
					if (region != null) {
						uriBuilder.queryParam("with_origin_country", region);
					}
					if (!(minYear.equals("notImpotant"))) {
						uriBuilder.queryParam("primary_release_date.gte", minYear);
					}
					if (!(maxYear.equals("notImpotant"))) {
						uriBuilder.queryParam("primary_release_date.lte", maxYear);
					}
					if (!(minRating.equals("notImpotant"))) {
						uriBuilder.queryParam("vote_average.gte", minRating);
					}
					if (!(runtimeMin.equals("notImpotant"))) {
						uriBuilder.queryParam("with_runtime.gte", runtimeMin);
					}
					if (!(runtimeMax.equals("notImpotant"))) {
						uriBuilder.queryParam("with_runtime.lte", runtimeMax);
					}
		            
					 return uriBuilder.build();
				})
				.retrieve()
			    .bodyToMono(String.class)
			    .block();
		try {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode root = objectMapper.readTree(response);
		JsonNode resultsNode = root.path("results");
		for (JsonNode node: resultsNode) {
			movies.add(node);
		}
		totalPages = root.path("total_pages").asInt();
		currentPage++;
		} catch (Exception e) {
			e.printStackTrace();
		}			
		}
		return movies;

	}
	
}
