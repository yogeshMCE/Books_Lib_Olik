package com.example.bookLibrary.DTOs;

import com.example.bookLibrary.Models.Book;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class RentalDto {

    private Long id;

    @NotBlank(message = "Name is Required")
    private String renterName; // Name of the person renting the book

    @NotNull(message = "Rental date is required")
    private Date rentalDate; // Date when the book is rented

    @NotNull(message = "Return date is required")
    private Date returnDate; // Date when the book is rented


    private BookDto book;
}
