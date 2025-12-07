package com.exam.stud.dto;

import com.exam.stud.enums.InvitationStatus;

public class StudentPerformanceDTO {

    private String studentName;
    private InvitationStatus status;
    private Integer score;
    private Integer totalQuestions;
    private Double percentage;
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public InvitationStatus getStatus() {
		return status;
	}
	public void setStatus(InvitationStatus status) {
		this.status = status;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Integer getTotalQuestions() {
		return totalQuestions;
	}
	public void setTotalQuestions(Integer totalQuestions) {
		this.totalQuestions = totalQuestions;
	}
	public Double getPercentage() {
		return percentage;
	}
	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}
    
    
}
