package com.project.bookreport.model.member;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class MemberDTO {
    private Long id;
    private String username;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
