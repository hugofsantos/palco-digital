package com.hugosantos.palco_digital.stage.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.hugosantos.palco_digital.stage.models.Channel;
import com.hugosantos.palco_digital.stage.models.Presentation;
import com.hugosantos.palco_digital.stage.models.PresentationStatus;
import com.hugosantos.palco_digital.stage.repositories.PresentationRepository;

@ExtendWith(MockitoExtension.class)
public class FinishPresentationUseCaseTest {

  @Mock
  private PresentationRepository presentationRepository;

  @InjectMocks
  private FinishPresentationUseCase finishPresentationUseCase;

  @Test
  @DisplayName("Should finish live presentation successfully")
  void finish_success() {
    final UUID presentationId = UUID.randomUUID();
    final Channel channel = new Channel(UUID.randomUUID(), "Channel", "Desc");
    final Presentation presentation = Presentation.createInstantPresentation(channel, "Live Show");

    when(presentationRepository.findById(presentationId)).thenReturn(Optional.of(presentation));

    finishPresentationUseCase.finish(presentationId);

    assertEquals(PresentationStatus.FINISHED, presentation.getStatus());
    verify(presentationRepository).findById(presentationId);
    verify(presentationRepository).save(presentation);
  }

  @Test
  @DisplayName("Should throw IllegalArgumentException when presentation is not found")
  void finish_notFound() {
    final UUID presentationId = UUID.randomUUID();

    when(presentationRepository.findById(presentationId)).thenReturn(Optional.empty());

    final IllegalArgumentException exception = assertThrows(
      IllegalArgumentException.class,
      () -> finishPresentationUseCase.finish(presentationId)
    );

    assertEquals("Presentation not found", exception.getMessage());
  }
}
