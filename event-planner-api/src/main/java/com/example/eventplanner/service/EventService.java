package com.example.eventplanner.service;

import com.example.eventplanner.model.Event;
import com.example.eventplanner.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
    // Holt alle Events
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
    // Holt ein Event nach ID
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }
    // Erstellt ein neues Event
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }
    // Aktualisiert ein bestehendes Event
    public Event updateEvent(Long id, Event updatedEvent) {
        return eventRepository.findById(id)
                .map(event -> {
                    event.setName(updatedEvent.getName());
                    event.setDate(updatedEvent.getDate());
                    event.setLocation(updatedEvent.getLocation());
                    event.setDescription(updatedEvent.getDescription());
                    return eventRepository.save(event);
                })
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }
    // Löscht ein Event
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
