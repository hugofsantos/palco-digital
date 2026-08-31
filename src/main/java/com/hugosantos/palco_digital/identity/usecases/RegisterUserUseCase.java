package com.hugosantos.palco_digital.identity.usecases;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hugosantos.palco_digital.identity.dtos.CreateUserRequest;
import com.hugosantos.palco_digital.identity.dtos.UserResponse;
import com.hugosantos.palco_digital.identity.models.Group;
import com.hugosantos.palco_digital.identity.models.User;
import com.hugosantos.palco_digital.identity.repositories.GroupRepository;
import com.hugosantos.palco_digital.identity.repositories.UserRepository;


@Service
public class RegisterUserUseCase {
  private final UserRepository userRepository;
  private final GroupRepository groupRepository;

  public RegisterUserUseCase(UserRepository userRepository, GroupRepository groupRepository) {
    this.userRepository = userRepository;
    this.groupRepository = groupRepository;
  }

  @Transactional
  public UserResponse register(CreateUserRequest request){ 
    if (userRepository.existsByEmail(request.email())) {
      throw new IllegalArgumentException("User with email " + request.email() + " already exists.");
    }

    final User user = new User(request.name(), request.email());

    final Group defaultGroup = groupRepository.findByName("ESPECTADOR_COMUM")
      .orElseThrow(() -> new IllegalStateException("Default group not found in database"));

    user.addToGroup(defaultGroup);

    final User savedUser = userRepository.save(user);

    Set<String> groups = savedUser.getGroups()
      .stream()
      .map(Group::getName)
      .collect(Collectors.toSet());

    return new UserResponse(
      savedUser.getId(), 
      savedUser.getName(),
      savedUser.getEmail(),
      groups
    );
  }
}
