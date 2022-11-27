package com.example.account.event;

import com.example.account.models.entities.User;
import com.example.account.models.entities.constants.EventAction;

public class ChangePasswordEvent extends UserChangeEvent {

    public ChangePasswordEvent(User user) {
        super(EventAction.CHANGE_PASSWORD, user);
    }
}
