package com.project.bookreport.exception.custom_exceptions;

import com.project.bookreport.exception.ErrorCode;

public class MemberException extends CustomException {

  public MemberException(ErrorCode errorCode) {
    super(errorCode);
  }
}
