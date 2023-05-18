package com.epam.springmvc.dao;

import java.util.Date;
import java.util.List;

import com.epam.springmvc.model.Event;

public interface EventDao extends Dao<Event> {
        @Override
        Event getById(long id);

        @Override
        Event create(Event obj);

        @Override
        Event update(Event obj);

        @Override
        boolean deleteById(long id);

        List<Event> getEventsByTitle(String title, int pageSize, int pageNum);

        List<Event> getEventsByDay(Date day, int pageSize, int pageNum);

}