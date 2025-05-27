package com.movierental.model;

import java.util.List;

public class RentalHistory {
  private final Customer customer;
  private final List<MovieRental> rentals;

  public RentalHistory(Customer customer, List<MovieRental> rentals) {
    this.customer = customer;
    this.rentals = rentals;
  }

  public Customer getCustomer() {
    return customer;
  }

  public List<MovieRental> getRentals() {
    return rentals;
  }
}