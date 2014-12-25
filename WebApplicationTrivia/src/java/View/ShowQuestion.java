/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.io.PrintWriter;
import java.util.List;
import logic.Manager;
import models.MultipleChoiceQuestion;
import models.Question;
import models.YesNoQuestion;

/**
 *
 * @author Aviran
 */
public class ShowQuestion {
    
    private static ShowQuestion showquestion;
    
    public static ShowQuestion getInsance() {
        if (showquestion == null)
            showquestion = new ShowQuestion();
        
        return showquestion;

    }
    
    public String printquestion (Question que){
        if ( que == null )
            return "";
        
        String out = "";
        out+="<!DOCTYPE html><html><head><title>Servlet StartGame</title></head><body>";
        out+="<form action=\"##\" method=\"GET\">";
        out+="<h1 align=\"center\" id=\"content\" style=\"font-weight:bold; color:#C8AC60;\">" + que.getQuestionText() + "  </h1>";
        out+="</body></html>";
        out+="<h2 align=\"center\" id=\"content\" style=\"font-weight:bold; color:#C8AC60;\">";
        
        if ( que instanceof MultipleChoiceQuestion ) {
            List<String> options = (( MultipleChoiceQuestion)que).getOptions();
            
            for (int i = 0; i < options.size(); i++) {
                
                out+="<input type=\"radio\" name=\"answer\" value="+(i+1)+">"+(i + 1) + ". " + options.get(i)  + "  </h2><br>";
            }
            
        }
        
        if ( que instanceof YesNoQuestion ){
          out+="Answer: <input type=\"radio\" name=\"answer\" value=\"Yes\" checked>Yes ";
          out+="<input type=\"radio\" name=\"answer\" value=\"No\">No <br>";
        }
        
        if (que instanceof Question) {
           out+="Answer: <input  type=\"text\"  name=\"answer\" size=\"35\" placeholder=\"Write Here the Answer\"><br>";
        }
        out+="<INPUT TYPE=\"SUBMIT\" VALUE=\"Continue\">\n";
        out+="</h2></form>";
        out+="</body></html>";
        
        return out;
    }

    
}
