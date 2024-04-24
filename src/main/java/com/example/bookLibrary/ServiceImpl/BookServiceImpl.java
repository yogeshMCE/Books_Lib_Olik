package com.example.bookLibrary.ServiceImpl;

import com.example.bookLibrary.DTOs.BookDto;
import com.example.bookLibrary.Exceptions.ResourseNotFoundException;
import com.example.bookLibrary.Models.Author;
import com.example.bookLibrary.Models.Book;
import com.example.bookLibrary.Repositories.AuthorRepository;
import com.example.bookLibrary.Repositories.BookRepository;
import com.example.bookLibrary.Services.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Override
    public BookDto createBook(BookDto bookDto, long authorID) {
        Author author= authorRepository.findById(authorID).orElseThrow(()->new ResourseNotFoundException("Author","AuthorID",authorID));
        Book book= modelMapper.map(bookDto,Book.class);
        book.setAuthor(author);
        book.setIsAvailable("Yes");
        Book createdBook= bookRepository.save(book);
        return modelMapper.map(createdBook,BookDto.class);

    }

    @Override
    public BookDto updateBook(BookDto bookDto, long bookID) {
        if (bookDto == null) {
            throw new IllegalArgumentException("BookDto cannot be null");
        }
        Book book= bookRepository.findById(bookID).orElseThrow(()->new ResourseNotFoundException("Book","BookID",bookID));
        book.setIsbn(bookDto.getIsbn());
        book.setTitle(bookDto.getTitle());
        book.setPublicationYear(bookDto.getPublicationYear());
        book.setIsAvailable(bookDto.getIsAvailable());
        Book updatedBook= bookRepository.save(book);
        return modelMapper.map(updatedBook,BookDto.class);
    }

    @Override
    public BookDto getBookByID(long bookID) {
        Book book= bookRepository.findById(bookID).orElseThrow(()->new ResourseNotFoundException("Book","BookID",bookID));
        return modelMapper.map(book,BookDto.class);
    }

    @Override
    public List<BookDto> getBooksByAuthor(long authorID) {
        Author author= authorRepository.findById(authorID).orElseThrow(()->new ResourseNotFoundException("Author","AuthorID",authorID));
       List<Book>books= bookRepository.findBookByAuthor(author);
       return books.stream().map(book->modelMapper.map(book,BookDto.class)).collect(Collectors.toList());

    }

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .filter(Objects::nonNull)
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDto> searchBookByKeyword(String Keyword) {
     List<Book>books= bookRepository.searchBook("%"+Keyword+"%");
        return books.stream().map(book->modelMapper.map(book,BookDto.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteBookById(long bookID) {
        Book book= bookRepository.findById(bookID).orElseThrow(()->new ResourseNotFoundException("Book","BookID",bookID));
        if(book.getIsAvailable().equalsIgnoreCase("no")){
            throw new RuntimeException("Book is rented to SomeOne Can Not Delete This Book");
        }
        bookRepository.delete(book);
    }
}
