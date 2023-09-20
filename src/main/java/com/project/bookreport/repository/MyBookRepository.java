package com.project.bookreport.repository;

import com.project.bookreport.domain.Book;
import com.project.bookreport.domain.Member;
import com.project.bookreport.domain.MyBook;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MyBookRepository extends JpaRepository<MyBook, Long> {

  Optional<MyBook> findByMemberAndBook(Member member, Book book);


  @Query("SELECT m FROM MyBook m " +
      "WHERE (YEAR(m.startDate) = :year OR YEAR(m.readingStartDate) = :year " +
      "       OR (m.myBookStatus = '읽고 싶은 책' AND YEAR(m.updateDate) = :year)) " +
      "AND m.member = :member")
  Page<MyBook> findAllByMember(Pageable pageable, @Param("member") Member member, @Param("year") int year);

  List<MyBook> findAllByBook(Book book);


}
