package com.example.account.event;

import com.example.account.models.entities.User;
import com.example.account.models.entities.constants.EventAction;

public class CreateUserEvent extends UserChangeEvent{
    public CreateUserEvent( User user) {
        super(EventAction.CREATE_USER, user);
    }
}
