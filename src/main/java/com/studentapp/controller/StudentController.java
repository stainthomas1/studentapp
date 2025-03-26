package com.studentapp.controller;
 
import com.studentapp.model.Student;
import com.studentapp.service.StudentService;
import com.studentapp.service.StudentService.StudentResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
 
import org.springframework.stereotype.Controller;
 
@Controller
@RequestMapping("/students")
public class StudentController {
 
    @Autowired
    private StudentService studentService;
 
    // Load HTML form to enter student ID and name
    @GetMapping("/form")
    public String showForm() {
        return "studentForm";
    }
 
    // Handle the form submission and show the result
    @PostMapping("/result")
    public String getStudentResult(@RequestParam int id, @RequestParam(required = false) String name, Model model) {
        StudentResult studentResult = studentService.getStudentResult(id);
        if (studentResult == null) {
            model.addAttribute("message", "Student not found");
            return "studentResult";
        }
        if (name != null && !name.equalsIgnoreCase(studentResult.getStudent().getName())) {
            model.addAttribute("message", "Student name does not match the record");
            return "studentResult";
        }
        model.addAttribute("studentResult", studentResult);
        return "studentResult";
    }
 
    // POST endpoint to add a new student record
    @PostMapping("/add")
    public String createStudent(@ModelAttribute Student student, Model model) {
        studentService.saveStudent(student);
        model.addAttribute("message", "Student added successfully with ID: " + student.getId());
        return "studentResult";
    }
 
    // GET endpoint to fetch student result without using form
    @GetMapping("/{id}")
    public String getStudentResultDirect(@PathVariable int id, Model model) {
        StudentResult studentResult = studentService.getStudentResult(id);
        if (studentResult == null) {
            model.addAttribute("message", "Student not found");
            return "studentResult";
        }
        model.addAttribute("studentResult", studentResult);
        return "studentResult";
    }
 
    // DELETE endpoint to delete a student record by id
    @DeleteMapping("/{id}")
    @ResponseBody
    public String deleteStudent(@PathVariable int id) {
        boolean deleted = studentService.deleteStudent(id);
        if (deleted) {
            return "Student with ID " + id + " deleted successfully.";
        } else {
            return "Student not found.";
        }
    }
}
