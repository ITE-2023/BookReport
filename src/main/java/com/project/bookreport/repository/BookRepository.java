package com.project.bookreport.repository;

import com.project.bookreport.domain.Book;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

  Optional<Book> findByBookNameAndAuthorAndPublisher(String bookName, String author,
      String publisher);
}
