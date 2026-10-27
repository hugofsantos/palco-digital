package com.hugosantos.palco_digital.stage.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hugosantos.palco_digital.stage.dtos.ChannelResponse;
import com.hugosantos.palco_digital.stage.dtos.CreateChannelRequest;
import com.hugosantos.palco_digital.stage.models.Channel;
import com.hugosantos.palco_digital.stage.repositories.ChannelRepository;

@ExtendWith(MockitoExtension.class)
public class CreateChannelUseCaseTest {

  @Mock
  private ChannelRepository channelRepository;

  @InjectMocks
  private CreateChannelUseCase createChannelUseCase;

  @Test
  @DisplayName("Should create channel successfully when owner does not have a channel")
  void create_success() {
    final UUID ownerId = UUID.randomUUID();
    final CreateChannelRequest request = new CreateChannelRequest(ownerId, "Channel Name", "Channel Description");

    when(channelRepository.existsByOwnerId(ownerId)).thenReturn(false);
    when(channelRepository.save(any(Channel.class))).thenAnswer(invocation -> invocation.getArgument(0));

    final ChannelResponse response = createChannelUseCase.create(request);

    assertNotNull(response);
    assertEquals(ownerId, response.ownerId());
    assertEquals("Channel Name", response.name());
    assertEquals("Channel Description", response.description());

    verify(channelRepository).existsByOwnerId(ownerId);
    verify(channelRepository).save(any(Channel.class));
  }

  @Test
  @DisplayName("Should throw IllegalArgumentException when owner already has a channel")
  void create_alreadyHasChannel() {
    final UUID ownerId = UUID.randomUUID();
    final CreateChannelRequest request = new CreateChannelRequest(ownerId, "Channel Name", "Channel Description");

    when(channelRepository.existsByOwnerId(ownerId)).thenReturn(true);

    final IllegalArgumentException exception = assertThrows(
      IllegalArgumentException.class,
      () -> createChannelUseCase.create(request)
    );

    assertEquals("Owner already has a channel", exception.getMessage());
    verify(channelRepository).existsByOwnerId(ownerId);
  }
}
