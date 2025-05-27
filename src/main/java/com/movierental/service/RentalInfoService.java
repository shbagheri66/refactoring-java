package com.movierental.service;

import com.movierental.exception.NotFoundException;
import com.movierental.model.*;
import com.movierental.repository.MovieRepository;

import java.util.Objects;

public class RentalInfoService {
  private final MovieRepository movieRepository;

  public RentalInfoService(MovieRepository movieRepository) {
    this.movieRepository = Objects.requireNonNull(movieRepository, "movieRepository must not be null");
  }

  /**
   * Generates a textual statement of all rentals for a given customer.
   *
   * @param rentalHistory rental history of the customer
   * @return a formatted multi‐line statement listing each rental, the total charge,
   *         and the total frequent renter points
   * @throws NullPointerException if customer is null
   * @throws NotFoundException    if any rental’s movie ID cannot be found in the repository
   */
  public String getStatement(RentalHistory rentalHistory) {
    Objects.requireNonNull(rentalHistory, "rentalHistory must not be null");
    Objects.requireNonNull(rentalHistory.getCustomer(), "customer must not be null");
    Objects.requireNonNull(rentalHistory.getRentals(), "rentals must not be null");
    StringBuilder sb = new StringBuilder();

    // Header
    sb.append(String.format("Rental Record for %s\n", rentalHistory.getCustomer().getName()));

    double totalAmount = 0;
    int frequentRenterPoints = 0;

    for (MovieRental rental : rentalHistory.getRentals()) {
      Movie movie = movieRepository.findById(rental.getMovieId())
              .orElseThrow(() -> new NotFoundException("Unknown movie ID: " + rental.getMovieId()));
      MovieCode code = movie.getCode();
      int days = rental.getDays();

      double thisAmount = calculateAmount(code, days);
      int thisPoints = calculatePoints(code, days);

      totalAmount += thisAmount;
      frequentRenterPoints += thisPoints;

      // statement body
      sb.append(String.format("\t%s\t%s\n", movie.getTitle(), thisAmount));
    }

    sb.append(String.format("Amount owed is %s\n", totalAmount));
    sb.append(String.format("You earned %d frequent points\n", frequentRenterPoints));

    return sb.toString();
  }

  /**
   * Calculates the rental charge for a given movie code and rental duration.
   *    REGULAR the first 2 days costs 2 and after that 1.5 each day
   *    NEW_MOVIE costs 3 each day
   *    CHILDRENS the first 3 days costs 1.5 and after that 1.5 each day
   * @param code movie code
   * @param days number of days
   * @return amount to be paid
   *  @throws NotFoundException if an unrecognized {@code MovieCode} is provided
   */
  private double calculateAmount(MovieCode code, int days) {
    return switch (code) {
      case REGULAR -> 2 + (days > 2 ? ( days - 2) * 1.5 : 0);
      case NEW_MOVIE -> days * 3.0;
      case CHILDRENS -> 1.5 + (days > 3 ? ( days - 3) * 1.5 : 0);
      default -> throw new NotFoundException("MovieCode not found: " + code);
    };
  }

  /**
   * Calculates the number of frequent points earned for a given movie code and rental duration.
   * frequent points are 2 if it is a NEW_MOVIE and rental period > 2 otherwise 1
   * @param code movie code
   * @param days number of days
   * @return frequent points earned
   */
  private int calculatePoints(MovieCode code, int days) {
    int points = 1;
    if (MovieCode.NEW_MOVIE.equals(code) && days > 2) {
      points++;
    }
    return points;
  }
}
