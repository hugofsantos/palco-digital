package com.hugosantos.palco_digital.stage.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hugosantos.palco_digital.stage.models.Presentation;
import com.hugosantos.palco_digital.stage.repositories.PresentationRepository;

@Service
public class StartPresentationUseCase {
  private final PresentationRepository presentationRepository;

  public StartPresentationUseCase(PresentationRepository presentationRepository) {
    this.presentationRepository = presentationRepository;
  }

  @Transactional
  public void start(UUID presentationId) {
    final Presentation presentation = presentationRepository.findById(presentationId)
        .orElseThrow(() -> new IllegalArgumentException("Presentation not found"));

    presentation.goLive();
    presentationRepository.save(presentation);
  }
}
