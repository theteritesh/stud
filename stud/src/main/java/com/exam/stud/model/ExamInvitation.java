package com.exam.stud.model;

import java.time.LocalDateTime;
import org.hibernate.annotations.UuidGenerator;

import com.exam.stud.enums.InvitationStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
public class ExamInvitation extends BaseEntity{

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String invitationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @Enumerated(EnumType.STRING) 
    @Column(nullable = false)
    private InvitationStatus status;

    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    
    private Integer score;
    private Integer totalQuestionsInExam;
}