package com.exam.stud.repository;

import com.exam.stud.model.ExamQuestion;
import com.exam.stud.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamQuestionRepository extends JpaRepository<ExamQuestion, String> {
	
    @Query("SELECT eq.question FROM ExamQuestion eq WHERE eq.exam.examId = :examId")
    List<Question> findQuestionsByExamId(@Param("examId") String examId);
    
    Optional<ExamQuestion> findByExamExamIdAndQuestionQuestionId(String examId, String questionId);
    
}