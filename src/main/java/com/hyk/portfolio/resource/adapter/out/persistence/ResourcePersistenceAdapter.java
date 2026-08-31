package com.hyk.portfolio.resource.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.portfolio.resource.application.port.out.SaveResourcePort;
import com.hyk.portfolio.resource.domain.model.Resource;

@RequiredArgsConstructor
@Component
class ResourcePersistenceAdapter implements SaveResourcePort {

  private final ResourceJpaRepository jpaRepository;

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public Resource save(Resource resource) {
    if (resource.getId() == null) {
      return ResourceMapper.toDomain(
          this.jpaRepository.save(ResourceMapper.toEntity(resource)));
    }
    ResourceJpaEntity entity = this.jpaRepository.findById(resource.getId())
        .orElseThrow(() -> new IllegalStateException(
            "저장하려는 리소스가 존재하지 않습니다: " + resource.getId()));
    ResourceMapper.updateEntity(entity, resource);
    return ResourceMapper.toDomain(entity);
  }

}
