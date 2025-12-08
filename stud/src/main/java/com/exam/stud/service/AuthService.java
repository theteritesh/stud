package com.exam.stud.service;

import com.exam.stud.dto.JwtResponse;
import com.exam.stud.dto.LoginRequest;
import com.exam.stud.dto.RegisterRequest;
import com.exam.stud.enums.UserRole;
import com.exam.stud.exception.DuplicateResourceException;
import com.exam.stud.model.Student;
import com.exam.stud.model.User;
import com.exam.stud.repository.StudentRepository;
import com.exam.stud.repository.UserRepository;
import com.exam.stud.security.JwtUtils;
import com.exam.stud.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        // 1. Check Email & Password using Spring Security
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        // 2. Set the context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Generate JWT
        String jwt = jwtUtils.generateJwtToken(authentication);

        // 4. Get User Details
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        return new JwtResponse(
            jwt, 
            userDetails.getUsername(), 
            role, 
            userDetails.getUserId()
        );
    }

    @Transactional
    public String registerStudent(RegisterRequest request) {
        // 1. Check if email exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email is already in use!");
        }

        // 2. Create the User (Identity)
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword())); // Encrypt password!
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(UserRole.STUDENT);
        
        // We don't save 'user' yet. We save it via the 'Student' entity to keep the transaction atomic.

        // 3. Create the Student (Profile)
        Student student = new Student();
        student.setGender(request.getGender());
        student.setUser(user); // Link them!

        // 4. Save (Cascading will save the User too)
        studentRepository.save(student);

        return "Student registered successfully!";
    }
}