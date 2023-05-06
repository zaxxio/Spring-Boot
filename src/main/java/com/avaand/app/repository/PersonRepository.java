package com.avaand.app.repository;

import com.avaand.app.domain.Person;
import jakarta.validation.constraints.NotNull;
import org.h2.mvstore.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, String> {
    Person findByUserId(@NotNull String userId);
}
