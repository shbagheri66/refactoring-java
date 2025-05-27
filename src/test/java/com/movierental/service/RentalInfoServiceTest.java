package com.movierental.service;

import com.movierental.exception.NotFoundException;
import com.movierental.model.Customer;
import com.movierental.model.MovieRental;
import com.movierental.model.RentalHistory;
import com.movierental.repository.InMemoryMovieRepository;
import com.movierental.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for RentalInfoService in Given-When-Then format.
 */
class RentalInfoServiceTest {
    private RentalInfoService service;
    private static final Customer customer = new Customer("Shahab");
    @BeforeEach
    void setUp() {
        MovieRepository repo = InMemoryMovieRepository.getInstance();
        service = new RentalInfoService(repo);
    }

    @Test
    void singleRegularRentalTest() {
        // given
        RentalHistory
                rentalHistory = new RentalHistory(customer, List.of(new MovieRental("F001", 1)));
        // when
        String statement = service.getStatement(rentalHistory);
        // then
        String expected = "Rental Record for Shahab\n" +
                "\tYou've Got Mail\t2.0\n" +
                "Amount owed is 2.0\n" +
                "You earned 1 frequent points\n";
        assertThat(statement)
                .isEqualTo(expected);
    }

    @Test
    void newReleaseThreeDayRentalTest() {
        // given
        RentalHistory
                rentalHistory = new RentalHistory(customer, List.of(new MovieRental("F004", 3)));
        // when
        String statement = service.getStatement(rentalHistory);
        // then
        String expected = "Rental Record for Shahab\n" +
                "\tFast & Furious X\t9.0\n" +
                "Amount owed is 9.0\n" +
                "You earned 2 frequent points\n";
        assertThat(statement)
                .isEqualTo(expected);
    }

    @Test
    void multipleDifferentRentalsTest() {
        // given
        RentalHistory rentalHistory = new RentalHistory(customer, List.of(
                new MovieRental("F001", 4),  // regular
                new MovieRental("F003", 5),  // children
                new MovieRental("F004", 2)   // new release
        ));
        // when
        String statement = service.getStatement(rentalHistory);
        // then
        String expected = "Rental Record for Shahab\n" +
                "\tYou've Got Mail\t5.0\n" +
                "\tCars\t4.5\n" +
                "\tFast & Furious X\t6.0\n" +
                "Amount owed is 15.5\n" +
                "You earned 3 frequent points\n";
        assertThat(statement)
                .isEqualTo(expected);
    }

    @Test
    void unknownMovieIdTest() {
        // given
        RentalHistory rentalHistory = new RentalHistory(customer, List.of(new MovieRental("UNKNOWN", 1)));
        // when / then
        assertThatThrownBy(() -> service.getStatement(rentalHistory))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Unknown movie ID: UNKNOWN");
    }
}
