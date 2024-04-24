package com.example.bookLibrary.Controllers;

import com.example.bookLibrary.DTOs.BookDto;
import com.example.bookLibrary.DTOs.Response;
import com.example.bookLibrary.Services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BooksController {
    @Autowired
    private BookService bookService;

    // create book
    @PostMapping("/create")
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookDto bookDto, @RequestParam("author_id") long author_id){
        BookDto createdBook = bookService.createBook(bookDto,author_id);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }
    //update book
    @PutMapping("/update/{book_id}")
    public ResponseEntity<BookDto>updatePost(@RequestBody BookDto bookDto ,@PathVariable("book_id") int book_id) throws IOException {
       BookDto updatedBook = bookService.updateBook(bookDto,book_id);
        return new ResponseEntity<>(updatedBook,HttpStatus.OK);
    }
    // get books by author id
    @GetMapping("/get-by-author/{author_id}")
    public ResponseEntity<List<BookDto>>getAllByAuthor(@PathVariable("author_id")long author_id){
        List<BookDto>books = bookService.getBooksByAuthor(author_id);
        return new ResponseEntity<>(books,HttpStatus.OK);
    }
    //get book by id
    @GetMapping("/get/{book_id}")
    public ResponseEntity<BookDto>getBookById(@PathVariable("book_id")int book_id){
        BookDto bookDto= bookService.getBookByID(book_id);
        return new ResponseEntity<>(bookDto,HttpStatus.OK);
    }
    // get all books
    @GetMapping("/get-all")
    public ResponseEntity<List<BookDto>>getAllBooks(){
        List<BookDto>books= bookService.getAllBooks();
        return new ResponseEntity<>(books,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{book_id}")
    public ResponseEntity<Response> deleteBookById(@PathVariable("book_id") long bookID){
        bookService.deleteBookById(bookID);
        return new ResponseEntity<>(new Response("Book with BookID:"+bookID+" Deleted Succesfully",true),HttpStatus.OK);
    }


    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<BookDto>>searchPost(@PathVariable("keyword")String keyword){
        List<BookDto>bookList= bookService.searchBookByKeyword(keyword);
        return new ResponseEntity<>(bookList,HttpStatus.OK);
    }


}
