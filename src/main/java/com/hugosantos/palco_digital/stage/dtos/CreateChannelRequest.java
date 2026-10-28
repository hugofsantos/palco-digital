package com.hugosantos.palco_digital.stage.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateChannelRequest(
  @NotNull(message = "Owner ID is required")
  UUID ownerId,

  @NotBlank(message = "Channel name is required")
  String name,

  String description
) {}
