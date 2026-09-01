package com.hugosantos.palco_digital.identity.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {
  @Test
  @DisplayName("Constructor should create a User with the provided name and email, and an empty set of groups")
  void constructor_validData() {
    // Arrange
    final String name = "John Doe";
    final String email = "john.doe@example.com";
    // Act
    final User user = new User(name, email);

    // Assert
    assertEquals(name, user.getName());
    assertEquals(email, user.getEmail());
    assertTrue(user.getGroups().isEmpty());
  }

  @Test
  @DisplayName("addToGroup should add a group to the user's set of groups")
  void addToGroup_validGroup() {
    // Arrange
    final User user = new User("John Doe", "john.doe@example.com");
    final Group group = new Group("ESPECTADOR_COMUM");

    // Act
    user.addToGroup(group);

    // Assert
    assertEquals(1, user.getGroups().size());
    assertTrue(user.getGroups().contains(group));
  }

  @Test
  @DisplayName("addToGroup should throw IllegalArgumentException when the group is null")
  void addToGroup_nullGroup() {
    // Arrange
    final User user = new User("John Doe", "john.doe@example.com");

    // Act & Assert
    final IllegalArgumentException ex = assertThrows(
      IllegalArgumentException.class, 
      () -> user.addToGroup(null)
    );

    assertEquals("Group cannot be null", ex.getMessage());
  }

  @Test
  @DisplayName("removeFromGroup should remove a group from the user's set of groups")
  void removeFromGroup_validGroup() {
    // Arrange
    final User user = new User("John Doe", "john.doe@example.com");
    final Group group = new Group("ESPECTADOR_COMUM");
    user.addToGroup(group);

    // Act
    user.removeFromGroup(group);

    // Assert
    assertTrue(user.getGroups().isEmpty());
  }

  @Test
  @DisplayName("removeFromGroup should throw IllegalArgumentException when the group is null")
  void removeFromGroup_nullGroup() {
    // Arrange
    final User user = new User("John Doe", "john.doe@example.com");

    // Act & Assert
    final IllegalArgumentException ex = assertThrows(
      IllegalArgumentException.class, 
      () -> user.removeFromGroup(null)
    );

    assertEquals("Group cannot be null", ex.getMessage());
  }
}
