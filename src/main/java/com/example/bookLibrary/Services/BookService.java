package com.example.bookLibrary.Services;

import com.example.bookLibrary.DTOs.BookDto;

import java.util.List;

public interface BookService {

    //create book
    BookDto createBook(BookDto bookDto, long authorID);

    //update book
    BookDto updateBook(BookDto bookDto,long bookID);

    //get book by id
    BookDto getBookByID(long bookID);

    // get book by Author
    List<BookDto> getBooksByAuthor(long authorID);

    // get all Books
    List<BookDto> getAllBooks();

    //get Book By KeyWord
    List<BookDto>searchBookByKeyword(String Keyword);

    // delete book
    public void deleteBookById(long bookID);


}
