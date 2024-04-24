package com.example.bookLibrary.Services;
import com.example.bookLibrary.DTOs.AuthorDto;
import com.example.bookLibrary.Models.Author;
import com.example.bookLibrary.Repositories.AuthorRepository;
import com.example.bookLibrary.ServiceImpl.AuthorServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.example.bookLibrary.Exceptions.ResourseNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AuthorServiceImpl authorService;


    @Test
    void testCreateAuthor() {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setName("Yogesh Kumar");
        authorDto.setBiography("author of book java Fundamentals");

        Author author = new Author();
        author.setName(authorDto.getName());
        author.setBiography(authorDto.getBiography());

        when(modelMapper.map(authorDto, Author.class)).thenReturn(author);
        when(authorRepository.save(author)).thenReturn(author);
        when(modelMapper.map(author, AuthorDto.class)).thenReturn(authorDto);

        AuthorDto created = authorService.createAuthor(authorDto);

        assertEquals(created.getName(), authorDto.getName());
        assertEquals(created.getBiography(), authorDto.getBiography());
    }

    @Test
    void testUpdateAuthor() {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setName("Updated Name");
        authorDto.setBiography("Updated Biography");

        Author author = new Author();
        author.setName("Original Name");
        author.setBiography("Original Biography");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(modelMapper.map(authorDto, Author.class)).thenReturn(author);
        when(authorRepository.save(author)).thenReturn(author);
        when(modelMapper.map(author, AuthorDto.class)).thenReturn(authorDto);

        AuthorDto updated = authorService.updateAuthor(authorDto, 1L);

        assertEquals(updated.getName(), authorDto.getName());
        assertEquals(updated.getBiography(), authorDto.getBiography());
    }

    @Test
    void testUpdateAuthorNotFound() {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setName("Updated Name");
        authorDto.setBiography("Updated Biography");

        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourseNotFoundException.class, () -> authorService.updateAuthor(authorDto, 1L));
    }

    @Test
    void testDeleteAuthor() {
        Author author = new Author();
        author.setName("Yogesh Kumar");
        author.setBiography("author of book java Fundamentals");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        doNothing().when(authorRepository).delete(author);

        authorService.deleteAuthor(1L);

        verify(authorRepository, times(1)).delete(author);
    }

    @Test
    void testDeleteAuthorNotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourseNotFoundException.class, () -> authorService.deleteAuthor(1L));
    }

    @Test
    void testGetAuthorById() {
        Author author = new Author();
        author.setName("Yogesh Kumar");
        author.setBiography("author of book java Fundamentals");

        AuthorDto authorDto = new AuthorDto();
        authorDto.setName(author.getName());
        authorDto.setBiography(author.getBiography());

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(modelMapper.map(author, AuthorDto.class)).thenReturn(authorDto);

        AuthorDto found = authorService.getAuthorById(1L);

        assertEquals(found.getName(), authorDto.getName());
        assertEquals(found.getBiography(), authorDto.getBiography());
    }

    @Test
    void testGetAuthorByIdNotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourseNotFoundException.class, () -> authorService.getAuthorById(1L));
    }

    @Test
    void testGetAllAuthors() {
        Author author1 = new Author();
        author1.setName("Yogesh Kumar");
        author1.setBiography("author of book java Fundamentals");

        Author author2 = new Author();
        author2.setName("John Doe");
        author2.setBiography("author of book Python Fundamentals");

        AuthorDto authorDto1 = new AuthorDto();
        authorDto1.setName(author1.getName());
        authorDto1.setBiography(author1.getBiography());

        AuthorDto authorDto2 = new AuthorDto();
        authorDto2.setName(author2.getName());
        authorDto2.setBiography(author2.getBiography());

        when(authorRepository.findAll()).thenReturn(Arrays.asList(author1, author2));
        when(modelMapper.map(author1, AuthorDto.class)).thenReturn(authorDto1);
        when(modelMapper.map(author2, AuthorDto.class)).thenReturn(authorDto2);

        List<AuthorDto> authors = authorService.getAllAuthors();

        assertEquals(authors.size(), 2);
        assertTrue(authors.contains(authorDto1));
        assertTrue(authors.contains(authorDto2));
    }

    @Test
    void testCreateAuthorWithNullDto() {
        assertThrows(IllegalArgumentException.class, () -> authorService.createAuthor(null));
    }

    @Test
    void testUpdateAuthorWithNullDto() {
        assertThrows(IllegalArgumentException.class, () -> authorService.updateAuthor(null, 1L));
    }


}