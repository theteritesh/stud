package com.exam.stud.model;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class StudentResponse extends BaseEntity{

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String responseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitation_id", nullable = false)
    private ExamInvitation examInvitation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(nullable = false)
    private String selectedAnswer;

    public StudentResponse() {
    }

    public StudentResponse(String responseId, ExamInvitation examInvitation, Question question, String selectedAnswer) {
        this.responseId = responseId;
        this.examInvitation = examInvitation;
        this.question = question;
        this.selectedAnswer = selectedAnswer;
    }

    // --- Getters and Setters ---

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public ExamInvitation getExamInvitation() {
        return examInvitation;
    }

    public void setExamInvitation(ExamInvitation examInvitation) {
        this.examInvitation = examInvitation;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }
}