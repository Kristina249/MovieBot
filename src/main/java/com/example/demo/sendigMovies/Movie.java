package com.example.demo.sendigMovies;

public class Movie {
	String name;
	String description;
	String releaseDate;
	String rating;
	
	public Movie(String name, String description, String releaseDate, String rating) {
		this.name = name;
		this.description = description;
		this.releaseDate = releaseDate;
		this.rating = rating;
	}
	
	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}

	public String getDescription() {
	    return description;
	}

	public void setDescription(String description) {
	    this.description = description;
	}

	public String getReleaseDate() {
	    return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
	    this.releaseDate = releaseDate;
	}

	public String getRating() {
	    return rating;
	}

	public void setRating(String rating) {
	    this.rating = rating;
	}
	public String getFormattedMovieInfo() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("*").append(name).append("*\n\n");
	    
	    if (description != null && !description.isEmpty()) {
	        sb.append("*Описание:* ").append(description).append("\n\n");
	    }
	    
	    if (releaseDate != null && !releaseDate.isEmpty()) {
	        sb.append("*Дата выхода:* ").append(releaseDate).append("\n");
	    }

	    if (rating != null && !rating.isEmpty()) {
	        sb.append("*Рейтинг:* ").append(rating).append(" / 10").append("\n");
	    }

	    return sb.toString();
	}

}
