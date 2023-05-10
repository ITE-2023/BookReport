package com.project.bookreport.domain.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * BaseEntity
 * 도메인별 공통 필드를 묶어놓은 클래스
 */
@Getter
@MappedSuperclass // 부모 클래스를 상속받는 자식 클래스에게 매핑 정보를 제공한다.
@SuperBuilder // 부모 클래스를 상속받는 자식 크래스를 생성할 때, 부모 클래스의 필드도 초기화하도록 한다.
@NoArgsConstructor // 기본 생성자를 생성한다.
@EntityListeners(AuditingEntityListener.class) // 생성일과 수정일, 생성자 자동 생성
public abstract class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createDate;

  @LastModifiedDate
  private LocalDateTime updateDate;
}
