package com.epam.springmvc.dao;

import com.epam.springmvc.model.Event;
import com.epam.springmvc.model.Ticket;
import com.epam.springmvc.model.User;

import java.util.List;

public interface TicketDao {

    Ticket bookTicket(long id,long userId, long eventId, int place, Ticket.Category category);

    List<Ticket> getBookedTicketsByUser(User user, int pageSize, int pageNum);

    List<Ticket> getBookedTicketsByEvent(Event event, int pageSize, int pageNum);

    boolean cancelTicketById(long ticketId);

}