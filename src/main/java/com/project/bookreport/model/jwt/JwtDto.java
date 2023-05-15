package com.project.bookreport.model.jwt;

import com.project.bookreport.domain.Member;
import lombok.Getter;

@Getter
public class JwtDto {

  private String accessToken;
  private String refreshToken;

  public static JwtDto from(Member member) {
    JwtDto jwtDto = new JwtDto();
    jwtDto.accessToken = member.getAccessToken();
    jwtDto.refreshToken = member.getRefreshToken();
    return jwtDto;
  }
}
