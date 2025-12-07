package com.exam.stud.model;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
public class Question extends BaseEntity{

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String questionId;

    @Column(nullable = false, length = 2000)
    private String questionText;

    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    @Column(nullable = false)
    private String correctAnswer;

}