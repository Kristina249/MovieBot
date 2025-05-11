package com.example.demo.handlers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.telegramBotSettings.Keyboards;
import com.example.demo.telegramBotSettings.TelegramBot;
import com.example.demo.telegramBotSettings.UserResponseHandler;

@Component
public class GenreHandler implements UserResponseHandler {

	@Override
	public void handle(String chatId, String message, TelegramBot bot) {
		String genreId = findGenreId(message);
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
	
	public String findGenreId(String value) {
		try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\krist\\eclipse-workspace\\MovieBot\\src\\main\\resources\\genres.csv"))) {
			String result = null;
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.isBlank()) continue;  
				 String[] parts = line.split(",", 2);
		            if (parts.length < 2) continue;            // если нет запятой — пропускаем
		            String genreName = parts[0].trim();        // название до запятой
		            String genreId   = parts[1].trim();        // всё после запятой
		            if (genreName.equalsIgnoreCase(value.trim())) {
		               result = genreId;
		            }
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
