package com.example.demo.telegramBotSettings;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component 
public class TelegramBot extends TelegramLongPollingBot {
	// Нужно добавить метод для проверки, существует ли объект в preferences и добавить проверку в updateMessage
	
	private final TelegramBotProperties  botConfig;
	private final Map<UserState, UserResponseHandler> handlers;

	public enum UserState {
		CHOOSE_GENRE,
		CHOOSE_DECADE,
		CHOOSE_REGION,
		CHOOSE_RATING,
		CHOOSE_TIME,
		READY
	}
	Map<String, UsersPreferences> preferences = new ConcurrentHashMap<>();
	

    public TelegramBot(TelegramBotProperties  botConfig, Map<UserState, UserResponseHandler> handlers) {
        this.botConfig = botConfig;
        this.handlers = handlers;

    }

    @Override
    public String getBotUsername() {
        return botConfig.getUsername();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			String chatId = update.getMessage().getChatId().toString();
			String receivedText = update.getMessage().getText();
			if (receivedText.equals("/start")) {
				sendMessage(chatId, "Название жанра");
				UsersPreferences user = new UsersPreferences(chatId, UserState.CHOOSE_GENRE.name());
				preferences.put(chatId, user);
			} else {
				UsersPreferences user = preferences.get(chatId);
		        UserState state = UserState.valueOf(user.getUserState());
		        UserResponseHandler handler = handlers.get(state);
		        handler.handle(chatId, receivedText, this);

					
				}
			}
	}

	
	public void sendMessage(String chatId, String text) {
		SendMessage message = new SendMessage();
		message.setChatId(chatId);
		message.setText(text);
		try {
			execute(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessageWithKeyboard(String chatId, String text, ReplyKeyboardMarkup keybord) {
		SendMessage message = new SendMessage();
	    message.setChatId(chatId);
	    ReplyKeyboardRemove keyboardRemove = new ReplyKeyboardRemove();
	    keyboardRemove.setRemoveKeyboard(true);
	    message.setReplyMarkup(keyboardRemove);
		message = SendMessage.builder().chatId(chatId.toString()).text(text).replyMarkup(keybord).build();

		try {
			execute(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
	public void removeKeyboard(String chatId, String text) {
	    SendMessage message = new SendMessage();
	    message.setChatId(chatId);
	    message.setText(text);
	    ReplyKeyboardRemove keyboardRemove = new ReplyKeyboardRemove();
	    keyboardRemove.setRemoveKeyboard(true);
	    message.setReplyMarkup(keyboardRemove);

	    try {
			execute(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
	public UsersPreferences getPreferences(String chatId) {
			return preferences.get(chatId);
	}
}
