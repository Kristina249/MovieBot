package com.example.demo.sendigMovies;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ParsingMovies {
	public List<Movie> parseMovies(List<JsonNode> jsonMovies) {
		List<Movie> movies = new ArrayList<>();
		try {
		for (JsonNode jsonMovie: jsonMovies) {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode movieInfo = mapper.readTree(jsonMovie.asText());	
			Movie movie = new Movie(movieInfo.path("title").asText(),
					movieInfo.path("overview").asText(), ParsingMovies.getReleaseDate(movieInfo.path("release_date").asText()), 
					movieInfo.path("vote_average").asText());
			movies.add(movie);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return movies;
	}
	
	static String getReleaseDate(String value) {
		return value.substring(0, 4);
	}
}
