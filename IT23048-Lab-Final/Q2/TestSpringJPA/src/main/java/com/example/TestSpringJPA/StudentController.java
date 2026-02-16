package com.example.TestSpringJPA;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // READ: List all students
    @GetMapping("/students")
    public String getStudents(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "student-list";
    }

    // INSERT: Show new student form
    @GetMapping("/students/new")
    public String showNewForm(Model model) {
        model.addAttribute("student", new Student());
        return "new-student";
    }

    // INSERT: Save new student
    @PostMapping("/students")
    public String addStudent(@ModelAttribute Student student) {
        studentService.saveStudent(student);
        return "redirect:/students";
    }

    // DELETE: Delete student by ID
    @PostMapping("/students/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }

    // UPDATE: Show edit form
    @GetMapping("/students/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        return "edit-student";
    }

    // UPDATE: Save edited student
    @PostMapping("/students/update")
    public String updateStudent(@ModelAttribute Student student) {
        studentService.saveStudent(student); // save updates if ID exists
        return "redirect:/students";
    }
}