package com.project.bookreport.repository;

import com.project.bookreport.domain.Book;
import com.project.bookreport.domain.Member;
import com.project.bookreport.domain.MemberBook;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberBookRepository extends JpaRepository<MemberBook, Long> {

  Optional<MemberBook> findByMemberAndBook(Member member, Book book);
}
