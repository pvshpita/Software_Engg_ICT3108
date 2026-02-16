package com.example.TestSpringJPA;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public void saveStudent(Student student) {
        repository.save(student);
    }

    public void deleteStudent(Long id) {
        repository.deleteById(id);
    }

    public Student getStudentById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id: " + id));
    }
}