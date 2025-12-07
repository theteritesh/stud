package com.exam.stud.model;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ExamQuestion extends BaseEntity{

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String examQuestionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;
    
    public ExamQuestion(String examQuestionId, Exam exam, Question question) {
        this.examQuestionId = examQuestionId;
        this.exam = exam;
        this.question = question;
    }

}