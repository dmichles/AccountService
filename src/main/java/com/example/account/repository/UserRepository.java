package com.example.account.repository;


import com.example.account.models.entities.User;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    public User findUserByEmail(String email);
    public User findUserById(Long id);
    public boolean existsUserByEmail(String email);
    public List<User> findAll();

    @Query("UPDATE User u SET u.failedAttempt = ?1 WHERE u.email = ?2")
    @Transactional
    @Modifying /*(flushAutomatically = true) */
    public void updateFailedAttempts(int failAttempts, String email);
}
