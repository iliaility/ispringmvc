package com.epam.springmvc.service.implementation;

import com.epam.springmvc.dao.TicketDao;
import com.epam.springmvc.util.TicketBatchLoader;

import com.epam.springmvc.util.TicketBatchLoaderWithInputStream;
import lombok.RequiredArgsConstructor;
import com.epam.springmvc.model.Event;
import com.epam.springmvc.model.Ticket;
import com.epam.springmvc.model.User;
import org.springframework.stereotype.Service;
import com.epam.springmvc.service.TicketService;

import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketDao ticketDao;

    @Override
    public Ticket bookTicket(long id, long userId, long eventId, int place, Ticket.Category category) {
        return ticketDao.bookTicket(id, userId, eventId, place, category);
    }

    @Override
    public List<Ticket> getBookedTicketsByUser(User user, int pageSize, int pageNum) {
        return ticketDao.getBookedTicketsByUser(user, pageSize, pageNum);
    }

    @Override
    public List<Ticket> getBookedTicketsByEvent(Event event, int pageSize, int pageNum) {
        return ticketDao.getBookedTicketsByEvent(event, pageSize, pageNum);
    }

    @Override
    public boolean cancelTicketById(long ticketId) {
        return ticketDao.cancelTicketById(ticketId);
    }

    public boolean preloadTickets() {
        TicketBatchLoader ticketBatchLoader = new TicketBatchLoader(ticketDao);
        return ticketBatchLoader.preloadTickets();
    }

    public boolean preloadTickets2(InputStream inputStream) {
        TicketBatchLoaderWithInputStream ticketBatchLoaderWithInputStream = new TicketBatchLoaderWithInputStream(ticketDao, inputStream);
        return ticketBatchLoaderWithInputStream.preloadTickets2(inputStream);
    }
}