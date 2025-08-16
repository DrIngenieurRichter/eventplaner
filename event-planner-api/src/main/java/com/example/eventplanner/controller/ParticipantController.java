package com.example.eventplanner.controller;

import com.example.eventplanner.model.Participant;
import com.example.eventplanner.service.ParticipantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    private final ParticipantService participantService;

    @Autowired
    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @GetMapping
    public List<Participant> getAllParticipants() {
        return participantService.getAllParticipants();
    }

    @GetMapping("/{id}")
    public Participant getParticipantById(@PathVariable Long id) {
        return participantService.getParticipantById(id)
                .orElseThrow(() -> new RuntimeException("Participant not found"));
    }

    @PostMapping
    public Participant createParticipant(@Valid @RequestBody Participant participant) {
        return participantService.createParticipant(participant);
    }

    @DeleteMapping("/{id}")
    public void deleteParticipant(@PathVariable Long id) {
        participantService.deleteParticipant(id);
    }

    @PutMapping("/{participantId}/assign/{eventId}")
    public Participant assignToEvent(@PathVariable Long participantId, @PathVariable Long eventId) {
        return participantService.assignToEvent(participantId, eventId);
    }

    @PutMapping("/{participantId}/remove")
    public Participant removeFromEvent(@PathVariable Long participantId) {
        return participantService.removeFromEvent(participantId);
    }

    @GetMapping("/event/{eventId}")
    public List<Participant> getParticipantsByEvent(@PathVariable Long eventId) {
        return participantService.getParticipantsByEvent(eventId);
    }
}
