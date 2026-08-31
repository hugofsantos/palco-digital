package com.hugosantos.palco_digital.identity.models;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, unique = true)
  private String email;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
    name="user_groups",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "group_id")
  )
  private Set<Group> groups = new HashSet<>();

  // Protected no-arg constructor for JPA
  protected User() {}


  public User(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public void addToGroup(Group group) {
    if(group == null) {
      throw new IllegalArgumentException("Group cannot be null");
    }
    this.groups.add(group);
  }

  public void removeFromGroup(Group group) {
    if(group == null) {
      throw new IllegalArgumentException("Group cannot be null");
    }
    this.groups.remove(group);
  }

  // Getters
  public UUID getId() {return id;}
  public String getName() {return name;}
  public String getEmail() {return email;}
  public Set<Group> getGroups() {
    return new HashSet<>(groups);
  }
}
