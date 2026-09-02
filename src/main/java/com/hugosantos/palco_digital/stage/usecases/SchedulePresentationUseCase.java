package com.hugosantos.palco_digital.stage.usecases;

import org.springframework.stereotype.Service;

import com.hugosantos.palco_digital.stage.dtos.PresentationResponse;
import com.hugosantos.palco_digital.stage.dtos.SchedulePresentationRequest;
import com.hugosantos.palco_digital.stage.models.Channel;
import com.hugosantos.palco_digital.stage.models.Presentation;
import com.hugosantos.palco_digital.stage.repositories.ChannelRepository;
import com.hugosantos.palco_digital.stage.repositories.PresentationRepository;

import jakarta.transaction.Transactional;

@Service
public class SchedulePresentationUseCase {
  private final ChannelRepository channelRepository;
  private final PresentationRepository presentationRepository;

  public SchedulePresentationUseCase(ChannelRepository channelRepository, PresentationRepository presentationRepository) {
    this.channelRepository = channelRepository;
    this.presentationRepository = presentationRepository;
  }

  @Transactional
  public PresentationResponse schedule(SchedulePresentationRequest request) {
    final Channel channel = channelRepository.findById(request.channelId())
      .orElseThrow(() -> new IllegalArgumentException("Channel not found"));

    final Presentation presentation = new Presentation(
      channel,
      request.title(),
      request.scheduledTime()
    );

    final Presentation savedPresentation = presentationRepository.save(presentation);

    return new PresentationResponse(
      savedPresentation.getId(),
      savedPresentation.getChannel().getId(),
      savedPresentation.getTitle(),
      savedPresentation.getScheduledTime(),
      savedPresentation.getStatus().name()
    );
  }

}
