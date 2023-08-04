package com.project.bookreport.repository;

import com.project.bookreport.domain.Report;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {

  List<Report> findAllByMember_Username(String username, Pageable pageable);
}
