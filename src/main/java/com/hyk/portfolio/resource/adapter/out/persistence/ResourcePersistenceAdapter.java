package com.hyk.portfolio.resource.adapter.out.persistence;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.portfolio.resource.application.port.out.DeleteResourcePort;
import com.hyk.portfolio.resource.application.port.out.LoadResourcePort;
import com.hyk.portfolio.resource.application.port.out.SaveResourcePort;
import com.hyk.portfolio.resource.domain.model.Resource;
import com.hyk.portfolio.resource.domain.model.ResourceStatus;
import com.hyk.portfolio.resource.domain.model.Target;

@RequiredArgsConstructor
@Component
class ResourcePersistenceAdapter
    implements SaveResourcePort, LoadResourcePort, DeleteResourcePort {

  private final ResourceJpaRepository jpaRepository;

  @Override
  // update 분기가 dirty checking에 의존한다. 바깥 트랜잭션이 없으면 수정이 조용히 유실된다
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

  @Override
  public List<Resource> findAllByUrlIn(Collection<String> urls) {
    return this.jpaRepository.findAllByUrlIn(urls).stream()
        .map(ResourceMapper::toDomain)
        .toList();
  }

  @Override
  public List<Resource> findAllByTarget(Target target) {
    return this.jpaRepository.findAllByTargetTypeAndTargetId(target.type(), target.id())
        .stream()
        .map(ResourceMapper::toDomain)
        .toList();
  }

  @Override
  public List<Resource> findAllPendingUploadedBefore(Instant threshold) {
    return this.jpaRepository.findAllByStatusAndUploadedAtBefore(ResourceStatus.PENDING, threshold)
        .stream()
        .map(ResourceMapper::toDomain)
        .toList();
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void delete(Resource resource) {
    this.jpaRepository.deleteById(resource.getId());
  }

}
