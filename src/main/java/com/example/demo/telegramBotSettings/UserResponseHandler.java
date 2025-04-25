package com.example.demo.telegramBotSettings;

import org.springframework.stereotype.Component;

@Component
public interface UserResponseHandler {
    void handle(String chatId, String message, TelegramBot bot);
}

