package com.exam.stud.service;

import com.exam.stud.model.Question;
import com.exam.stud.repository.QuestionRepository;
import com.exam.stud.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Transactional
    public Question createQuestion(Question question) {
        question.setQuestionId(null); 
        return questionRepository.save(question);
    }

    public Page<Question> getAllQuestions(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    public Question getQuestionById(String questionId) {
        return questionRepository.findById(questionId)
            .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + questionId));
    }

    
    @Transactional
    public Question updateQuestion(String questionId, Question questionDetails) {
    	
        Question existingQuestion = getQuestionById(questionId);

        // 2. Update the fields
        existingQuestion.setQuestionText(questionDetails.getQuestionText());
        existingQuestion.setOptionA(questionDetails.getOptionA());
        existingQuestion.setOptionB(questionDetails.getOptionB());
        existingQuestion.setOptionC(questionDetails.getOptionC());
        existingQuestion.setOptionD(questionDetails.getOptionD());
        existingQuestion.setCorrectAnswer(questionDetails.getCorrectAnswer());

        // 3. Save the updated question
        return questionRepository.save(existingQuestion);
    }

    @Transactional
    public void deleteQuestion(String questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new ResourceNotFoundException("Question not found with id: " + questionId);
        }
        questionRepository.deleteById(questionId);
    }
}