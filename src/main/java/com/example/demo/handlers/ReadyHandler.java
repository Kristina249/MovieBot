package com.example.demo.handlers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.sendigMovies.Movie;
import com.example.demo.sendigMovies.ParsingMovies;
import com.example.demo.sendigMovies.Randomizer;
import com.example.demo.sendigMovies.TMDBClient;
import com.example.demo.telegramBotSettings.TelegramBot;
import com.example.demo.telegramBotSettings.UserResponseHandler;
import com.example.demo.telegramBotSettings.UsersPreferences;
import com.fasterxml.jackson.databind.JsonNode;

@Component
public class ReadyHandler implements UserResponseHandler {

	@Override
	public void handle(String chatId, String message, TelegramBot bot) {
		TMDBClient tmdbClient = new TMDBClient();
		UsersPreferences user = bot.getPreferences(chatId);
		List<JsonNode> foundMovies = tmdbClient.sendRequestForMovies(user.getGenre(), user.getMinYear(), user.getMaxYear(), 
				user.getRegion(), user.getMinRating(), user.getRuntimeMin(), user.getRuntimeMax());
		Randomizer randomizer = new Randomizer();
		ParsingMovies parsingMovies = new ParsingMovies();
		List<Integer> numbersOfMovies = randomizer.randomizeNumberOfMovies(foundMovies);
		List<JsonNode> choosenMovies = randomizer.getRundomizedMovies(foundMovies, numbersOfMovies);
		List<Movie> movies = parsingMovies.parseMovies(choosenMovies);
		StringBuilder str = new StringBuilder();
		for (Movie movie: movies) {
			str.append(movie).append("\n");
		}
		str.append("Нажмите /more, чтобы показались другие фильмы");
		bot.sendMessage(chatId, str.toString());
		List<JsonNode> remainingMovies = new ArrayList<>();
		for (JsonNode movie: foundMovies) {
			if (!(choosenMovies.contains(movie))) {
				remainingMovies.add(movie);
			}
		}
		bot.getPreferences(chatId).setMovies(remainingMovies);
	}

}
