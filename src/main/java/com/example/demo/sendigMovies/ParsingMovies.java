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
			String title = (movieInfo.path("title").asText() != null) ? movieInfo.path("title").asText() : null;
			String overview = (movieInfo.path("overview").asText() != null) ? movieInfo.path("overview").asText() : null;
			String releaseDate = ((movieInfo.path("release_date").asText() != null) && !movieInfo.path("release_date").asText().isEmpty()) ? ParsingMovies.getReleaseDate(movieInfo.path("release_date").asText()) : null;
			String averageVote = (movieInfo.path("vote_average").asText() != null) ? movieInfo.path("vote_average").asText() : null;
			Movie movie = new Movie(title, overview, releaseDate, averageVote);
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
	/*
	 * [{"adult":false,"backdrop_path":"/ohfwSmfTAdYStqMEeZ4i05f9yPC.jpg",
	 * "genre_ids":[10770,35,10749,18],"id":939575,"original_language":"en",
	 * "original_title":"Love, Classified"
	 * ,"overview":"Автор романтических книг Эмилия возвращается домой после долгого отсутствия. Ей предстоит наладить отношения с детьми, которые ищут свои пути к обретению истинной любви."
	 * ,"popularity":2.2533,"poster_path":"/h6oA5yWqcCyZ0q5358059jz5f8b.jpg",
	 * "release_date":"2022-04-16","title":"Классифицированная любовь","video":false
	 * ,"vote_average":6.66,"vote_count":53}
	 */
	}
