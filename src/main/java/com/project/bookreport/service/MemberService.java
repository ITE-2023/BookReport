package com.project.bookreport.service;

import static com.project.bookreport.exception.ErrorCode.*;

import com.project.bookreport.domain.Member;
import com.project.bookreport.domain.status.MemberRole;
import com.project.bookreport.exception.MemberException;
import com.project.bookreport.model.jwt.JwtDto;
import com.project.bookreport.model.member.JoinRequest;
import com.project.bookreport.model.member.LoginRequest;
import com.project.bookreport.model.member.MemberDTO;
import com.project.bookreport.repository.MemberRepository;
import com.project.bookreport.security.jwt.JwtProvider;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        if (memberRepository.findByUsername(joinRequest.getUsername()).isPresent()) {
            throw new MemberException(MEMBER_NOT_UNIQUE);
        }

        Member member = Member.builder()
                .username(joinRequest.getUsername())
                .password(passwordEncoder.encode(joinRequest.getPassword()))
                .build();
        Set<MemberRole> roles = new HashSet<>();
        roles.add(MemberRole.ROLE_MEMBER);
        member.setRoleSet(roles);
        Member savedMember = memberRepository.save(member);
        return MemberDTO.builder()
                .id(savedMember.getId())
                .username(savedMember.getUsername())
                .createDate(savedMember.getCreateDate())
                .updateDate(savedMember.getUpdateDate())
                .build();

    }

    @Transactional
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
    public Optional<Member> findMemberById(Long id) {
        return memberRepository.findMemberById(id);
    }

    public boolean verifyWithMemberToken(Member member, String token) {
        return member.getAccessToken().equals(token);
    }

    @Transactional
    public JwtDto reissue(String refreshToken) {
        String token = jwtProvider.resolveToken(refreshToken);
        Member member = memberRepository.findByRefreshToken(token)
            .orElseThrow(() -> new MemberException(INVALID_TOKEN));

        if (!jwtProvider.verifyRefreshToken(token)) {
            throw new MemberException(INVALID_TOKEN);
        }

        String accessToken = jwtProvider.generateAccessToken(member);
        member.setAccessToken(accessToken);
        return JwtDto.from(member);
    }
}
