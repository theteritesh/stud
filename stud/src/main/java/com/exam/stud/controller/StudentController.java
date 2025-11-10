package com.exam.stud.controller;

import com.exam.stud.model.Student;
import com.exam.stud.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/createStudent")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student newStudent = studentService.createStudent(student);
        return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
    }

    @GetMapping("/getAllStudent")
    public ResponseEntity<Page<Student>> getAllStudents(
    		@PageableDefault(size = 10, sort = "studentId", direction = Direction.DESC) Pageable pageable) {
        Page<Student> students = studentService.getAllStudents(pageable);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/getStudentById/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable("studentId") String studentId) {
        Student student = studentService.getStudentById(studentId);
        return ResponseEntity.ok(student);
    }

    @PutMapping("/updateStudent/{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable("studentId") String studentId, @RequestBody Student studentDetails) {
        Student updatedStudent = studentService.updateStudent(studentId, studentDetails);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/deleteStudentById/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable("studentId") String studentId) {
        studentService.deleteStudent(studentId);
        Map<String, String> response = new HashMap<>();
        
        response.put("deletedStudentId", studentId);
        response.put("message", "Student deleted successfully");
        
        return ResponseEntity.ok(response);
    }
}