package com.project.bookreport.service;

import static com.project.bookreport.exception.ErrorCode.*;

import com.project.bookreport.domain.Member;
import com.project.bookreport.exception.MemberException;
import com.project.bookreport.model.jwt.JwtDto;
import com.project.bookreport.model.member.JoinRequest;
import com.project.bookreport.model.member.LoginRequest;
import com.project.bookreport.model.member.MemberDTO;
import com.project.bookreport.repository.MemberRepository;
import com.project.bookreport.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public MemberDTO join(JoinRequest joinRequest) {
        if (!joinRequest.getPassword().equals(joinRequest.getPassword2())) {
            throw new MemberException(AUTHENTICATION_FAILED);
        }
        Member member = Member.builder()
                .username(joinRequest.getUsername())
                .password(passwordEncoder.encode(joinRequest.getPassword()))
                .build();
        Member savedMember = memberRepository.save(member);
        return MemberDTO.builder()
                .id(savedMember.getId())
                .username(savedMember.getUsername())
                .createDate(savedMember.getCreateDate())
                .updateDate(savedMember.getUpdateDate())
                .build();

    }

    public JwtDto login(LoginRequest loginRequest) {
        Member member = memberRepository.findMemberByUsername(loginRequest.getUsername())
            .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));

        if (member == null || !passwordEncoder.matches(loginRequest.getPassword(),
            member.getPassword())) {
            throw new MemberException(AUTHENTICATION_FAILED);
        }

        String accessToken = jwtProvider.generateAccessToken(member);
        String refreshToken = jwtProvider.generateRefreshToken(member);
        member.setAccessToken(accessToken);
        member.setRefreshToken(refreshToken);
        return JwtDto.from(member);
    }
}
