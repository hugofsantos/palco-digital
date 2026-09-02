package com.hugosantos.palco_digital.stage.dtos;

import java.util.UUID;

public record CreateChannelRequest(
  UUID ownerId,
  String name,
  String description
) {}
