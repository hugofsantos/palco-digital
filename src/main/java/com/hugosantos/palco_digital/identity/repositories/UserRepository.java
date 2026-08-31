package com.hugosantos.palco_digital.identity.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hugosantos.palco_digital.identity.models.User;

public interface UserRepository extends JpaRepository<User, UUID> {
  boolean existsByEmail(String email);
}
