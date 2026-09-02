package com.hugosantos.palco_digital.stage.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hugosantos.palco_digital.stage.models.Presentation;

public interface PresentationRepository extends JpaRepository<Presentation, UUID> {}
