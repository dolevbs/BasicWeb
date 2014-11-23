package triviaconsole;

import enums.*;
import helpers.ParseHelper;
import models.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import logic.Manager;

public class TriviaConsole {

    private static BufferedReader reader;
    private static Manager manager;
    private static final int NumOfCatagories=4;
    private static String[] selectedCat=new String[NumOfCatagories];
    

    public static void main(String[] args) throws IOException {

        reader = new BufferedReader(new InputStreamReader(System.in));
        manager = new Manager();

        System.out.println("Welcome to Trivia Game!");

        do {
            MainMenuOptions selectedOption = showMainMenu();

            switch (selectedOption) {
            case None:
                System.out.println("Invalid selection, please pick again");
                break;

            case Play:
                playGame();
                break;

            case AddQuestion:
                addQuestion();
                break;

            case DeleteQuestion:
                deleteQuestion();
                break;

            case Save:
                save();
                break;

            case Quit:
                System.out.println("Thank you for playing, Goodbye");
                return;
            }
        } while (true);
    }

    private static MainMenuOptions showMainMenu() throws IOException {
        System.out.println("Please choose from the following options:");
        System.out.println(MainMenuOptions.Play.ordinal() + ". Play Trivia Game");
        System.out.println(MainMenuOptions.AddQuestion.ordinal() + ". Add Question");
        System.out.println(MainMenuOptions.DeleteQuestion.ordinal() + ". Delete Question");
        System.out.println(MainMenuOptions.Save.ordinal() + ". Save Changes");
        System.out.println(MainMenuOptions.Quit.ordinal() + ". Quit");

        String selection = reader.readLine();

        return ParseHelper.parseMainMenuOptions(selection);
    }

    private static void playGame() throws IOException{ 
        System.out.println("\nIf you want to quit in the middle, please type 'Quit' or '-1'");
        System.out.println("\nPlease choose one of more categories that you want to play, when you finish choose 0");
        List<Category> categoriesToPlay = new ArrayList<>();
        
        for (int i=0;i<NumOfCatagories;i++)
            selectedCat[i]="";
        
        Category cat;
        do { 
            cat = getCategory();
            categoriesToPlay.add(cat);
        } while(cat != Category.None);
        
        System.out.println("\nGame starting\n\n");
        manager.startPlayMode(categoriesToPlay.toArray(new Category[1]));
        while( !manager.isGameEnded() ) {
            Question curQuestion = manager.getNextQuestionForPlay();            
            playQuestion(curQuestion);
        }
        System.out.println("Game Ended");
    }

    private static void addQuestion() throws IOException {

        System.out.println("Please insert the question:");
        String questionText = reader.readLine();

        Difficulty difficulty = getDifficulty();
        Category category = Category.None;

        do {
            category = getCategory();

            if (category == Category.None) {
                System.out.println("Invalid selection");
                if ( wantToStop() ) {
                    return;
                }
            }
        } while (category == Category.None);

        Question question = null;

        while (true) {
            System.out.println("Select question type:");
            System.out.println("1. Open question");
            System.out.println("2. Yes or No question");
            System.out.println("3. Multiple choice question");

            String selection = reader.readLine();

            switch (selection) {
            case "1":
                System.out.println("Please insert the answer:");
                String answer = reader.readLine();
                question = new Question(difficulty, category, questionText, answer);
                break;
            case "2":
                boolean isTrue = GetIsTrue();
                question = new YesNoQuestion(difficulty, category, questionText, isTrue);
                break;
            case "3":

                List<String> options = getOptions();
                int answerIndex = getAnswerIndex(options);
                question = new MultipleChoiceQuestion(difficulty, category, questionText, options, answerIndex);

                break;
            default:
                System.out.println("Invalid selection, please pick again");
                break;
            }

            if (question != null) {
                manager.addQuestion(question);
                return;
            }
        }
    }

    public static boolean GetIsTrue() throws IOException {

        do {
            System.out.println("Please insert the answer:");
            System.out.println("1. Yes");
            System.out.println("2. No");
            String selection = reader.readLine();

            switch (selection) {
            case "1":
                return true;
            case "2":
                return false;
            default:
                System.out.println("Invalid selection, please pick again");
                break;
            }
        } while (true);
    }

    private static Difficulty getDifficulty() throws IOException {
        Difficulty difficulty = Difficulty.None;
        while (difficulty == Difficulty.None) {
            System.out.println("Please select the difficulty:");
            System.out.println(Difficulty.Easy.ordinal() + ". Easy");
            System.out.println(Difficulty.Medium.ordinal() + ". Medium");
            System.out.println(Difficulty.Hard.ordinal() + ". Hard");

            String difficultySelection = reader.readLine();

            difficulty = ParseHelper.parseDifficulty(difficultySelection);

            if (difficulty == Difficulty.None) {
                System.out.println("Invalid selection, please pick again");
            }
        }

        return difficulty;
    }

    private static Category getCategory() throws IOException {
        Category category = Category.None;
        System.out.println("Please select the category:");
        System.out.println(Category.General.ordinal() + ". General " + selectedCat[Category.General.ordinal()-1]);
        System.out.println(Category.Geography.ordinal() + ". Geography " + selectedCat[Category.Geography.ordinal()-1]);
        System.out.println(Category.History.ordinal() + ". History " + selectedCat[Category.History.ordinal()-1]);
        System.out.println(Category.Sports.ordinal() + ". Sports " + selectedCat[Category.Sports.ordinal()-1]);

        String selection = reader.readLine();
        
         if ("Quit".equals(selection) || "-1".equals(selection)){
                System.out.println("Thank you for playing, Goodbye");
                System.exit(0);
         }  
         if (Integer.parseInt(selection)>0 && Integer.parseInt(selection)<=NumOfCatagories)
             selectedCat[Integer.parseInt(selection)-1]="- Choosen";
         
        category = ParseHelper.parseCategory(selection);

        return category;
    }

    private static void deleteQuestion() throws IOException {
        Question[] questions = manager.getQuestions();

        if (questions.length == 0) {
            System.out.println("There are no questions to delete");
            return;
        }

        System.out.println("Choose a question to delete:");

        for (int i = 1; i <= questions.length; i++) {
            System.out.println(i + ". " + questions[i - 1].getQuestionText());
        }

        String selection = reader.readLine();
        int number = ParseHelper.tryParseNumber(selection);

        if (questions.length < number) {
            System.out.println("Invalid question number entered");
            return;
        }

        manager.deleteQuestion(questions[number - 1]);
    }

    private static void save() {
        manager.Save();
    }

    private static List<String> getOptions() throws IOException {
        ArrayList<String> options = new ArrayList<>();

        int answersCount = GetAnswersCount();

        for (int i = 1; i < answersCount + 1; i++) {
            System.out.println("Please enter answer number " + i + ":");
            options.add(reader.readLine());
        }

        return options;
    }

    public static int GetAnswersCount() throws IOException {
        while (true) {

            System.out.println("Plase insret the number of answers:");
            String input = reader.readLine();

            int answerCount = ParseHelper.tryParseNumber(input);

            if (answerCount <= 0 || answerCount == Integer.MAX_VALUE) {
                System.out.println("Invalid number entered, please enter a valid number");
            } else {
                return answerCount;
            }
        }
    }

    private static int getAnswerIndex(List<String> options) throws IOException {
        System.out.println("This are the entered answers:");
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }

        while (true) {
            System.out.println("please insert the currect answer number:");
            String input = reader.readLine();

            int answerNumber = ParseHelper.tryParseNumber(input);

            if (options.size() < answerNumber || answerNumber <= 0) {
                System.out.println("Invalid answer number entered, please enter a valid answer number");
            } else {
                return answerNumber;
            }
        }
    }
    private static boolean wantToStop() throws IOException {
        System.out.println("Do you want to quit? (Y/N)");
        String questionText = reader.readLine();
        return questionText.equalsIgnoreCase("Y");

    }

    private static void playQuestion(Question que) throws IOException {
        if ( que == null ) {
            return;
        }
         System.out.println(que.getQuestionText()); 
         if ( que instanceof MultipleChoiceQuestion ) {
            List<String> options = (( MultipleChoiceQuestion)que).getOptions();
            for (int i = 0; i < options.size(); i++) {
                System.out.println((i + 1) + ". " + options.get(i));
            }
         }
         System.out.print("Your answer:");
         String input = reader.readLine();
         if ("Quit".equals(input) || "-1".equals(input)){
                System.out.println("Thank you for playing, Goodbye");
                System.exit(0);
         }   
         if ( true == que.verifyAnswer(input) ) {
            System.out.println("You Answered correctly");
         } else {
            System.out.println("Wrong answer");
         }
    }
}
