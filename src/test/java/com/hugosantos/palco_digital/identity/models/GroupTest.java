package com.hugosantos.palco_digital.identity.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GroupTest {
  @Test
  @DisplayName("Given a valid group name, when creating a Group, then it should be created successfully")
  void constructor_validName() {
    // Arrange
    final String groupName = "ESPECTADOR_COMUM";

    // Act
    final Group group = new Group(groupName);

    // Assert
    assertEquals(groupName, group.getName());
  }

  @Test
  @DisplayName("Given a null group name, when creating a Group, then it should throw an IllegalArgumentException")
  void constructor_nullName() {
    // Arrange
    final String groupName = null;

    // Act & Assert
    final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      new Group(groupName);
    });

    assertEquals("Group name cannot be null or empty", exception.getMessage());
  }

  @Test
  @DisplayName("Given an empty group name, when creating a Group, then it should throw an IllegalArgumentException")
  void constructor_emptyName() {
    // Arrange
    final String groupName = "   ";

    // Act & Assert
    final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      new Group(groupName);
    });

    assertEquals("Group name cannot be null or empty", exception.getMessage());
  }
}
