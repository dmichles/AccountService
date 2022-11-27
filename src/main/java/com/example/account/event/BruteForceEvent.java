package com.example.account.event;

import org.springframework.context.ApplicationEvent;

public class BruteForceEvent extends ApplicationEvent {
    public BruteForceEvent(String subject) {
        super(subject);
    }

    public String getSubject(){
        return (String) super.getSource();
    }
}
