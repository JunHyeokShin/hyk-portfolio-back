package com.hyk.portfolio.resource.adapter.in.web;

import com.hyk.portfolio.resource.application.port.in.UploadResourceResult;

record UploadResourceResponse(Long id, String url) {

  static UploadResourceResponse from(UploadResourceResult result) {
    return new UploadResourceResponse(result.id(), result.url());
  }

}
