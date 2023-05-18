package com.epam.springmvc.dao.implementation;

import com.epam.springmvc.dao.EventDao;
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

    @Override
    public Event getById(long id) {
        return bookingStorage.getEvents().get(id);
    }

    @Override
    public List<Event> readAll() {
        return new ArrayList<>(bookingStorage.getEvents().values());
    }

    @Override
    public Event create(Event event) {
        event.setId(bookingStorage.getNextId(Event.class));
        bookingStorage.getEvents().put(event.getId(), event);
        return bookingStorage.getEvents().get(event.getId());
    }

    @Override
    public Event update(Event obj) {
        return bookingStorage.getEvents().replace(obj.getId(), obj);
    }

    @Override
    public boolean deleteById(long id) {
        bookingStorage.getEvents().remove(id);
        return true;
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        List<Event> events = new ArrayList<>(bookingStorage.getEvents().values());
        return events.stream()
                .filter(event -> Objects.equals(event.getTitle(), title))
                .skip((pageNum - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    @Override
    public List<Event> getEventsByDay(Date day, int pageSize, int pageNum) {
        List<Event> events = new ArrayList<>(bookingStorage.getEvents().values());
        return events.stream()
                .filter(event -> Objects.equals(event.getDate(), day))
                .skip((pageNum - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public void setBookingStorage(BookingStorage bookingStorage) {
        this.bookingStorage = bookingStorage;
    }
}