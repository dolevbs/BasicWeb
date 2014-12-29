/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import enums.Category;
import helpers.ParseHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logic.Manager;
import models.Question;

public class ServletPlay extends HttpServlet {
    
    protected void startgame(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String question="";
        HttpSession session = request.getSession(true);
        List<Category> categoriesToPlay = new ArrayList<>();
            
        for (int i = 1; i <= 4; i++)
                if(request.getParameter("Catagory"+i)!=null){
                    String selection=Integer.toString(i);
                    categoriesToPlay.add(ParseHelper.parseCategory(selection));
                    String cat=ParseHelper.parseCategory(selection).name();
                    session.setAttribute (cat, new int[2]);  
                }
        if (categoriesToPlay.size()>0){
             Manager.getInsance().startPlayMode(categoriesToPlay.toArray(new Category[1]),request.getParameter("difficulty"));
             session.setAttribute ("Categories", categoriesToPlay.toArray(new Category[1]));
        }
        else {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletPlay</title>");    
            out.println("</head>");
            out.println("<body>");
            out.println("<form action=\"ServletCatagory\">");
            out.println("<h1 align=\"center\" style=\"font-weight:bold;color:red\"> No categories choosen!<br>");  
            out.println("<br><br><INPUT TYPE=\"SUBMIT\" VALUE=\"Return\">\n");
            out.println("</h1></form>");
            out.println("</body>");
            out.println("</html>");
            return;
            }
        }
       
        
    }
    
    protected String getname(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();  
        
        if (cookies==null)
            return null;
        
        String sname=""; 
         for (Cookie c : cookies)
             if (c.getName().equals("Name"))
                 sname=c.getValue();
         
         return sname;
        
    }
    
    protected void updateanswers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        HttpSession session = request.getSession(true);
        Question que=(Question) session.getAttribute ("Question");
        String input=request.getParameter("answer");
        int[] Catanswers=(int[]) session.getAttribute(que.getCategory().name());
        if ( true == que.verifyAnswer(input)){
           int CorrectAnswers=(int) session.getAttribute("CorrectAnswers");
           session.setAttribute ("CorrectAnswers", CorrectAnswers+1);
           Catanswers[1]++;
        }
           Catanswers[0]++;
           int NumofQuestions=(int) session.getAttribute("NumofQuestions");
           session.setAttribute ("NumofQuestions", NumofQuestions+1); 
           session.setAttribute (que.getCategory().name(), Catanswers);
        
    }
    
        
        
           
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String question="";
        HttpSession session = request.getSession(true);
        
        // Update number of correct answers
         if (session.isNew()) {
        Integer CorrectAnswers = new Integer(0);
        String stringCorrectAnswers = new String("CorrectAnswers");
        session.setAttribute (stringCorrectAnswers, CorrectAnswers);  
        
        Integer NumofQuestions = new Integer(0);
        String stringNumofQuestions = new String("NumofQuestions");
        session.setAttribute (stringNumofQuestions, NumofQuestions); 
        
        }
         
        else 
             updateanswers(request,response);
        
        // Start the game "Play" means it came from ServletCatagory
        if (request.getParameter("Play")!=null)
            startgame(request,response);
            
            
        
        // HTML
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletPlay</title>");    
            out.println("<link href=\"questions.css\" rel=\"stylesheet\" type=\"text/css\">");    
            out.println("</head>");
            out.println("<body>");
             
            // For messege if correct of wrong
             if (!session.isNew()) {      
                Question que=(Question) session.getAttribute ("Question");
                String input=request.getParameter("answer");
                if ( true == que.verifyAnswer(input))
                    out.println("<h1 align=\"center\" style=\"font-weight:bold;\"> Correct answer!<br></h1>");  
                else
                   out.println("<h1 align=\"center\" style=\"font-weight:bold;\"> Wrong answer!<br></h1>");      
                
             }
            
            // Print question from ShowQuestion class while game not ended
             Question curQuestion;
            if ((curQuestion = Manager.getInsance().getNextQuestionForPlay())!=null && request.getParameter("end")==null){
               session.setAttribute ("Question", curQuestion); 
               question = ShowQuestion.getInsance().playQuestion(curQuestion);
               if (request.getParameter("Play")==null) // for prevent messege in first enter
                     out.println("<h1 align=\"center\" style=\"font-weight:bold;\"> Next question:<br></h1>");
               out.println(question);
               out.println("<br><br><h1 align=\"center\" style=\"font-weight:bold;\">");
               out.println("If you want to end the game click <a href=\"ServletPlay?end=true\" >here</a></h1>");
               
            }
                
            
            // Print Game Ended + number of correct answers
            else{
                int count=(int) session.getAttribute("CorrectAnswers");
                int numberquestions=(int) session.getAttribute("NumofQuestions");
                 out.println("<h2 align=\"center\" style=\"font-weight:bold; color:red;\">");
                 out.println("Score: "+(count*100/numberquestions)+"%</h2><br>");
                 out.println("<h1 align=\"center\" style=\"font-weight:bold; color:orange;\"> Game Ended<br><br>");
                 out.println("You got "+ count +" of "+numberquestions+" questions correct<br><br>");
                 out.println("Selected Categories:<br>");
                 
                 Category[] cat=(Category[])session.getAttribute ("Categories"); 
                 for (int i=0;i<cat.length;i++){
                     int[] correctanswers=(int[])session.getAttribute (cat[i].name()); 
                     out.println(cat[i].name()+": You got "+correctanswers[1]+" of "+correctanswers[0]+" correct<br>");
                     
                 }
                 String name=getname(request,response);
                 if (name!=null){
                      out.println("<h1 align=\"center\" style=\"font-weight:bold; color:blue;\">");
                      out.println(name+", Thank you for playing<br>");
                 }
                     
                 out.println("<h1 align=\"center\" style=\"font-weight:bold; color:black;\">");
                 out.println("<br>If you want to play again click <a href=\"ServletCatagory\" >here</a></h1>");
                 session.invalidate(); 
            }
            
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
