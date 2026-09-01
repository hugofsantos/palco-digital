package com.hugosantos.palco_digital.identity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hugosantos.palco_digital.identity.dtos.CreateUserRequest;
import com.hugosantos.palco_digital.identity.dtos.UserResponse;
import com.hugosantos.palco_digital.identity.usecases.RegisterUserUseCase;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/users")
public class UserController {
  private final RegisterUserUseCase registerUserUseCase;

  public UserController(RegisterUserUseCase registerUserUseCase) {
    this.registerUserUseCase = registerUserUseCase;
  }

  @PostMapping
  public ResponseEntity<UserResponse> create(@RequestBody CreateUserRequest request) {
    final UserResponse response = registerUserUseCase.register(request);  
    
    final URI location = URI.create("/users/" + response.id());
    return ResponseEntity.created(location).body(response);
  }
  
}
