package com.exam.stud.service;

import com.exam.stud.dto.ExamPerformanceReportDTO;
import com.exam.stud.dto.QuestionResultDTO;
import com.exam.stud.dto.ResultStatus;
import com.exam.stud.dto.StudentExamResultDTO;
import com.exam.stud.dto.StudentPerformanceDTO;
import com.exam.stud.exception.ClientRequestException;
import com.exam.stud.exception.ResourceNotFoundException;
import com.exam.stud.model.Exam;
import com.exam.stud.model.ExamInvitation;
import com.exam.stud.model.InvitationStatus;
import com.exam.stud.model.Question;
import com.exam.stud.model.Student;
import com.exam.stud.model.StudentResponse;
import com.exam.stud.repository.ExamInvitationRepository;
import com.exam.stud.repository.ExamQuestionRepository;
import com.exam.stud.repository.ExamRepository;
import com.exam.stud.repository.StudentRepository;
import com.exam.stud.repository.StudentResponseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportingService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private ExamInvitationRepository examInvitationRepository;
    
    @Autowired
    private ExamQuestionRepository examQuestionRepository;
    
    @Autowired
    private StudentResponseRepository studentResponseRepository;

    private final List<InvitationStatus> attendedStatuses = List.of(
        InvitationStatus.ATTENDED, 
        InvitationStatus.COMPLETED
    );

    @Transactional(readOnly = true)
    public Map<String, Object> getStudentReport(String studentId) {
        
        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));

        long totalInvited = examInvitationRepository.countByStudentStudentId(studentId);

        long totalAttended = examInvitationRepository.countByStudentStudentIdAndStatusIn(
            studentId, 
            attendedStatuses
        );

        Map<String, Object> report = new HashMap<>();
        report.put("studentId", student.getStudentId());
        report.put("firstName", student.getFirstName());
        report.put("lastName", student.getLastName());
        report.put("email", student.getEmail());
        report.put("totalExamsInvited", totalInvited);
        report.put("totalExamsAttended", totalAttended);

        return report;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getExamReport(String examId) {

        Exam exam = examRepository.findById(examId)
            .orElseThrow(() -> new ResourceNotFoundException("Exam not found with id: " + examId));

        long totalInvited = examInvitationRepository.countByExamExamId(examId);

        long totalAttended = examInvitationRepository.countByExamExamIdAndStatusIn(
            examId, 
            attendedStatuses
        );

        List<Student> attendingStudents = examInvitationRepository.findAttendingStudentsByExamId(
            examId, 
            attendedStatuses
        );

        List<String> attendingStudentNames = attendingStudents.stream()
            .map(student -> student.getFirstName() + " " + student.getLastName())
            .collect(Collectors.toList());

        // 6. Build the Map response
        Map<String, Object> report = new HashMap<>();
        report.put("examId", exam.getExamId());
        report.put("examTitle", exam.getTitle());
        report.put("startTime", exam.getStartTime());
        report.put("endTime", exam.getEndTime());
        report.put("totalStudentsInvited", totalInvited);
        report.put("totalStudentsAttended", totalAttended);
        report.put("attendingStudentNames", attendingStudentNames);

        return report;
    }
    
    @Transactional(readOnly = true)
    public StudentExamResultDTO getStudentExamResult(String invitationId) {

        ExamInvitation invitation = examInvitationRepository.findById(invitationId)
            .orElseThrow(() -> new ResourceNotFoundException("No exam attempt found with id: " + invitationId));

        if (invitation.getStatus() != InvitationStatus.COMPLETED) {
            throw new ClientRequestException("Report is not available. The exam has not been completed.");
        }

        String examId = invitation.getExam().getExamId();
        List<Question> allExamQuestions = examQuestionRepository.findQuestionsByExamId(examId);

        List<StudentResponse> studentResponses = studentResponseRepository.findByExamInvitationInvitationId(invitationId);

        Map<String, StudentResponse> responseMap = studentResponses.stream()
            .collect(Collectors.toMap(sr -> sr.getQuestion().getQuestionId(), sr -> sr));

        List<QuestionResultDTO> questionResults = new ArrayList<>();
        int totalAttempted = 0;
        int totalIncorrect = 0;
        int totalSkipped = 0;

        for (Question question : allExamQuestions) {
            String questionId = question.getQuestionId();
            String correctAnswer = question.getCorrectAnswer();
            
            StudentResponse response = responseMap.get(questionId);

            if (response == null) {
                // Student SKIPPED this question
                totalSkipped++;
                questionResults.add(new QuestionResultDTO(
                    question.getQuestionText(),
                    "N/A", // No answer
                    correctAnswer,
                    ResultStatus.SKIPPED
                ));
            } else {
                totalAttempted++;
                String yourAnswer = response.getSelectedAnswer();
                
                if (yourAnswer.equalsIgnoreCase(correctAnswer)) {
                    questionResults.add(new QuestionResultDTO(
                        question.getQuestionText(),
                        yourAnswer,
                        correctAnswer,
                        ResultStatus.CORRECT
                    ));
                } else {
                    totalIncorrect++;
                    questionResults.add(new QuestionResultDTO(
                        question.getQuestionText(),
                        yourAnswer,
                        correctAnswer,
                        ResultStatus.INCORRECT
                    ));
                }
            }
        }

        StudentExamResultDTO report = new StudentExamResultDTO();
        report.setInvitationId(invitationId);
        report.setStudentName(invitation.getStudent().getFirstName() + " " + invitation.getStudent().getLastName());
        report.setExamTitle(invitation.getExam().getTitle());
        report.setStatus("COMPLETED");
        report.setCompletedOn(invitation.getCheckOutTime());
        report.setScore(invitation.getScore());
        report.setTotalQuestions(invitation.getTotalQuestionsInExam());
        report.setTotalAttempted(totalAttempted);
        report.setTotalCorrect(invitation.getScore());
        report.setTotalIncorrect(totalIncorrect);
        report.setTotalSkipped(totalSkipped);

        report.setQuestionResults(questionResults);

        return report;
    }
    
    @Transactional(readOnly = true)
    public ExamPerformanceReportDTO getExamPerformanceReport(String examId) {
        
        Exam exam = examRepository.findById(examId)
            .orElseThrow(() -> new ResourceNotFoundException("Exam not found with id: " + examId));
        
        List<ExamInvitation> allInvitations = examInvitationRepository.findByExamExamId(examId);

        ExamPerformanceReportDTO report = new ExamPerformanceReportDTO();
        List<StudentPerformanceDTO> performanceList = new ArrayList<>();
        
        long totalAttended = 0;
        long totalCompleted = 0;
        long totalSkipped = 0;
        double totalPercentageSum = 0.0;
        int topScore = -1;
        String topPerformerName = "N/A";
        
        for (ExamInvitation inv : allInvitations) {
            StudentPerformanceDTO studentDto = new StudentPerformanceDTO();
            String studentName = inv.getStudent().getFirstName() + " " + inv.getStudent().getLastName();
            studentDto.setStudentName(studentName);
            studentDto.setStatus(inv.getStatus());

            if (inv.getStatus() == InvitationStatus.INVITED) {
                totalSkipped++;
            }
            
            if (inv.getStatus() == InvitationStatus.ATTENDED) {
                totalAttended++;
            }
            
            if (inv.getStatus() == InvitationStatus.COMPLETED) {
                totalAttended++; 
                totalCompleted++;
                
                Integer score = inv.getScore();
                Integer totalQ = inv.getTotalQuestionsInExam();
                
                if (score != null && totalQ != null && totalQ > 0) {
                    double percentage = ((double) score / totalQ) * 100.0;
                    studentDto.setScore(score);
                    studentDto.setTotalQuestions(totalQ);
                    studentDto.setPercentage(percentage);
                    
                    totalPercentageSum += percentage;
                    
                    if (score > topScore) {
                        topScore = score;
                        topPerformerName = studentName;
                    }
                }
            }
            performanceList.add(studentDto);
        }
        
        report.setExamId(exam.getExamId());
        report.setExamTitle(exam.getTitle());

        report.setTotalInvited(allInvitations.size());
        report.setTotalAttended(totalAttended);
        report.setTotalCompleted(totalCompleted);
        report.setTotalSkipped(totalSkipped);
        
        if (totalCompleted > 0) {
            report.setAveragePercentage(totalPercentageSum / totalCompleted);
            report.setTopPerformerName(topPerformerName);
            report.setTopScore(topScore);
        } else {
            report.setAveragePercentage(0.0);
        }
        
        report.setStudentPerformances(performanceList);
        
        return report;
    }
}