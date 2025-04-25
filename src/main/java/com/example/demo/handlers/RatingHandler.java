package com.example.demo.handlers;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.telegramBotSettings.Keyboards;
import com.example.demo.telegramBotSettings.TelegramBot;
import com.example.demo.telegramBotSettings.UserResponseHandler;

@Component
public class RatingHandler implements UserResponseHandler {

	@Override
	public void handle(String chatId, String message, TelegramBot bot) {
		String rating = message.trim();
		if (message.trim().equalsIgnoreCase("/skip")) {
			rating = "notImpotant";
		} else if (!(0 <= Integer.parseInt(rating) || !(Integer.parseInt(rating) >= 10))) {
			bot.sendMessage(chatId, "Напишите в правильном формате");
			return;
		}
		bot.getPreferences(chatId).setMinRating(rating);
		bot.getPreferences(chatId).setUserState("CHOOSE_TIME");
		Keyboards keyboard = new Keyboards();
		List<String> values = List.of("До 1 часа", "От 1 до 1.5 часов", "От 1.5 до 2 часов", "Более 2 часов", "Не важно");
		bot.sendMessageWithKeyboard(chatId,"Выбери время, которое будет длиться фильм, из списка ниже", keyboard.setKeybord(values));
	}

}
