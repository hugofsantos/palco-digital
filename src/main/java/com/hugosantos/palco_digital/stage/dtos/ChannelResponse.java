package com.hugosantos.palco_digital.stage.dtos;

import java.util.UUID;

public record ChannelResponse (
  UUID id,
  UUID ownerId,
  String name,
  String description
){}
