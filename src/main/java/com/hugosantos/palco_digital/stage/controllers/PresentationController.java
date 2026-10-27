package com.hugosantos.palco_digital.stage.controllers;

import com.hugosantos.palco_digital.stage.usecases.StartPresentationUseCase;
import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hugosantos.palco_digital.stage.dtos.PresentationResponse;
import com.hugosantos.palco_digital.stage.dtos.SchedulePresentationRequest;
import com.hugosantos.palco_digital.stage.dtos.StartInstantPresentationRequest;
import com.hugosantos.palco_digital.stage.usecases.CancelPresentationUseCase;
import com.hugosantos.palco_digital.stage.usecases.FinishPresentationUseCase;
import com.hugosantos.palco_digital.stage.usecases.SchedulePresentationUseCase;
import com.hugosantos.palco_digital.stage.usecases.StartInstantPresentationUseCase;

@RestController
@RequestMapping("/presentations")
public class PresentationController {
  private final SchedulePresentationUseCase schedulePresentationUseCase;
  private final StartInstantPresentationUseCase startInstantPresentationUseCase;
  private final StartPresentationUseCase startPresentationUseCase;
  private final FinishPresentationUseCase finishPresentationUseCase;
  private final CancelPresentationUseCase cancelPresentationUseCase;

  public PresentationController(
    SchedulePresentationUseCase schedulePresentationUseCase, 
    StartPresentationUseCase startPresentationUseCase,
    StartInstantPresentationUseCase startInstantPresentationUseCase,
    FinishPresentationUseCase finishPresentationUseCase,
    CancelPresentationUseCase cancelPresentationUseCase
  ) {
    this.schedulePresentationUseCase = schedulePresentationUseCase;
    this.startInstantPresentationUseCase = startInstantPresentationUseCase;
    this.startPresentationUseCase = startPresentationUseCase;
    this.finishPresentationUseCase = finishPresentationUseCase;
    this.cancelPresentationUseCase = cancelPresentationUseCase;
  }

  // TODO: Verify if the user is the owner of the channel before scheduling a presentation
  @PostMapping()
  public ResponseEntity<PresentationResponse> schedule(@RequestBody SchedulePresentationRequest request) {
    final PresentationResponse response = schedulePresentationUseCase.schedule(request);
    return ResponseEntity.created(URI.create("/presentations/" + response.id())).body(response);
  }

  // TODO: Verify if the user is the owner of the channel before starting an instant presentation
  @PostMapping("/instant")
  public ResponseEntity<PresentationResponse> startInstant(@RequestBody StartInstantPresentationRequest request) {
    final PresentationResponse response = startInstantPresentationUseCase.start(request);      
    return ResponseEntity.created(URI.create("/presentations/" + response.id())).body(response);
  }
  

  // TODO: Verify if the user is the owner of the channel before starting a presentation
  @PatchMapping("/{presentationId}/start")
  public ResponseEntity<Void> startLive(@PathVariable UUID presentationId) {
    startPresentationUseCase.start(presentationId);
    return ResponseEntity.noContent().build();
  }

  // TODO: Verify if the user is the owner of the channel before finishing a presentation
  @PatchMapping("/{presentationId}/finish")
  public ResponseEntity<Void> finishLive(@PathVariable UUID presentationId) {
    finishPresentationUseCase.finish(presentationId);

    return ResponseEntity.noContent().build();
  }

  // TODO: Verify if the user is the owner of the channel before canceling a presentation
  @PatchMapping("/{presentationId}/cancel")
  public ResponseEntity<Void> cancel(@PathVariable UUID presentationId) {
    cancelPresentationUseCase.cancel(presentationId);
   
    return ResponseEntity.noContent().build();
  }
}
