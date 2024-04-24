package com.example.bookLibrary.Services;

import com.example.bookLibrary.DTOs.BookDto;
import com.example.bookLibrary.Exceptions.ResourseNotFoundException;
import com.example.bookLibrary.Models.Author;
import com.example.bookLibrary.Models.Book;
import com.example.bookLibrary.Repositories.AuthorRepository;
import com.example.bookLibrary.Repositories.BookRepository;
import com.example.bookLibrary.ServiceImpl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookServiceImplTest {


    @Mock
    private ModelMapper modelMapper;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Author savedAuthor;

    @BeforeEach
    void setUp() {
        Author author = new Author();
        author.setId(1L); // Set the id here
        author.setName("John Doe");
        author.setBiography("Biography of John Doe");
        // set other fields of author...

        when(authorRepository.save(any(Author.class))).thenReturn(author);
        savedAuthor = authorRepository.save(author);
    }

    @Test
    void testCreateBook() {
        // Arrange
        long authorId = savedAuthor.getId(); // replace with the actual author ID

        Author author = new Author();
        author.setId(authorId);
        // set other fields of author...

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        BookDto bookDto = new BookDto();
        bookDto.setTitle("Java Fundamentals");
        bookDto.setIsbn("123-4567890123");
        bookDto.setPublicationYear(2000);
        bookDto.setIsAvailable("yes");
        // set other fields...

        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setIsbn(bookDto.getIsbn());
        book.setPublicationYear(bookDto.getPublicationYear());
        book.setIsAvailable(bookDto.getIsAvailable());
        // set other fields...

        when(modelMapper.map(bookDto, Book.class)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(modelMapper.map(book, BookDto.class)).thenReturn(bookDto);

        // Act
        BookDto created = bookService.createBook(bookDto, authorId);

        // Assert
        assertEquals(created.getTitle(), bookDto.getTitle());
        assertEquals(created.getIsbn(), bookDto.getIsbn());
        assertEquals(created.getPublicationYear(), bookDto.getPublicationYear());
        assertEquals(created.getIsAvailable(), bookDto.getIsAvailable());
        // assert other fields...
    }

    @Test
    void testUpdateBook() {
        // Arrange
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Updated Title");
        bookDto.setIsbn("123-4567890123");
        bookDto.setPublicationYear(2000);
        bookDto.setIsAvailable("yes");
        // set other fields...

        Book book = new Book();
        book.setTitle("Original Title");
        book.setIsbn("987-6543210987");
        book.setPublicationYear(1990);
        book.setIsAvailable("no");
        // set other fields...

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(modelMapper.map(bookDto, Book.class)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(modelMapper.map(book, BookDto.class)).thenReturn(bookDto);

        // Act
        BookDto updated = bookService.updateBook(bookDto, 1L);

        // Assert
        assertEquals(updated.getTitle(), bookDto.getTitle());
        assertEquals(updated.getIsbn(), bookDto.getIsbn());
        assertEquals(updated.getPublicationYear(), bookDto.getPublicationYear());
        assertEquals(updated.getIsAvailable(), bookDto.getIsAvailable());
        // assert other fields...
    }




    @Test
    void testGetBookByID() {
        // Arrange
        long bookId = 1L; // replace with the actual book ID

        Book book = new Book();
        book.setId(bookId);
        // set other fields...

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(modelMapper.map(book, BookDto.class)).thenReturn(new BookDto());

        // Act
        BookDto bookDto = bookService.getBookByID(bookId);

        // Assert
        assertNotNull(bookDto);
    }

    @Test
    void testGetBooksByAuthor() {
        // Arrange
        long authorId = 1L; // replace with the actual author ID

        Author author = new Author();
        author.setId(authorId);
        // set other fields...

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(bookRepository.findBookByAuthor(author)).thenReturn(List.of(new Book()));

        // Act
        List<BookDto> books = bookService.getBooksByAuthor(authorId);

        // Assert
        assertFalse(books.isEmpty());
    }

    @Test
    void testSearchBookByKeyword() {
        // Arrange
        String keyword = "Java"; // replace with the actual keyword

        when(bookRepository.searchBook("%" + keyword + "%")).thenReturn(List.of(new Book()));

        // Act
        List<BookDto> books = bookService.searchBookByKeyword(keyword);

        // Assert
        assertFalse(books.isEmpty());
    }

    @Test
    void testGetAllBooks() {
        // Arrange
        when(bookRepository.findAll()).thenReturn(List.of(new Book()));

        // Act
        List<BookDto> books = bookService.getAllBooks();

        // Assert
        assertFalse(books.isEmpty());
    }

    @Test
    void testDeleteBookById() {
        // Arrange
        long bookId = 1L; // replace with the actual book ID

        Book book = new Book();
        book.setId(bookId);
        book.setIsAvailable("yes");
        // set other fields...

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // Act
        assertDoesNotThrow(() -> bookService.deleteBookById(bookId));

        // Assert
        verify(bookRepository, times(1)).delete(book);
    }
    @Test
void testCreateBookWithEmptyTitle() {
    assertThrows(ResourseNotFoundException.class, () -> bookService.createBook(null,savedAuthor.getId()));
}


@Test
void testGetBookByIdWithNegativeId() {
    assertThrows(ResourseNotFoundException.class, () -> bookService.getBookByID(-1L));
}

@Test
void testDeleteBookWithNegativeId() {
    assertThrows(ResourseNotFoundException.class, () -> bookService.deleteBookById(-1L));
}

@Test
void testGetAllBooksWhenNoneExist() {
    when(bookRepository.findAll()).thenReturn(Collections.emptyList());

    List<BookDto> books = bookService.getAllBooks();

    assertTrue(books.isEmpty());
}

    @Test
    void testUpdateBookWithNullDto() {
        assertThrows(IllegalArgumentException.class, () -> bookService.updateBook(null, 1L));
    }



    @Test
    void testGetBookByIdNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourseNotFoundException.class, () -> bookService.getBookByID(1L));
    }

    @Test
    void testDeleteBookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourseNotFoundException.class, () -> bookService.deleteBookById(1L));
    }
}
