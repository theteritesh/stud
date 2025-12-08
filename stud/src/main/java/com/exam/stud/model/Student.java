package com.exam.stud.model;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Student extends BaseEntity{

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String studentId;
    private String gender;
    
    @OneToOne(cascade = CascadeType.ALL) // If you delete Student, delete User too (optional)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;
	
    @OneToMany(
        mappedBy = "student",
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    private Set<ExamInvitation> examInvitations = new HashSet<>();
    
    
}
