package com.epam.springmvc.model.implementation;

import com.epam.springmvc.model.Ticket;

import java.util.HashSet;
import java.util.Set;

public class TicketImpl implements Ticket {

    private long id;
    private static Set<Long> uniqueIds = new HashSet<>();
    private long userId;
    private long eventId;
    private int place;
    private Category category;

    public TicketImpl() {
    }

    public TicketImpl(long userId, long eventId, int place, Category category) {
        this.userId = userId;
        this.eventId = eventId;
        this.place = place;
        this.category = category;
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
    public long getEventId() {
        return eventId;
    }

    @Override
    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    @Override
    public long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public Category getCategory() {
        return category;
    }

    @Override
    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public int getPlace() {
        return place;
    }

    @Override
    public void setPlace(int place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "Ticket: "
                + "id="
                + id
                + ", eventId="
                + eventId
                + ", userId="
                + userId
                + ", place="
                + place
                + ", category="
                + category
                + '.';
    }
}