package com.project.repository;

import com.project.entity.record.InflowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InflowRecordRepository extends JpaRepository<InflowRecord, Long> {

}
