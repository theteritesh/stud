package com.exam.stud.service;

import com.exam.stud.model.Exam;
import com.exam.stud.repository.ExamRepository;
import com.exam.stud.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Transactional
    public Exam createExam(Exam exam) {
        exam.setExamId(null);
        return examRepository.save(exam);
    }

    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    public Exam getExamById(String examId) {
        return examRepository.findById(examId)
            .orElseThrow(() -> new ResourceNotFoundException("Exam not found with id: " + examId));
    }

    @Transactional
    public Exam updateExam(String examId, Exam examDetails) {
        Exam existingExam = getExamById(examId);
        
        existingExam.setTitle(examDetails.getTitle());
        existingExam.setDescription(examDetails.getDescription());
        existingExam.setStartTime(examDetails.getStartTime());
        existingExam.setEndTime(examDetails.getEndTime());

        return examRepository.save(existingExam);
    }

    @Transactional
    public void deleteExam(String examId) {
        if (!examRepository.existsById(examId)) {
            throw new ResourceNotFoundException("Exam not found with id: " + examId);
        }
        examRepository.deleteById(examId);
    }
}