package com.exam.stud.controller;

import com.exam.stud.dto.AnswerDTO;
import com.exam.stud.dto.ExamInvitationDTO;
import com.exam.stud.dto.QuestionDTO;
import com.exam.stud.model.ExamInvitation;
import com.exam.stud.service.StudentExamService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/studentExam")
public class StudentExamController {

    @Autowired
    private StudentExamService studentExamService;

    @GetMapping("/invitations/{studentId}")
    public ResponseEntity<List<ExamInvitationDTO>> getStudentInvitations(@PathVariable String studentId) {
        List<ExamInvitationDTO> invitations = studentExamService.getStudentInvitations(studentId);
        return ResponseEntity.ok(invitations);
    }

    @PostMapping("/checkIn/{studentId}/{examId}")
    public ResponseEntity<Map<String, Object>> checkInToExam(
            @PathVariable String studentId, 
            @PathVariable String examId) {
        
        Map<String, Object> response = studentExamService.checkInToExam(studentId, examId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/checkOut/{studentId}/{examId}")
    public ResponseEntity<Map<String, Object>> checkOutFromExam(
            @PathVariable String studentId, 
            @PathVariable String examId) {
        
        Map<String, Object> response = studentExamService.checkOutFromExam(studentId, examId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/getExamQuestionsToSubmit/{studentId}/{examId}")
    public ResponseEntity<List<QuestionDTO>> getExamQuestions(
            @PathVariable String studentId,
            @PathVariable String examId) {
        
        List<QuestionDTO> questions = studentExamService.getExamQuestions(studentId, examId);
        return ResponseEntity.ok(questions);
    }

    
    @PostMapping("/submitAnswer/{studentId}/{examId}")
    public ResponseEntity<Map<String, Object>> submitExamAnswers(
            @PathVariable String studentId,
            @PathVariable String examId,
            @RequestBody List<AnswerDTO> answers) {
        
        Map<String, Object> response = studentExamService.submitExamAnswers(studentId, examId, answers);
        return ResponseEntity.ok(response);
    }
}