package com.epam.springmvc.controller;

import com.epam.springmvc.facade.BookingFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
public class RestTicketController {

    private final BookingFacade bookingFacade;

    public RestTicketController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @PostMapping("/preload-tickets")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            bookingFacade.preloadTickets2(inputStream);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
        }
    }
}