package com.donovanbrun.organizr.Service;

import com.donovanbrun.organizr.Entity.Event;
import com.donovanbrun.organizr.Repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getEvents() {
        return this.eventRepository.findAll();
    }

    public List<Event> getEventsByUserId(String userId) {
        return this.eventRepository.getEventsByUserId(userId);
    }

    public Event addEvent(Event event) {
        return this.eventRepository.save(event);
    }

    public Event updateEvent(Event event) {
        Event e;
        if (this.eventRepository.findById(event.getId()).isPresent()) {
            e = this.eventRepository.save(event);
            return e;
        }
        else throw new RuntimeException("Event doesn't exist");
    }

    public void deleteEvent(Integer eventId) {
        if (this.eventRepository.findById(eventId).isPresent()) {
            this.eventRepository.deleteById(eventId);
        }
        else throw new RuntimeException("Event doesn't exist");
    }
}
