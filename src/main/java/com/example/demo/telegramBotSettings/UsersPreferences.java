package com.example.demo.telegramBotSettings;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

public class UsersPreferences {
	String telegramId;
	String userState;
	String genre;
	String minYear;
	String maxYear;
	String region;
	String minRating;
	String runtimeMin;
	String runtimeMax;
	List<JsonNode> movies;
	int page = 1;
	
	public UsersPreferences(String telegramId, String userState) {
		this.telegramId = telegramId;
		this.userState = userState;
	}
	
	 public String getTelegramId() {
	        return telegramId;
	    }

	    public void setTelegramId(String telegramId) {
	        this.telegramId = telegramId;
	    }

	    public String getGenre() {
	        return genre;
	    }

	    public void setGenre(String genre) {
	        this.genre = genre;
	    }

	    public String getMinYear() {
	        return minYear;
	    }

	    public void setMinYear(String minYear) {
	        this.minYear = minYear;
	    }

	    public String getMaxYear() {
	        return maxYear;
	    }

	    public void setMaxYear(String maxYear) {
	        this.maxYear = maxYear;
	    }

	    public String getRegion() {
	        return region;
	    }

	    public void setRegion(String region) {
	        this.region = region;
	    }

	    public String getMinRating() {
	        return minRating;
	    }

	    public void setMinRating(String minRating) {
	        this.minRating = minRating;
	    }

	    public String getRuntimeMin() {
	        return runtimeMin;
	    }

	    public void setRuntimeMin(String runtimeMin) {
	        this.runtimeMin = runtimeMin;
	    }

	    public String getRuntimeMax() {
	        return runtimeMax;
	    }

	    public void setRuntimeMax(String runtimeMax) {
	        this.runtimeMax = runtimeMax;
	    }
	    public String getUserState() {
	        return userState;
	    }

	    public void setUserState(String userState) {
	        this.userState = userState;
	    }
	    public List<JsonNode> getMovies() {
	    	return movies;
	    }
	    public void setMovies(List<JsonNode> movies) {
	    	this.movies = movies;
	    }
	    public int getPage() {
	    	return page;
	    }
	    public void setPage(int page) {
	    	this.page = page;
	    }
}
