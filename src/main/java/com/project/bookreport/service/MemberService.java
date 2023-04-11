package com.project.bookreport.service;

import com.project.bookreport.domain.Member;
import com.project.bookreport.model.member.JoinRequest;
import com.project.bookreport.model.member.MemberDTO;
import com.project.bookreport.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    public MemberDTO join(JoinRequest joinRequest) {
        if (!joinRequest.getPassword().equals(joinRequest.getPassword2())) {
            throw new RuntimeException();
        }
        Member member = Member.builder()
                .username(joinRequest.getUsername())
                .password(joinRequest.getPassword())
                .build();
        Member savedMember = memberRepository.save(member);
        return MemberDTO.builder()
                .id(savedMember.getId())
                .username(savedMember.getUsername())
                .createDate(savedMember.getCreateDate())
                .updateDate(savedMember.getUpdateDate())
                .build();

    }
}
