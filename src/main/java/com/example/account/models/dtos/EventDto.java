package com.example.account.models.dtos;

import com.example.account.models.entities.Event;
import com.example.account.models.entities.constants.EventAction;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class EventDto {
    private Long id;

    private Date date;

    private String action;

    private String subject;

    private String object;

    private String path;

    public static EventDto of(Event event) {
        return new EventDto(
                event.getId(),
                event.getDate(),
                event.getAction().name(),
                event.getSubject(),
                event.getObject(),
                event.getPath()
        );
    }


}
