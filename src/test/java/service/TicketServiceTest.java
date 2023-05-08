package service;

import dao.TicketDao;
import model.Ticket;
import model.implementation.IEvent;
import model.implementation.IUser;
import model.implementation.ITicket;
import service.implementation.ITicketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {
    @Mock
    private TicketDao ticketDao;

    @InjectMocks
    private ITicketService ticketService;

    @Test
    void bookTicketTest() {
        Ticket expected = new ITicket(1, 1, 1, 10, Ticket.Category.STANDARD);
        when(ticketDao.bookTicket(1, 1, 1, 10, Ticket.Category.STANDARD)).thenReturn(expected);
        Ticket actual = ticketService.bookTicket(1, 1, 1, 10, Ticket.Category.STANDARD);
        assertEquals(expected, actual);
    }

    @Test
    void bookAlreadyExistingTicketTest() {
        when(ticketDao.bookTicket(1, 1, 1, 111, Ticket.Category.STANDARD)).thenThrow(IllegalStateException.class);
        assertThrows(IllegalStateException.class, () -> ticketService.bookTicket(1, 1, 1, 111, Ticket.Category.STANDARD));
    }

    @Test
    void getBookedTicketsByUserTest() {
        Ticket expected = new ITicket(1, 1, 1, 10, Ticket.Category.STANDARD);
        IUser user = new IUser(1, "user", "user@gmail.com");
        doReturn(singletonList(expected)).when(ticketDao).getBookedTicketsByUser(user, 1, 1);
        List<Ticket> actualTickets = ticketService.getBookedTicketsByUser(user, 1, 1);
        assertEquals(1, actualTickets.size());
        assertEquals(expected, actualTickets.get(0));
    }

    @Test
    void getBookedTicketsByEventTest() {
        Ticket expected = new ITicket(1, 1, 1, 10, Ticket.Category.STANDARD);
        IEvent event = new IEvent(1, "event", new Date());
        doReturn(singletonList(expected)).when(ticketDao).getBookedTicketsByEvent(event, 1, 1);
        List<Ticket> actualTickets = ticketService.getBookedTicketsByEvent(event, 1, 1);
        assertEquals(1, actualTickets.size());
        assertEquals(expected, actualTickets.get(0));
    }

    @Test
    void cancelTicketByIdTrueTest() {
        when(ticketDao.cancelTicketById(1)).thenReturn(true);
        assertTrue(ticketService.cancelTicketById(1));
    }
}