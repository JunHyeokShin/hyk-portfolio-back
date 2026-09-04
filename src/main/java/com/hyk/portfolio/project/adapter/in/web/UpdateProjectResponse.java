package com.hyk.portfolio.project.adapter.in.web;

import com.hyk.portfolio.project.domain.model.Slug;

record UpdateProjectResponse(String slug) {

  static UpdateProjectResponse from(Slug slug) {
    return new UpdateProjectResponse(slug.value());
  }

}
