package com.epam.springmvc.dao.implementation;

import com.epam.springmvc.dao.EventDao;
import com.epam.springmvc.exception.NotFoundException;
import com.epam.springmvc.model.implementation.EventImpl;
import lombok.Setter;
import com.epam.springmvc.model.Event;
import com.epam.springmvc.storage.BookingStorage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Setter
public class EventDaoImp implements EventDao {

    private BookingStorage bookingStorage;

    public EventDaoImp(BookingStorage bookingStorage) {
        this.bookingStorage = bookingStorage;
    }

    private List<EventImpl> getEventsData() {
        return (List<EventImpl>) bookingStorage.getData(EventImpl.class);
    }

    @Override
    public List<Event> readAll() {
        return new ArrayList<>(bookingStorage.getEvents().values());
    }

    @Override
    public Event getById(long id) {
        return getEventsData().stream()
                .filter(event -> Objects.equals(event.getId(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Event create(Event event) {
        event.setId(bookingStorage.getNextId(Event.class));
        bookingStorage.getEvents().put(event.getId(), event);
        return bookingStorage.getEvents().get(event.getId());
    }

    @Override
    public Event update(Event event) {
        if (!bookingStorage.getUsers().containsKey(event.getId())){
            throw new NotFoundException("Event not found");
        }
        bookingStorage.getEvents().replace(event.getId(),event);
        return event;
    }

    @Override
    public boolean deleteById(long id) {
        if (!bookingStorage.getUsers().containsKey(id)){
            throw new NotFoundException("Event not found");
        }
        bookingStorage.getEvents().remove(id);
        return true;
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        List<Event> filteredEvents = getEventsData().stream()
                .filter(event -> Objects.equals(event.getTitle(), title))
                .skip((pageNum - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
        if (filteredEvents.isEmpty()) {
            throw new NotFoundException("Event with title " + title + " doesn't exist");
        }
        return filteredEvents;
    }

    @Override
    public List<Event> getEventsByDay(Date day, int pageSize, int pageNum) {
        List<Event> filteredEvents = getEventsData().stream()
                .filter(event -> Objects.equals(event.getDate(), day))
                .skip((pageNum - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
        if (filteredEvents.isEmpty()) {
            throw new NotFoundException("Event with date " + day + " doesn't exist");
        }
        return filteredEvents;
    }

    public void setBookingStorage(BookingStorage bookingStorage) {
        this.bookingStorage = bookingStorage;
    }
}