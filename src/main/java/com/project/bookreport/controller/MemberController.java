package com.project.bookreport.controller;

import com.project.bookreport.model.jwt.JwtDto;
import com.project.bookreport.model.member.JoinRequest;
import com.project.bookreport.model.member.LoginRequest;
import com.project.bookreport.model.member.MemberDTO;
import com.project.bookreport.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member/join")
    public ResponseEntity<MemberDTO> join(@Valid @RequestBody JoinRequest joinRequest){
        MemberDTO memberDTO = memberService.join(joinRequest);

        return ResponseEntity.ok(memberDTO) ;
    }

    @PostMapping("/member/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginRequest loginRequest) {
        JwtDto jwtDto = memberService.login(loginRequest);

        return ResponseEntity.ok()
            .header("AccessToken", jwtDto.getAccessToken())
            .header("RefreshToken", jwtDto.getRefreshToken())
            .build();
    }
}
