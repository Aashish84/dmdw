package com.example.student_percent.service;

import com.example.student_percent.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> allStudent();
    Student addStudent(Student student);
    String checkPercent(boolean passOrFail);
}
