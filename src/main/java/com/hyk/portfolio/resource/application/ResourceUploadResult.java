package com.hyk.portfolio.resource.application;

import java.util.UUID;

public record ResourceUploadResult(

    UUID id,
    String originalFilename,
    String savedFilename,
    String url

) {

}
