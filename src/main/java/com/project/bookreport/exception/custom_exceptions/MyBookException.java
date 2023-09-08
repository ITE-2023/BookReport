package com.project.bookreport.exception.custom_exceptions;

import com.project.bookreport.exception.ErrorCode;

public class MyBookException  extends CustomException {
  public MyBookException(ErrorCode errorCode) {
    super(errorCode);
  }
}
