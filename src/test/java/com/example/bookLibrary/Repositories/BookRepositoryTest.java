package com.example.bookLibrary.Repositories;

import com.example.bookLibrary.Models.Author;
import com.example.bookLibrary.Models.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    void testBookByAuthor(){
        Author author = new Author();
        author.setName("Yogesh Kumar");
        author.setId(2L);
        author.setBiography("author of book java Fundamentals");

        //when
        List<Book>bookList= bookRepository.findBookByAuthor(author);

        //then
        assertEquals(3,bookList.size());

    }

    @Test
    void testSearchBook() {
        //given
        Book book = new Book();
        book.setTitle("The Alchemist");
        book.setIsbn("978-0062315007");
        bookRepository.save(book);

        //when
        List<Book> books = bookRepository.searchBook("The Alchemist");

        //then
        assertTrue(books.stream().anyMatch(b -> b.getTitle().equals("The Alchemist")));
    }
}
