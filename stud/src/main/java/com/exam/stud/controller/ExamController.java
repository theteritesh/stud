package com.exam.stud.controller;

import com.exam.stud.model.Exam;
import com.exam.stud.service.ExamService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exams")
public class ExamController {

    @Autowired
    private ExamService examService;

    @PostMapping("/createExam")
    public ResponseEntity<Exam> createExam(@RequestBody Exam exam) {
        Exam newExam = examService.createExam(exam);
        return new ResponseEntity<>(newExam, HttpStatus.CREATED);
    }

    @GetMapping("/getAllExams")
    public ResponseEntity<List<Exam>> getAllExams() {
        List<Exam> exams = examService.getAllExams();
        return ResponseEntity.ok(exams);
    }

    @GetMapping("/getExamById/{examId}")
    public ResponseEntity<Exam> getExamById(@PathVariable("examId") String examId) {
        Exam exam = examService.getExamById(examId);
        return ResponseEntity.ok(exam);
    }

    @PutMapping("/updateExam/{examId}")
    public ResponseEntity<Exam> updateExam(@PathVariable("examId") String examId, @RequestBody Exam examDetails) {
        Exam updatedExam = examService.updateExam(examId, examDetails);
        return ResponseEntity.ok(updatedExam);
    }

    @DeleteMapping("/deleteExamById/{examId}")
    public ResponseEntity<?> deleteExam(@PathVariable("examId") String examId) {
        examService.deleteExam(examId);
        
        Map<String, String> response = new HashMap<>();
        response.put("deletedExamId", examId);
        response.put("message", "Exam deleted successfully");
        
        return ResponseEntity.ok(response);
    }
}