package com.example.student_percent.repository;

import com.example.student_percent.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query(value = "select * from student where percent = ( select max(percent) from student where pass_or_fail = false);", nativeQuery = true)
    Student findLowPercent();
    @Query(value = "select * from student where percent = ( select min(percent) from student where pass_or_fail = true);", nativeQuery = true)
    Student findHighPercent();
}
