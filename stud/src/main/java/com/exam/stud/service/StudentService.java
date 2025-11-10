package com.exam.stud.service;

import com.exam.stud.model.Student;
import com.exam.stud.repository.StudentRepository;
// Import our new exceptions
import com.exam.stud.exception.DuplicateResourceException;
import com.exam.stud.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service 
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;

    @Transactional
    public Student createStudent(Student student) {
        
        Optional<Student> existingStudent = studentRepository.findByEmail(student.getEmail());
        if (existingStudent.isPresent()) {
            throw new DuplicateResourceException("A student with email " + student.getEmail() + " already exists.");
        }
        return studentRepository.save(student);
    }
    
    public Page<Student> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    public Student getStudentById(String studentId) {
        return studentRepository.findById(studentId)
            .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));
    }

    @Transactional
    public Student updateStudent(String studentId, Student updatedStudentData) {
        
        Student existingStudent = getStudentById(studentId);

        // Update the fields
        existingStudent.setFirstName(updatedStudentData.getFirstName());
        existingStudent.setLastName(updatedStudentData.getLastName());
        existingStudent.setGender(updatedStudentData.getGender());
        existingStudent.setEmail(updatedStudentData.getEmail());

        // Save the updated student
        return studentRepository.save(existingStudent);
    }

    @Transactional
    public void deleteStudent(String studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found with id: " + studentId);
        }
        studentRepository.deleteById(studentId);
    }
}