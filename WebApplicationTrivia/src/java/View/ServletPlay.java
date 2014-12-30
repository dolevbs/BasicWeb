/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import enums.Category;
import enums.Difficulty;
import helpers.ParseHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logic.FileManager;
import logic.Manager;
import models.Question;

public class ServletPlay extends HttpServlet {
    
    public final FileManager<ArrayList<Question>> fileManager = new FileManager<>(getClass().getResource("/File/SavedGame.txt").getFile());
    private ArrayList<Question> ListOfQuestions;
    private boolean[] categoriesInPlay;
    private int[] indexOfRandomQuestions;
    private int currentIndex;    
    private Difficulty[] SelectedDifficulty;   
    
    protected Question getNextQuestionForPlay (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ( categoriesInPlay == null ) {
            System.err.println("Tring to play without initiate");
            return null;
        }
        Question toReturn = null;
        for ( int i = currentIndex; i < ListOfQuestions.size(); i++, currentIndex++ ) {
            if ( categoriesInPlay[ListOfQuestions.get(indexOfRandomQuestions[i]).getCategory().ordinal()] == true &&
                    ListOfQuestions.get(indexOfRandomQuestions[i]).getDifficulty()==SelectedDifficulty[ListOfQuestions.get(indexOfRandomQuestions[i]).getCategory().ordinal()-1]) {
                toReturn = ListOfQuestions.get(indexOfRandomQuestions[i]);
                currentIndex++;
                break;
            }
        }
        return toReturn;
    }
    
    public void startPlayMode(HttpServletRequest request, HttpServletResponse response,Category[] categories, String[] difficulty) throws ServletException, IOException {   
        
        HttpSession session = request.getSession(true);
        
        categoriesInPlay = new boolean[Category.values().length];
        SelectedDifficulty=new Difficulty[4];
        for ( int i = 0; i < categories.length; i++ ){
            categoriesInPlay[categories[i].ordinal()] = true;
            SelectedDifficulty[categories[i].ordinal()-1]=ParseHelper.parseDifficulty(difficulty[categories[i].ordinal()-1]);
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
        session.setAttribute ("categoriesInPlay", categoriesInPlay); 
        session.setAttribute ("SelectedDifficulty", SelectedDifficulty); 
        session.setAttribute ("currentIndex", currentIndex); 
        session.setAttribute ("indexOfRandomQuestions", indexOfRandomQuestions); 
    }
    
    
        
        
    
    protected void startgame(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String question="";
        HttpSession session = request.getSession(true);
        List<Category> categoriesToPlay = new ArrayList<>();
        String[] difficulty=new String[4];
            
        for (int i = 1; i <= 4; i++)
                if(request.getParameter("Catagory"+i)!=null){
                    String selection=Integer.toString(i);
                    categoriesToPlay.add(ParseHelper.parseCategory(selection));
                    String cat=ParseHelper.parseCategory(selection).name();
                    session.setAttribute (cat, new int[2]);
                    difficulty[i-1]=request.getParameter("difficulty"+i);
                }
        if (categoriesToPlay.size()>0){
             startPlayMode(request,response,categoriesToPlay.toArray(new Category[1]),difficulty);
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
        
        String sname="";
        
        if (cookies==null)
            return "";
        
         
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
        
        ListOfQuestions=Manager.getInsance().ListOfQuestions;
        HttpSession session = request.getSession(true);
        String question="";
        
        
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
        
        categoriesInPlay=(boolean[]) session.getAttribute ("categoriesInPlay");
        indexOfRandomQuestions=(int[]) session.getAttribute ("indexOfRandomQuestions");
        currentIndex=(int) session.getAttribute ("currentIndex");
        SelectedDifficulty=(Difficulty[]) session.getAttribute ("SelectedDifficulty");
            
            
        
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
                if ( true == que.verifyAnswer(input)){
                    out.println("<h1 align=\"center\" style=\"font-weight:bold;\"> Correct answer!<br>"); 
                    out.println("<img align=\"center\" src=\"\\Images\\correct.png\" width=\"62\" height=\"62\">"); 
                    out.println("</h1>");
                }
                else {
                   out.println("<h1 align=\"center\" style=\"font-weight:bold;\"> Wrong answer!<br>");  
                   out.println("<img align=\"center\" src=\"\\Images\\incorrect.jpg\" width=\"80\" height=\"62\">"); 
                   out.println("</h1>"); 
                }
                
             }
            
            // Print question from ShowQuestion class while game not ended
             Question curQuestion;
            if ((curQuestion = getNextQuestionForPlay(request,response))!=null && request.getParameter("end")==null){
               session.setAttribute ("Question", curQuestion); 
               
               session.setAttribute ("currentIndex", currentIndex+1); 
               question = ShowQuestion.getInsance().playQuestion(curQuestion);
               if (request.getParameter("Play")==null) { // for prevent messege in first enter
                     out.println("<h1 align=\"center\">");
                     out.println("<br><img align=\"center\" src=\"\\Images\\nextQuestionRed.png\" width=\"304\" height=\"57\"><br>");
                     out.println("</h1>");
                     
               }
               out.println(question);
               out.println("<br><br><h1 align=\"center\" style=\"font-weight:bold; color:#2a5980;\">");
               out.println("If you want to end the game click <a href=\"ServletPlay?end=true\" >here</a></h1>");
               
            }
                
            
            // Print Game Ended + number of correct answers
            else{
                int count=(int) session.getAttribute("CorrectAnswers");
                int numberquestions=(int) session.getAttribute("NumofQuestions");
                
                 out.println("<br><h2 align=\"center\" style=\"font-weight:bold;font-family: 'HelveticaRegular',Helvetica,Arial;background-color:lightgreen;border: 2px solid #286e87;margin: 7px auto 7px auto;width: 200px;border: 2px solid #286e87;border-radius: 10px;\">");
                 if (numberquestions>0)
                     out.println("Score: "+(count*100/numberquestions)+"%</h2><br>");
                 
                 out.println("<h1 align=\"center\" style=\"font-weight:bold; color:orange;font-family: 'HelveticaRegular',Helvetica,Arial;\"> Game Ended<br></h1>");
                 
                 out.println("<h1 align=\"center\" style=\"font-weight:bold; color:#2a5980;font-family: 'HelveticaRegular',Helvetica,Arial;\">");
                 out.println("You got "+ count +" of "+numberquestions+" questions correct<br><br></h1>");
                 
                 out.println("<h1 align=\"center\" style=\"font-weight:bold; color:#bbd9ee;font-family: 'HelveticaRegular',Helvetica,Arial;\">");
                 out.println("Selected Categories:<br></h1>");
                 
                 out.println("<h1 align=\"center\" style=\"font-weight:bold; color:#2a5980;font-family: 'HelveticaRegular',Helvetica,Arial;\">");
                 Category[] cat=(Category[])session.getAttribute ("Categories"); 
                 for (int i=0;i<cat.length;i++){
                     int[] correctanswers=(int[])session.getAttribute (cat[i].name()); 
                     out.println(cat[i].name()+": You got "+correctanswers[1]+" of "+correctanswers[0]+" correct<br>");
                     
                 }
                 String name=getname(request,response);
                 if (name!=""){
                      out.println("<h1 align=\"center\" style=\"font-weight:bold; color:blue;font-family: 'HelveticaRegular',Helvetica,Arial;\">");
                      out.println(name+", Thank you for playing<br>");
                 }
                     
                 out.println("<h1 align=\"center\" style=\"font-weight:bold; color:black;font-family: 'HelveticaRegular',Helvetica,Arial;\">");
                 out.println("<br>If you want to play again click <a href=\"ServletCatagory\" >here</a></h1>");
                 session.invalidate(); 
            }
            
            out.println("<br><br><br></body>");
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
