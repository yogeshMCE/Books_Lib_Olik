package com.example.bookLibrary.ServiceImpl;

import com.example.bookLibrary.DTOs.AuthorDto;
import com.example.bookLibrary.Exceptions.ResourseNotFoundException;
import com.example.bookLibrary.Models.Author;
import com.example.bookLibrary.Repositories.AuthorRepository;
import com.example.bookLibrary.Services.AuthorService;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
   @Autowired
    private   AuthorRepository authorRepository;
   @Autowired
    private    ModelMapper modelMapper;

    @Override
    public AuthorDto createAuthor(AuthorDto authorDto) {
        if (authorDto == null) {
            throw new IllegalArgumentException("AuthorDto cannot be null");
        }
        Author author = modelMapper.map(authorDto, Author.class);
        authorRepository.save(author);
        return modelMapper.map(author, AuthorDto.class);
    }

    @Override
    public AuthorDto updateAuthor(AuthorDto authorDto, long author_id) {
        if (authorDto == null) {
            throw new IllegalArgumentException("AuthorDto cannot be null");
        }
       Author author= authorRepository.findById(author_id)
                                      .orElseThrow(()-> new ResourseNotFoundException("Author","AuthorID",author_id));
         author.setName(authorDto.getName());
         author.setBiography(authorDto.getBiography());
         Author savedAuthor = authorRepository.save(author);
         return modelMapper.map(savedAuthor,AuthorDto.class);
    }

    @Override
    public void deleteAuthor(long author_id) {
       Author author= authorRepository.findById(author_id)
               .orElseThrow(()-> new ResourseNotFoundException("Author","AuthorID",author_id));
       authorRepository.delete(author);
    }

    @Override
    public AuthorDto getAuthorById(long author_id) {
        Author author= authorRepository.findById(author_id)
                .orElseThrow(()-> new ResourseNotFoundException("Author","AuthorID",author_id));
        return modelMapper.map(author,AuthorDto.class);
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        List<Author> authorList = authorRepository.findAll();
        return authorList.stream()
                .filter(Objects::nonNull)
                .map(author -> modelMapper.map(author, AuthorDto.class))
                .collect(Collectors.toList());
    }
}
