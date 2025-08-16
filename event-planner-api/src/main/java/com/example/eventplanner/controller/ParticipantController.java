package com.example.eventplanner.controller;

import com.example.eventplanner.dto.ParticipantDto;
import com.example.eventplanner.model.Event;
import com.example.eventplanner.model.Participant;
import com.example.eventplanner.service.EventService;
import com.example.eventplanner.service.ParticipantService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participants")
public class ParticipantController {

    private final ParticipantService participantService;
    private final EventService eventService;

    public ParticipantController(ParticipantService participantService, EventService eventService) {
        this.participantService = participantService;
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<ParticipantDto> createParticipant(@Valid @RequestBody ParticipantDto dto) {
        Participant participant = participantService.toEntity(dto);

        if (dto.getEventId() != null) {
            Event event = eventService.getEventById(dto.getEventId());
            participant.setEvent(event);
        }

        Participant saved = participantService.createParticipant(participant);
        return ResponseEntity.ok(participantService.toDto(saved));
    }

    @GetMapping
    public ResponseEntity<List<ParticipantDto>> getAllParticipants() {
        List<Participant> participants = participantService.getAllParticipants();
        List<ParticipantDto> dtos = participants.stream()
                .map(participantService::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipantDto> getParticipantById(@PathVariable Long id) {
        Participant participant = participantService.getParticipantById(id);
        return ResponseEntity.ok(participantService.toDto(participant));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParticipantDto> updateParticipant(@PathVariable Long id, @Valid @RequestBody ParticipantDto dto) {
        Participant updated = participantService.updateParticipant(id, participantService.toEntity(dto));
        return ResponseEntity.ok(participantService.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable Long id) {
        participantService.deleteParticipant(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{participantId}/assign-event/{eventId}")
    public ResponseEntity<ParticipantDto> assignEventToParticipant(
            @PathVariable Long participantId,
            @PathVariable Long eventId) {

        Participant participant = participantService.getParticipantById(participantId);
        Event event = eventService.getEventById(eventId); // Du brauchst diesen Service

        participant.setEvent(event);
        Participant updated = participantService.createParticipant(participant); // save()

        return ResponseEntity.ok(participantService.toDto(updated));
    }
}