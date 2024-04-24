package com.example.bookLibrary.Repositories;

import com.example.bookLibrary.DTOs.AuthorDto;
import com.example.bookLibrary.Models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {


}
