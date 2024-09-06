package com.onlaine.excel.repository;

import com.onlaine.excel.domain.ExcelPathology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcelRepository extends JpaRepository<ExcelPathology, Long> {
}
