package com.exam.stud.dto;

public class QuestionResultDTO {

    private String questionText;
    private String yourAnswer;
    private String correctAnswer;
    private ResultStatus status;

    public QuestionResultDTO(String questionText, String yourAnswer, String correctAnswer, ResultStatus status) {
        this.questionText = questionText;
        this.yourAnswer = yourAnswer;
        this.correctAnswer = correctAnswer;
        this.status = status;
    }
    
    // --- Getters and Setters ---

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public String getYourAnswer() {
		return yourAnswer;
	}

	public void setYourAnswer(String yourAnswer) {
		this.yourAnswer = yourAnswer;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public ResultStatus getStatus() {
		return status;
	}

	public void setStatus(ResultStatus status) {
		this.status = status;
	}

}