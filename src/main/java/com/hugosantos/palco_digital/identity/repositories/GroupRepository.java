package com.hugosantos.palco_digital.identity.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hugosantos.palco_digital.identity.models.Group;

public interface GroupRepository extends JpaRepository<Group, UUID> {
  Optional<Group> findByName(String name);
}
