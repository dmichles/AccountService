package com.example.account.event;

import com.example.account.models.entities.User;
import com.example.account.models.entities.constants.EventAction;
import org.springframework.context.ApplicationEvent;

public class UserChangeEvent extends ApplicationEvent {
    private EventAction eventAction;

    private String object;

    private String subject;

    public UserChangeEvent(EventAction eventAction, User user) {
        super(user);
        this.eventAction = eventAction;
        this.object = user.getEmail().toLowerCase();
    }

    public EventAction getEventAction() {
        return eventAction;
    }

    public User getUser() {
        return (User) super.getSource();
    }

    public String getSubject() {
        return subject;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

}
