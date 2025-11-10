package com.exam.stud.dto;

public class AnswerDTO {
    
    private String questionId;
    private String selectedAnswer;

    public AnswerDTO() {
    }

    // --- Getters and Setters ---

    public String getQuestionId() { return questionId; }
    public void setQuestionId(String questionId) { this.questionId = questionId; }
    public String getSelectedAnswer() { return selectedAnswer; }
    public void setSelectedAnswer(String selectedAnswer) { this.selectedAnswer = selectedAnswer; }
}