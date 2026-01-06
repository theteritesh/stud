package com.exam.stud.controller;

import com.exam.stud.dto.ExamPerformanceReportDTO;
import com.exam.stud.dto.StudentExamResultDTO;
import com.exam.stud.model.Student;
import com.exam.stud.model.User;
import com.exam.stud.service.ReportingService;
import com.exam.stud.service.SecurityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/reports")
public class ReportingController {

    @Autowired
    private ReportingService reportingService;
    
    @Autowired
    private SecurityService securityService;

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN')")
    public ResponseEntity<Map<String, Object>> getStudentReport(@PathVariable String studentId) {
    	User currentUser = securityService.getCurrentUser();
        if (currentUser.getRole().name().equals("STUDENT")) {
            Student myProfile = securityService.getCurrentStudent();
            if (!myProfile.getStudentId().equals(studentId)) {
                throw new RuntimeException("Access Denied: You cannot view another student's report.");
            }
        }
        Map<String, Object> report = reportingService.getStudentReport(studentId);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/exam/{examId}")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    public ResponseEntity<Map<String, Object>> getExamReport(@PathVariable String examId) {
        Map<String, Object> report = reportingService.getExamReport(examId);
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/studentResult/{invitationId}")
    public ResponseEntity<StudentExamResultDTO> getStudentExamResult(@PathVariable String invitationId) {
        StudentExamResultDTO report = reportingService.getStudentExamResult(invitationId);
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/examPerformance/{examId}")
    public ResponseEntity<ExamPerformanceReportDTO> getExamPerformanceReport(@PathVariable String examId) {
        ExamPerformanceReportDTO report = reportingService.getExamPerformanceReport(examId);
        return ResponseEntity.ok(report);
    }
}