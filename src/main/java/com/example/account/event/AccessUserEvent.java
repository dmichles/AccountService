package com.example.account.event;

import com.example.account.models.entities.User;
import com.example.account.models.entities.constants.AccessOperation;
import com.example.account.models.entities.constants.EventAction;

public class AccessUserEvent extends UserChangeEvent{
    public AccessUserEvent(AccessOperation operation, User user) {
        super(operation == AccessOperation.UNLOCK ? EventAction.UNLOCK_USER : EventAction.LOCK_USER, user);
        String object = getObject();
        if (operation == AccessOperation.LOCK) {
            setSubject(object);
        }
        setObject(
                (operation == AccessOperation.UNLOCK ? "Unlock" : "Lock") + " user " + object);
    }
}
