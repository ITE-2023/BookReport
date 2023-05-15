package com.project.bookreport.security.jwt;

import com.project.bookreport.domain.Member;
import com.project.bookreport.model.member.MemberContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component // 클래스를 빈 등록
@RequiredArgsConstructor
public class JwtProvider {

  private final SecretKey secretKey;
  private final SecretKey secretKey2;

  private static final Long ACCESS_VALID_TIME = 1000L * 60 * 60; // (1시간)
  private static final Long REFRESH_VALID_TIME = 1000L * 60 * 60 * 24 * 7;

  private SecretKey getSecretKey() {
    return secretKey;
  }
  private SecretKey getSecretKey2() {
    return secretKey2;
  }
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

  public boolean verifyAccessToken(String accessToken) {
    try {
      Jws<Claims> claimsJws = Jwts.parserBuilder()
          .setSigningKey(getSecretKey())
          .build()
          .parseClaimsJws(accessToken);

      return !claimsJws.getBody().getExpiration().before(new Date());
    } catch (Exception e) {
      return false;
    }
  }

  public Claims getClaims(String jwt) {
    return Jwts.parserBuilder()
        .setSigningKey(getSecretKey())
        .build()
        .parseClaimsJws(jwt)
        .getBody();
  }
  public Authentication getAuthentication(Member member) {
    MemberContext memberContext = new MemberContext(member);
    return new UsernamePasswordAuthenticationToken(memberContext, null,
        memberContext.getAuthorities());
  }

  public String resolveToken(String bearerToken) {
    if (StringUtils.hasText(bearerToken)) {
      return bearerToken.substring("Bearer ".length());
    }
    return null;
  }

  public boolean verifyRefreshToken(String refreshToken) {
    try {
      Jws<Claims> claimsJws = Jwts.parserBuilder()
          .setSigningKey(getSecretKey2())
          .build()
          .parseClaimsJws(refreshToken);

      return !claimsJws.getBody().getExpiration().before(new Date());
    } catch (Exception e) {
      return false;
    }
  }
}
