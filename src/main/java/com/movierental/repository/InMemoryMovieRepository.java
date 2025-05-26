package com.movierental.repository;

import com.movierental.model.Movie;
import com.movierental.model.MovieCode;

import java.util.Map;
import java.util.Optional;

public class InMemoryMovieRepository implements MovieRepository {
    // create a single instance
    private static final InMemoryMovieRepository INSTANCE = new InMemoryMovieRepository();

    // private constructor to ensure only one instance exists
    private InMemoryMovieRepository() {}

    // get the single instance
    public static InMemoryMovieRepository getInstance() {
        return INSTANCE;
    }

    // create a map of example movies
    private static final Map<String, Movie> MOVIES = Map.of(
            "F001", new Movie("F001", "You've Got Mail", MovieCode.REGULAR),
            "F002", new Movie("F002","Matrix", MovieCode.REGULAR),
            "F003", new Movie("F003","Cars", MovieCode.CHILDRENS),
            "F004", new Movie("F004","Fast & Furious X", MovieCode.NEW_MOVIE)
    );

    @Override
    public Optional<Movie> findById(String movieId) {
        return Optional.ofNullable(MOVIES.get(movieId));
    }


}