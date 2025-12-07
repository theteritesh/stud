package com.exam.stud.dto;

import java.time.LocalDateTime;

import com.exam.stud.enums.InvitationStatus;

public class ExamInvitationDTO {

    private String invitationId;
    private String studentId;
    private String studentName;
    private String examId;
    private String examTitle;
    private InvitationStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private Integer score;
    private Integer totalQuestionsInExam;

    // Default constructor for JSON
    public ExamInvitationDTO() {
    }

    // Constructor to make mapping easy
    public ExamInvitationDTO(String invitationId, String studentId, String studentName, String examId, String examTitle, 
    		InvitationStatus status, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime checkInTime, LocalDateTime checkOutTime,
    		Integer score, Integer totalQuestionsInExam) {
        this.invitationId = invitationId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.examId = examId;
        this.examTitle = examTitle;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.score = score;
        this.totalQuestionsInExam = totalQuestionsInExam;
    }
    
    // --- All Getters and Setters ---

	public String getInvitationId() {
		return invitationId;
	}

	public void setInvitationId(String invitationId) {
		this.invitationId = invitationId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getExamTitle() {
		return examTitle;
	}

	public void setExamTitle(String examTitle) {
		this.examTitle = examTitle;
	}

	public InvitationStatus getStatus() {
		return status;
	}

	public void setStatus(InvitationStatus status) {
		this.status = status;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public LocalDateTime getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(LocalDateTime checkInTime) {
		this.checkInTime = checkInTime;
	}

	public LocalDateTime getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(LocalDateTime checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getTotalQuestionsInExam() {
		return totalQuestionsInExam;
	}

	public void setTotalQuestionsInExam(Integer totalQuestionsInExam) {
		this.totalQuestionsInExam = totalQuestionsInExam;
	}

    
}