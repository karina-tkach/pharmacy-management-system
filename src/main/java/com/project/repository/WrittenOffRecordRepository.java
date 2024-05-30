package com.project.repository;

import com.project.entity.record.WrittenOffRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WrittenOffRecordRepository extends JpaRepository<WrittenOffRecord, Long> {

  @Query("SELECT wor FROM WrittenOffRecord wor JOIN wor.storageProduct sp WHERE sp.product.id = :productId AND wor.writtenOffAt BETWEEN :startDate AND :endDate ORDER BY wor.writtenOffAt ASC")
  List<WrittenOffRecord> findAllByProductIdAndBetweenDates(
      @Param("productId") Long productId,
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate);
}
