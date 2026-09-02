package com.hugosantos.palco_digital.stage.dtos;

import java.time.OffsetDateTime;
import java.util.UUID;

public record PresentationResponse (
  UUID id,
  UUID channelId,
  String title,
  OffsetDateTime scheduledTime,
  String status
){}
