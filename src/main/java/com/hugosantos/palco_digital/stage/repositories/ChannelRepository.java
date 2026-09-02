package com.hugosantos.palco_digital.stage.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hugosantos.palco_digital.stage.models.Channel;

public interface ChannelRepository extends JpaRepository<Channel, UUID> {
  boolean existsByOwnerId(UUID ownerId);
}
