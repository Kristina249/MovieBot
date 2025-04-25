package com.example.demo.handlers;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.demo.telegramBotSettings.Keyboards;
import com.example.demo.telegramBotSettings.TelegramBot;
import com.example.demo.telegramBotSettings.UserResponseHandler;

@Component 
public class DecadeHandler implements UserResponseHandler {
	Map <String, String> minYears = Map.of("До 1960 года", "notImpotant", "1960–1979", "1960", "1980–1999", "1980", "2000–2009", "2000", "2010–2019", "2010",
			"2020–настоящее время", "2020", "Не важно", "notImpotant");
	Map <String, String> maxYears = Map.of("До 1960 года", "1960", "1960–1979", "1979", "1980–1999", "1999", "2000–2009", "2009", "2010–2019", "2019",
			"2020–настоящее время", "notImpotant", "Не важно", "notImpotant");
	
	@Override
	public void handle(String chatId, String message, TelegramBot bot) {
		if (!(minYears.containsKey(message.trim()))) {
			bot.sendMessage(chatId, "Выбери из списка ниже");
			return;
		}
		bot.getPreferences(chatId).setMinYear(minYears.get(message));
		bot.getPreferences(chatId).setMaxYear(maxYears.get(message));
		bot.getPreferences(chatId).setUserState("CHOOSE_REGION");
		Keyboards keyboard = new Keyboards();
		List<String> values = List.of("Не важно", "США", "Россия", "Великобритания", "Германия", "Франция");
		bot.sendMessageWithKeyboard(chatId, "Страна производства (Можешь выбрать из списка или написать свою)", keyboard.setKeybord(values));
	}

}
