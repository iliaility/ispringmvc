package com.epam.springmvc.storage;

import com.epam.springmvc.model.implementation.UserImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.epam.springmvc.exception.JsonParsingException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import com.epam.springmvc.model.Event;
import com.epam.springmvc.model.Ticket;
import com.epam.springmvc.model.User;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.util.*;


@Slf4j
@Setter
public class BookingStorage {
    private ObjectMapper mapper;
    private Map<Long, User> users = new HashMap<>();
    private Map<Long, Event> events = new HashMap<>();
    private Map<Long, Ticket> tickets = new HashMap<>();

    public Map<Long, User> getUsers() {
        return users;
    }

    public Map<Long, Event> getEvents() {
        return events;
    }

    public Map<Long, Ticket> getTickets() {
        return tickets;
    }

    public void loadData(Class<? extends Base> clazz, String path) throws IOException {
        log.info("Received path for test data: {}", path);
        Resource resource = new ClassPathResource(path);
        List<String> lines = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8);
        lines.forEach(line -> {
            try {
                Base base = mapper.readValue(line, clazz);
                if (base instanceof User) {
                    users.put(base.getId(), (User) base);
                } else if (base instanceof Event) {
                    events.put(base.getId(), (Event) base);
                } else if (base instanceof Ticket) {
                    tickets.put(base.getId(), (Ticket) base);
                }
            } catch (JsonProcessingException e) {
                throw new JsonParsingException("Error while parsing JSON data", e);
            }
        });

        log.info("Data was read successfully!");
    }

    public List<? extends Base> getData(Class<? extends Base> clazz) {
        if (clazz.equals(UserImpl.class)) {
//            User user1 = new UserImpl(getNextId(User.class), "user1", "user1@gmail.com");
//            User user2 = new UserImpl(getNextId(User.class), "user2", "user2@gmail.com");
            User user1 = new UserImpl(1, "user1", "user1@gmail.com");
            User user2 = new UserImpl(2, "user2", "user2@gmail.com");
            users.put(user1.getId(), user1);
            users.put(user2.getId(), user2);
            System.out.println(user1);
            System.out.println(user2);

            return new ArrayList<>(users.values());
        } else if (clazz.equals(Event.class)) {
            return new ArrayList<>(events.values());
        } else if (clazz.equals(Ticket.class)) {
            return new ArrayList<>(tickets.values());
        } else {
            throw new IllegalArgumentException("Unsupported class type for retrieving data");
        }
    }

    public long getNextId(Class<? extends Base> clazz) {
        if (clazz.equals(User.class)) {
            return users.keySet().stream()
                    .mapToLong(Long::valueOf)
                    .max()
                    .orElse(0L) + 1L;
        } else if (clazz.equals(Event.class)) {
            return events.keySet().stream()
                    .mapToLong(Long::valueOf)
                    .max()
                    .orElse(0L) + 1L;
        } else if (clazz.equals(Ticket.class)) {
            return tickets.keySet().stream()
                    .mapToLong(Long::valueOf)
                    .max()
                    .orElse(0L) + 1L;
        } else {
            throw new IllegalArgumentException("Unsupported class type for generating ID");
        }
    }
}