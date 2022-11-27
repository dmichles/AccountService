package com.example.account.event;

import com.example.account.models.entities.User;
import com.example.account.models.entities.constants.EventAction;
import com.example.account.models.entities.constants.RoleOperation;
import com.example.account.models.entities.constants.UserRole;

public class UpdateRoleEvent extends UserChangeEvent{
    public UpdateRoleEvent(User user, UserRole role, RoleOperation operation) {
        super(operation == RoleOperation.GRANT ? EventAction.GRANT_ROLE : EventAction.REMOVE_ROLE, user);
        String object = getObject();
        setObject(operation == RoleOperation.REMOVE
                ? "Remove role " + role.name() + " from " + object
                : "Grant role " + role.name() + " to " + object
        );
    }
}
