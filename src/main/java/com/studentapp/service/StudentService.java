package com.studentapp.service;
 
import com.studentapp.model.Student;
import com.studentapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
@Service
public class StudentService {
 
    @Autowired
    private StudentRepository studentRepository;
 
    // Retrieve student by id and calculate average and result
    public StudentResult getStudentResult(int id) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student == null) {
            return null; // Return null if student not found
        }
 
        // Check if any mark is below 40
        if (student.getMark1() < 40 || student.getMark2() < 40 || student.getMark3() < 40 ||
            student.getMark4() < 40 || student.getMark5() < 40) {
            return new StudentResult(student, calculateAverage(student), "Fail");
        }
 
        // Calculate average and check result
        double average = calculateAverage(student);
        String result = average > 40 ? "Pass" : "Fail";
 
        return new StudentResult(student, average, result);
    }
 
    // Calculate the average of marks
    private double calculateAverage(Student student) {
        int sum = student.getMark1() + student.getMark2() + student.getMark3() +
                  student.getMark4() + student.getMark5();
        return sum / 5.0;
    }
    
    // Save a new student record
    public void saveStudent(Student student) {
        studentRepository.save(student);
    }
    
    // Delete a student record by id
    public boolean deleteStudent(int id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return true;
        }
        return false;
    }
 
    // Inner class to hold result details
    public static class StudentResult {
        private Student student;
        private double average;
        private String result;
 
        public StudentResult(Student student, double average, String result) {
            this.student = student;
            this.average = average;
            this.result = result;
        }
 
        public Student getStudent() {
            return student;
        }
        public double getAverage() {
            return average;
        }
        public String getResult() {
            return result;
        }
    }
}