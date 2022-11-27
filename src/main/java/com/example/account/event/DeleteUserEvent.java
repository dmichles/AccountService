package com.example.account.event;

import com.example.account.models.entities.User;
import com.example.account.models.entities.constants.EventAction;

public class DeleteUserEvent extends UserChangeEvent{
    public DeleteUserEvent(User user) {
        super(EventAction.DELETE_USER, user);
    }
}
