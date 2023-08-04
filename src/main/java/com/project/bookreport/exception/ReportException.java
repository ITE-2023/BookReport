package com.project.bookreport.exception;

public class ReportException extends CustomException{

  public ReportException(ErrorCode errorCode) {
    super(errorCode);
  }
}
