package com.exam.stud.model;

import java.time.LocalDateTime;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Exam extends BaseEntity{

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String examId;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}