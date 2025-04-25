package com.example.demo.handlers;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.sendigMovies.TMDBClient;
import com.example.demo.telegramBotSettings.Keyboards;
import com.example.demo.telegramBotSettings.TelegramBot;
import com.example.demo.telegramBotSettings.UserResponseHandler;

@Component
public class GenreHandler implements UserResponseHandler {

	@Override
	public void handle(String chatId, String message, TelegramBot bot) {
		TMDBClient tmdbClient = new TMDBClient();
		String genreId = tmdbClient.requestForGenreId(message);
		if (genreId == null) {
			bot.sendMessage(chatId, "Такого жанра не нашлось");
			return;
		}
		bot.getPreferences(chatId).setGenre(genreId);
		bot.getPreferences(chatId).setUserState("CHOOSE_DECADE");
		Keyboards keyboard = new Keyboards();
		List<String> values = List.of("До 1960 года", "1960–1979", "1980–1999", "2000–2009", "2010–2019", "Не важно");
		bot.sendMessageWithKeyboard(chatId, "Десятилетие", keyboard.setKeybord(values));
	}
}
