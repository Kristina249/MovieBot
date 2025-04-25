package com.example.demo.sendigMovies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.databind.JsonNode;

public class Randomizer {
	public List<Integer> randomizeNumberOfMovies(List<JsonNode> movies) {
		List<Integer> numbers = new ArrayList<>();
		Random random = new Random();
		while(numbers.size() < 5) {
			int number = random.nextInt(movies.size());
			if (!numbers.contains(number)) {
				numbers.add(number);
			}
		}
		
		return numbers;
	}
	
	
	public List<JsonNode> getRundomizedMovies(List<JsonNode> jsonMovies, List<Integer> requiredMovies) {
		List<JsonNode> movies = new ArrayList<>();
		for (int number: requiredMovies) {
			movies.add(jsonMovies.get(number));
		}
		return movies;
	}
	
	
}
