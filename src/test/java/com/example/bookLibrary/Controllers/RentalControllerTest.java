package com.example.bookLibrary.Controllers;

import com.example.bookLibrary.DTOs.RentalDto;
import com.example.bookLibrary.Services.RentalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RentalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RentalService rentalService;

    @Autowired
    private ObjectMapper objectMapper;

    private RentalDto rentalDto;

    @BeforeEach
    public void setup() {
        rentalDto = new RentalDto();
        rentalDto.setRenterName("John Doe");

        // Set rentalDate to current date
        rentalDto.setRentalDate(new Date());

        // Set returnDate to one week from now
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        rentalDto.setReturnDate(calendar.getTime());
    }

    @Test
    public void testCreateRental() throws Exception {
        when(rentalService.createRental(any(RentalDto.class), anyLong())).thenReturn(rentalDto);

        mockMvc.perform(post("/api/rental/create")
                        .param("book_id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rentalDto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateRental() throws Exception {
        when(rentalService.updateRental(any(RentalDto.class), anyLong())).thenReturn(rentalDto);

        mockMvc.perform(put("/api/rental/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rentalDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetRentalByID() throws Exception {
        when(rentalService.getRentalByID(anyLong())).thenReturn(rentalDto);

        mockMvc.perform(get("/api/rental/get/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllRental() throws Exception {
        when(rentalService.getAllRentals()).thenReturn(Collections.singletonList(rentalDto));

        mockMvc.perform(get("/api/rental/get"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetRentalByBook() throws Exception {
        when(rentalService.getRentalByBook(anyLong())).thenReturn(rentalDto);

        mockMvc.perform(get("/api/rental/get-by-book/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteRental() throws Exception {
        mockMvc.perform(delete("/api/rental/delete/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllOverdueRentals() throws Exception {
        when(rentalService.getAllOverDueRentals()).thenReturn(Collections.singletonList(rentalDto));

        mockMvc.perform(get("/api/rental/get-overdue-rentals"))
                .andExpect(status().isOk());
    }
}