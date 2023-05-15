package com.project.bookreport.security.jwt;

import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 클래스 빈 등록 후, 내부 @Bean이 붙은 메서드 빈 등록
public class JwtConfig {

  @Value("${custom.jwt.secretKey}")
  private String plainSecretKey;

  @Value("${custom.jwt.secretKey2}")
  private String plainSecretKey2;

  @Bean(name = "secretKey")
  public SecretKey getSecretKey() {
    String encodedKey = Base64.getEncoder().encodeToString(plainSecretKey.getBytes());
    return Keys.hmacShaKeyFor(encodedKey.getBytes());
  }

  @Bean(name = "secretKey2")
  public SecretKey getSecretKey2() {
    String encodedKey2 = Base64.getEncoder().encodeToString(plainSecretKey2.getBytes());
    return Keys.hmacShaKeyFor(encodedKey2.getBytes());
  }
}
