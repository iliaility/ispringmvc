package com.epam.springmvc.util;

import com.epam.springmvc.dao.TicketDao;
import com.epam.springmvc.model.Ticket;
import com.epam.springmvc.model.implementation.TicketImpl;
import com.epam.springmvc.util.xml.TicketXml;
import com.epam.springmvc.util.xml.TicketsXml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

public class TicketBatchLoaderWithInputStream {
    private final TicketDao ticketDao;
    private final InputStream inputStream;

    public TicketBatchLoaderWithInputStream(TicketDao ticketDao, InputStream inputStream) {
        this.ticketDao = ticketDao;
        this.inputStream = inputStream;
    }


    public boolean preloadTickets2(InputStream inputStream) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(TicketsXml.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            TicketsXml ticketsXml = (TicketsXml) unmarshaller.unmarshal(inputStream);

            for (TicketXml ticketXml : ticketsXml.getTickets()) {
                Ticket ticket = convertToTicket(ticketXml);
                ticketDao.bookTicket(ticket.getId(), ticket.getUserId(), ticket.getEventId(), ticket.getPlace(), ticket.getCategory());
            }

            return true;
        } catch (JAXBException | NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Ticket convertToTicket(TicketXml ticketXml) {
        Ticket ticket = new TicketImpl();
        ticket.setId(ticketXml.getUserId());
        ticket.setUserId(ticketXml.getUserId());
        ticket.setEventId(ticketXml.getEventId());
        ticket.setPlace(ticketXml.getPlace());
        ticket.setCategory(ticketXml.getCategory());
        return ticket;
    }

}

