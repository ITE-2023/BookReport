package com.project.bookreport.exception.custom_exceptions;

import com.project.bookreport.exception.ErrorCode;

public class ReportException extends CustomException {

  public ReportException(ErrorCode errorCode) {
    super(errorCode);
  }
}
