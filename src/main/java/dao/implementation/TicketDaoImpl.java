package dao.implementation;

import dao.TicketDao;
import lombok.Setter;
import model.Event;
import model.Ticket;
import model.User;
import model.implementation.TicketImpl;
import storage.BookingStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Setter
public class TicketDaoImpl implements TicketDao {
    private BookingStorage bookingStorage;

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
        List<Ticket> userTickets = new ArrayList<>();
        for (Ticket ticket : bookingStorage.getTickets().values()) {
            if (ticket.getUserId() == user.getId()) {
                userTickets.add(ticket);
            }
        }
        return userTickets;
    }

    @Override
    public List<Ticket> getBookedTicketsByEvent(Event event, int pageSize, int pageNum) {
        List<Ticket> eventTickets = new ArrayList<>();
        for (Ticket ticket : bookingStorage.getTickets().values()) {
            if (ticket.getEventId() == event.getId()) {
                eventTickets.add(ticket);
            }
        }
        return eventTickets;
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