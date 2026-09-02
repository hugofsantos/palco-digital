package com.hugosantos.palco_digital.stage.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hugosantos.palco_digital.stage.dtos.ChannelResponse;
import com.hugosantos.palco_digital.stage.dtos.CreateChannelRequest;
import com.hugosantos.palco_digital.stage.usecases.CreateChannelUseCase;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/channels")
public class ChannelController {
  private final CreateChannelUseCase createChannelUseCase;

  public ChannelController(CreateChannelUseCase createChannelUseCase) {
    this.createChannelUseCase = createChannelUseCase;
  }

  @PostMapping()
  public ResponseEntity<ChannelResponse> create(@RequestBody CreateChannelRequest request) {
    final ChannelResponse entity = createChannelUseCase.create(request);      
    return ResponseEntity.created(URI.create("/channels/" + entity.id())).body(entity);
  }
  
}
