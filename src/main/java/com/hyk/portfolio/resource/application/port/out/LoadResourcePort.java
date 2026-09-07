package com.hyk.portfolio.resource.application.port.out;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

import com.hyk.portfolio.resource.domain.model.Resource;
import com.hyk.portfolio.resource.domain.model.Target;

public interface LoadResourcePort {

  List<Resource> findAllByUrlIn(Collection<String> urls);

  List<Resource> findAllByTarget(Target target);

  List<Resource> findAllPendingUploadedBefore(Instant threshold);

}
