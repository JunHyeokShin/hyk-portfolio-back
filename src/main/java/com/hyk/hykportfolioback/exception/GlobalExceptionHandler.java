package com.hyk.hykportfolioback.exception;

import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import com.hyk.hykportfolioback.dto.response.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
  public ResponseEntity<ResponseDto> validationExceptionHandler(Exception exception) {
    return ResponseDto.validationFailed();
  }

  @ExceptionHandler({RuntimeJsonMappingException.class})
  public ResponseEntity<ResponseDto> jsonProcessingExceptionHandler(Exception exception) {
    return ResponseDto.databaseError();
  }

}
