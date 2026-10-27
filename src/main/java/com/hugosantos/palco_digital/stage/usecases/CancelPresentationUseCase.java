package com.hugosantos.palco_digital.stage.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hugosantos.palco_digital.stage.models.Presentation;
import com.hugosantos.palco_digital.stage.repositories.PresentationRepository;

@Service
public class CancelPresentationUseCase {
  private final PresentationRepository presentationRepository;

  public CancelPresentationUseCase(PresentationRepository presentationRepository) {
    this.presentationRepository = presentationRepository;
  }

  @Transactional
  public void cancel(UUID presentationId) {
    final Presentation presentation = presentationRepository.findById(presentationId)
        .orElseThrow(() -> new IllegalArgumentException("Presentation not found"));

    presentation.cancel();
    presentationRepository.save(presentation);
  }
}
