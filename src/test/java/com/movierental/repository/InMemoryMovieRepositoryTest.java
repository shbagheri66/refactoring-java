package com.movierental.repository;

import com.movierental.model.Movie;
import com.movierental.model.MovieCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class InMemoryMovieRepositoryTest {
    private static MovieRepository repo;

    @BeforeAll
    static void setup() {
        repo = InMemoryMovieRepository.getInstance();
    }

    @Test
    void findById_existingMovie_returnsMovie() {
        Optional<Movie> movie = repo.findById("F001");

        assertThat(movie).isPresent();

        assertThat(movie.get().getTitle()).isEqualTo("You've Got Mail");

        assertThat(movie.get().getCode()).isEqualTo(MovieCode.REGULAR);
    }

    @Test
    void findById_nonExistingMovie_returnsEmpty() {
        Optional<Movie> result = repo.findById("NOT_EXISTING_MOVIE");
        assertThat(result).isNotPresent();
    }
}
