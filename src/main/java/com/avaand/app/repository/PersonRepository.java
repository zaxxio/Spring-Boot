package com.avaand.app.repository;

import com.avaand.app.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, String> {
    Person findByUserId(UUID uuid);
}
