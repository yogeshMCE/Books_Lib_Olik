package com.example.bookLibrary.Controllers;

import com.example.bookLibrary.DTOs.BookDto;
import com.example.bookLibrary.Services.BookService;
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
public class BooksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private BookDto bookDto;

    @BeforeEach
    public void setup() {
        bookDto = new BookDto();
        bookDto.setTitle("Test Book");
        bookDto.setIsbn("123-4567899136");
        bookDto.setPublicationYear(2002);
        // Set other fields as necessary
    }
    @Test
    public void testCreateBook() throws Exception {

        when(bookService.createBook(any(BookDto.class), anyLong())).thenReturn(bookDto);

        mockMvc.perform(post("/api/book/create")
                        .param("author_id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateBook() throws Exception {
        when(bookService.updateBook(any(BookDto.class), anyLong())).thenReturn(bookDto);

        mockMvc.perform(put("/api/book/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetBookByID() throws Exception {
        when(bookService.getBookByID(anyLong())).thenReturn(bookDto);

        mockMvc.perform(get("/api/book/get/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(Collections.singletonList(bookDto));

        mockMvc.perform(get("/api/book/get-all"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteBook() throws Exception {
        mockMvc.perform(delete("/api/book/delete/1"))
                .andExpect(status().isOk());
    }
}