package com.hugosantos.palco_digital.stage.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PresentationTest {

  private Channel channel;

  @BeforeEach
  void setUp() {
    channel = new Channel(UUID.randomUUID(), "Main Channel", "Description");
  }

  @Test
  @DisplayName("Given valid parameters, when creating a scheduled presentation, then it should be in SCHEDULED status")
  void constructor_validData() {
    final String title = "Future Presentation";
    final OffsetDateTime futureTime = OffsetDateTime.now().plusHours(2);

    final Presentation presentation = new Presentation(channel, title, futureTime);

    assertNotNull(presentation);
    assertEquals(channel, presentation.getChannel());
    assertEquals(title, presentation.getTitle());
    assertEquals(futureTime, presentation.getScheduledTime());
    assertEquals(PresentationStatus.SCHEDULED, presentation.getStatus());
  }

  @Test
  @DisplayName("Given null channel, when creating presentation, then it should throw IllegalArgumentException")
  void constructor_nullChannel() {
    assertThrows(
      IllegalArgumentException.class,
      () -> new Presentation(null, "Title", OffsetDateTime.now().plusHours(1))
    );
  }

  @Test
  @DisplayName("Given null or blank title, when creating presentation, then it should throw IllegalArgumentException")
  void constructor_invalidTitle() {
    assertThrows(
      IllegalArgumentException.class,
      () -> new Presentation(channel, null, OffsetDateTime.now().plusHours(1))
    );

    assertThrows(
      IllegalArgumentException.class,
      () -> new Presentation(channel, "  ", OffsetDateTime.now().plusHours(1))
    );
  }

  @Test
  @DisplayName("Given past scheduled time, when creating presentation, then it should throw IllegalArgumentException")
  void constructor_pastTime() {
    assertThrows(
      IllegalArgumentException.class,
      () -> new Presentation(channel, "Title", OffsetDateTime.now().minusHours(1))
    );
  }

  @Test
  @DisplayName("Given valid channel and title, when creating instant presentation, then it should be in LIVE status")
  void createInstantPresentation_valid() {
    final Presentation presentation = Presentation.createInstantPresentation(channel, "Instant Live");

    assertNotNull(presentation);
    assertEquals(PresentationStatus.LIVE, presentation.getStatus());
  }

  @Test
  @DisplayName("Given a scheduled presentation, when goLive, then status should change to LIVE")
  void goLive_success() {
    final Presentation presentation = new Presentation(channel, "Title", OffsetDateTime.now().plusHours(1));
    presentation.goLive();

    assertEquals(PresentationStatus.LIVE, presentation.getStatus());
  }

  @Test
  @DisplayName("Given a non-scheduled presentation, when goLive, then it should throw IllegalStateException")
  void goLive_invalidState() {
    final Presentation presentation = Presentation.createInstantPresentation(channel, "Instant Live");

    assertThrows(
      IllegalStateException.class,
      presentation::goLive
    );
  }

  @Test
  @DisplayName("Given a LIVE presentation, when finish, then status should change to FINISHED")
  void finish_success() {
    final Presentation presentation = Presentation.createInstantPresentation(channel, "Live Stream");
    presentation.finish();

    assertEquals(PresentationStatus.FINISHED, presentation.getStatus());
  }

  @Test
  @DisplayName("Given a non-LIVE presentation, when finish, then it should throw IllegalStateException")
  void finish_invalidState() {
    final Presentation presentation = new Presentation(channel, "Title", OffsetDateTime.now().plusHours(1));

    assertThrows(
      IllegalStateException.class,
      presentation::finish
    );
  }

  @Test
  @DisplayName("Given a SCHEDULED or LIVE presentation, when cancel, then status should change to CANCELED")
  void cancel_success() {
    final Presentation presentation = new Presentation(channel, "Title", OffsetDateTime.now().plusHours(1));
    presentation.cancel();

    assertEquals(PresentationStatus.CANCELED, presentation.getStatus());
  }

  @Test
  @DisplayName("Given a FINISHED presentation, when cancel, then it should throw IllegalStateException")
  void cancel_finishedPresentation() {
    final Presentation presentation = Presentation.createInstantPresentation(channel, "Live Stream");
    presentation.finish();

    assertThrows(
      IllegalStateException.class,
      presentation::cancel
    );
  }
}
