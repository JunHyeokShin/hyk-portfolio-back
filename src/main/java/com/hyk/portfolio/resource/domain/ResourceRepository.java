package com.hyk.portfolio.resource.domain;

import java.time.Instant;
import java.util.List;

public interface ResourceRepository {

  Resource save(Resource resource);

  List<Resource> findByStatusAndCreatedAtBefore(ResourceStatus status, Instant threshold);

  void delete(Resource resource);

}
