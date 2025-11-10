package com.exam.stud.service;

import com.exam.stud.dto.AnswerDTO;
import com.exam.stud.dto.ExamInvitationDTO;
import com.exam.stud.dto.QuestionDTO;
import com.exam.stud.exception.ClientRequestException;
import com.exam.stud.exception.ResourceNotFoundException;
import com.exam.stud.model.ExamInvitation;
import com.exam.stud.model.InvitationStatus;
import com.exam.stud.model.Question;
import com.exam.stud.model.StudentResponse;
import com.exam.stud.repository.ExamInvitationRepository;
import com.exam.stud.repository.ExamQuestionRepository;
import com.exam.stud.repository.QuestionRepository;
import com.exam.stud.repository.StudentResponseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentExamService {


    @Autowired
    private ExamInvitationRepository examInvitationRepository;
    
    @Autowired
    private ExamQuestionRepository examQuestionRepository;
    
    @Autowired
    private QuestionRepository questionRepository;
    
    @Autowired
    private StudentResponseRepository studentResponseRepository;

    
    @Transactional(readOnly = true) // Use readOnly for GET methods
    public List<ExamInvitationDTO> getStudentInvitations(String studentId) {
        
        List<ExamInvitation> invitations = examInvitationRepository.findByStudentStudentId(studentId);
        
        return invitations.stream()
                .map(this::toDTO) // Use a helper method
                .collect(Collectors.toList());
    }

    @Transactional
    public Map<String, Object> checkInToExam(String studentId, String examId) {
        
        ExamInvitation invitation = examInvitationRepository.findByStudentStudentIdAndExamExamId(studentId, examId)
            .orElseThrow(() -> new ResourceNotFoundException("You are not invited to this exam."));

        if (invitation.getStatus() == InvitationStatus.ATTENDED || invitation.getStatus() == InvitationStatus.COMPLETED) {
        	throw new ClientRequestException("You have already checked in to this exam.");
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = invitation.getExam().getStartTime();
        LocalDateTime endTime = invitation.getExam().getEndTime();
        LocalDateTime checkinWindowStart = startTime.minusMinutes(15); // 15-minute buffer

        
        // CASE 1: Too Early
        if (now.isBefore(checkinWindowStart)) {
            Duration timeToWindow = Duration.between(now, checkinWindowStart);
            String remaining = formatDuration(timeToWindow);
            throw new ClientRequestException("Too early to check in. Please try again in " + remaining + " (at " + checkinWindowStart + ")");
        }

        // CASE 2: Too Late (Exam is over)
        if (now.isAfter(endTime)) {
            throw new ClientRequestException("This exam has already finished.");
        }

        // CASE 3: Valid Check-in Window (On time, or late but exam is still running)
        invitation.setStatus(InvitationStatus.ATTENDED);
        invitation.setCheckInTime(now);
        examInvitationRepository.save(invitation);

        String message;
        if (now.isBefore(startTime)) {
            // Checked in during the 15-min buffer
            Duration timeToStart = Duration.between(now, startTime);
            message = "Check-in successful! The exam will begin in " + formatDuration(timeToStart) + ".";
        } else {
            // Checked in after the exam started
            Duration timeLeft = Duration.between(now, endTime);
            message = "Check-in successful (late). You have " + formatDuration(timeLeft) + " remaining.";
        }
        
        return Map.of(
            "message", message,
            "invitationId", invitation.getInvitationId(),
            "studentName", invitation.getStudent().getFirstName(),
            "examTitle", invitation.getExam().getTitle(),
            "checkInTime", now
        );
    }

   
    @Transactional
    public Map<String, Object> checkOutFromExam(String studentId, String examId) {

        ExamInvitation invitation = examInvitationRepository.findByStudentStudentIdAndExamExamId(studentId, examId)
            .orElseThrow(() -> new ResourceNotFoundException("Invitation not found."));
        
        if (invitation.getStatus() == InvitationStatus.COMPLETED) {
            throw new ClientRequestException("You have already completed and checked out from this exam.");
        }

        if (invitation.getStatus() != InvitationStatus.ATTENDED) {
            throw new ClientRequestException("You cannot check out. You have not successfully checked in yet.");
        }

        LocalDateTime now = LocalDateTime.now();
        invitation.setStatus(InvitationStatus.COMPLETED);
        invitation.setCheckOutTime(now);
        examInvitationRepository.save(invitation);

        return Map.of(
            "message", "Exam completed and checked out successfully.",
            "invitationId", invitation.getInvitationId(),
            "checkOutTime", now
        );
    }

   
    
    @Transactional(readOnly = true)
    public List<QuestionDTO> getExamQuestions(String studentId, String examId) {
        
        ExamInvitation invitation = examInvitationRepository.findByStudentStudentIdAndExamExamId(studentId, examId)
            .orElseThrow(() -> new ResourceNotFoundException("You are not invited to this exam."));

        if (invitation.getStatus() != InvitationStatus.ATTENDED) {
            throw new ClientRequestException("You must be checked in to view the exam questions.");
        }
        
        if (LocalDateTime.now().isAfter(invitation.getExam().getEndTime())) {
            throw new ClientRequestException("The time for this exam is over.");
        }

        List<Question> questions = examQuestionRepository.findQuestionsByExamId(examId);

        return questions.stream()
                .map(QuestionDTO::new) // Uses the new constructor in QuestionDTO
                .collect(Collectors.toList());
    }
    
    @Transactional
    public Map<String, Object> submitExamAnswers(String studentId, String examId, List<AnswerDTO> answers) {
        
        ExamInvitation invitation = examInvitationRepository.findByStudentStudentIdAndExamExamId(studentId, examId)
            .orElseThrow(() -> new ResourceNotFoundException("You are not invited to this exam."));

        if (invitation.getStatus() != InvitationStatus.ATTENDED) {
            throw new ClientRequestException("You must be checked in to submit answers.");
        }

        List<StudentResponse> responsesToSave = new ArrayList<>();
        int correctCount = 0;

        for (AnswerDTO answer : answers) {
            Question question = questionRepository.findById(answer.getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException("Question not found: " + answer.getQuestionId()));
            
            if (answer.getSelectedAnswer() != null && 
                    answer.getSelectedAnswer().equalsIgnoreCase(question.getCorrectAnswer())) {
                    correctCount++;
                }
            
            StudentResponse response = new StudentResponse(
                null,
                invitation,
                question,
                answer.getSelectedAnswer()
            );
            responsesToSave.add(response);
        }

        studentResponseRepository.saveAll(responsesToSave);
        int totalQuestions = (int) examQuestionRepository.findQuestionsByExamId(examId).size();
        
        invitation.setStatus(InvitationStatus.COMPLETED);
        invitation.setCheckOutTime(LocalDateTime.now());
        invitation.setScore(correctCount);
        invitation.setTotalQuestionsInExam(totalQuestions);
        examInvitationRepository.save(invitation);

        return Map.of(
            "message", "Answers submitted successfully.",
            "totalAnswers", responsesToSave.size(),
            "score", correctCount,
            "totalQuestions", totalQuestions,
            "status", "COMPLETED"
        );
    }
    
    
    //Helper methods
    
    // Make durations user-friendly
    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();

        if (hours > 0) {
            return String.format("%d hours, %d minutes", hours, minutes);
        } else if (minutes > 0) {
            return String.format("%d minutes, %d seconds", minutes, seconds);
        } else {
            return String.format("%d seconds", seconds);
        }
    }
    
    private ExamInvitationDTO toDTO(ExamInvitation invitation) {
        return new ExamInvitationDTO(
            invitation.getInvitationId(),
            invitation.getStudent().getStudentId(),
            invitation.getStudent().getFirstName() + " " + invitation.getStudent().getLastName(),
            invitation.getExam().getExamId(),
            invitation.getExam().getTitle(),
            invitation.getStatus(),
            invitation.getExam().getStartTime(),
            invitation.getExam().getEndTime(),
            invitation.getCheckInTime(),
            invitation.getCheckOutTime(),
            invitation.getScore(),
            invitation.getTotalQuestionsInExam()
        );
    }
    
    
}