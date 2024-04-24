package com.example.bookLibrary.Services;

import com.example.bookLibrary.DTOs.RentalDto;

import java.util.List;

public interface RentalService {
    // create rental
    RentalDto createRental(RentalDto rentalDto, long bookID);

    // update rental
    RentalDto updateRental(RentalDto rentalDto, long rentalID);

    //get All Rentals
    List<RentalDto> getAllRentals();

    // get Rental by ID
    public RentalDto getRentalByID(long rentalID);

    // get All OverDue Rentals
    List<RentalDto> getAllOverDueRentals();

    // get rental by Book
    RentalDto getRentalByBook(long bookID);

    // Delete Rental
    public void deleteRentalsByID(long rentalID);
}
