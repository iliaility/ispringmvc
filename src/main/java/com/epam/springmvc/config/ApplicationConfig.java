package com.epam.springmvc.config;

import com.epam.springmvc.dao.implementation.EventDaoImp;
import com.epam.springmvc.dao.implementation.TicketDaoImpl;
import com.epam.springmvc.dao.implementation.UserDaoImpl;
import com.epam.springmvc.facade.BookingFacade;
import com.epam.springmvc.facade.BookingFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.epam.springmvc.service.implementation.EventServiceImpl;
import com.epam.springmvc.service.implementation.TicketServiceImpl;
import com.epam.springmvc.service.implementation.UserServiceImpl;
import com.epam.springmvc.storage.BookingStorage;

@Configuration
public class ApplicationConfig {

    @Autowired
    private BookingStorage bookingStorage;

    @Bean
    public EventDaoImp eventDao() {
        EventDaoImp eventDao = new EventDaoImp();
        eventDao.setBookingStorage(bookingStorage);
        return eventDao;
    }

    @Bean
    public TicketDaoImpl ticketDao() {
        TicketDaoImpl ticketDao = new TicketDaoImpl();
        ticketDao.setBookingStorage(bookingStorage);
        return ticketDao;
    }

    @Bean
    public UserDaoImpl userDao() {
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.setBookingStorage(bookingStorage);
        return userDao;
    }

    @Bean
    public UserServiceImpl userService() {
        UserServiceImpl userService = new UserServiceImpl(userDao());
        return userService;
    }

    @Bean
    public EventServiceImpl eventService() {
        EventServiceImpl eventService = new EventServiceImpl(eventDao());
        return eventService;
    }

    @Bean
    public TicketServiceImpl ticketService() {
        TicketServiceImpl ticketService = new TicketServiceImpl(ticketDao());
        return ticketService;
    }

    @Bean
    public BookingStorage bookingStorage() {
        return new BookingStorage();
    }

    @Bean
    public BookingFacade bookingFacade() {
        return new BookingFacadeImpl(userService(), eventService(), ticketService());
    }
}