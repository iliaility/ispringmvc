package com.epam.springmvc.controller;

import com.epam.springmvc.facade.BookingFacade;

import com.epam.springmvc.model.User;
import com.epam.springmvc.model.implementation.UserImpl;
import com.epam.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final BookingFacade bookingFacade;

   // @Autowired
    public UserController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @GetMapping("/user/{userId}")
    public String getUser(@PathVariable("userId") long userId, Model model) {
        model.addAttribute("users", bookingFacade.getUserById(userId));
        return "users-list";
    }

    @GetMapping(path = "/email/{email}")
    public String getUserByEmail(@PathVariable("email") String email, Model model) {
        model.addAttribute("users", bookingFacade.getUsersByEmail(email, 1, 1));
        return "users-list";
    }

    @GetMapping(path = "/name/{name}")
    public String getUserByName(@PathVariable("name") String name, Model model) {
        List<User> users = bookingFacade.getUsersByName(name, 1, 1);
        model.addAttribute("users", users);
        return "users-list";
    }

    @GetMapping("/create/{name}/{email}")
    public String createUser(Model model,@PathVariable("name") String name,@PathVariable("email") String email) {
        User user = new UserImpl();
        user.setName(name);
        user.setEmail(email);
        bookingFacade.createUser(user);
        model.addAttribute("users", user);
        return "users-list";
    }

    @GetMapping("/update/{userId}/{updatedName}/{updated-email}")
    public String updateUser(@PathVariable("userId") long userId,@PathVariable("updatedName") String name,@PathVariable("updated-email") String email, Model model) {
        User user = bookingFacade.getUserById(userId);
        if (user != null) {
            user.setName(name);
            user.setEmail(email);
            bookingFacade.updateUser(user);
        }
        model.addAttribute("users", user);
        return "users-list";
    }

    @GetMapping("/delete/{userId}")
    public String deleteUser(@PathVariable("userId") long userId, Model model) {
        boolean deleted = bookingFacade.deleteUser(userId);
        if (deleted) {
            model.addAttribute("message", "User deleted successfully");
        } else {
            model.addAttribute("message", "User not found");
        }
        return "users-list";
    }
}
