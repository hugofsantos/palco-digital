package com.hugosantos.palco_digital.stage.dtos;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SchedulePresentationRequest (
    @NotNull(message = "Channel ID is required")
    UUID channelId,
    
    @NotBlank(message = "Title is required")
    String title,

    @NotNull(message = "Scheduled time is required")
    @Future(message = "Scheduled time must be in the future")
    OffsetDateTime scheduledTime
) {}
