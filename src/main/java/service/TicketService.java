package service;

import model.Event;
import model.Ticket;
import model.User;

import java.util.List;

public interface TicketService {
    Ticket bookTicket(long id, long userId, long eventId, int place, Ticket.Category category);

    List<Ticket> getBookedTicketsByUser(User user, int pageSize, int pageNum);

    List<Ticket> getBookedTicketsByEvent(Event event, int pageSize, int pageNum);

    boolean cancelTicketById(long ticketId);
}