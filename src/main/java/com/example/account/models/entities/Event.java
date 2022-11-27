package com.example.account.models.entities;

import com.example.account.models.entities.constants.EventAction;
import lombok.Data;


import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Date date;

    private EventAction action;

    private String subject;

    private String object;

    private String path;

}
