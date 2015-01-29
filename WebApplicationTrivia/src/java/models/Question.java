package models;

import enums.*;
import java.io.Serializable;
import java.util.Random;

public class Question implements Serializable {

    private Difficulty difficulty;
    private Category category;
    private String questionText;
    private String correctAnswer;
    private final int ID;
    

    public Question(Difficulty difficulty, Category category, String questionText, String answer) {
        this.difficulty = difficulty;
        this.category = category;
        this.questionText = questionText;
        this.correctAnswer = answer;

        Random t = new Random();
        this.ID = t.nextInt(1000000000);

    }

    public Question(Difficulty difficulty, Category category, String questionText, String answer,int ID) {
        this.difficulty = difficulty;
        this.category = category;
        this.questionText = questionText;
        this.correctAnswer = answer;
        this.ID = ID;

    }

    public int getID() {
        return ID;
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

    public String getAnswer() {
        return correctAnswer;
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
