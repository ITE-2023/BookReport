package com.project.bookreport.security;

import com.project.bookreport.security.jwt.JWTAuthorizationFilter;
import com.project.bookreport.security.jwt.JwtProvider;
import com.project.bookreport.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity // 스프링 시큐리티 사용을 위한 어노테이션
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

  private final JwtProvider jwtProvider;
  private final MemberService memberService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http,
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    http
        .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
            .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
            .requestMatchers("/member/join", "/member/login", "/member/reissue").permitAll()
            .anyRequest().authenticated())
        .cors().disable()       // 타 도메인에서 API 접근 허용
        .csrf().disable()       // CSRF 토큰 기능 사용 X
        .httpBasic().disable()   // Http basic Auth 기반 로그인 기능 사용 X
        .headers().frameOptions().sameOrigin()
        .and()
        .formLogin().disable()   // security form 로그인 사용 X
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 스프링 시큐리티가 생성하지도 않고 존재해도 사용하지 않음
        .and()
        .addFilterBefore(
            new JWTAuthorizationFilter(authenticationConfiguration.getAuthenticationManager(),
                jwtProvider, memberService),
            UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
