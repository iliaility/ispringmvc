package service.implementation;

import dao.TicketDao;
import lombok.RequiredArgsConstructor;
import model.Event;
import model.Ticket;
import model.User;
import org.springframework.stereotype.Service;
import service.TicketService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ITicketService implements TicketService {

    private final TicketDao ticketDao;

    @Override
    public Ticket bookTicket(long id,long userId, long eventId, int place, Ticket.Category category) {
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
}