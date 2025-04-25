package com.example.demo.telegramBotSettings;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.handlers.DecadeHandler;
import com.example.demo.handlers.GenreHandler;
import com.example.demo.handlers.RatingHandler;
import com.example.demo.handlers.RegionHandler;
import com.example.demo.handlers.TimeHandler;
import com.example.demo.telegramBotSettings.TelegramBot.UserState;

@Configuration
public class HandlerConfig {
	@Bean
    public Map<UserState, UserResponseHandler> handlers(
        GenreHandler genreHandler,
        DecadeHandler decadeHandler,
        RatingHandler ratingHandler,
        RegionHandler regionHandler,
        TimeHandler timeHandler
    ) {
        Map<UserState, UserResponseHandler> map = new EnumMap<>(UserState.class);
        map.put(UserState.CHOOSE_GENRE, genreHandler);
        map.put(UserState.CHOOSE_DECADE, decadeHandler);
        map.put(UserState.CHOOSE_RATING, ratingHandler);
        map.put(UserState.CHOOSE_REGION, regionHandler);
        map.put(UserState.CHOOSE_TIME, timeHandler);

        return map;
    }
}
