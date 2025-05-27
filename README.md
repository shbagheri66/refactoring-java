# Movie Rental Refactoring

This project is a refactored version of a simple movie rental application. It reads a customer’s rental history, looks up movie data in an in-memory repository, calculates charges and frequent renter points, and produces a formatted text statement.

## What it does

* **Models**: `Customer`, `Movie`, `MovieRental`, `RentalHistory` capture domain data.
* **Repository**: `InMemoryMovieRepository` provides hard‑coded movie data.
* **Service**: `RentalInfoService` (formerly `RentalInfo`) generates a statement by:
    1. Fetching each `MovieRental` from a `RentalHistory`
    2. Looking up the corresponding `Movie` and its `MovieCode`
    3. Calculating the rental amount and points
    4. Building a multi‑line string as rental info
* **Tests**: Unit tests using JUnit 5 and AssertJ validate both repository lookups and service logic.

## Prerequisites

* **Java Development Kit (JDK) 17**
* **Apache Maven 3.6+**

## Build & Run

Open a terminal in the project root and execute:

```bash
# Compile sources
mvn compile

# Run tests
mvn test

# Package (compile + test)
mvn clean install
```

## Future Improvements

* **Persistence**: Replace `InMemoryMovieRepository` with a database-backed repository (JPA/Hibernate, JDBC).
* **Dependency Injection**: Integrate a lightweight DI framework (e.g. Spring) to manage singletons and wiring.
* **Logging & Monitoring**: Introduce a logging library for structured logging of rental calculations.
* **More entity refactoring**: Thinking of if using Movie instead of just movieId in MovieRental
* **New repositories** : for example for RentalHistory where we send a customer and get a list of rentals
* **More services** : Like a separate service for rental price calculation instead of having all in RentalInfoService
