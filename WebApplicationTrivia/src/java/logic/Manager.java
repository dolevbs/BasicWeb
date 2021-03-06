package logic;

import java.util.ArrayList;
import models.Question;
import enums.Category;
import enums.Difficulty;
import helpers.ParseHelper;
import java.util.*;


public class Manager {

    public ArrayList<Question> ListOfQuestions;

    private boolean[] categoriesInPlay;
    private int[] indexOfRandomQuestions;
    private int currentIndex;
    private Difficulty[] SelectedDifficulty;

    public Manager() {

        categoriesInPlay = null;
        ListOfQuestions = DB.UpdateList();
    }

    public Question[] getQuestions() {
        return ListOfQuestions.toArray(new Question[ListOfQuestions.size()]);
    }

    public void deleteQuestion(Question question) {
        ListOfQuestions.remove(question);
        DB.delete(question);
    }

    public void startPlayMode(Category[] categories, String[] difficulty) {
        categoriesInPlay = new boolean[Category.values().length];
        SelectedDifficulty = new Difficulty[4];
        for (int i = 0; i < categories.length; i++) {
            categoriesInPlay[categories[i].ordinal()] = true;
            SelectedDifficulty[categories[i].ordinal() - 1] = ParseHelper.parseDifficulty(difficulty[categories[i].ordinal() - 1]);
        }
        currentIndex = 0;
        indexOfRandomQuestions = new int[ListOfQuestions.size()];

        for (int i = 0; i < ListOfQuestions.size(); i++) // Reset Random Question Index
        {
            indexOfRandomQuestions[i] = -1;
        }

        for (int i = 0; i < ListOfQuestions.size(); i++) {   // for Random Questions
            Random rand = new Random();
            int RandomNumber = rand.nextInt(ListOfQuestions.size());
            while (indexOfRandomQuestions[RandomNumber] != -1) {
                RandomNumber = rand.nextInt(ListOfQuestions.size());
            }
            indexOfRandomQuestions[RandomNumber] = i;
        }

    }

    public Question getNextQuestionForPlay() {
        if (categoriesInPlay == null) {
            System.err.println("Tring to play without initiate");
            return null;
        }
        Question toReturn = null;
        for (int i = currentIndex; i < ListOfQuestions.size(); i++, currentIndex++) {
            if (categoriesInPlay[ListOfQuestions.get(indexOfRandomQuestions[i]).getCategory().ordinal()] == true
                    && ListOfQuestions.get(indexOfRandomQuestions[i]).getDifficulty() == SelectedDifficulty[ListOfQuestions.get(indexOfRandomQuestions[i]).getCategory().ordinal() - 1]) {
                toReturn = ListOfQuestions.get(indexOfRandomQuestions[i]);
                currentIndex++;
                break;
            }
        }
        return toReturn;
    }

    public boolean isGameEnded() {
        return (currentIndex) >= ListOfQuestions.size();
    }

    public void addQuestion(Question question) {
        ListOfQuestions.add(question);
        DB.addQuestion(question);

    }

}
