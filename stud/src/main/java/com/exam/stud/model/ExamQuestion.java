package com.exam.stud.model;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ExamQuestion {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String examQuestionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    public ExamQuestion() {
    }

    public ExamQuestion(String examQuestionId, Exam exam, Question question) {
        this.examQuestionId = examQuestionId;
        this.exam = exam;
        this.question = question;
    }

    // --- Getters and Setters ---

    public String getExamQuestionId() {
        return examQuestionId;
    }

    public void setExamQuestionId(String examQuestionId) {
        this.examQuestionId = examQuestionId;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}