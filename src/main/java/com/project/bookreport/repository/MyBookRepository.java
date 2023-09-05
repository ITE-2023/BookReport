package com.project.bookreport.repository;

import com.project.bookreport.domain.Book;
import com.project.bookreport.domain.Member;
import com.project.bookreport.domain.MyBook;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyBookRepository extends JpaRepository<MyBook, Long> {

  Optional<MyBook> findByMemberAndBook(Member member, Book book);

  List<MyBook> findAllByMember(Member member);
}
