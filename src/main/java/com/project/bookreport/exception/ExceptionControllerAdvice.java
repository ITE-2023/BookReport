package com.project.bookreport.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

  @ExceptionHandler({MemberException.class})
  public ResponseEntity<Object> handleCustomException(CustomException e) {

    return ResponseEntity.status(e.getErrorCode().getStatus())
        .body(e.getErrorCode().getMsg());
  }
}
