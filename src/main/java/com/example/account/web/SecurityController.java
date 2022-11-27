package com.example.account.web;

import com.example.account.models.dtos.EventDto;
import com.example.account.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SecurityController {
    @Autowired
    EventRepository eventRepository;

    @GetMapping("/api/security/events")
    public List<EventDto> getEvents(){
        List<EventDto> list = eventRepository.getAllByOrderByDateAscIdAsc().stream().map(EventDto::of).collect(Collectors.toList());
        return list;
    }
}
