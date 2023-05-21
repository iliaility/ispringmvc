package com.epam.springmvc.dao.implementation;

import com.epam.springmvc.dao.TicketDao;
import com.epam.springmvc.exception.NotFoundException;
import lombok.Setter;
import com.epam.springmvc.model.Event;
import com.epam.springmvc.model.Ticket;
import com.epam.springmvc.model.User;
import com.epam.springmvc.model.implementation.TicketImpl;
import com.epam.springmvc.storage.BookingStorage;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Setter
public class TicketDaoImpl implements TicketDao {

    private BookingStorage bookingStorage;

    private List<TicketImpl> getTicketsData() {
        return (List<TicketImpl>) bookingStorage.getData(TicketImpl.class);
    }

    @Override
    public Ticket bookTicket(long id, long userId, long eventId, int place, Ticket.Category category) {
        User user = bookingStorage.getUsers().get(userId);
        Event event = bookingStorage.getEvents().get(eventId);
        if (user == null || event == null) {
            return null;
        }
        Map<Long, Ticket> tickets = bookingStorage.getTickets();
        long ticketId = bookingStorage.getNextId(Ticket.class);
        Ticket ticket = new TicketImpl(userId, eventId, place, category);
        ticket.setId(ticketId);
        tickets.put(ticketId, ticket);
        return ticket;
    }

    @Override
    public List<Ticket> getBookedTicketsByUser(User user, int pageSize, int pageNum) {
        List<Ticket> filteredTickets = getTicketsData().stream()
                .filter(ticket -> Objects.equals(ticket.getUserId(), user.getId()))
                .skip((pageNum - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

        if (filteredTickets.isEmpty()) {
            throw new NotFoundException("No booked tickets found for the user");
        }
        return filteredTickets;
    }

    @Override
    public List<Ticket> getBookedTicketsByEvent(Event event, int pageSize, int pageNum) {
        List<Ticket> filteredTickets = getTicketsData().stream()
                .filter(ticket -> Objects.equals(ticket.getUserId(), event.getId()))
                .skip((pageNum - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

        if (filteredTickets.isEmpty()) {
            throw new NotFoundException("No booked tickets found for the user");
        }
        return filteredTickets;
    }

    @Override
    public boolean cancelTicketById(long ticketId) {
        Map<Long, Ticket> tickets = bookingStorage.getTickets();
        if (tickets.containsKey(ticketId)) {
            tickets.remove(ticketId);
            return true;
        }
        return false;
    }

    public void setBookingStorage(BookingStorage bookingStorage) {
        this.bookingStorage = bookingStorage;
    }
}