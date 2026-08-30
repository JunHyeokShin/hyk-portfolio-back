package com.hyk.portfolio.resource.adapter.out.persistence;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.hyk.portfolio.resource.domain.model.Resource;
import com.hyk.portfolio.resource.domain.model.Target;
import com.hyk.portfolio.resource.domain.model.TargetType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class ResourceMapper {

  static ResourceJpaEntity toEntity(Resource resource) {
    Target target = resource.getTarget();
    return ResourceJpaEntity.builder()
        .id(resource.getId())
        .filename(resource.getFilename())
        .url(resource.getUrl())
        .targetType(targetTypeOf(target))
        .targetId(targetIdOf(target))
        .status(resource.getStatus())
        .uploadedAt(resource.getUploadedAt())
        .build();
  }

  static Resource toDomain(ResourceJpaEntity entity) {
    return Resource.reconstitute(
        entity.getId(),
        entity.getFilename(),
        entity.getUrl(),
        targetOf(entity.getTargetType(), entity.getTargetId()),
        entity.getStatus(),
        entity.getUploadedAt()
    );
  }

  static void updateEntity(ResourceJpaEntity entity, Resource resource) {
    Target target = resource.getTarget();
    entity.update(targetTypeOf(target), targetIdOf(target), resource.getStatus());
  }

  private static TargetType targetTypeOf(Target target) {
    return target != null ? target.type() : null;
  }

  private static Long targetIdOf(Target target) {
    return target != null ? target.id() : null;
  }

  private static Target targetOf(TargetType type, Long id) {
    return type != null ? Target.of(type, id) : null;
  }

}
