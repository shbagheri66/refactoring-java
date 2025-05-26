package com.movierental.service;

import com.movierental.exception.NotFoundException;
import com.movierental.model.Movie;
import com.movierental.model.MovieRental;
import com.movierental.model.RentalHistory;
import com.movierental.repository.MovieRepository;

public class RentalInfoService {

  private final MovieRepository movieRepository;

  public RentalInfoService(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public String statement(RentalHistory rentalHistory) {

    double totalAmount = 0;
    int frequentEnterPoints = 0;
    String result = "Rental Record for " + rentalHistory.getCustomer().getName() + "\n";
    for (MovieRental r : rentalHistory.getRentals()) {
      double thisAmount = 0;

      // find the movie
      Movie movie = movieRepository.findById(r.getMovieId())
              .orElseThrow(() -> new NotFoundException("Unknown movie ID: " + r.getMovieId()));

      String movieCode = movie.getCode().getValue();
      // determine amount for each movie
      if (movieCode.equals("regular")) {
        thisAmount = 2;
        if (r.getDays() > 2) {
          thisAmount = ((r.getDays() - 2) * 1.5) + thisAmount;
        }
      }
      if (movieCode.equals("new")) {
        thisAmount = r.getDays() * 3;
      }
      if (movieCode.equals("childrens")) {
        thisAmount = 1.5;
        if (r.getDays() > 3) {
          thisAmount = ((r.getDays() - 3) * 1.5) + thisAmount;
        }
      }

      //add frequent bonus points
      frequentEnterPoints++;
      // add bonus for a two day new release rental
      if (movieCode == "new" && r.getDays() > 2) frequentEnterPoints++;

      //print figures for this rental
      result += "\t" + movie.getTitle() + "\t" + thisAmount + "\n";
      totalAmount = totalAmount + thisAmount;
    }
    // add footer lines
    result += "Amount owed is " + totalAmount + "\n";
    result += "You earned " + frequentEnterPoints + " frequent points\n";

    return result;
  }
}
