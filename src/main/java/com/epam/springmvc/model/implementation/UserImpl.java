package com.epam.springmvc.model.implementation;

import com.epam.springmvc.model.User;

import java.util.HashSet;
import java.util.Set;

public class UserImpl implements User {

    private long id;
    private static Set<Long> uniqueIds = new HashSet<>();
    private String name;
    private String email;
    private static Set<String> uniqueEmails = new HashSet<>();


    public UserImpl() {
    }

    public UserImpl( String name, String email) {
        this.name = name;
        this.email = email;
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
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        if (uniqueEmails.contains(email)) {
            throw new IllegalArgumentException("Email is already in use");
        }
        uniqueEmails.remove(this.email);
        this.email = email;
        uniqueEmails.add(email);
    }

    @Override
    public String toString() {
        return "User: " + "id=" + id + ", name='" + name + '\'' + ", email='" + email + '\'' + '.';
    }
}