package com.example.account.repository;

import com.example.account.models.entities.Group;
import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<Group, Long> {
    public Group findGroupByRole(String role);
}
