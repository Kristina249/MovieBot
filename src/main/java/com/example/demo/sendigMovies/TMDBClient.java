package com.example.demo.sendigMovies;


import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TMDBClient {
	public static void main(String args[]) {
		TMDBClient t = new TMDBClient();
		System.out.println(t.sendRequestForMovies("12", "2000", "2009", "US", "7", "90", "120", 1));
	}
	final String API_KEY = "62972d61e70eda6830e96befdca61fff"; 
	final String BASE_URL = "https://api.themoviedb.org/3";
	public List<JsonNode> sendRequestForMovies(String genreId, String minYear, String maxYear, String region, 
			String minRating, String runtimeMin, String runtimeMax, int page) {	
		List<JsonNode> movies = new ArrayList<>();	
		WebClient webClient = WebClient.builder()
				.baseUrl(BASE_URL)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
		
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
						uriBuilder.queryParam("release_date.gte", minYear + "-01-01");
					}
					if (!(runtimeMax.equals("notImpotant"))) {
						uriBuilder.queryParam("release_date.lte", maxYear + "-12-31");
					}
		            
					 return uriBuilder.build();
				})
				.retrieve()
			    .bodyToMono(String.class)
			    .block();
		if (response.isEmpty()) {
			return null;
		}
		try {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode root = objectMapper.readTree(response);
		JsonNode resultsNode = root.path("results");
		for (JsonNode node: resultsNode) {
			JsonNode genreIdsNode = node.path("genre_ids");
			List<Integer> genreIds = new ArrayList<>();
			for (JsonNode id: genreIdsNode) {
				genreIds.add(id.asInt());
			}
			String nodeReleaseDate = node.path("release_date").asText();
			int date;
			if (nodeReleaseDate.length() >= 4) {
				date = Integer.parseInt(nodeReleaseDate.substring(0, 4));
			} else {
				date = 0;
			}
			if ((genreIds.get(0) == Integer.parseInt(genreId)) && (!(date == 0)) 
					&& (date > Integer.parseInt(minYear)) && (date < Integer.parseInt(maxYear))) {
			movies.add(node);
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}			
		
		return movies;

	}
	
}
