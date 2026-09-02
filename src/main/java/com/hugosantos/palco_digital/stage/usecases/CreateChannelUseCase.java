package com.hugosantos.palco_digital.stage.usecases;


import org.springframework.stereotype.Service;

import com.hugosantos.palco_digital.stage.dtos.ChannelResponse;
import com.hugosantos.palco_digital.stage.dtos.CreateChannelRequest;
import com.hugosantos.palco_digital.stage.models.Channel;
import com.hugosantos.palco_digital.stage.repositories.ChannelRepository;

import jakarta.transaction.Transactional;

@Service
public class CreateChannelUseCase {
  private final ChannelRepository channelRepository;

  public CreateChannelUseCase(ChannelRepository channelRepository) {
    this.channelRepository = channelRepository;
  }

  @Transactional
  public ChannelResponse create(CreateChannelRequest request) {
    if (channelRepository.existsByOwnerId(request.ownerId())) {
      throw new IllegalArgumentException("Owner already has a channel");
    }

    final Channel channel = new Channel(
      request.ownerId(),
      request.name(),
      request.description()
    );

    final Channel savedChannel = channelRepository.save(channel);

    return new ChannelResponse(
      savedChannel.getId(),
      savedChannel.getOwnerId(),
      savedChannel.getName(),
      savedChannel.getDescription()
    );
  }
}
