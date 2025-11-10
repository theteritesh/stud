package com.exam.stud.dto;

import java.time.LocalDateTime;
import java.util.List;

public class StudentExamResultDTO {

    // --- Report Header ---
    private String invitationId;
    private String studentName;
    private String examTitle;
    private String status;
    private LocalDateTime completedOn;

    // --- Summary ---
    private int score;
    private int totalQuestions;
    private int totalAttempted;
    private int totalCorrect;
    private int totalIncorrect;
    private int totalSkipped;

    // --- Detailed List ---
    private List<QuestionResultDTO> questionResults;

	public String getInvitationId() {
		return invitationId;
	}
	
	// --- Getters and Setters for all fields ---

	public void setInvitationId(String invitationId) {
		this.invitationId = invitationId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getExamTitle() {
		return examTitle;
	}

	public void setExamTitle(String examTitle) {
		this.examTitle = examTitle;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCompletedOn() {
		return completedOn;
	}

	public void setCompletedOn(LocalDateTime completedOn) {
		this.completedOn = completedOn;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getTotalQuestions() {
		return totalQuestions;
	}

	public void setTotalQuestions(int totalQuestions) {
		this.totalQuestions = totalQuestions;
	}

	public int getTotalAttempted() {
		return totalAttempted;
	}

	public void setTotalAttempted(int totalAttempted) {
		this.totalAttempted = totalAttempted;
	}

	public int getTotalCorrect() {
		return totalCorrect;
	}

	public void setTotalCorrect(int totalCorrect) {
		this.totalCorrect = totalCorrect;
	}

	public int getTotalIncorrect() {
		return totalIncorrect;
	}

	public void setTotalIncorrect(int totalIncorrect) {
		this.totalIncorrect = totalIncorrect;
	}

	public int getTotalSkipped() {
		return totalSkipped;
	}

	public void setTotalSkipped(int totalSkipped) {
		this.totalSkipped = totalSkipped;
	}

	public List<QuestionResultDTO> getQuestionResults() {
		return questionResults;
	}

	public void setQuestionResults(List<QuestionResultDTO> questionResults) {
		this.questionResults = questionResults;
	}

}