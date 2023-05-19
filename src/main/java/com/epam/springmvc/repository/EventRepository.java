package com.epam.springmvc.repository;

import com.epam.springmvc.exception.JsonParsingException;
import com.epam.springmvc.model.Event;
import com.epam.springmvc.model.implementation.EventImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class EventRepository {
    private final String filePath = "data/events.txt";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();

        try {
            Resource resource = new ClassPathResource(filePath);
            List<String> lines = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8);

            for (String line : lines) {
                Event event = objectMapper.readValue(line, EventImpl.class);
                events.add(event);
            }
        } catch (IOException e) {
            throw new JsonParsingException("Error while parsing JSON data", e);
        }
        return events;
    }
}