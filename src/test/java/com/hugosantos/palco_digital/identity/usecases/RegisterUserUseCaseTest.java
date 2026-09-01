package com.hugosantos.palco_digital.identity.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hugosantos.palco_digital.identity.dtos.CreateUserRequest;
import com.hugosantos.palco_digital.identity.dtos.UserResponse;
import com.hugosantos.palco_digital.identity.models.Group;
import com.hugosantos.palco_digital.identity.models.User;
import com.hugosantos.palco_digital.identity.repositories.GroupRepository;
import com.hugosantos.palco_digital.identity.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class RegisterUserUseCaseTest {
  @Mock
  private UserRepository userRepository;

  @Mock
  private GroupRepository groupRepository;

  @InjectMocks
  private RegisterUserUseCase registerUserUseCase;

  @Test
  @DisplayName("Should successfully register user and assign default group")
  void register_successful() {
    // Arrange
    final CreateUserRequest request = new CreateUserRequest("John Doe", "john.doe@email.com");
    final Group defaultGroup = new Group("ESPECTADOR_COMUM");
    
    when(userRepository.existsByEmail(request.email())).thenReturn(false);
    when(groupRepository.findByName("ESPECTADOR_COMUM")).thenReturn(Optional.of(defaultGroup));
    
    when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
      final User savedUser = invocation.getArgument(0);
      return savedUser; 
    });

    // Act
    final UserResponse response = registerUserUseCase.register(request);

    // Assert
    assertNotNull(response);
    assertEquals("John Doe", response.name());
    assertTrue(response.groups().contains("ESPECTADOR_COMUM"));

    verify(userRepository, times(1)).existsByEmail(request.email());
    verify(groupRepository, times(1)).findByName("ESPECTADOR_COMUM");
    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  @DisplayName("Should throw exception when user with email already exists")
  void register_userAlreadyExists() {
    // Arrange
    final CreateUserRequest request = new CreateUserRequest("John Doe", "john.doe@email.com");
    when(userRepository.existsByEmail(request.email())).thenReturn(true);

    // Act & Assert
    final IllegalArgumentException exception = assertThrows(
      IllegalArgumentException.class, 
      () -> registerUserUseCase.register(request)
    );

    assertEquals("User with email " + request.email() + " already exists.", exception.getMessage());
    verify(userRepository, times(1)).existsByEmail(request.email());
    verify(groupRepository, never()).findByName("ESPECTADOR_COMUM");
    verify(userRepository, never()).save(any(User.class));
  }

  @Test
  @DisplayName("Should throw exception when default group is not found")
  void register_defaultGroupNotFound() {
    // Arrange
    final CreateUserRequest request = new CreateUserRequest("John Doe", "john.doe@email.com");
    when(userRepository.existsByEmail(request.email())).thenReturn(false);
    when(groupRepository.findByName("ESPECTADOR_COMUM")).thenReturn(Optional.empty());

    // Act & Assert
    final IllegalStateException exception = assertThrows(
      IllegalStateException.class,
      () -> registerUserUseCase.register(request)
    );

    assertEquals("Default group not found in database", exception.getMessage());
    verify(userRepository, times(1)).existsByEmail(request.email());
    verify(groupRepository, times(1)).findByName("ESPECTADOR_COMUM");
    verify(userRepository, never()).save(any(User.class));
  }
}
