package com.studentapp.repository;
 
import com.studentapp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
 
public interface StudentRepository extends JpaRepository<Student, Integer> {
   
}