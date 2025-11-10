package com.exam.stud.repository;

import com.exam.stud.model.StudentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentResponseRepository extends JpaRepository<StudentResponse, String> {

    List<StudentResponse> findByExamInvitationInvitationId(String invitationId);
}