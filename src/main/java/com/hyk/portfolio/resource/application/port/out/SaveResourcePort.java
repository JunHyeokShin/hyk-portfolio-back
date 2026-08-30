package com.hyk.portfolio.resource.application.port.out;

import com.hyk.portfolio.resource.domain.model.Resource;

public interface SaveResourcePort {

  Resource save(Resource resource);

}
