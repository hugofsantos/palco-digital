package com.hugosantos.palco_digital.stage.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hugosantos.palco_digital.stage.dtos.PresentationResponse;
import com.hugosantos.palco_digital.stage.dtos.SchedulePresentationRequest;
import com.hugosantos.palco_digital.stage.usecases.SchedulePresentationUseCase;

@RestController
@RequestMapping("/presentations")
public class PresentationController {
  private final SchedulePresentationUseCase schedulePresentationUseCase;

  public PresentationController(SchedulePresentationUseCase schedulePresentationUseCase) {
    this.schedulePresentationUseCase = schedulePresentationUseCase;
  }

  @PostMapping()
  public ResponseEntity<PresentationResponse> schedule(@RequestBody SchedulePresentationRequest request) {
    final PresentationResponse response = schedulePresentationUseCase.schedule(request);
    return ResponseEntity.created(URI.create("/presentations/" + response.id())).body(response);
  }
}
