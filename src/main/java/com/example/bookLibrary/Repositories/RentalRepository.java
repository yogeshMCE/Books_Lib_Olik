package com.example.bookLibrary.Repositories;

import com.example.bookLibrary.Models.Book;
import com.example.bookLibrary.Models.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental,Long> {
    Optional<Rental> findRentalByBook(Book book);

    @Query("SELECT r FROM Rental r WHERE DATEDIFF(CURRENT_DATE, r.returnDate) > 15")
    List<Rental> findOverdueRentals();
}
