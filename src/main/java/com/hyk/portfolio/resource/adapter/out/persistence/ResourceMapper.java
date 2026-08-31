package com.hyk.portfolio.resource.adapter.out.persistence;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.hyk.portfolio.resource.domain.model.Resource;
import com.hyk.portfolio.resource.domain.model.Target;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class ResourceMapper {

  static ResourceJpaEntity toEntity(Resource resource) {
    Target target = resource.getTarget();
    return ResourceJpaEntity.builder()
        .id(resource.getId())
        .filename(resource.getFilename())
        .url(resource.getUrl())
        .targetType(target != null ? target.type() : null)
        .targetId(target != null ? target.id() : null)
        .status(resource.getStatus())
        .uploadedAt(resource.getUploadedAt())
        .build();
  }

  static Resource toDomain(ResourceJpaEntity entity) {
    return Resource.reconstitute(
        entity.getId(),
        entity.getFilename(),
        entity.getUrl(),
        entity.getTargetType() != null
            ? Target.of(entity.getTargetType(), entity.getTargetId())
            : null,
        entity.getStatus(),
        entity.getUploadedAt()
    );
  }

  static void updateEntity(ResourceJpaEntity entity, Resource resource) {
    Target target = resource.getTarget();
    entity.update(
        target != null ? target.type() : null,
        target != null ? target.id() : null,
        resource.getStatus()
    );
  }

}
