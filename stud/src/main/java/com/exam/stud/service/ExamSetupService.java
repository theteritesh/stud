package com.exam.stud.service;

import com.exam.stud.enums.InvitationStatus;
import com.exam.stud.exception.DuplicateResourceException;
import com.exam.stud.exception.ResourceNotFoundException;
import com.exam.stud.model.Exam;
import com.exam.stud.model.ExamInvitation;
import com.exam.stud.model.ExamQuestion;
import com.exam.stud.model.Question;
import com.exam.stud.model.Student;
import com.exam.stud.repository.ExamInvitationRepository;
import com.exam.stud.repository.ExamQuestionRepository;
import com.exam.stud.repository.ExamRepository;
import com.exam.stud.repository.QuestionRepository;
import com.exam.stud.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExamSetupService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ExamQuestionRepository examQuestionRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private ExamInvitationRepository examInvitationRepository;

    @Transactional
    public List<ExamQuestion> assignMultipleQuestionsToExam(String examId, List<String> questionIds) {
        
        Exam exam = examRepository.findById(examId)
            .orElseThrow(() -> new ResourceNotFoundException("Exam not found with id: " + examId));
        
        List<ExamQuestion> newLinksToSave = new ArrayList<>();
        
        for (String questionId : questionIds) {
        	
            boolean alreadyExists = examQuestionRepository
                                     .findByExamExamIdAndQuestionQuestionId(examId, questionId)
                                     .isPresent();
            
            if (!alreadyExists) {
                Question question = questionRepository.findById(questionId)
                    .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + questionId));
                
                newLinksToSave.add(new ExamQuestion(null, exam, question));
            }
        }

        if (newLinksToSave.isEmpty()) {
            throw new DuplicateResourceException("All questions in the list are already assigned to this exam.");
        }
        
        return examQuestionRepository.saveAll(newLinksToSave);
    }

    public List<Question> getQuestionsForExam(String examId) {
        if (!examRepository.existsById(examId)) {
            throw new ResourceNotFoundException("Exam not found with id: " + examId);
        }
        return examQuestionRepository.findQuestionsByExamId(examId);
    }

    @Transactional
    public void removeQuestionFromExam(String examId, String questionId) {
        
        ExamQuestion examQuestionLink = examQuestionRepository.findByExamExamIdAndQuestionQuestionId(examId, questionId)
            .orElseThrow(() -> new ResourceNotFoundException("Question " + questionId + " is not assigned to exam " + examId));

        examQuestionRepository.delete(examQuestionLink);
    }
    
    @Transactional
    public List<ExamInvitation> inviteStudentsToExam(String examId, List<String> studentIds) {

        Exam exam = examRepository.findById(examId)
            .orElseThrow(() -> new ResourceNotFoundException("Exam not found with id: " + examId));
        
        List<ExamInvitation> newInvitations = new ArrayList<>();
        
        for (String studentId : studentIds) {
            
            boolean alreadyInvited = examInvitationRepository
                                      .findByStudentStudentIdAndExamExamId(studentId, examId)
                                      .isPresent();
            
            if (!alreadyInvited) {
                Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));
                
                ExamInvitation newInvitation = new ExamInvitation(
                    null,                  
                    student,               
                    exam,                 
                    InvitationStatus.INVITED, 
                    null,                  
                    null,
                    null,                  
                    null
                );
                
                newInvitations.add(newInvitation);
            }
        }

        if (newInvitations.isEmpty()) {
            throw new DuplicateResourceException("All students in the list are already invited to this exam.");
        }

        return examInvitationRepository.saveAll(newInvitations);
    }
}