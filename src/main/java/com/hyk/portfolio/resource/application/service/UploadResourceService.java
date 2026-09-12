package com.hyk.portfolio.resource.application.service;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.portfolio.resource.application.port.in.UploadResourceCommand;
import com.hyk.portfolio.resource.application.port.in.UploadResourceResult;
import com.hyk.portfolio.resource.application.port.in.UploadResourceUseCase;
import com.hyk.portfolio.resource.application.port.out.SaveResourcePort;
import com.hyk.portfolio.resource.application.port.out.StoreFilePort;
import com.hyk.portfolio.resource.domain.model.Extension;
import com.hyk.portfolio.resource.domain.model.Resource;

@RequiredArgsConstructor
@Transactional
@Service
class UploadResourceService implements UploadResourceUseCase {

  private final StoreFilePort storeFilePort;
  private final SaveResourcePort saveResourcePort;

  private static String generateFilename(Extension extension) {
    return UUID.randomUUID() + "." + extension.value();
  }

  @Override
  public UploadResourceResult upload(UploadResourceCommand command) {
    String filename = generateFilename(command.extension());
    String url = this.storeFilePort.store(filename, command.content());
    Resource resource = Resource.upload(filename, url);
    Resource saved = this.saveResourcePort.save(resource);
    return UploadResourceResult.from(saved);
  }

}
