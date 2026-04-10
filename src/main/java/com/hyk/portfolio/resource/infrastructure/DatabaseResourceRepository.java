package com.hyk.portfolio.resource.infrastructure;

import java.time.Instant;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.hyk.portfolio.resource.domain.Resource;
import com.hyk.portfolio.resource.domain.ResourceRepository;
import com.hyk.portfolio.resource.domain.ResourceStatus;

@RequiredArgsConstructor
@Repository
public class DatabaseResourceRepository implements ResourceRepository {

  private final JpaResourceRepository jpaResourceRepository;

  @Override
  public Resource save(Resource resource) {
    return this.jpaResourceRepository.save(resource);
  }

  @Override
  public List<Resource> findByStatusAndCreatedAtBefore(ResourceStatus status, Instant threshold) {
    return this.jpaResourceRepository.findByStatusAndCreatedAtBefore(status, threshold);
  }

  @Override
  public void delete(Resource resource) {
    this.jpaResourceRepository.delete(resource);
  }

}
