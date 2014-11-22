package logic;

import java.util.ArrayList;
import models.Question;
import enums.Category;
import java.io.IOException;
import java.io.FileNotFoundException;


public class Manager {

    public final FileManager<ArrayList<Question>> fileManager;

    private ArrayList<Question> questions;    
    private boolean[] categoriesInPlay;
    private int curIndex;    

    public Manager() {
        fileManager = new FileManager<>("SavedGame.txt");
        categoriesInPlay = null;
        try {
            questions = fileManager.Load();
        } catch (FileNotFoundException ex) {
            // Doesn't suppose to happen but ok...
        } catch(IOException ex) {
            System.err.println("Failed to read save file");
            questions = null;
        } catch ( ClassNotFoundException ex) {
            System.err.println("Failed to read data, ClassNotFoundException");
            questions = null;
        }
        // Some error occurd (maybe file not found and stuff) initiate a new save
        if ( questions == null ) {
           questions = new ArrayList<>();
        }

    }

    public void Save() {
        try{
            fileManager.Save(questions);
        } catch (FileNotFoundException ex) {
            System.err.println("Failed to find save file");
        } catch(IOException ex) {
            System.err.println("Failed to write to save file");            
        } 
    }

    public Question[] getQuestions() {
        return questions.toArray(new Question[questions.size()]);
    }

    public void deleteQuestion(Question question) {
        questions.remove(question);
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void startPlayMode(Category[] categories) {         
        categoriesInPlay = new boolean[Category.values().length];
        for ( int i = 0; i < categories.length; i++ ){
            categoriesInPlay[categories[i].ordinal()] = true;
        }
        curIndex = 0;

    }
    public Question getNextQuestionForPlay() {
        if ( categoriesInPlay == null ) {
            System.err.println("Tring to play without initiate");
            return null;
        }
        Question toReturn = null;
        for ( int i = curIndex; i < questions.size(); i++, curIndex++ ) {
            if ( categoriesInPlay[questions.get(i).getCategory().ordinal()] == true ) {
                toReturn = questions.get(i);
                curIndex++;
                break;
            }
        }
        return toReturn;
    }

    public boolean isGameEnded() { 
        return (curIndex) >= questions.size();
    }
}
