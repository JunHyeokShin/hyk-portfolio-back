package com.hyk.portfolio.resource.adapter.out.persistence;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.hyk.portfolio.resource.domain.model.ResourceStatus;
import com.hyk.portfolio.resource.domain.model.TargetType;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "resources")
@Entity
class ResourceJpaEntity {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long id;

  @Column(nullable = false, unique = true)
  private String filename;

  @Column(nullable = false, unique = true)
  private String url;

  @Enumerated(EnumType.STRING)
  private TargetType targetType;

  private Long targetId;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private ResourceStatus status;

  @Column(nullable = false, updatable = false)
  private Instant uploadedAt;

  void update(TargetType targetType, Long targetId, ResourceStatus status) {
    this.targetType = targetType;
    this.targetId = targetId;
    this.status = status;
  }

}
