package com.hugosantos.palco_digital.stage.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.hugosantos.palco_digital.stage.models.Channel;
import com.hugosantos.palco_digital.stage.models.Presentation;
import com.hugosantos.palco_digital.stage.models.PresentationStatus;
import com.hugosantos.palco_digital.stage.repositories.PresentationRepository;

@ExtendWith(MockitoExtension.class)
public class StartPresentationUseCaseTest {

  @Mock
  private PresentationRepository presentationRepository;

  @InjectMocks
  private StartPresentationUseCase startPresentationUseCase;

  @Test
  @DisplayName("Should start scheduled presentation successfully")
  void start_success() {
    final UUID presentationId = UUID.randomUUID();
    final Channel channel = new Channel(UUID.randomUUID(), "Channel", "Desc");
    final Presentation presentation = new Presentation(channel, "Show", OffsetDateTime.now().plusHours(1));

    when(presentationRepository.findById(presentationId)).thenReturn(Optional.of(presentation));

    startPresentationUseCase.start(presentationId);

    assertEquals(PresentationStatus.LIVE, presentation.getStatus());
    verify(presentationRepository).findById(presentationId);
    verify(presentationRepository).save(presentation);
  }

  @Test
  @DisplayName("Should throw IllegalArgumentException when presentation is not found")
  void start_notFound() {
    final UUID presentationId = UUID.randomUUID();

    when(presentationRepository.findById(presentationId)).thenReturn(Optional.empty());

    final IllegalArgumentException exception = assertThrows(
      IllegalArgumentException.class,
      () -> startPresentationUseCase.start(presentationId)
    );

    assertEquals("Presentation not found", exception.getMessage());
  }
}
