package com.example.bookLibrary.Controllers;

import com.example.bookLibrary.DTOs.AuthorDto;
import com.example.bookLibrary.Services.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Autowired
    private ObjectMapper objectMapper;

    private AuthorDto authorDto;

    @BeforeEach
    public void setup() {
        authorDto = new AuthorDto();
        authorDto.setName("Test Author");
        authorDto.setBiography("Test Biography");
        // Set other fields as necessary
    }

    @Test
    public void testCreateAuthor() throws Exception {
        when(authorService.createAuthor(any(AuthorDto.class))).thenReturn(authorDto);

        mockMvc.perform(post("/api/author/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorDto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateAuthor() throws Exception {
        when(authorService.updateAuthor(any(AuthorDto.class), anyLong())).thenReturn(authorDto);

        mockMvc.perform(put("/api/author/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorDto)))
                .andExpect(status().isOk());
    }
    @Test
    public void testDeleteAuthor() throws Exception {
        mockMvc.perform(delete("/api/author/delete/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAuthorById() throws Exception {
        when(authorService.getAuthorById(anyLong())).thenReturn(authorDto);

        mockMvc.perform(get("/api/author/get/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllAuthors() throws Exception {
        when(authorService.getAllAuthors()).thenReturn(Collections.singletonList(authorDto));

        mockMvc.perform(get("/api/author/get-all"))
                .andExpect(status().isOk());
    }

}