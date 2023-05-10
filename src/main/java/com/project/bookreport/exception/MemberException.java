package com.project.bookreport.exception;

public class MemberException extends CustomException{

  public MemberException(ErrorCode errorCode) {
    super(errorCode);
  }
}
