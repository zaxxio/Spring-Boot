package com.avaand.app.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID roleId;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return getRoleId() != null && Objects.equals(getRoleId(), role.getRoleId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
