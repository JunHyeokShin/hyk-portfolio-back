package com.hyk.portfolio.resource.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.hyk.portfolio.resource.domain.Resource;
import com.hyk.portfolio.resource.domain.ResourceRepository;

@RequiredArgsConstructor
@Repository
public class DatabaseResourceRepository implements ResourceRepository {

  private final JpaResourceRepository jpaResourceRepository;

  @Override
  public Resource save(Resource resource) {
    return this.jpaResourceRepository.save(resource);
  }

  @Override
  public void delete(Resource resource) {
    this.jpaResourceRepository.delete(resource);
  }

}
