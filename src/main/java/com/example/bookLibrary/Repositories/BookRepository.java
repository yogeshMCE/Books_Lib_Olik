package com.example.bookLibrary.Repositories;

import com.example.bookLibrary.Models.Author;
import com.example.bookLibrary.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {

    List<Book> findBookByAuthor(Author author);
    @Query("select b from Book b where b.title like :key")
    List<Book>searchBook(@Param("key")String keyword);
}
