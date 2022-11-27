package com.example.account.repository;

import com.example.account.models.entities.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository extends CrudRepository<Event, Long> {
    List<Event> getAllByOrderByDateAscIdAsc();
}
