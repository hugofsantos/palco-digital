package com.hugosantos.palco_digital.identity.dtos;

public record CreateUserRequest(
  String name,
  String email
) {}
