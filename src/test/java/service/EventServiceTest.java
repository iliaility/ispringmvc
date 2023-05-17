package service;

import dao.EventDao;
import exception.NotFoundException;
import model.Event;
import model.User;
import model.implementation.EventImpl;
import model.implementation.UserImpl;
import service.implementation.EventServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {
    @Mock
    private EventDao eventDao;

    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    void getByIdTest() {
        Event expected = new EventImpl( "Event 1", new Date());
        doReturn(expected).when(eventDao).getById(1);
        Event actual = eventDao.getById(1);
        assertEquals(expected, actual);
    }

    @Test
    void getEventByIdNotFoundTest() {
        long eventId = 1;
        doReturn(null).when(eventDao).getById(eventId);
        assertThrows(NotFoundException.class, () -> eventService.getEventById(eventId));
    }

    @Test
    void getEventsByTitleTest() {
        String title = "title";
        Event expected = new EventImpl(title,new Date());
        doReturn(singletonList(expected)).when(eventDao).getEventsByTitle(title, 1, 1);
        List<Event> actualEvents = eventService.getEventsByTitle(title, 1, 1);
        assertEquals(1, actualEvents.size());
        assertEquals(expected, actualEvents.get(0));
    }

    @Test
    void getNotExistingEventByTitleTest() {
        String title = "invalid title";
        doThrow(NotFoundException.class).when(eventDao).getEventsByTitle(title, 1, 1);
        assertThrows(NotFoundException.class, () -> eventService.getEventsByTitle(title, 1, 1));
    }


    @Test
    void getEventsByDayTest() {
        Date date = new Date();
        Event expected = new EventImpl( "title", date);
        doReturn(singletonList(expected)).when(eventDao).getEventsByDay(date, 1, 1);
        List<Event> actualEvents = eventService.getEventsByDay(date, 1, 1);
        assertEquals(1, actualEvents.size());
        assertEquals(expected, actualEvents.get(0));
    }

    @Test
    void getNotExistingEventByDayTest() {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        final Date invalidDate;
        try {
            invalidDate = format.parse("2023-10-30");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        doThrow(NotFoundException.class).when(eventDao).getEventsByDay(invalidDate, 1, 1);
        assertThrows(NotFoundException.class, () -> eventService.getEventsByDay(invalidDate, 1, 1));
    }

    @Test
    void createEventTest() {
        Event expected = new EventImpl( "Event 1", new Date());
        doReturn(expected).when(eventDao).create(expected);
        Event actual = eventService.createEvent(expected);
        assertEquals(expected, actual);
    }

    @Test
    void createAlreadyExistingEventTest() {
        when(eventDao.create(any())).thenThrow(IllegalStateException.class);
        assertThrows(IllegalStateException.class, () -> eventService.createEvent(any()));
    }

    @Test
    void updateEventTest() {
        Event expected = new EventImpl( "title", new Date());
        doReturn(expected).when(eventDao).update(expected);
        Event actual = eventService.updateEvent(expected);
        assertEquals(expected, actual);
    }

    @Test
    void updateNotExistingEventTest() {
        when(eventDao.update(any())).thenThrow(IllegalStateException.class);
        assertThrows(IllegalStateException.class, () -> eventService.updateEvent(any()));
    }

    @Test
    void deleteByIdTest() {
        doReturn(true).when(eventDao).deleteById(1);
        boolean actual = eventDao.deleteById(1);
        assertTrue(actual);
    }
}