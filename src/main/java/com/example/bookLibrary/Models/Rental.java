package com.example.bookLibrary.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.Date;


@Entity
@Table(name = "rental")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(name = "name", nullable = false)
    private String renterName; // Name of the person renting the book


    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "rental_date", nullable = false, updatable = false)
    private Date rentalDate; // Date when the book is rented



    @Column(name = "return_date",nullable = false)
    private Date returnDate; // Date when the book is rented

    @OneToOne
    private Book book;

}
