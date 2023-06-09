package com.epam.springmvc.model;

import com.epam.springmvc.storage.Base;

import java.util.Date;

/**
 * Created by maksym_govorischev.
 */
public interface Event extends Base {
    /**
     * Event id. UNIQUE.
     * @return Event Id
     */
    long getId();
    void setId(long id);
    String getTitle();
    void setTitle(String title);
    Date getDate();
    void setDate(Date date);
}