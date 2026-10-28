package com.hugosantos.palco_digital.stage.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StartInstantPresentationRequest(
    @NotNull(message = "Channel ID is required")
    UUID channelId,

    @NotBlank(message = "Title is required")
    String title
) {}