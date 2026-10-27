package com.hugosantos.palco_digital.stage.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChannelTest {

  @Test
  @DisplayName("Given valid data, when creating a Channel, then it should be created successfully")
  void constructor_validData() {
    final UUID ownerId = UUID.randomUUID();
    final String name = "Tech Talk Channel";
    final String description = "Channel for tech discussions";

    final Channel channel = new Channel(ownerId, name, description);

    assertNotNull(channel);
    assertEquals(ownerId, channel.getOwnerId());
    assertEquals(name, channel.getName());
    assertEquals(description, channel.getDescription());
  }

  @Test
  @DisplayName("Given null ownerId, when creating a Channel, then it should throw IllegalArgumentException")
  void constructor_nullOwnerId() {
    final IllegalArgumentException exception = assertThrows(
      IllegalArgumentException.class,
      () -> new Channel(null, "Tech Talk", "Description")
    );
    assertEquals("Owner ID cannot be null", exception.getMessage());
  }

  @Test
  @DisplayName("Given null or blank name, when creating a Channel, then it should throw IllegalArgumentException")
  void constructor_invalidName() {
    final UUID ownerId = UUID.randomUUID();

    assertThrows(
      IllegalArgumentException.class,
      () -> new Channel(ownerId, null, "Description")
    );

    assertThrows(
      IllegalArgumentException.class,
      () -> new Channel(ownerId, "   ", "Description")
    );
  }
}
