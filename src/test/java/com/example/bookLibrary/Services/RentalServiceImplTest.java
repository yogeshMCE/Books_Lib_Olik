package com.example.bookLibrary.Services;

import com.example.bookLibrary.DTOs.RentalDto;
import com.example.bookLibrary.Exceptions.ResourseNotFoundException;
import com.example.bookLibrary.Models.Book;
import com.example.bookLibrary.Models.Rental;
import com.example.bookLibrary.Repositories.BookRepository;
import com.example.bookLibrary.Repositories.RentalRepository;
import com.example.bookLibrary.ServiceImpl.RentalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RentalServiceImplTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private RentalRepository rentalRepository;
    @InjectMocks
    private RentalServiceImpl rentalService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRentalByIDNotFound() {
        when(rentalRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourseNotFoundException.class, () -> rentalService.getRentalByID(1L));
    }

    @Test
    void testDeleteRentalsByIDNotFound() {
        when(rentalRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourseNotFoundException.class, () -> rentalService.deleteRentalsByID(1L));
    }

    @Test
    void testGetRentalByBookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourseNotFoundException.class, () -> rentalService.getRentalByBook(1L));
    }

    @Test
    void testCreateRentalBookNotAvailable() {
        RentalDto rentalDto = new RentalDto();
        Book book = new Book();
        book.setIsAvailable("NO");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        assertThrows(RuntimeException.class, () -> rentalService.createRental(rentalDto, 1L));
    }

    @Test
    void testUpdateRental() {
        RentalDto rentalDto = new RentalDto();
        Rental rental = new Rental();
        when(rentalRepository.findById(1L)).thenReturn(Optional.of(rental));
        when(rentalRepository.save(any(Rental.class))).thenReturn(rental);
        when(modelMapper.map(any(Rental.class), any())).thenReturn(rentalDto);
        RentalDto result = rentalService.updateRental(rentalDto, 1L);
        assertEquals(rentalDto, result);
    }

    @Test
    void testGetAllRentals() {
        List<Rental> rentals = new ArrayList<>();
        when(rentalRepository.findAll()).thenReturn(rentals);
        List<RentalDto> result = rentalService.getAllRentals();
        assertEquals(rentals.size(), result.size());
    }

    @Test
    void testGetAllOverDueRentals() {
        List<Rental> overDueRentals = new ArrayList<>();
        when(rentalRepository.findOverdueRentals()).thenReturn(overDueRentals);
        List<RentalDto> result = rentalService.getAllOverDueRentals();
        assertEquals(overDueRentals.size(), result.size());
    }

    @Test
    void testGetRentalByBook() {
        Book book = new Book();
        Rental rental = new Rental();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(rentalRepository.findRentalByBook(book)).thenReturn(Optional.of(rental));
        when(modelMapper.map(rental, RentalDto.class)).thenReturn(new RentalDto());
        RentalDto result = rentalService.getRentalByBook(1L);
        assertNotNull(result);
    }

    @Test
    void testDeleteRentalsByID() {
        Rental rental = new Rental();
        Book book = new Book();
        book.setId(1L); // Set the id for the book
        rental.setBook(book);
        when(rentalRepository.findById(1L)).thenReturn(Optional.of(rental));
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));
        doNothing().when(rentalRepository).delete(rental);
        assertDoesNotThrow(() -> rentalService.deleteRentalsByID(1L));
    }

    @Test
    void testCreateRental() {
        RentalDto rentalDto = new RentalDto();
        Book book = new Book();
        book.setIsAvailable("YES");
        Rental rental = new Rental();
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));
        when(modelMapper.map(rentalDto, Rental.class)).thenReturn(rental);
        when(rentalRepository.save(rental)).thenReturn(rental);
        when(modelMapper.map(rental, RentalDto.class)).thenReturn(rentalDto);
        RentalDto result = rentalService.createRental(rentalDto, 1L);
        assertEquals(rentalDto, result);
    }


    @Test
    void testCreateRentalBookRepositoryThrowsException() {
        RentalDto rentalDto = new RentalDto();
        when(bookRepository.findById(any(Long.class))).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> rentalService.createRental(rentalDto, 1L));
    }

    @Test
    void testUpdateRentalRentalRepositoryThrowsException() {
        RentalDto rentalDto = new RentalDto();
        when(rentalRepository.findById(any(Long.class))).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> rentalService.updateRental(rentalDto, 1L));
    }

    @Test
    void testGetAllRentalsRentalRepositoryThrowsException() {
        when(rentalRepository.findAll()).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> rentalService.getAllRentals());
    }

    @Test
    void testGetAllOverDueRentalsRentalRepositoryThrowsException() {
        when(rentalRepository.findOverdueRentals()).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> rentalService.getAllOverDueRentals());
    }

    @Test
    void testGetRentalByBookBookRepositoryThrowsException() {
        when(bookRepository.findById(any(Long.class))).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> rentalService.getRentalByBook(1L));
    }

    @Test
    void testDeleteRentalsByIDRentalRepositoryThrowsException() {
        when(rentalRepository.findById(any(Long.class))).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> rentalService.deleteRentalsByID(1L));
    }
}