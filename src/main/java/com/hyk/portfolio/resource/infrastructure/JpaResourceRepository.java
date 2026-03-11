package com.hyk.portfolio.resource.infrastructure;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyk.portfolio.resource.domain.Resource;
import com.hyk.portfolio.resource.domain.ResourceStatus;

public interface JpaResourceRepository extends JpaRepository<Resource, UUID> {

  List<Resource> findByStatusAndCreatedAtBefore(ResourceStatus status, Instant threshold);

}
