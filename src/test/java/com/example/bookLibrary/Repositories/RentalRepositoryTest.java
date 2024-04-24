package com.example.bookLibrary.Repositories;


import com.example.bookLibrary.Models.Book;
import com.example.bookLibrary.Models.Rental;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Calendar;

import static org.assertj.core.api.InstanceOfAssertFactories.DATE;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RentalRepositoryTest {
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private BookRepository bookRepository;

    // Write your tests here
    @Test
    void testRentalByBook() throws ParseException {
        //given
        Book book = new Book();
        book.setTitle("The Alchemist");
        book.setIsbn("978-0062315007");
        Book book1= bookRepository.save(book);

        Rental rental = new Rental();
        rental.setBook(book1);
        rental.setRenterName("John Doe");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        rental.setRentalDate(format.parse("2021-06-01"));
        rental.setReturnDate(format.parse("2021-06-15"));
        rentalRepository.save(rental);

        //when
        Optional<Rental> rentals = rentalRepository.findRentalByBook(book1);

        //then
        assertThat(rentals).isNotEmpty();
    }

    @Test
void testFindOverdueRentals() throws ParseException {
    //given
    Book book = new Book();
    book.setTitle("The Alchemist");
    book.setIsbn("978-0062315007");
    Book book1= bookRepository.save(book);

    Rental rental = new Rental();
    rental.setBook(book1);
    rental.setRenterName("John Doe");

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Calendar c = Calendar.getInstance();
    c.setTime(new Date());
    c.add(Calendar.DATE, -20);
    rental.setRentalDate(c.getTime());

    c.setTime(new Date());
    c.add(Calendar.DATE, -16);
    rental.setReturnDate(c.getTime());

    rentalRepository.save(rental);

    //when
    List<Rental> overdueRentals = rentalRepository.findOverdueRentals();

    //then
    assertThat(overdueRentals).isNotEmpty();
    assertThat(overdueRentals.stream().anyMatch(r -> r.getBook().equals(book1))).isTrue();
}
}
