package com.hugosantos.palco_digital.stage.usecases;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hugosantos.palco_digital.stage.dtos.PresentationResponse;
import com.hugosantos.palco_digital.stage.dtos.StartInstantPresentationRequest;
import com.hugosantos.palco_digital.stage.models.Channel;
import com.hugosantos.palco_digital.stage.models.Presentation;
import com.hugosantos.palco_digital.stage.repositories.ChannelRepository;
import com.hugosantos.palco_digital.stage.repositories.PresentationRepository;

@Service
public class StartInstantPresentationUseCase {
  private final ChannelRepository channelRepository;
  private final PresentationRepository presentationRepository;

  public StartInstantPresentationUseCase(ChannelRepository channelRepository, PresentationRepository presentationRepository) {
    this.channelRepository = channelRepository;
    this.presentationRepository = presentationRepository;
  }

  @Transactional
  public PresentationResponse start(StartInstantPresentationRequest request) {
    final Channel channel = channelRepository.findById(request.channelId())
        .orElseThrow(() -> new IllegalArgumentException("Channel not found"));
    
    final Presentation presentation = Presentation.createInstantPresentation(channel, request.title());

    final Presentation savedPresentation = presentationRepository.save(presentation);

    return new PresentationResponse(
      savedPresentation.getId(),
      savedPresentation.getChannelId(),
      savedPresentation.getTitle(),
      savedPresentation.getScheduledTime(),
      savedPresentation.getPresentationStatusName()
    );
  }
}
