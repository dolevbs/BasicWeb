/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import enums.Category;
import enums.Difficulty;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class YesNoQuestion extends MultipleChoiceQuestion {

    public YesNoQuestion(Difficulty difficulty, Category category, String questionText, boolean isTrue) {
        super(difficulty, category, questionText, Arrays.asList(new String[]{"Yes", "No"}), isTrue ? 1 : 2);
    }
}
