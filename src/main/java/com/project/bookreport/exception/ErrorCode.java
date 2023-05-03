package com.project.bookreport.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

  INVALID_TOKEN(401, "Token이 유효하지 않습니다."),
  ACCESS_DENIED(403, "접근 권한이 없습니다."),

  MEMBER_NOT_FOUND(404, "존재하지 않는 회원입니다."),
  AUTHENTICATION_FAILED(400, "아이디 또는 비밀번호가 옳지 않습니다.");

  private final int status;
  private final String msg;
}
