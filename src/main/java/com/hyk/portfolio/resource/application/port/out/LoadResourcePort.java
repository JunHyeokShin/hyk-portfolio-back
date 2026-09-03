package com.hyk.portfolio.resource.application.port.out;

import java.util.Collection;
import java.util.List;

import com.hyk.portfolio.resource.domain.model.Resource;

public interface LoadResourcePort {

  List<Resource> findAllByUrlIn(Collection<String> urls);

}
