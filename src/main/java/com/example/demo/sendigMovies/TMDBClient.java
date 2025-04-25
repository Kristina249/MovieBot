package com.example.demo.sendigMovies;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
	
	public String requestForGenreId(String value) {
	    String genreId = null;

	    Map<String, String> genreMapping = new HashMap<>();

	    // 🔫 Боевик
	    genreMapping.put("экшен", "боевик");
	    genreMapping.put("action", "боевик");
	    genreMapping.put("стрелялки", "боевик");
	    genreMapping.put("драки", "боевик");
	    genreMapping.put("бой", "боевик");
	    genreMapping.put("битва", "боевик");

	    // 🧭 Приключения
	    genreMapping.put("adventure", "приключения");
	    genreMapping.put("приключение", "приключения");
	    genreMapping.put("авантюра", "приключения");

	    // 🎨 Мультфильм
	    genreMapping.put("анимация", "мультфильм");
	    genreMapping.put("cartoon", "мультфильм");
	    genreMapping.put("аниме", "мультфильм");

	    // 😂 Комедия
	    genreMapping.put("юмор", "комедия");
	    genreMapping.put("смешное", "комедия");
	    genreMapping.put("комедийный", "комедия");

	    // 🕵️ Криминал
	    genreMapping.put("преступление", "криминал");
	    genreMapping.put("преступный", "криминал");
	    genreMapping.put("crime", "криминал");

	    // 📚 Документальный
	    genreMapping.put("док", "документальный");
	    genreMapping.put("док. фильм", "документальный");
	    genreMapping.put("реальный", "документальный");
	    genreMapping.put("документалка", "документальный");

	    // 🎭 Драма
	    genreMapping.put("романтика", "драма");
	    genreMapping.put("романтический", "драма");
	    genreMapping.put("любовь", "драма");
	    genreMapping.put("психологическая", "драма");
	    genreMapping.put("эмоции", "драма");

	    // 👨‍👩‍👧‍👦 Семейный
	    genreMapping.put("семейное", "семейный");
	    genreMapping.put("для семьи", "семейный");
	    genreMapping.put("family", "семейный");

	    // 🧝 Фэнтези
	    genreMapping.put("магия", "фэнтези");
	    genreMapping.put("волшебство", "фэнтези");
	    genreMapping.put("сказка", "фэнтези");
	    genreMapping.put("fantasy", "фэнтези");

	    // 🏛️ История
	    genreMapping.put("исторический", "история");
	    genreMapping.put("реальные события", "история");
	    genreMapping.put("древность", "история");

	    // 👻 Ужасы
	    genreMapping.put("хоррор", "ужасы");
	    genreMapping.put("страшное", "ужасы");
	    genreMapping.put("пугающее", "ужасы");
	    genreMapping.put("страх", "ужасы");
	    genreMapping.put("мистика", "ужасы");

	    // 🎵 Музыка
	    genreMapping.put("мюзикл", "музыка");
	    genreMapping.put("песни", "музыка");
	    genreMapping.put("звук", "музыка");
	    genreMapping.put("муз", "музыка");

	    // 🔍 Детектив
	    genreMapping.put("расследование", "детектив");
	    genreMapping.put("тайна", "детектив");
	    genreMapping.put("детективный", "детектив");
	    genreMapping.put("mystery", "детектив");

	    // 💕 Мелодрама
	    genreMapping.put("ромком", "мелодрама");
	    genreMapping.put("слезливое", "мелодрама");
	    genreMapping.put("романтика+", "мелодрама");

	    // 🪐 Фантастика
	    genreMapping.put("sci-fi", "фантастика");
	    genreMapping.put("научная фантастика", "фантастика");
	    genreMapping.put("будущее", "фантастика");
	    genreMapping.put("роботы", "фантастика");
	    genreMapping.put("звезды", "фантастика");

	    // 📺 Телевизионный фильм
	    genreMapping.put("тв", "телевизионный фильм");
	    genreMapping.put("tv", "телевизионный фильм");
	    genreMapping.put("сериал", "телевизионный фильм");
	    genreMapping.put("эпизод", "телевизионный фильм");

	    // 🧨 Триллер
	    genreMapping.put("напряжение", "триллер");
	    genreMapping.put("psychological", "триллер");
	    genreMapping.put("психологический", "триллер");
	    genreMapping.put("интрига", "триллер");

	    // ⚔️ Военный
	    genreMapping.put("армия", "военный");
	    genreMapping.put("война", "военный");
	    genreMapping.put("military", "военный");

	    // 🤠 Вестерн
	    genreMapping.put("ковбои", "вестерн");
	    genreMapping.put("дикий запад", "вестерн");
	    genreMapping.put("шериф", "вестерн");
	    genreMapping.put("western", "вестерн");

	    try {
	        WebClient webClient = WebClient.builder()
	                .baseUrl("https://api.themoviedb.org/3")
	                .build();

	        String response = webClient.get()
	                .uri(uriBuilder -> uriBuilder
	                        .path("/genre/movie/list")
	                        .queryParam("api_key", API_KEY)
	                        .queryParam("language", "ru")
	                        .build())
	                .retrieve()
	                .bodyToMono(String.class)
	                .block();

	        ObjectMapper mapper = new ObjectMapper();
	        JsonNode genres = mapper.readTree(response).path("genres");
	        System.out.println(genres);

	        // Заменяем value, если есть аналог
	        value = genreMapping.getOrDefault(value.trim().toLowerCase(), value.trim().toLowerCase());

	        for (JsonNode genre : genres) {
	            if (genre.path("name").asText().equalsIgnoreCase(value)) {
	                genreId = genre.path("id").asText();
	                break;
	            }
	        }

	        return genreId;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	
	
}
