package com.studentapp.controller;
 
import com.studentapp.model.Student;
import com.studentapp.service.StudentService;
import com.studentapp.service.StudentService.StudentResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
 
@RestController
@RequestMapping("/students")
public class StudentController {
 
    @Autowired
    private StudentService studentService;
    
    // GET endpoint to retrieve student result by id (with optional name check)
    @GetMapping("/{id}")
    public Object getStudentResult(@PathVariable int id, @RequestParam(required = false) String name) {
        StudentResult studentResult = studentService.getStudentResult(id);
        if (studentResult == null) {
            return "Student not found";
        }
        // Optional: Check if the provided name matches the record
        if (name != null && !name.equalsIgnoreCase(studentResult.getStudent().getName())) {
            return "Student name does not match the record";
        }
        return studentResult;
    }
    
    // POST endpoint to create a new student record
    @PostMapping("/")
    public String createStudent(@RequestBody Student student) {
        studentService.saveStudent(student);
        return "Student marks details created successfully";
    }
    
    // DELETE endpoint to delete a student record by id
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable int id) {
        boolean deleted = studentService.deleteStudent(id);
        if (deleted) {
            return "Student deleted successfully";
        } else {
            return "Student not found";
        }
    }
}
 