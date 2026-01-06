package com.exam.stud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.exam.stud.exception.ResourceNotFoundException;
import com.exam.stud.model.Student;
import com.exam.stud.model.User;
import com.exam.stud.repository.StudentRepository;
import com.exam.stud.repository.UserRepository;

@Service
public class SecurityService {
	@Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public Student getCurrentStudent() {
        User user = getCurrentUser();
        return studentRepository.findByUserEmail(user.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found. Are you logged in as a Student?"));
    }
}
