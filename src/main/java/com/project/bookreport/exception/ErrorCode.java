package com.project.bookreport.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 상태코드와 오류 메세지
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {

  INVALID_TOKEN(401, "Token이 유효하지 않습니다."),
  ACCESS_DENIED(403, "접근 권한이 없습니다."),

  MEMBER_NOT_FOUND(404, "존재하지 않는 회원입니다."),
  MEMBER_NOT_UNIQUE(409, "이미 존재하는 회원입니다."),
  AUTHENTICATION_FAILED(400, "아이디 또는 비밀번호가 옳지 않습니다."),

  REPORT_NOT_FOUND(404, "존재하지 않는 독후감입니다."),
  REPORT_EXIST(409, "이미 독후감이 존재합니다."),

  BOOK_NOT_FOUND(404, "존재하지 않는 책입니다."),
  BOOK_SEARCH_FAIL(400, "해당 검색어에 대한 검색 결과가 없습니다."),

  MY_BOOK_NOT_UNIQUE(409, "이미 내 서재에 추가되었습니다."),
  MY_BOOK_NOT_FOUND(404, "내 서재에 존재하지 않습니다."),

  EMOTION_NOT_FOUND(404, "감정 분석 중 오류가 발생했습니다."),

  MUSIC_NOT_RECOMMEND(404, "음악 추천 중 오류가 발생했습니다.");
  private final int status;
  private final String msg;
}
