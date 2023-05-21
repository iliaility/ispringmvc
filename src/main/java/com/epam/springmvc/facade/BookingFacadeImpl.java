package com.epam.springmvc.facade;

import com.epam.springmvc.util.PdfGenerator;
import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.epam.springmvc.model.Event;
import com.epam.springmvc.model.Ticket;
import com.epam.springmvc.model.User;
import org.springframework.stereotype.Component;
import com.epam.springmvc.service.implementation.EventServiceImpl;
import com.epam.springmvc.service.implementation.TicketServiceImpl;
import com.epam.springmvc.service.implementation.UserServiceImpl;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookingFacadeImpl implements BookingFacade {
    private final UserServiceImpl userService;
    private final EventServiceImpl eventService;
    private final TicketServiceImpl ticketService;

    @Override
    @Transactional
    public boolean preloadTickets() {
        return ticketService.preloadTickets();
    }

    @Override
    public ByteArrayInputStream generatePdfTicketsReport(Long userId) {
        User user = userService.getUserById(userId);
        List<Ticket> tickets = ticketService.getBookedTicketsByUser(user, 1, 1);
        try {
            byte[] pdfBytes = PdfGenerator.generatePdf(tickets);
            return new ByteArrayInputStream(pdfBytes);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Event getEventById(long eventId) {
        log.info("Get event by id " + eventId);
        return eventService.getEventById(eventId);
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        log.info("Get event with " + title);
        return eventService.getEventsByTitle(title, pageSize, pageNum);
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        log.info("Get event for " + day);
        return eventService.getEventsByDay(day, pageSize, pageNum);
    }

    @Override
    public Event createEvent(Event event) {
        log.info("Event " + event.toString() + " was created successfully");
        return eventService.createEvent(event);
    }

    @Override
    public Event updateEvent(Event event) {
        log.info("Event "+  event.toString() + " was updated successfully");
        return eventService.updateEvent(event);
    }

    @Override
    public boolean deleteEvent(long eventId) {
        log.info("Event with id" + eventId + " was deleted");
        return eventService.deleteEventById(eventId);
    }

    @Override
    public User getUserById(long userId) {
        log.info("Get user by id " + userId);
        return userService.getUserById(userId);
    }

    @Override
    public List<User> getUsersByEmail(String email, int pageSize, int pageNum) {
        log.info("Get users with email " + email);
        return userService.getUsersByEmail(email, pageSize, pageNum);
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        log.info("Get users with name " + name);
        return userService.getUsersByName(name, pageSize, pageNum);
    }

    @Override
    public User createUser(User user) {
        log.info("User " + user.toString() + " was created");
        return userService.createUser(user);
    }

    @Override
    public User updateUser(User user) {
        log.info("User " + user.toString() + " was updated");
        return userService.updateUser(user);
    }

    @Override
    public boolean deleteUser(long userId) {
        log.info("User with id" + userId + " was deleted");
        return userService.deleteUserById(userId);
    }

    @Override
    public Ticket bookTicket(long id,long userId, long eventId, int place, Ticket.Category category) {
        log.info("Book ticket: userId {}, eventId {}, place {}", userId, eventId, place);
        return ticketService.bookTicket(id, userId, eventId, place, category);
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        log.info("Get booked tickets for user " + user);
        return ticketService.getBookedTicketsByUser(user, pageSize, pageNum);
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        log.info("Get booked tickets for event " + event);
        return ticketService.getBookedTicketsByEvent(event, pageSize, pageNum);
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        log.info("Cancel ticket with id " + ticketId);
       return ticketService.cancelTicketById(ticketId);
    }
}