package com.hugosantos.palco_digital.stage.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hugosantos.palco_digital.stage.dtos.PresentationResponse;
import com.hugosantos.palco_digital.stage.dtos.SchedulePresentationRequest;
import com.hugosantos.palco_digital.stage.models.Channel;
import com.hugosantos.palco_digital.stage.models.Presentation;
import com.hugosantos.palco_digital.stage.models.PresentationStatus;
import com.hugosantos.palco_digital.stage.repositories.ChannelRepository;
import com.hugosantos.palco_digital.stage.repositories.PresentationRepository;

@ExtendWith(MockitoExtension.class)
public class SchedulePresentationUseCaseTest {

  @Mock
  private ChannelRepository channelRepository;

  @Mock
  private PresentationRepository presentationRepository;

  @InjectMocks
  private SchedulePresentationUseCase schedulePresentationUseCase;

  @Test
  @DisplayName("Should schedule presentation successfully when channel exists")
  void schedule_success() {
    final UUID channelId = UUID.randomUUID();
    final Channel channel = new Channel(UUID.randomUUID(), "My Channel", "Desc");
    final OffsetDateTime futureTime = OffsetDateTime.now().plusDays(1);
    final SchedulePresentationRequest request = new SchedulePresentationRequest(channelId, "Title", futureTime);

    when(channelRepository.findById(channelId)).thenReturn(Optional.of(channel));
    when(presentationRepository.save(any(Presentation.class))).thenAnswer(invocation -> invocation.getArgument(0));

    final PresentationResponse response = schedulePresentationUseCase.schedule(request);

    assertNotNull(response);
    assertEquals("Title", response.title());
    assertEquals(futureTime, response.scheduledTime());
    assertEquals(PresentationStatus.SCHEDULED.name(), response.status());

    verify(channelRepository).findById(channelId);
    verify(presentationRepository).save(any(Presentation.class));
  }

  @Test
  @DisplayName("Should throw IllegalArgumentException when channel is not found")
  void schedule_channelNotFound() {
    final UUID channelId = UUID.randomUUID();
    final SchedulePresentationRequest request = new SchedulePresentationRequest(channelId, "Title", OffsetDateTime.now().plusDays(1));

    when(channelRepository.findById(channelId)).thenReturn(Optional.empty());

    final IllegalArgumentException exception = assertThrows(
      IllegalArgumentException.class,
      () -> schedulePresentationUseCase.schedule(request)
    );

    assertEquals("Channel not found", exception.getMessage());
  }
}
