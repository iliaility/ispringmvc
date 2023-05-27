package com.epam.springmvc.model.implementation;

import com.epam.springmvc.model.Event;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class EventImpl implements Event {
    private long id;
    private static Set<Long> uniqueIds = new HashSet<>();
    private String title;
    private Date date;

    public EventImpl() {
    }

    public EventImpl( String title, Date date) {
        this.title = title;
        this.date = date;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        if (uniqueIds.contains(id)) {
            throw new IllegalArgumentException("ID is already in use");
        }
        uniqueIds.remove(this.id);
        this.id = id;
        uniqueIds.add(id);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Event: " + "id=" + id + ", title='" + title + '\'' + ", date=" + date + '.';
    }
}