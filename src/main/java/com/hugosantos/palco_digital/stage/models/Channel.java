package com.hugosantos.palco_digital.stage.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "channels")
public class Channel {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  
  @Column(nullable = false, unique = true)
  private UUID ownerId;

  @Column(nullable = false)
  private String name;

  @Column(length = 500)
  private String description;

  protected Channel() {} // Empty constructor for JPA

  public Channel(UUID ownerId, String name, String description) {
    if (ownerId == null) {throw new IllegalArgumentException("Owner ID cannot be null");}
    if (name == null || name.isBlank()) {throw new IllegalArgumentException("Name cannot be null or blank");}

    this.ownerId = ownerId;
    this.name = name;
    this.description = description;
  }

  // Getters
  public UUID getId() {return id;}
  public UUID getOwnerId() {return ownerId;}
  public String getName() {return name;}
  public String getDescription() {return description;}
}
