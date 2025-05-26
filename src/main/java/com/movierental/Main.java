package com.movierental;

import com.movierental.model.Customer;
import com.movierental.model.MovieRental;
import com.movierental.model.RentalHistory;
import com.movierental.repository.InMemoryMovieRepository;
import com.movierental.service.RentalInfoService;

import java.util.Arrays;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    String expected = "Rental Record for C. U. Stomer\n\tYou've Got Mail\t3.5\n\tMatrix\t2.0\nAmount owed is 5.5\nYou earned 2 frequent points\n";
    Customer customer = new Customer("C. U. Stomer");
    List<MovieRental> rentals = Arrays.asList(new MovieRental("F001", 3), new MovieRental("F002", 1));
    RentalHistory rentalHistory = new RentalHistory(customer, rentals);

    String result = new RentalInfoService(InMemoryMovieRepository.getInstance()).statement(rentalHistory);

    if (!result.equals(expected)) {
      throw new AssertionError("Expected: " + System.lineSeparator() + String.format(expected) + System.lineSeparator() + System.lineSeparator() + "Got: " + System.lineSeparator() + result);
    }

    System.out.println("Success");
  }
}
