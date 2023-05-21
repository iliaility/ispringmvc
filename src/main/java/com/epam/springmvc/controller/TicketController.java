package com.epam.springmvc.controller;

import com.epam.springmvc.facade.BookingFacade;
import com.epam.springmvc.model.Ticket;
import com.epam.springmvc.model.User;
import com.epam.springmvc.util.PdfGenerator;
import com.itextpdf.text.DocumentException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    private final BookingFacade bookingFacade;

    public TicketController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @GetMapping(value = "/booked-tickets/user/{userId}/report", produces = MediaType.APPLICATION_PDF_VALUE)
    public String downloadPdfBookedTicketsByUserIdReport(@PathVariable("userId") long userId, Model model, HttpServletResponse response) {
        User user = bookingFacade.getUserById(userId);
        List<Ticket> tickets = bookingFacade.getBookedTickets(user, 1, 1);
        try {
            byte[] pdfBytes = PdfGenerator.generatePdf(tickets);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"booked_tickets.pdf\"");
            response.getOutputStream().write(pdfBytes);
            response.flushBuffer();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("tickets", tickets);
        return "tickets-list";
    }

    @GetMapping(path = "/search/user/{userId}/report", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generatePdfBookedTicketsByUserIdReport(@PathVariable Long userId) {
        ByteArrayInputStream byteArrayInputStream = bookingFacade.generatePdfTicketsReport(userId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=tickets.pdf");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(byteArrayInputStream));
    }

    @GetMapping("/booked-tickets/user/{userId}")
    public String getBookedTicketsByUser(@PathVariable("userId") long userId, Model model) {
        User user = bookingFacade.getUserById(userId);
        List<Ticket> tickets = bookingFacade.getBookedTickets(user, 1, 1);
        model.addAttribute("tickets", tickets);
        return "tickets-list";
    }

    @GetMapping(path = "/preload")
    public ResponseEntity<?> preloadTickets() {
        if (bookingFacade.preloadTickets()) {
            return ResponseEntity.ok("Successfully preloaded tickets");
        }
        return ResponseEntity.badRequest().body("Tickets weren't preloaded");
    }
}