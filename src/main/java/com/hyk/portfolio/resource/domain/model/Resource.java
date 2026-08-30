package com.hyk.portfolio.resource.domain.model;

import java.time.Instant;

import lombok.Getter;

import com.hyk.portfolio.common.exception.BusinessException;
import com.hyk.portfolio.resource.domain.exception.ResourceErrorCode;

@Getter
public class Resource {

  private final Long id;
  private final String filename;
  private final String url;
  private Target target;
  private ResourceStatus status;
  private final Instant uploadedAt;

  private Resource(
      Long id,
      String filename,
      String url,
      Target target,
      ResourceStatus status,
      Instant uploadedAt
  ) {
    this.id = id;
    this.filename = filename;
    this.url = url;
    this.target = target;
    this.status = status;
    this.uploadedAt = uploadedAt;
    validate();
  }

  public static Resource upload(String filename, String url) {
    return new Resource(null, filename, url, null, ResourceStatus.PENDING, Instant.now());
  }

  public static Resource reconstitute(
      Long id,
      String filename,
      String url,
      Target target,
      ResourceStatus status,
      Instant uploadedAt
  ) {
    if (id == null) {
      throw new IllegalArgumentException("id는 필수입니다");
    }
    return new Resource(id, filename, url, target, status, uploadedAt);
  }

  public void attach(Target target) {
    if (target == null) {
      throw new IllegalArgumentException("target은 필수입니다");
    }
    if (this.status == ResourceStatus.ATTACHED) {
      if (this.target.equals(target)) {
        return;
      }
      throw new BusinessException(ResourceErrorCode.RESOURCE_TARGET_CONFLICT);
    }
    this.target = target;
    this.status = ResourceStatus.ATTACHED;
    validate();
  }

  public void detach() {
    this.target = null;
    this.status = ResourceStatus.PENDING;
    validate();
  }

  private void validate() {
    if (this.filename == null || this.filename.isBlank()) {
      throw new IllegalArgumentException("filename은 필수입니다");
    }
    if (this.url == null || this.url.isBlank()) {
      throw new IllegalArgumentException("url은 필수입니다");
    }
    if (this.status == null) {
      throw new IllegalArgumentException("status는 필수입니다");
    }
    if (this.uploadedAt == null) {
      throw new IllegalArgumentException("uploadedAt은 필수입니다");
    }
    if (this.status == ResourceStatus.PENDING && this.target != null) {
      throw new IllegalArgumentException("PENDING 상태의 리소스는 target을 가질 수 없습니다");
    }
    if (this.status == ResourceStatus.ATTACHED && this.target == null) {
      throw new IllegalArgumentException("ATTACHED 상태의 리소스는 target이 필수입니다");
    }
  }

}
