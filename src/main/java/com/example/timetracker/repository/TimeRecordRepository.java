package com.example.timetracker.repository;

import com.example.timetracker.entity.record.TimeRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeRecordRepository extends JpaRepository<TimeRecord, Long> {
    List<TimeRecord> findAllByProjectId(long projectId);
}
