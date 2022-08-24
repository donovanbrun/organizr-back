package com.donovanbrun.organizr.Controller;

import com.donovanbrun.organizr.Entity.Event;
import com.donovanbrun.organizr.Service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/event")
public class EventController {

    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(path = "user/{userId}")
    public List<Event> getEventsByUserId(@PathVariable String userId) {
        return this.eventService.getEventsByUserId(userId);
    }
}
