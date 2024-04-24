package com.example.bookLibrary.ServiceImpl;

import com.example.bookLibrary.DTOs.RentalDto;
import com.example.bookLibrary.Exceptions.ResourseNotFoundException;
import com.example.bookLibrary.Models.Book;
import com.example.bookLibrary.Models.Rental;
import com.example.bookLibrary.Repositories.BookRepository;
import com.example.bookLibrary.Repositories.RentalRepository;
import com.example.bookLibrary.Services.RentalService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentalServiceImpl implements RentalService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RentalRepository rentalRepository;

    @Override
    public RentalDto createRental(RentalDto rentalDto, long bookID) {
        Book book= bookRepository.findById(bookID).orElseThrow(()->new ResourseNotFoundException("Book","BookID",bookID));
        if(book.getIsAvailable().equalsIgnoreCase("no")){
            throw new RuntimeException("Book is Already rented By SomeOne");
        }
        book.setIsAvailable("NO");
        Book savedBook= bookRepository.save(book);
        Rental rental= modelMapper.map(rentalDto,Rental.class);
        rental.setBook(savedBook);
        Rental savedRental= rentalRepository.save(rental);
        return modelMapper.map(savedRental,RentalDto.class);
    }

    @Override
    public RentalDto updateRental(RentalDto rentalDto, long rentalID) {
        Rental rental= rentalRepository.findById(rentalID).orElseThrow(()->new ResourseNotFoundException("Rental","RentalID",rentalID));
        rental.setRenterName(rentalDto.getRenterName());
        rental.setRentalDate(rentalDto.getRentalDate());
        rental.setReturnDate(rentalDto.getReturnDate());
        Rental updatedRental= rentalRepository.save(rental);
        return modelMapper.map(updatedRental,RentalDto.class);
    }

    @Override
    public List<RentalDto> getAllRentals() {
        List<Rental>rentals= rentalRepository.findAll();
        return rentals.stream().map(rental-> modelMapper.map(rental,RentalDto.class)).collect(Collectors.toList());
    }

    @Override
    public RentalDto getRentalByID(long rentalID) {
        Rental rental= rentalRepository.findById(rentalID).orElseThrow(()->new ResourseNotFoundException("Rental","RentalID",rentalID));
        return modelMapper.map(rental,RentalDto.class);
    }

    @Override
    public List<RentalDto> getAllOverDueRentals() {
        List<Rental>overDueRentals= rentalRepository.findOverdueRentals();
        return overDueRentals.stream().map(rental -> modelMapper.map(rental,RentalDto.class)).collect(Collectors.toList());
    }

    @Override
    public RentalDto getRentalByBook(long bookID) {
        Book book= bookRepository.findById(bookID).orElseThrow(()->new ResourseNotFoundException("Book","BookID",bookID));
        Optional<Rental> optionalRental= rentalRepository.findRentalByBook(book);
       if(optionalRental.isEmpty()){
           throw  new ResourseNotFoundException("Rental","BookID",bookID);
       }
       Rental rental= optionalRental.get();
       return modelMapper.map(rental,RentalDto.class);
    }

    @Override
    public void deleteRentalsByID(long rentalID) {
        Rental rental= rentalRepository.findById(rentalID).orElseThrow(()->new ResourseNotFoundException("Rental","RentalID",rentalID));
        long bookID= rental.getBook().getId();
        Book book= bookRepository.findById(bookID).orElseThrow(()->new ResourseNotFoundException("Book","BookID",bookID));
        book.setIsAvailable("Yes");
        bookRepository.save(book);
        rentalRepository.delete(rental);
    }
}
