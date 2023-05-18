package com.epam.springmvc.repository;

import com.epam.springmvc.model.User;
import com.epam.springmvc.exception.JsonParsingException;
import com.epam.springmvc.model.implementation.UserImpl;
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
public class UserRepository {
    private final String filePath = "data/users.txt";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try {
            Resource resource = new ClassPathResource(filePath);
            List<String> lines = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8);

            for (String line : lines) {
                User user = objectMapper.readValue(line, UserImpl.class);
                users.add(user);
            }
        } catch (IOException e) {
            throw new JsonParsingException("Error while parsing JSON data", e);
        }
        return users;
    }
}