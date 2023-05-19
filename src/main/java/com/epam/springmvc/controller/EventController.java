package com.epam.springmvc.controller;

import com.epam.springmvc.facade.BookingFacade;
import com.epam.springmvc.model.Event;
import com.epam.springmvc.model.implementation.EventImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Controller
@RequestMapping("/events")
public class EventController {
    private final BookingFacade bookingFacade;
    Calendar calendar = Calendar.getInstance();
    Date today = calendar.getTime();

    @Autowired
    public EventController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("CEST"));
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/event/{eventId}")
    public String getEventsById(@PathVariable("eventId") long eventId, Model model) {
        List<Event> events = List.of(bookingFacade.getEventById(eventId));
        model.addAttribute("events", events);
        return "events-list";
    }

    @GetMapping("/title/{title}")
    public String getEventsByTitle(@PathVariable("title") String title, Model model) {
        List<Event> events = bookingFacade.getEventsByTitle(title, 1, 1);
        model.addAttribute("events", events);
        return "events-list";
    }

    @GetMapping("/day/{day}")
    public String getEventsByDay(@PathVariable("day") Date day, Model model) {
        List<Event> events = bookingFacade.getEventsForDay(day, 1, 1);
        model.addAttribute("events", events);
        return "events-list";
    }

    @GetMapping("/create")
    public String createEvent(Model model) {
        Event event = new EventImpl();
        event.setTitle("title");
        event.setDate(today);
        bookingFacade.createEvent(event);
        model.addAttribute("events", event);
        return "events-list";
    }

    @GetMapping("/update/{eventId}")
    public String updateEvent(@PathVariable("eventId") long eventId, Model model) {
        Event event = bookingFacade.getEventById(eventId);
        if (event != null) {
            event.setTitle("updatedTitle");
            event.setDate(today);
            bookingFacade.updateEvent(event);
        }
        model.addAttribute("users", event);
        return "events-list";
    }

    @GetMapping("/delete/{eventId}")
    public String deleteEvent(@PathVariable("eventId") long eventId, Model model) {
        boolean deleted = bookingFacade.deleteEvent(eventId);
        if (deleted) {
            model.addAttribute("message", "Event deleted successfully");
        } else {
            model.addAttribute("message", "Event not found");
        }
        return "events-list";
    }
}