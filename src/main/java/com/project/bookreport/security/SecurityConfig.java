package com.project.bookreport.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bookreport.exception.CustomAccessDeniedHandler;
import com.project.bookreport.exception.CustomAuthenticationEntryPoint;
import com.project.bookreport.security.jwt.JWTAuthorizationFilter;
import com.project.bookreport.security.jwt.JwtProvider;
import com.project.bookreport.service.MemberService;
import java.util.List;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity // 스프링 시큐리티 사용을 위한 어노테이션
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

  private final JwtProvider jwtProvider;
  private final MemberService memberService;
  private final ObjectMapper objectMapper;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http,
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    http
        .exceptionHandling()
        .authenticationEntryPoint(new CustomAuthenticationEntryPoint(objectMapper))
        .accessDeniedHandler(new CustomAccessDeniedHandler(objectMapper))
        .and()
        .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
            .requestMatchers("/member/join", "/member/login", "/member/reissue", "/book/search/**" , "/book/detail/**", "/reports/**", "/emotion/**")
            .permitAll()
            .requestMatchers("/book/create", "/book/delete/**", "/book/update/**").hasRole("ADMIN")
            .anyRequest().authenticated())
        .cors().configurationSource(corsConfigurationSource()) // 타 도메인에서 API 접근 허용
        .and()
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

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedMethod("*");
    configuration.addAllowedHeader("*");
    configuration.addAllowedOrigin("http://localhost:3000");
    configuration.setAllowCredentials(true);
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
