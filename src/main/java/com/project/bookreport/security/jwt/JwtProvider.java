package com.project.bookreport.security.jwt;

import com.project.bookreport.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component // 클래스를 빈 등록
@RequiredArgsConstructor
public class JwtProvider {

  private final SecretKey secretKey;
  private final SecretKey secretKey2;

  private static final Long ACCESS_VALID_TIME = 1000L * 60 * 60; // (1시간)
  private static final Long REFRESH_VALID_TIME = 1000L * 60 * 60 * 24 * 7;

  public String generateAccessToken(Member member) {

    Claims claims = Jwts.claims().setSubject(String.valueOf(member.getId()));
    claims.put("member_roles", member.getRoleSet());

    Date now = new Date();

    return Jwts.builder()
        .setClaims(claims)
        .setExpiration(new Date(now.getTime() + ACCESS_VALID_TIME))
        .signWith(secretKey, SignatureAlgorithm.HS512)
        .compact();
  }

  public String generateRefreshToken(Member member) {
    Claims claims = Jwts.claims().setSubject(String.valueOf(member.getId()));
    claims.put("member_roles", member.getRoleSet());

    Date now = new Date();

    return Jwts.builder()
        .setClaims(claims)
        .setExpiration(new Date(now.getTime() + REFRESH_VALID_TIME))
        .signWith(secretKey2, SignatureAlgorithm.HS512)
        .compact();
  }
}
