package com.hugosantos.palco_digital.stage.models;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "presentations")
public class Presentation {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "channel_id", nullable = false)
  private Channel channel;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private OffsetDateTime scheduledTime;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PresentationStatus status;

  @Version
  private long version; // For optimistic locking

  protected Presentation() {} // Empty constructor for JPA

  public Presentation(Channel channel, String title, OffsetDateTime scheduledTime) {
    if (channel == null) {throw new IllegalArgumentException("Channel cannot be null");}
    if (title == null || title.isBlank()) {throw new IllegalArgumentException("Title cannot be null or blank");}
    if (scheduledTime == null) {throw new IllegalArgumentException("Scheduled time cannot be null");}
    if (scheduledTime.isBefore(OffsetDateTime.now())) {throw new IllegalArgumentException("Scheduled time cannot be in the past");}

    this.channel = channel;
    this.title = title;
    this.scheduledTime = scheduledTime;
    this.status = PresentationStatus.SCHEDULED;
  }

  public static Presentation createInstantPresentation(Channel channel, String title) {    
    final Presentation presentation = new Presentation(channel, title, OffsetDateTime.now());
    presentation.status = PresentationStatus.LIVE; 
    return presentation;
  }

  public void goLive() {
    if (this.status != PresentationStatus.SCHEDULED) {
      throw new IllegalStateException("Only scheduled presentations can go live. Current status: " + this.status);
    }
    this.status = PresentationStatus.LIVE;
  }

  public void finish() {
    if (this.status != PresentationStatus.LIVE) {
      throw new IllegalStateException("Only live presentations can be finished. Current status: " + this.status);
    }
    this.status = PresentationStatus.FINISHED;
  }

  public void cancel() {
    if (this.status == PresentationStatus.FINISHED) {
      throw new IllegalStateException("Finished presentations cannot be canceled.");
    }
    this.status = PresentationStatus.CANCELED;
  }

  // Getters
  public UUID getId() {return id;}
  public Channel getChannel() {return channel;}
  public String getTitle() {return title;}
  public OffsetDateTime getScheduledTime() {return scheduledTime;}
  public PresentationStatus getStatus() {return status;}
  public UUID getChannelId() {return channel.getId();}
  public String getPresentationStatusName() {return status.name();}
  
}
