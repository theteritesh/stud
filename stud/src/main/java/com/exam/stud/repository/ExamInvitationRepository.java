package com.exam.stud.repository;

import com.exam.stud.model.ExamInvitation;
import com.exam.stud.model.InvitationStatus;
import com.exam.stud.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamInvitationRepository extends JpaRepository<ExamInvitation, String> {

    long countByStudentStudentId(String studentId);

    long countByStudentStudentIdAndStatusIn(String studentId, List<InvitationStatus> statuses);

    long countByExamExamId(String examId);

    long countByExamExamIdAndStatusIn(String examId, List<InvitationStatus> statuses);

    @Query("SELECT ei.student FROM ExamInvitation ei WHERE ei.exam.examId = :examId AND ei.status IN :statuses")
    List<Student> findAttendingStudentsByExamId(@Param("examId") String examId, @Param("statuses") List<InvitationStatus> statuses);

	Optional<ExamInvitation> findByStudentStudentIdAndExamExamId(String studentId, String examId);
	
	List<ExamInvitation> findByStudentStudentId(String studentId);
	
	List<ExamInvitation> findByExamExamId(String examId);

}