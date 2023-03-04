package com.example.student_percent.controller;

import com.example.student_percent.entity.Student;
import com.example.student_percent.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/get")
    public ResponseEntity<?> allStudent(){
        return new ResponseEntity<>(studentService.allStudent(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addStudent(@RequestBody Student student){
        return new ResponseEntity<>(studentService.addStudent(student),HttpStatus.CREATED);
    }

    @PostMapping("/check")
    public ResponseEntity<?> checkPercent(@RequestParam("passOrFail") boolean remark){
        return new ResponseEntity<>(studentService.checkPercent(remark), HttpStatus.OK);
    }
}
