package com.project.bookreport.repository;


import com.project.bookreport.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findMemberByUsername(String username);
}
