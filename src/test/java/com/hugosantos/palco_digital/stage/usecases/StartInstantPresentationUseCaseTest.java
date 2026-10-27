package com.hugosantos.palco_digital.stage.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hugosantos.palco_digital.stage.dtos.PresentationResponse;
import com.hugosantos.palco_digital.stage.dtos.StartInstantPresentationRequest;
import com.hugosantos.palco_digital.stage.models.Channel;
import com.hugosantos.palco_digital.stage.models.Presentation;
import com.hugosantos.palco_digital.stage.models.PresentationStatus;
import com.hugosantos.palco_digital.stage.repositories.ChannelRepository;
import com.hugosantos.palco_digital.stage.repositories.PresentationRepository;

@ExtendWith(MockitoExtension.class)
public class StartInstantPresentationUseCaseTest {

  @Mock
  private ChannelRepository channelRepository;

  @Mock
  private PresentationRepository presentationRepository;

  @InjectMocks
  private StartInstantPresentationUseCase startInstantPresentationUseCase;

  @Test
  @DisplayName("Should start instant presentation successfully when channel exists")
  void start_success() {
    final UUID channelId = UUID.randomUUID();
    final Channel channel = new Channel(UUID.randomUUID(), "Live Channel", "Desc");
    final StartInstantPresentationRequest request = new StartInstantPresentationRequest(channelId, "Instant Show");

    when(channelRepository.findById(channelId)).thenReturn(Optional.of(channel));
    when(presentationRepository.save(any(Presentation.class))).thenAnswer(invocation -> invocation.getArgument(0));

    final PresentationResponse response = startInstantPresentationUseCase.start(request);

    assertNotNull(response);
    assertEquals("Instant Show", response.title());
    assertEquals(PresentationStatus.LIVE.name(), response.status());

    verify(channelRepository).findById(channelId);
    verify(presentationRepository).save(any(Presentation.class));
  }

  @Test
  @DisplayName("Should throw IllegalArgumentException when channel is not found")
  void start_channelNotFound() {
    final UUID channelId = UUID.randomUUID();
    final StartInstantPresentationRequest request = new StartInstantPresentationRequest(channelId, "Instant Show");

    when(channelRepository.findById(channelId)).thenReturn(Optional.empty());

    final IllegalArgumentException exception = assertThrows(
      IllegalArgumentException.class,
      () -> startInstantPresentationUseCase.start(request)
    );

    assertEquals("Channel not found", exception.getMessage());
  }
}
