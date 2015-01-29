/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;
import models.Question;
import enums.Category;
import enums.Difficulty;
import helpers.ParseHelper;
import java.sql.*;
import static logic.DB.urlCn;
import models.MultipleChoiceQuestion;
import models.YesNoQuestion;

/**
 *
 * @author Aviran
 */
public class DB {

    public static String urlCn = "jdbc:derby://localhost:1527/Questions";

    public static ArrayList<Question> UpdateList() {
        ArrayList<Question> ListOfQuestions = new ArrayList<>();
        try {
            Question question;
            Class.forName("org.apache.derby.jdbc.ClientDriver");

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

                switch (type) {
                    case "Open":
                        question = new Question(difficulty, category, questionText, answer, ID);
                        ListOfQuestions.add(question);
                        break;
                    case "YesNo":
                        boolean isTrue;
                        if (answer.equals("1")) {
                            isTrue = true;
                        } else {
                            isTrue = false;
                        }
                        question = new YesNoQuestion(difficulty, category, questionText, isTrue, ID);
                        ListOfQuestions.add(question);
                        break;
                    case "Multi":
                        String[] optionstemp = answertext.split(":");

                        ArrayList<String> options = new ArrayList<>();

                        for (int i = 1; i < optionstemp.length; i++) {
                            options.add(optionstemp[i]);
                        }
                        question = new MultipleChoiceQuestion(difficulty, category, questionText, options, Integer.parseInt(answer), ID);
                        ListOfQuestions.add(question);
                        break;

                }
                
            }

        } catch (Exception ex) {
        }
        return ListOfQuestions;

    }

    public static Category getCategorybyName(String cat) {
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

    public static Difficulty getDifficultybyName(String dif) {
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

    public static void delete(Question question) {

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            Connection cn = DriverManager.getConnection(urlCn, "manager", "1234");

            PreparedStatement pst = cn.prepareStatement("delete from questions where ID=?");
            int num = question.getID(); //429793775
            pst.setInt(1, question.getID());

            pst.executeUpdate();

            cn.close();

        } catch (Exception ex) {
        }

    }

    public static void addQuestion(Question question) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");

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

}
