package com.example.eventplanner.service;

import com.example.eventplanner.dto.ParticipantDto;
import com.example.eventplanner.model.Event;
import com.example.eventplanner.model.Participant;
import com.example.eventplanner.repository.EventRepository;
import com.example.eventplanner.repository.ParticipantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final EventRepository eventRepository;

    public ParticipantService(ParticipantRepository participantRepository, EventRepository eventRepository) {
        this.participantRepository = participantRepository;
        this.eventRepository = eventRepository;
    }

    public Participant createParticipant(Participant participant) {
        return participantRepository.save(participant);
    }

    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }

    public Participant getParticipantById(Long id) {
        return participantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Participant not found with id: " + id));
    }

    public Participant updateParticipant(Long id, Participant updatedParticipant) {
        Participant existing = getParticipantById(id);
        existing.setFirstName(updatedParticipant.getFirstName());
        existing.setLastName(updatedParticipant.getLastName());
        existing.setEmail(updatedParticipant.getEmail());
        existing.setEvent(updatedParticipant.getEvent());
        return participantRepository.save(existing);
    }

    public void deleteParticipant(Long id) {
        participantRepository.deleteById(id);
    }

    // 🔄 DTO → Entity
    public Participant toEntity(ParticipantDto dto) {
        Participant participant = new Participant();
        participant.setId(dto.getId());
        participant.setFirstName(dto.getFirstName());
        participant.setLastName(dto.getLastName());
        participant.setEmail(dto.getEmail());

        if (dto.getEventId() != null) {
            Event event = eventRepository.findById(dto.getEventId())
                    .orElseThrow(() -> new RuntimeException("Event not found with id: " + dto.getEventId()));
            participant.setEvent(event);
        }

        return participant;
    }

    // 🔄 Entity → DTO
    public ParticipantDto toDto(Participant participant) {
        ParticipantDto dto = new ParticipantDto();
        dto.setId(participant.getId());
        dto.setFirstName(participant.getFirstName());
        dto.setLastName(participant.getLastName());
        dto.setEmail(participant.getEmail());

        if (participant.getEvent() != null) {
            dto.setEventId(participant.getEvent().getId());
        }

        return dto;
    }
}