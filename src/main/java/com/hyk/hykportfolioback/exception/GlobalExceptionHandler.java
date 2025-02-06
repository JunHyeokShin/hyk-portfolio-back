package com.hyk.hykportfolioback.exception;

import com.hyk.hykportfolioback.dto.response.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
  public ResponseEntity<ResponseDto> validationExceptionHandler(Exception exception) {
    return ResponseDto.validationFailed();
  }

  @ExceptionHandler({MultipartException.class})
  public ResponseEntity<ResponseDto> notMultipartRequestExceptionHandler(Exception exception) {
    return ResponseDto.emptyFile();
  }

}
