package com.project.bookreport.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

  INVALID_TOKEN(401, "Token이 유효하지 않습니다."),
  ACCESS_DENIED(403, "접근 권한이 없습니다."),

  MEMBER_NOT_FOUND(404, "존재하지 않는 회원입니다."),
  MEMBER_NOT_UNIQUE(409, "이미 존재하는 회원입니다."),
  AUTHENTICATION_FAILED(400, "아이디 또는 비밀번호가 옳지 않습니다."),

  REPORT_NOT_FOUND(404, "존재하지 않는 독후감입니다."),

  BOOK_NOT_FOUND(404, "존재하지 않는 책입니다.");
  private final int status;
  private final String msg;
}
