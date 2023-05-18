package com.epam.springmvc.service.implementation;

import com.epam.springmvc.dao.EventDao;
import com.epam.springmvc.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.epam.springmvc.model.Event;
import org.springframework.stereotype.Service;
import com.epam.springmvc.service.EventService;

import java.util.*;

import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventDao eventDao;

    public Event getEventById(long id) {
        return ofNullable(eventDao.getById(id))
                .orElseThrow(() -> new NotFoundException("Event " + id + " not found"));
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        return eventDao.getEventsByTitle(title, pageSize, pageNum);
    }

    public List<Event> getEventsByDay(Date day, int pageSize, int pageNum) {
        return eventDao.getEventsByDay(day, pageSize, pageNum);
    }

    public Event createEvent(Event event) {
        return eventDao.create(event);
    }

    public Event updateEvent(Event event) {
        return eventDao.update(event);
    }

    @Override
    public boolean deleteEventById(long eventId) {
        return eventDao.deleteById(eventId);
    }
}