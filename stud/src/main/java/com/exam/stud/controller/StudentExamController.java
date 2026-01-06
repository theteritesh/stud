package com.exam.stud.controller;

import com.exam.stud.dto.AnswerDTO;
import com.exam.stud.dto.ExamInvitationDTO;
import com.exam.stud.dto.QuestionDTO;
import com.exam.stud.model.ExamInvitation;
import com.exam.stud.service.SecurityService;
import com.exam.stud.service.StudentExamService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/studentExam")
@PreAuthorize("hasRole('STUDENT')")
public class StudentExamController {

    @Autowired
    private StudentExamService studentExamService;
    
    @Autowired
    private SecurityService securityService;
    
    @GetMapping("/invitations")
    public ResponseEntity<List<ExamInvitationDTO>> getStudentInvitations() {
    	String studentId = securityService.getCurrentStudent().getStudentId();
        List<ExamInvitationDTO> invitations = studentExamService.getStudentInvitations(studentId);
        return ResponseEntity.ok(invitations);
    }

    @PostMapping("/checkIn/{examId}")
    public ResponseEntity<Map<String, Object>> checkInToExam(
            @PathVariable String examId) {
    	String studentId = securityService.getCurrentStudent().getStudentId();
        Map<String, Object> response = studentExamService.checkInToExam(studentId, examId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/checkOut/{examId}")
    public ResponseEntity<Map<String, Object>> checkOutFromExam(
            @PathVariable String examId) {
    	String studentId = securityService.getCurrentStudent().getStudentId();
        Map<String, Object> response = studentExamService.checkOutFromExam(studentId, examId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/getExamQuestionsToSubmit/{examId}")
    public ResponseEntity<List<QuestionDTO>> getExamQuestions(
            @PathVariable String examId) {
    	String studentId = securityService.getCurrentStudent().getStudentId();
        List<QuestionDTO> questions = studentExamService.getExamQuestions(studentId, examId);
        return ResponseEntity.ok(questions);
    }

    
    @PostMapping("/submitAnswer/{examId}")
    public ResponseEntity<Map<String, Object>> submitExamAnswers(
            @PathVariable String examId,
            @RequestBody List<AnswerDTO> answers) {
    	String studentId = securityService.getCurrentStudent().getStudentId();
        Map<String, Object> response = studentExamService.submitExamAnswers(studentId, examId, answers);
        return ResponseEntity.ok(response);
    }
}