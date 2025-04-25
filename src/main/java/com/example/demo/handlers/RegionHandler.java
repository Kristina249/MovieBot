package com.example.demo.handlers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.example.demo.telegramBotSettings.TelegramBot;
import com.example.demo.telegramBotSettings.UserResponseHandler;

@Component 
public class RegionHandler implements UserResponseHandler {

	@Override
	public void handle(String chatId, String message, TelegramBot bot) {
		String country;
		if (message.trim().equalsIgnoreCase("Не важно")) {
			country = "notImpotant"; 
		} else {
			country = requestForCountryCode(message);
		if (country == null) {
			bot.sendMessage(chatId, "Такого региона нет в списке");
			return;
		}
		}
		bot.getPreferences(chatId).setRegion(country);
		bot.getPreferences(chatId).setUserState("CHOOSE_RATING");
		bot.removeKeyboard(chatId, "Напишите минимальный рейтинг (от 1 до 10). Или напишите /skip, если это не важно");
	}
	
	public String requestForCountryCode(String value) {
		  try (BufferedReader reader = new BufferedReader(new FileReader("D:\\eclipse\\eclipse workspace\\MovieBot2\\src\\main\\resources\\countries.csv"))) {
			  String line;
			  String result = null;
			  while ((line = reader.readLine()) != null) {
				 String countryName = line.substring(0, line.indexOf(","));
				 if (countryName.equalsIgnoreCase(value)) {
					 result = line.substring(line.indexOf(",") + 2);
					 break;
				 }		 
			  }
			  return result;
		  } catch (IOException e) {
			  e.printStackTrace();
			  return null;
		  }
		}

}
