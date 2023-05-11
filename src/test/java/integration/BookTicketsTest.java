package integration;

import facade.BookingFacade;
import model.Ticket;
import model.implementation.EventImpl;
import model.implementation.UserImpl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/applicationContext.xml")
@TestPropertySource("classpath:application.properties")
public class BookTicketsTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testIntegrationScenario() {

        BookingFacade bookingFacade = applicationContext.getBean("bookingFacade", BookingFacade.class);

        // create test users
        UserImpl user1 = new UserImpl(1, "user1", "user1@gmail.com");
        UserImpl user2 = new UserImpl(2, "user2", "user2@gmail.com");
        UserImpl user3 = new UserImpl(3, "user3", "user3@gmail.com");
        bookingFacade.createUser(user1);
        bookingFacade.createUser(user2);
        bookingFacade.createUser(user3);

        // create test event
        EventImpl event = new EventImpl(1, "title", new Date());
        bookingFacade.createEvent(event);

        // book tickets for test users and event
        Ticket ticket1 = bookingFacade.bookTicket(1, user1.getId(), event.getId(), 10, Ticket.Category.STANDARD);
        Ticket ticket2 = bookingFacade.bookTicket(2, user1.getId(), event.getId(), 11, Ticket.Category.STANDARD);
        Ticket ticket3 = bookingFacade.bookTicket(3, user1.getId(), event.getId(), 12, Ticket.Category.STANDARD);
        Ticket ticket4 = bookingFacade.bookTicket(4, user2.getId(), event.getId(), 14, Ticket.Category.STANDARD);
        Ticket ticket5 = bookingFacade.bookTicket(5, user2.getId(), event.getId(), 15, Ticket.Category.STANDARD);
        Ticket ticket6 = bookingFacade.bookTicket(6, user2.getId(), event.getId(), 16, Ticket.Category.STANDARD);
        Ticket ticket7 = bookingFacade.bookTicket(7, user3.getId(), event.getId(), 17, Ticket.Category.STANDARD);
        Ticket ticket8 = bookingFacade.bookTicket(8, user3.getId(), event.getId(), 18, Ticket.Category.STANDARD);
        Ticket ticket9 = bookingFacade.bookTicket(9, user3.getId(), event.getId(), 19, Ticket.Category.STANDARD);

        // assert booked tickets for test user1 and event
        List<Ticket> expectedTicketsByUser = Arrays.asList(ticket1, ticket2, ticket3);
        List<Ticket> actualTicketsByUser = bookingFacade.getBookedTickets(user1, 1, 1);
        assertEquals(expectedTicketsByUser, actualTicketsByUser);

        List<Ticket> expectedTicketsByEvent = Arrays.asList(ticket1, ticket2, ticket3, ticket4, ticket5, ticket6, ticket7, ticket8, ticket9);
        List<Ticket> actualTicketsByEvent = bookingFacade.getBookedTickets(event, 1, 1);
        assertEquals(expectedTicketsByEvent, actualTicketsByEvent);

        // cancel user3 tickets
        assertTrue(bookingFacade.cancelTicket(ticket7.getId()));
        assertTrue(bookingFacade.cancelTicket(ticket8.getId()));
        assertTrue(bookingFacade.cancelTicket(ticket9.getId()));

        // assert booked tickets for test user3
        List<Ticket> actualTicketsByUser3 = bookingFacade.getBookedTickets(user3, 1, 1);
        assertTrue(actualTicketsByUser3.isEmpty());
    }
}