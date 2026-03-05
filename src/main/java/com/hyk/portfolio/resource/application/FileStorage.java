package com.hyk.portfolio.resource.application;

import java.io.InputStream;

public interface FileStorage {

  void upload(String filename, InputStream inputStream);

  void delete(String filename);

  String getUrl(String filename);

}
