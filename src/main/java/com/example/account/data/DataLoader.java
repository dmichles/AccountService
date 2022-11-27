package com.example.account.data;

import com.example.account.models.entities.Group;
import com.example.account.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    private GroupRepository groupRepository;

    @Autowired
    public DataLoader(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
        createRoles();
    }

    private void createRoles() {
        try {
            groupRepository.save(new Group("ADMINISTRATOR"));
            groupRepository.save(new Group("USER"));
            groupRepository.save(new Group("ACCOUNTANT"));
            groupRepository.save(new Group("AUDITOR"));
        } catch (Exception e) {

        }
    }
}
