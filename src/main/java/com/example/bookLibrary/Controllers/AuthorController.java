package com.example.bookLibrary.Controllers;

import com.example.bookLibrary.DTOs.AuthorDto;
import com.example.bookLibrary.DTOs.Response;
import com.example.bookLibrary.Services.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    //create author
    @PostMapping("/create")
    public ResponseEntity<AuthorDto> createAuthor(@Valid @RequestBody AuthorDto authorDto){

        AuthorDto createdAuthor = authorService.createAuthor(authorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor);
    }
    //update Author
    @PutMapping("/update/{author_id}")
    public ResponseEntity<AuthorDto> updateAuthor(@Valid @RequestBody AuthorDto authorDto, @PathVariable("author_id") long id){
        AuthorDto updatedAuthor = authorService.updateAuthor(authorDto,id);
        return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
    }
    // delete Author
    @DeleteMapping("/delete/{author_id}")
    public ResponseEntity<Response> deleteAuthorById(@PathVariable("author_id") long id){
        authorService.deleteAuthor(id);
        Response response= new Response("Author with ID : "+ id+" Deleted Successfully",true);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    //get Author By ID
    @GetMapping("/get/{author_id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable("author_id") long id){
        AuthorDto authorDto = authorService.getAuthorById(id);
        return new ResponseEntity<>(authorDto, HttpStatus.OK);
    }
    //get All Authors
    @GetMapping("/get-all")
    public ResponseEntity<List<AuthorDto>> getAllAuthors(){
        List<AuthorDto> authors = authorService.getAllAuthors();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

}
