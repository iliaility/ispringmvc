package com.epam.springmvc.controller;

import com.epam.springmvc.facade.BookingFacade;

import com.epam.springmvc.model.User;
import com.epam.springmvc.model.implementation.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final BookingFacade bookingFacade;

    @Autowired
    public UserController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @GetMapping("/hello_user")
    public String hi() {
        return "hello_world";
    }

    @GetMapping("/user/{userId}")
    public String getUsers(@PathVariable("userId") long userId, Model model) {
        List<User> users = List.of(bookingFacade.getUserById(userId));
        model.addAttribute("users", users);
        return "users-list";
    }

    @GetMapping(path = "/email/{email}")
    public String getUserByEmail(@PathVariable("email") String email, Model model) {
        List<User> users = bookingFacade.getUsersByEmail(email, 1, 1);
        model.addAttribute("users", users);
        return "users-list";
    }

    @GetMapping(path = "/name/{name}")
    public String getUserByName(@PathVariable("name") String name, Model model) {
        List<User> users = bookingFacade.getUsersByName(name, 1, 1);
        model.addAttribute("users", users);
        return "users-list";
    }

    @GetMapping("/create")
    public String showCreateUserForm(Model model) {
        User user = new UserImpl(10, "user1", "user1@gmail.com");
        bookingFacade.createUser(user);
        return "create-user";
    }
}
