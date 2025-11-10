package com.exam.stud.dto;

import java.util.List;

public class ExamPerformanceReportDTO {

    // --- Exam Details ---
    private String examId;
    private String examTitle;

    private long totalInvited;
    private long totalAttended;
    private long totalCompleted;
    private long totalSkipped; 
    private Double averagePercentage;
    private String topPerformerName;
    private Integer topScore;

    // --- Detailed List ---
    private List<StudentPerformanceDTO> studentPerformances;
    
    // --- Getters and Setters for all fields ---
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

	public long getTotalInvited() {
		return totalInvited;
	}

	public void setTotalInvited(long totalInvited) {
		this.totalInvited = totalInvited;
	}

	public long getTotalAttended() {
		return totalAttended;
	}

	public void setTotalAttended(long totalAttended) {
		this.totalAttended = totalAttended;
	}

	public long getTotalCompleted() {
		return totalCompleted;
	}

	public void setTotalCompleted(long totalCompleted) {
		this.totalCompleted = totalCompleted;
	}

	public long getTotalSkipped() {
		return totalSkipped;
	}

	public void setTotalSkipped(long totalSkipped) {
		this.totalSkipped = totalSkipped;
	}

	public Double getAveragePercentage() {
		return averagePercentage;
	}

	public void setAveragePercentage(Double averagePercentage) {
		this.averagePercentage = averagePercentage;
	}

	public String getTopPerformerName() {
		return topPerformerName;
	}

	public void setTopPerformerName(String topPerformerName) {
		this.topPerformerName = topPerformerName;
	}

	public Integer getTopScore() {
		return topScore;
	}

	public void setTopScore(Integer topScore) {
		this.topScore = topScore;
	}

	public List<StudentPerformanceDTO> getStudentPerformances() {
		return studentPerformances;
	}

	public void setStudentPerformances(List<StudentPerformanceDTO> studentPerformances) {
		this.studentPerformances = studentPerformances;
	}

}