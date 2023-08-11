package com.project.bookreport.exception;

import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 전역 예외 핸들러
 */
@RestControllerAdvice
public class ExceptionControllerAdvice {

  @ExceptionHandler({MemberException.class})
  public ResponseEntity<Object> handleCustomException(CustomException e) {

    return ResponseEntity.status(e.getErrorCode().getStatus())
        .body(e.getErrorCode().getMsg());
  }

  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResponseEntity<Object> exceptionHandling(MethodArgumentNotValidException e) {

    String msg = e
        .getBindingResult()
        .getAllErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.joining(", "));

    return ResponseEntity.badRequest()
        .body(msg);
  }
}
