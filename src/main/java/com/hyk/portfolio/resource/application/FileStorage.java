package com.hyk.portfolio.resource.application;

import java.io.InputStream;

public interface FileStorage {

  String upload(String filename, InputStream inputStream);

  void delete(String filename);

}
