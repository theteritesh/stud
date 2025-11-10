package com.exam.stud.controller;

import com.exam.stud.model.Question;
import com.exam.stud.service.QuestionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/createQuestion")
    public ResponseEntity<Question> createQuestion(@RequestBody Question question) {
        Question newQuestion = questionService.createQuestion(question);
        return new ResponseEntity<>(newQuestion, HttpStatus.CREATED);
    }

    @GetMapping("/getAllQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/getQuestionById/{questionId}")
    public ResponseEntity<Question> getQuestionById(@PathVariable("questionId") String questionId) {
        Question question = questionService.getQuestionById(questionId);
        return ResponseEntity.ok(question);
    }

    @PutMapping("/updateQuestion/{questionId}")
    public ResponseEntity<Question> updateQuestion(@PathVariable("questionId") String questionId, @RequestBody Question questionDetails) {
        Question updatedQuestion = questionService.updateQuestion(questionId, questionDetails);
        return ResponseEntity.ok(updatedQuestion);
    }

    @DeleteMapping("/deleteQuestionById/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable("questionId") String questionId) {
        questionService.deleteQuestion(questionId);

        Map<String, String> response = new HashMap<>();
        response.put("deletedQuestionId", questionId);
        response.put("message", "Question deleted successfully");
        
        return ResponseEntity.ok(response);
    }
}