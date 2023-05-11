package service;

import dao.TicketDao;
import model.Ticket;
import model.implementation.EventImpl;
import model.implementation.UserImpl;
import model.implementation.TicketImpl;
import service.implementation.TicketServiceImpl;
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
    private TicketServiceImpl ticketService;

    @Test
    void bookTicketTest() {
        Ticket expected = new TicketImpl(1, 1, 1, 10, Ticket.Category.STANDARD);
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
        Ticket expected = new TicketImpl(1, 1, 1, 10, Ticket.Category.STANDARD);
        UserImpl user = new UserImpl(1, "user", "user@gmail.com");
        doReturn(singletonList(expected)).when(ticketDao).getBookedTicketsByUser(user, 1, 1);
        List<Ticket> actualTickets = ticketService.getBookedTicketsByUser(user, 1, 1);
        assertEquals(1, actualTickets.size());
        assertEquals(expected, actualTickets.get(0));
    }

    @Test
    void getBookedTicketsByEventTest() {
        Ticket expected = new TicketImpl(1, 1, 1, 10, Ticket.Category.STANDARD);
        EventImpl event = new EventImpl(1, "event", new Date());
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