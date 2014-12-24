package logic;

import java.util.ArrayList;
import models.Question;
import enums.Category;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Random;


public class Manager {

    public final FileManager<ArrayList<Question>> fileManager;

    private ArrayList<Question> ListOfQuestions;    
    private boolean[] categoriesInPlay;
    private int[] indexOfRandomQuestions;
    private int currentIndex;    
    private static Manager manager;
    
        public static Manager getInsance() {
        if (manager == null) {
            manager = new Manager();
        }

        return manager;
    }

    public Manager() {
        fileManager = new FileManager<>("C:\\Users\\Aviran\\Documents\\GitHub\\BasicWeb\\WebApplicationTrivia\\SavedGame.txt");
        categoriesInPlay = null;
        try {
            ListOfQuestions = fileManager.Load();
        } catch (FileNotFoundException ex) {
            // Doesn't suppose to happen but ok...
        } catch(IOException ex) {
            System.err.println("Failed to read save file");
            ListOfQuestions = null;
        } catch ( ClassNotFoundException ex) {
            System.err.println("Failed to read data, ClassNotFoundException");
            ListOfQuestions = null;
        }
        // Some error occurd (maybe file not found and stuff) initiate a new save
        if ( ListOfQuestions == null ) {
           ListOfQuestions = new ArrayList<>();
        }

    }

    public void Save() {
        try{
            fileManager.Save(ListOfQuestions);
        } catch (FileNotFoundException ex) {
            System.err.println("Failed to find save file");
        } catch(IOException ex) {
            System.err.println("Failed to write to save file");            
        } 
    }

    public Question[] getQuestions() {
        return ListOfQuestions.toArray(new Question[ListOfQuestions.size()]);
    }

    public void deleteQuestion(Question question) {
        ListOfQuestions.remove(question);
    }

    public void addQuestion(Question question) {
        ListOfQuestions.add(question);
    }

    public void startPlayMode(Category[] categories) {         
        categoriesInPlay = new boolean[Category.values().length];
        for ( int i = 0; i < categories.length; i++ ){
            categoriesInPlay[categories[i].ordinal()] = true;
        }
        currentIndex = 0;
        indexOfRandomQuestions=new int[ListOfQuestions.size()];
        
        for ( int i = 0; i < ListOfQuestions.size(); i++)  // Reset Random Question Index
                indexOfRandomQuestions[i]=-1;
                
        for ( int i = 0; i < ListOfQuestions.size(); i++) {   // for Random Questions
                Random rand=new Random();
                int RandomNumber=rand.nextInt(ListOfQuestions.size());
                while (indexOfRandomQuestions[RandomNumber]!=-1)
                    RandomNumber=rand.nextInt(ListOfQuestions.size());
                indexOfRandomQuestions[RandomNumber]=i;
        }
        

    }
    public Question getNextQuestionForPlay() {
        if ( categoriesInPlay == null ) {
            System.err.println("Tring to play without initiate");
            return null;
        }
        Question toReturn = null;
        for ( int i = currentIndex; i < ListOfQuestions.size(); i++, currentIndex++ ) {
            if ( categoriesInPlay[ListOfQuestions.get(indexOfRandomQuestions[i]).getCategory().ordinal()] == true ) {
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
}
