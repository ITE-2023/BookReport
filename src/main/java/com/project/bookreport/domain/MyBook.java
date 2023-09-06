package com.project.bookreport.domain;

import com.project.bookreport.domain.base.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Member - Book
 * 다대다 관계를 매핑하기 위한 엔티티
 */
@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
public class MyBook extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  private Book book;

  private String myBookStatus;
  private Integer rate;
  private LocalDateTime startDate;
  private LocalDateTime endDate;

  @OneToOne(mappedBy = "myBook", cascade = CascadeType.REMOVE)
  private Report report;
}
