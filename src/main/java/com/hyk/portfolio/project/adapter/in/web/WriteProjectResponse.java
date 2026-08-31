package com.hyk.portfolio.project.adapter.in.web;

import com.hyk.portfolio.project.domain.model.Slug;

record WriteProjectResponse(String slug) {

  static WriteProjectResponse from(Slug slug) {
    return new WriteProjectResponse(slug.value());
  }

}
