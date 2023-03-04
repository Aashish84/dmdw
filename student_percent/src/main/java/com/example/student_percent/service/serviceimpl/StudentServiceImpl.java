package com.example.student_percent.service.serviceimpl;

import com.example.student_percent.entity.Student;
import com.example.student_percent.repository.StudentRepository;
import com.example.student_percent.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> allStudent() {
        return studentRepository.findAll();
    }

    @Override
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public String checkPercent(boolean passOrFail) {
        String res = "you percent is ";
        if (passOrFail){
            double percent = studentRepository.findHighPercent().getPercent();
            res += "greater than or equal to "+percent;
        }else{
            double percent = studentRepository.findLowPercent().getPercent();
            res += "less than or equal to "+percent;
        }
        return res;
    }
}
