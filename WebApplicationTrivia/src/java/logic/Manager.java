package logic;

import java.util.ArrayList;
import models.Question;
import enums.Category;
import enums.Difficulty;
import helpers.ParseHelper;
import java.io.IOException;
import java.io.FileNotFoundException;
import static java.lang.ProcessBuilder.Redirect.from;
import java.sql.*;
import java.util.*;
import javax.servlet.RequestDispatcher;
import models.MultipleChoiceQuestion;
import models.YesNoQuestion;

public class Manager {

    public final FileManager<ArrayList<Question>> fileManager;

    public ArrayList<Question> ListOfQuestions;

    private boolean[] categoriesInPlay;
    private int[] indexOfRandomQuestions;
    private int currentIndex;
    private Difficulty[] SelectedDifficulty;

    public Manager(String path) {
        fileManager = new FileManager<>(path + "//SavedGame.txt");
        categoriesInPlay = null;
        /*         try {
         ListOfQuestions = fileManager.Load();
            
         } catch (FileNotFoundException ex) {
         // Doesn't suppose to happen but ok...
         } catch (IOException ex) {
         System.err.println("Failed to read save file");
         ListOfQuestions = null;
         } catch (ClassNotFoundException ex) {
         System.err.println("Failed to read data, ClassNotFoundException");
         ListOfQuestions = null;
         }
         // Some error occurd (maybe file not found and stuff) initiate a new save
        
         if (ListOfQuestions == null) {
         ListOfQuestions = new ArrayList<>();
         }
          
         for (Question q : ListOfQuestions) {
         addQuestion(q);
              
         }
         */
        UpdateList();
    }

    public void UpdateList() {
        try {
            ListOfQuestions = new ArrayList<>();
            Question question;
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String urlCn = "jdbc:derby://localhost:1527/Questions";
            Connection cn = DriverManager.getConnection(urlCn, "manager", "1234");

            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("select * from questions");
            while (rs.next()) {
                int ID = rs.getInt(1);
                String questionText = rs.getString(2);
                Category category = getCategorybyName(rs.getString(3));
                String type = rs.getString(4);
                Difficulty difficulty = getDifficultybyName(rs.getString(5));
                String answer = rs.getString(6);
                String answertext = rs.getString(7);

                if (type.equals("Open")) {
                    question = new Question(difficulty, category, questionText, answer, ID);
                } else if (type.equals("YesNo")) {
                    boolean isTrue;
                    if (answer.equals("Yes")) {
                        isTrue = true;
                    } else {
                        isTrue = false;
                    }
                    question = new YesNoQuestion(difficulty, category, questionText, isTrue, ID);
                } else {
                    String[] optionstemp = answertext.split(":");

                    ArrayList<String> options = new ArrayList<>();

                    for (int i = 1; i < optionstemp.length; i++) {
                        options.add(optionstemp[i]);
                    }
                    question = new MultipleChoiceQuestion(difficulty, category, questionText, options, Integer.parseInt(answer), ID);

                }
                ListOfQuestions.add(question);
            }

        } catch (Exception ex) {
        }

    }

    public void Save() {
        try {
            fileManager.Save(ListOfQuestions);
        } catch (FileNotFoundException ex) {
            System.err.println("Failed to find save file");
        } catch (IOException ex) {
            System.err.println("Failed to write to save file");
        }
    }

    public Question[] getQuestions() {
        return ListOfQuestions.toArray(new Question[ListOfQuestions.size()]);
    }

    public void deleteQuestion(Question question) {
        ListOfQuestions.remove(question);
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String urlCn = "jdbc:derby://localhost:1527/Questions";
            Connection cn = DriverManager.getConnection(urlCn, "manager", "1234");

            PreparedStatement pst = cn.prepareStatement("delete from questions where ID=?");
            int num = question.getID(); //429793775
            pst.setInt(1, question.getID());

            pst.executeUpdate();

            cn.close();

        } catch (Exception ex) {
        }

    }

    public void addQuestion(Question question) {
        ListOfQuestions.add(question);
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String urlCn = "jdbc:derby://localhost:1527/Questions";
            Connection cn = DriverManager.getConnection(urlCn, "manager", "1234");

            PreparedStatement pst = cn.prepareStatement("insert into questions values(?,?,?,?,?,?,?)");
            pst.setInt(1, question.getID());
            pst.setString(2, question.getQuestionText());
            pst.setString(3, question.getCategory().toString());
            if (question instanceof YesNoQuestion) {
                pst.setString(4, "YesNo");
                pst.setString(7, "");
            } else if (question instanceof MultipleChoiceQuestion) {
                pst.setString(4, "Multi");
                pst.setString(7, ((MultipleChoiceQuestion) question).getAnsOptions());
            } else if (question instanceof Question) {
                pst.setString(4, "Open");
                pst.setString(7, "");
            }
            pst.setString(5, question.getDifficulty().toString());
            pst.setString(6, question.getAnswer());

            pst.executeUpdate();

            cn.close();

        } catch (Exception ex) {
        }

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

    public Category getCategorybyName(String cat) {
        String category = "";
        switch (cat) {
            case "General":
                category = "1";
                break;
            case "Geography":
                category = "2";
                break;
            case "History":
                category = "3";
                break;
            case "Sports":
                category = "4";
                break;
        }
        return ParseHelper.parseCategory(category);

    }

    public Difficulty getDifficultybyName(String dif) {
        String difficulty = "";
        switch (dif) {
            case "Easy":
                difficulty = "1";
                break;
            case "Medium":
                difficulty = "2";
                break;
            case "Hard":
                difficulty = "3";
                break;
        }
        return ParseHelper.parseDifficulty(difficulty);

    }
}
