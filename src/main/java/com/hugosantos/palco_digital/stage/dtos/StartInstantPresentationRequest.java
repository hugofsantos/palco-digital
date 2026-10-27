package com.hugosantos.palco_digital.stage.dtos;

import java.util.UUID;

public record StartInstantPresentationRequest(
    UUID channelId,
    String title
) {}