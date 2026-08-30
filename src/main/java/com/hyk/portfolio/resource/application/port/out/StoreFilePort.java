package com.hyk.portfolio.resource.application.port.out;

import java.io.InputStream;

public interface StoreFilePort {

  String store(String filename, InputStream content);

}

