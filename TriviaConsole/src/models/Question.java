package models;

import enums.*;
import java.io.Serializable;

public class Question implements Serializable  {

    private Difficulty difficulty;
    private Category category;
    private String questionText;
    private String correctAnswer;

    public Question(Difficulty difficulty, Category category, String questionText, String answer) {
        this.difficulty = difficulty;
        this.category = category;
        this.questionText = questionText;
        this.correctAnswer = answer;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setCurrectAnswer(String answer) {
        correctAnswer = answer;
    }
    public boolean verifyAnswer(String answer) { 

        return correctAnswer.equalsIgnoreCase(answer);
    }
}
