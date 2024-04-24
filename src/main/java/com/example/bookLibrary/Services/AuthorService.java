package com.example.bookLibrary.Services;

import com.example.bookLibrary.DTOs.AuthorDto;

import java.util.List;

public interface AuthorService {
    //create
    AuthorDto createAuthor(AuthorDto authorDto);

    //update
    AuthorDto updateAuthor(AuthorDto authorDto,long author_id );

    //delete
    public void deleteAuthor(long author_id);

    //get by id
    AuthorDto getAuthorById(long author_id);

    // get all
    List<AuthorDto> getAllAuthors();

}
