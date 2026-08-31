package com.hyk.portfolio.project.application.port.out;

import com.hyk.portfolio.project.domain.model.Slug;

public interface LoadProjectPort {

  boolean existsBySlug(Slug slug);

}
