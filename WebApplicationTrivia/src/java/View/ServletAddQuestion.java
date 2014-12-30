
package View;

import enums.Category;
import enums.Difficulty;
import helpers.ParseHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jdk.nashorn.internal.ir.RuntimeNode;
import logic.Manager;
import models.MultipleChoiceQuestion;
import models.Question;
import models.YesNoQuestion;

public class ServletAddQuestion extends HttpServlet {
    
    protected void emptyanswer(HttpServletRequest request, HttpServletResponse response,String type) throws ServletException, IOException {
        
        String questionText=request.getParameter("questionText");
        String difficulty=request.getParameter("difficulty");
        String category=request.getParameter("category");
        String typeofquestion = request.getParameter("typeofquestion");
        String numofanswers = request.getParameter("NOQ");
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StartGame</title>");            
            out.println("</head>");
            out.println("<body>"); 
            out.println("<form action=\"ServletAddQuestion\" method=\"GET\">");
            out.println("<h2 align=\"center\" id=\"content\" style=\"font-weight:bold; color:red;\">");
            out.println("<img align=\"center\" src=\"\\Images\\Status-Not-completed.png\"><br><br>"); 
            if (type=="open")
                out.println("Answer field is empty<br>");
            else
                out.println("One of the answer fields is empty<br>");
            out.println("<input type=\"hidden\" name=\"question\" value='"+questionText+"'>");
            out.println("<input type=\"hidden\" name=\"difficulty\" value="+difficulty+">");
            out.println("<input type=\"hidden\" name=\"category\" value="+category+">");
            out.println("<input type=\"hidden\" name=\"type\" value="+typeofquestion+">");
            out.println("<input type=\"hidden\" name=\"numofanswers\" value="+numofanswers+">");
            out.println("<INPUT TYPE=\"SUBMIT\" VALUE=\"Return\">");
            out.println("</h2></form>");
            out.println("</body>");
            out.println("</html>");
            
            
        }
        
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StartGame</title>");            
            out.println("</head>");
            out.println("<body>");   
            
            
            //Add the question
            
            if (request.getParameter("answer")!=null){
                    Question question = null;
                    
                    
                    String questionText=request.getParameter("questionText");
                    Difficulty difficulty=ParseHelper.parseDifficulty(request.getParameter("difficulty"));
                    Category category=ParseHelper.parseCategory(request.getParameter("category"));
                    String typeofquestion = request.getParameter("typeofquestion");
                    
                    switch (typeofquestion) {
                        case "Open":
                            String answer=request.getParameter("answer");
                            if (answer.equals("")){
                                emptyanswer(request,response,"open");
                                return;
                            }
                            question = new Question(difficulty, category, questionText, answer);
                            break;
                        case "YesNo":   
                            boolean isTrue;
                            if (request.getParameter("answer").equals("Yes"))
                                isTrue=true;
                            else isTrue=false;
                            question = new YesNoQuestion(difficulty, category, questionText, isTrue);
                            break;
                        case "ML":   
                            int NOQ = Integer.parseInt (request.getParameter("NOQ"));
                            int correctanswer=Integer.parseInt (request.getParameter("answer"));
                            
                            ArrayList<String> options = new ArrayList<>();
                            
                            for (int i = 1; i < NOQ + 1; i++){
                               if (request.getParameter("answer"+i).equals("")){
                                emptyanswer(request,response,"ML");
                                return;
                            }
                                options.add(request.getParameter("answer"+i));
                            }
                            
                            question = new MultipleChoiceQuestion(difficulty, category, questionText, options, correctanswer);
                            break;
                                
                    }
                if (question != null)
                    Manager.getInsance().addQuestion(question);
                
                out.println("<h1 align=\"center\">");
                out.println("<img align=\"center\" src=\"\\Images\\Status-Completed.png\"><br><br>");
                out.println("Added successful");
                out.println("<form action=\"StartGame\" method=\"GET\">");
                out.println("<br>");
                out.println("<INPUT TYPE=\"SUBMIT\" VALUE=\"Return\">");
                out.println("</form></h1>");
                out.println("<br><h4 align=\"center\"> * Question will be added permanently only after saving changes in StartGame menu </h4>");
                out.println("</body>");
                out.println("</html>");
                return;
                
            }
            
            // Main screen
            if (request.getParameter("add")!=null) {
                
            out.println("<form action=\"ServletAddQuestion\" method=\"GET\">");
            //question
            out.println("<h1 align=\"center\" id=\"content\" style=\"font-weight:bold; color:#2a5980;\">");
            out.println("Please insert the question:</h1><br>");
            out.println("<h2 align=\"center\" id=\"content\" style=\"font-weight:bold; color:#2a5980;\">");
            out.println("Question: <input  type=\"text\"  name=\"question\" size=\"35\" placeholder=\"Write Here the Question\"></h2><br>");
            //difficulty
            out.println("<h2 align=\"center\" id=\"content\" style=\"font-weight:bold; color:#2a5980;\">");
            out.println("Please select the difficulty:</h2>");
            out.println("<h3 align=\"center\" id=\"content\">");
            out.println("<input type=\"radio\" name=\"difficulty\" value=\"1\">Easy  ");
            out.println("<input type=\"radio\" name=\"difficulty\" value=\"2\">Medium  ");
            out.println("<input type=\"radio\" name=\"difficulty\" value=\"3\">Hard<br>");
            //category
            out.println("<h2 align=\"center\" id=\"content\" style=\"font-weight:bold; color:#2a5980;\">");
            out.println("Please select the category:</h2>");
            out.println("<h3 align=\"center\" id=\"content\">");
            out.println("<input type=\"radio\" name=\"category\" value=\"1\">General ");
            out.println("<input type=\"radio\" name=\"category\" value=\"2\">Geography ");
            out.println("<input type=\"radio\" name=\"category\" value=\"3\">History ");
            out.println("<input type=\"radio\" name=\"category\" value=\"4\">Sports<br>");
            //type of question
            out.println("<h2 align=\"center\" id=\"content\" style=\"font-weight:bold; color:#2a5980;\">");
            out.println("Select question type:</h2>");
            out.println("<h3 align=\"center\" id=\"content\">");
            out.println("<input type=\"radio\" name=\"type\" value=\"Open\">1. Open question<br>");
            out.println("<input type=\"radio\" name=\"type\" value=\"YesNo\">2. Yes or No question<br>");
            out.println("<input type=\"radio\" name=\"type\" value=\"ML\">3. Multiple choice question");
            out.println("-Number of Answers: <input  type=\"text\"  name=\"numofanswers\" size=\"1\"><br><br>");
         
            out.println("<INPUT TYPE=\"SUBMIT\" VALUE=\"Add\">\n");
            out.println("</form>");
            
            out.println("</h3>");  
            
            }
            
            else {
                
                
                // In case one of the fields empty
                if (request.getParameter("question")=="" || 
                        request.getParameter("difficulty")==null || 
                        request.getParameter("category")==null || 
                        request.getParameter("type")==null){
                    out.println("<form action=\"ServletAddQuestion?add=true\" method=\"POST\">");
                    out.println("<h2 align=\"center\" id=\"content\" style=\"font-weight:bold; color:red;\">");
                    out.println("<img align=\"center\" src=\"\\Images\\Status-Not-completed.png\"><br><br>"); 
                    if (request.getParameter("question")=="")
                        out.println("Question Field is empty<br>");
                    if (request.getParameter("difficulty")==null)
                        out.println("Difficulty Field is empty<br>");
                    if (request.getParameter("category")==null)
                        out.println("Category Field is empty<br>");
                    if (request.getParameter("type")==null)
                        out.println("Type of question Field is empty<br>");
                    
                    out.println("<INPUT TYPE=\"SUBMIT\" VALUE=\"Return\">\n");
                    out.println("</h2></form>");
                        
                }
                else {
                    String questionText=request.getParameter("question");
                    String difficulty=request.getParameter("difficulty");
                    String category=request.getParameter("category");
                    String typeofquestion = request.getParameter("type");
                    
                    switch (request.getParameter("type")){
                    case "Open":
                        out.println("<form action=\"ServletAddQuestion\" method=\"POST\">");
                        out.println("<input type=\"hidden\" name=\"questionText\" value='"+questionText+"'>");
                        out.println("<input type=\"hidden\" name=\"difficulty\" value="+difficulty+">");
                        out.println("<input type=\"hidden\" name=\"category\" value="+category+">");
                        out.println("<input type=\"hidden\" name=\"typeofquestion\" value="+typeofquestion+">");;
                        out.println("<h2 align=\"center\" id=\"content\" style=\"font-weight:bold; color:#2a5980;\">");
                        out.println("Answer: <input  type=\"text\"  name=\"answer\" size=\"35\" placeholder=\"Write Here the Answer\"><br>");
                        out.println("<INPUT TYPE=\"SUBMIT\" VALUE=\"Add\">\n");
                        out.println("</h2></form>");
                        out.println("<form action=\"ServletAddQuestion\" method=\"GET\">");
                        out.println("<h2 align=\"center\" id=\"content\" style=\"font-weight:bold; color:#C8AC60;\">");
                        out.println("<INPUT TYPE=\"SUBMIT\" VALUE=\"Return\">\n");
                        out.println("</h2></form>");
                        break;
                        
                    case "YesNo":
                        out.println("<form action=\"ServletAddQuestion\" method=\"POST\">");
                        out.println("<input type=\"hidden\" name=\"questionText\" value='"+questionText+"'>");
                        out.println("<input type=\"hidden\" name=\"difficulty\" value="+difficulty+">");
                        out.println("<input type=\"hidden\" name=\"category\" value="+category+">");
                        out.println("<input type=\"hidden\" name=\"typeofquestion\" value="+typeofquestion+">");
                        out.println("<h2 align=\"center\" id=\"content\" style=\"font-weight:bold; color:#2a5980;\">");
                        out.println("Answer: <input type=\"radio\" name=\"answer\" value=\"Yes\" checked>Yes ");
                        out.println("<input type=\"radio\" name=\"answer\" value=\"No\">No ");
                        out.println("<br><INPUT TYPE=\"SUBMIT\" VALUE=\"Add\">\n");
                        out.println("</h2></form>");
                        out.println("<form action=\"ServletAddQuestion\" method=\"GET\">");
                        out.println("<h2 align=\"center\" id=\"content\" style=\"font-weight:bold; color:#2a5980;\">");
                        out.println("<INPUT TYPE=\"SUBMIT\" VALUE=\"Return\">\n");
                        out.println("</h2></form>");
                        break;
                        
                   case "ML":
                       // In case Number of Answers Field empty
                       if (request.getParameter("numofanswers")=="" || !Pattern.matches("[0-9]+", request.getParameter("numofanswers"))
                               || Integer.parseInt (request.getParameter("numofanswers"))<=0){
                            out.println("<form action=\"ServletAddQuestion?add=true\" method=\"POST\">");
                            out.println("<h2 align=\"center\" id=\"content\" style=\"font-weight:bold; color:red;\">");
                            out.println("<img align=\"center\" src=\"\\Images\\Status-Not-completed.png\"><br><br>");
                            out.println("You didn't enter a valid character to Number of Answers Field or the the field is empty<br>");
                            out.println("<INPUT TYPE=\"SUBMIT\" VALUE=\"Return\">\n");
                            out.println("</h2></form>");
                       }
                       else{
                           int NOQ = Integer.parseInt (request.getParameter("numofanswers"));

                           out.println("<form action=\"ServletAddQuestion\" method=\"POST\">");
                           out.println("<input type=\"hidden\" name=\"questionText\" value='"+questionText+"'>");
                           out.println("<input type=\"hidden\" name=\"difficulty\" value="+difficulty+">");
                           out.println("<input type=\"hidden\" name=\"category\" value="+category+">");
                           out.println("<input type=\"hidden\" name=\"typeofquestion\" value="+typeofquestion+">");
                           out.println("<input type=\"hidden\" name=\"NOQ\" value="+NOQ+">");
                           out.println("<h2 align=\"center\" id=\"content\" style=\"font-weight:bold; color:#2a5980;\">");
                           for (int i = 1; i <= NOQ; i++)
                               out.println("Answer "+i+": <input  type=\"text\"  name=\"answer"+i+"\" size=\"35\" placeholder=\"Write Here the Answer\"><br>");
                           
                           // ComboBox for RightAnswer
                           out.println("<br><h2 align=\"center\"> The Correct answer: <select name=\"answer\">");
                           for (int i = 1; i <= NOQ; i++)
                               out.println("<option value=\""+i+"\">"+i+"</option>");                  
                           out.println("</select>");
                           
                           out.println("<br><INPUT TYPE=\"SUBMIT\" VALUE=\"Add\">\n");
                           out.println("</h2></form>"); 
                           out.println("<form action=\"ServletAddQuestion\" method=\"GET\">");
                           out.println("<h2 align=\"center\" id=\"content\" style=\"font-weight:bold; color:#2a5980;\">");
                           out.println("<INPUT TYPE=\"SUBMIT\" VALUE=\"Return\">\n");
                           out.println("</h2></form>");
                       }
                      break;
                    }              
                }
                
            }
            
            out.println("<br><h4 align=\"center\"> * Question will be added permanently only after saving changes in StartGame menu </h4>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
