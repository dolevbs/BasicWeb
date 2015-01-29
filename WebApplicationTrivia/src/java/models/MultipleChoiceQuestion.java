package models;

import enums.Category;
import enums.Difficulty;
import java.util.List;

public class MultipleChoiceQuestion extends Question {

    protected List<String> options;

    public MultipleChoiceQuestion(Difficulty difficulty, Category category, String questionText, List<String> options, int answerIndex) {
        super(difficulty, category, questionText, "" + answerIndex);
        this.options = options;
    }
        public MultipleChoiceQuestion(Difficulty difficulty, Category category, String questionText, List<String> options, int answerIndex,int ID) {
        super(difficulty, category, questionText, "" + answerIndex,ID);
        this.options = options;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getAnsOptions() {
        String ans="";
        for (String list : options )
            ans+=":"+list;
        return ans;
    }
}
