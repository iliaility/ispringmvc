package storage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exception.JsonParsingException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import model.Event;
import model.Ticket;
import model.User;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@Slf4j
@Setter
public class BookingStorage {
    private ObjectMapper mapper;

    private final ConcurrentHashMap<String, List<? extends Base>> storage = new ConcurrentHashMap<>();

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
        List<Base> data = lines.stream().map(line -> {
            try {
                return mapper.readValue(line, clazz);
            } catch (JsonProcessingException e) {
                throw new JsonParsingException("Error while parsing JSON data", e);
            }
        }).collect(Collectors.toList());
        storage.put(clazz.getSimpleName(), data);
        log.info("Data was read successfully!");
    }

    public List<? extends Base> getData(Class<? extends Base> clazz) {
        return storage.get(clazz.getSimpleName());
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