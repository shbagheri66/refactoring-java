package com.movierental.model;

public class Movie {
    private final String movieId;
    private final String title;
    private final MovieCode code;

    public Movie(String movieId, String title, MovieCode code) {
        this.movieId = movieId;
        this.title = title;
        this.code = code;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public MovieCode getCode() {
        return code;
    }
}
