package com.project.bookreport.repository;

import com.project.bookreport.domain.Book;
import com.project.bookreport.domain.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {

  Page<Report> findAllByBook(Book book, Pageable pageable);
}
