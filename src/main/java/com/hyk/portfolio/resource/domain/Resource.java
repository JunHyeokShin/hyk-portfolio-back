package com.hyk.portfolio.resource.domain;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "resource")
@Entity
public class Resource {

  @Column(name = "id", nullable = false, updatable = false)
  @Id
  private UUID id;

  @Column(name = "original_filename", nullable = false)
  private String originalFilename;

  @Column(name = "saved_filename", nullable = false, unique = true)
  private String savedFilename;

  @Column(name = "url", nullable = false, length = 2048)
  private String url;

  @Column(name = "target_type", length = 50)
  @Enumerated(EnumType.STRING)
  private TargetType targetType;

  @Column(name = "target_id")
  private String targetId;

  @Column(name = "status", nullable = false, length = 20)
  @Enumerated(EnumType.STRING)
  private ResourceStatus status = ResourceStatus.PENDING;

  @Column(name = "created_at", nullable = false, updatable = false)
  @CreatedDate
  private Instant createdAt;

  private Resource(UUID id, String originalFilename, String savedFilename, String url) {
    this.id = id;
    this.originalFilename = originalFilename;
    this.savedFilename = savedFilename;
    this.url = url;
  }

  public static Resource create(UUID id, String originalFilename, String savedFilename, String url) {
    return new Resource(id, originalFilename, savedFilename, url);
  }

  public void linkTo(String targetId, TargetType targetType) {
    this.targetId = targetId;
    this.targetType = targetType;
    this.status = ResourceStatus.ACTIVATED;
  }

}
