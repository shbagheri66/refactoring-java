package com.movierental.repository;

import com.movierental.model.Movie;
import java.util.Optional;

public interface MovieRepository {
    Optional<Movie> findById(String movieId);
}