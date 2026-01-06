package com.exam.stud.controller;

import com.exam.stud.model.ExamInvitation;
import com.exam.stud.model.ExamQuestion;
import com.exam.stud.model.Question;
import com.exam.stud.service.ExamSetupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/examSetup")
public class ExamSetupController {

    @Autowired
    private ExamSetupService examSetupService;

    @PostMapping("/assignQuestions/{examId}")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    public ResponseEntity<List<ExamQuestion>> assignMultipleQuestionsToExam(
            @PathVariable String examId,
            @RequestBody List<String> questionIds) {
        
        List<ExamQuestion> newLinks = examSetupService.assignMultipleQuestionsToExam(
            examId, 
            questionIds 
        );
        
        return new ResponseEntity<>(newLinks, HttpStatus.CREATED);
    }

    @GetMapping("/getAllQuestionForExam/{examId}")
    public ResponseEntity<List<Question>> getQuestionsForExam(@PathVariable String examId) {
        List<Question> questions = examSetupService.getQuestionsForExam(examId);
        return ResponseEntity.ok(questions);
    }

    @DeleteMapping("/removeQuestionFromExam/{examId}/{questionId}")
    public ResponseEntity<?> removeQuestionFromExam(
            @PathVariable String examId,
            @PathVariable String questionId) {
                
        examSetupService.removeQuestionFromExam(examId,questionId);
        Map<String, String> response = new HashMap<>();
        response.put("deletedQuestionId", questionId);
        response.put("message", "Question deleted successfully from exam: "+examId);
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/inviteStudents/{examId}")
    public ResponseEntity<List<ExamInvitation>> inviteStudentsToExam(
            @PathVariable String examId,
            @RequestBody List<String> studentIds) {
        
        List<ExamInvitation> newInvitations = examSetupService.inviteStudentsToExam(examId, studentIds);
        
        return new ResponseEntity<>(newInvitations, HttpStatus.CREATED);
    }
    //check
}