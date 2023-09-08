package com.project.bookreport.exception;

import com.project.bookreport.exception.custom_exceptions.BookException;
import com.project.bookreport.exception.custom_exceptions.CustomException;
import com.project.bookreport.exception.custom_exceptions.MemberException;
import com.project.bookreport.exception.custom_exceptions.MyBookException;
import com.project.bookreport.exception.custom_exceptions.ReportException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 전역 예외 핸들러
 */
@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

  @ExceptionHandler({MemberException.class, BookException.class, ReportException.class,
      MyBookException.class})
  public ResponseEntity<Object> handleCustomException(CustomException e) {
    log.error("Error Message {} ", e.getErrorCode().getMsg());
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
