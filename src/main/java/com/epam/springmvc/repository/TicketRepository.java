package com.epam.springmvc.repository;

import com.epam.springmvc.model.Ticket;
import com.epam.springmvc.exception.JsonParsingException;
import com.epam.springmvc.model.implementation.TicketImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TicketRepository {
    private final String filePath = "data/tickets.txt";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Ticket> getAllTickets() {
        List<Ticket> tickets = new ArrayList<>();

        try {
            Resource resource = new ClassPathResource(filePath);
            List<String> lines = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8);

            for (String line : lines) {
                Ticket ticket = objectMapper.readValue(line, TicketImpl.class);
                tickets.add(ticket);
            }
        } catch (IOException e) {
            throw new JsonParsingException("Error while parsing JSON data", e);
        }
        return tickets;
    }
}