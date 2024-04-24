package com.example.bookLibrary.Controllers;


import com.example.bookLibrary.DTOs.RentalDto;
import com.example.bookLibrary.DTOs.Response;
import com.example.bookLibrary.Services.RentalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rental")
public class RentalController {
    @Autowired
    private RentalService rentalService;

    // create rental
    @PostMapping("/create")
    public ResponseEntity<RentalDto> createRental(@Valid @RequestBody RentalDto rentalDto, @RequestParam("book_id") long bookID){
        RentalDto createdRental= rentalService.createRental(rentalDto,bookID);
        return new ResponseEntity<>(createdRental, HttpStatus.CREATED);
    }
    // update Rental
    @PutMapping("/update/{rental_id}")
    public ResponseEntity<RentalDto> updateRental(@RequestBody RentalDto rentalDto, @PathVariable("rental_id") long rentalID){
        RentalDto updatedRental= rentalService.updateRental(rentalDto,rentalID);
        return new ResponseEntity<>(updatedRental,HttpStatus.OK);
    }

    //get rental by id
    @GetMapping("/get/{rental_id}")
    public ResponseEntity<RentalDto> getRentalByID(@PathVariable("rental_id") long rentalID){
        RentalDto rentalDto= rentalService.getRentalByID(rentalID);
        return new ResponseEntity<>(rentalDto,HttpStatus.OK);
    }

    // get all Rentals
    @GetMapping("/get")
    public ResponseEntity<List<RentalDto>> getAllRental(){
       List<RentalDto> rentalDtoList= rentalService.getAllRentals();
        return new ResponseEntity<>(rentalDtoList,HttpStatus.OK);
    }

    // get rental by book
    @GetMapping("/get-by-book/{book_id}")
    public ResponseEntity<RentalDto>getRentalByBook(@PathVariable("book_id")long bookID){
        RentalDto rentalDto= rentalService.getRentalByBook(bookID);
        return new ResponseEntity<>(rentalDto,HttpStatus.OK);
    }

    // delete rental
    @DeleteMapping("/delete/{rental_id}")
    public ResponseEntity<Response> deleteRental(@PathVariable("rental_id")long rentalID){
        rentalService.deleteRentalsByID(rentalID);
        Response response= new Response("Rental with RentalId:"+rentalID+" have been Deleted Successfully",true);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/get-overdue-rentals")
    public ResponseEntity<List<RentalDto>> getAllOverdueRentals(){
        List<RentalDto> rentalDtoList= rentalService.getAllOverDueRentals();
        return new ResponseEntity<>(rentalDtoList,HttpStatus.OK);
    }

}
