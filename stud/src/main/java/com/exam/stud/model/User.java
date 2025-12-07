package com.exam.stud.model;

import org.hibernate.annotations.UuidGenerator;

import com.exam.stud.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User extends BaseEntity{
	@Id
    @UuidGenerator
    private String userId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password; // Encrypted string

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;
}
