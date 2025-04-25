package com.example.demo.telegramBotSettings;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;


public class Keyboards {
	public ReplyKeyboardMarkup setKeybord(List<String> values) {
	    List<KeyboardButton> buttons = new ArrayList<>();
	    List<KeyboardRow> keyboard = new ArrayList<>();

	    for (String value : values) {
	        KeyboardButton button = new KeyboardButton(value);
	        buttons.add(button);
	    }

	    for (int i = 0; i < buttons.size(); i++) {
	        KeyboardRow row = new KeyboardRow();
	        row.add(buttons.get(i));
	            if (i + 1 < buttons.size()) {
	            row.add(buttons.get(i + 1));
	            i++; 
	        }

	        keyboard.add(row);
	    }

	    return ReplyKeyboardMarkup.builder()
	            .keyboard(keyboard)
	            .resizeKeyboard(true)
	            .oneTimeKeyboard(false)
	            .build();
	}

}
