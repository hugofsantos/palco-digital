package com.hugosantos.palco_digital.identity.dtos;

import java.util.Set;
import java.util.UUID;

public record UserResponse(
  UUID id,
  String name,
  String email,
  Set<String> groups
) {}