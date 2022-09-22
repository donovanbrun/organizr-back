package com.donovanbrun.organizr.Controller;

import com.donovanbrun.organizr.Entity.Event;
import com.donovanbrun.organizr.Service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path = "add")
    public ResponseEntity<Event> addTask(@RequestBody Event event) {
        Event e = this.eventService.addEvent(event);
        return new ResponseEntity<Event>(e, HttpStatus.CREATED);
    }
}
