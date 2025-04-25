package com.example.demo.handlers;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.demo.telegramBotSettings.TelegramBot;
import com.example.demo.telegramBotSettings.UserResponseHandler;

@Component
public class TimeHandler implements UserResponseHandler {
	Map <String, String> minTime = Map.of("До 1 часа", "notImpotant", "От 1 до 1.5 часов", "60", "От 1.5 до 2 часов", 
			"90", "Более 2 часов", "120", "Не важно", "notImpotant");
	Map <String, String> maxTime = Map.of("До 1 часа", "60", "От 1 до 1.5 часов", "90", "От 1.5 до 2 часов", 
			"120", "Более 2 часов", "notImpotant", "Не важно", "notImpotant");
	@Override
	public void handle(String chatId, String message, TelegramBot bot) {
		if (!(minTime.containsKey(message.trim()))) {
			bot.sendMessage(chatId, "Выбери из списка ниже");
			return;
		}
		bot.getPreferences(chatId).setRuntimeMin(minTime.get(message));
		bot.getPreferences(chatId).setRuntimeMin(maxTime.get(message));
		bot.getPreferences(chatId).setUserState("READY");
		bot.removeKeyboard(chatId, "Ищем фильмы");
	}

}
