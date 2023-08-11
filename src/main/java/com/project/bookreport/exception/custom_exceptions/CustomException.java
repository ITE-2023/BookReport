package com.project.bookreport.exception.custom_exceptions;

import com.project.bookreport.exception.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

  private final ErrorCode errorCode;

  public CustomException(ErrorCode errorCode) {
    this.errorCode = errorCode;
  }
}
