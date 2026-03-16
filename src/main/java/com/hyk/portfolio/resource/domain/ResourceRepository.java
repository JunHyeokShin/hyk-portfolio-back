package com.hyk.portfolio.resource.domain;

public interface ResourceRepository {

  Resource save(Resource resource);

  void delete(Resource resource);

}
