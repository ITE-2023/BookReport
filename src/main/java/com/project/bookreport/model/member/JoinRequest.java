package com.project.bookreport.model.member;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class JoinRequest {
    // @NotNull: Null 허용하지 않는다.
    // @NotEmpty: Null && "" 허용하지 않는다.
    // @NotBlank: Null && "" && " " 허용하지 않는다.
    @NotBlank(message = "아이디를 입력해주세요.")
    private String username;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password2;

}
