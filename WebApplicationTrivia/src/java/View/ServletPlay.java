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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logic.Manager;
import models.Question;

public class ServletPlay extends HttpServlet {
    
        
        
           
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String question="";
        HttpSession session = request.getSession(true);
        
        // Update number of correct answers
         if (session.isNew()) {
        Integer CorrectAnswers = new Integer(0);
        String stringCorrectAnswers = new String("CorrectAnswers");
        session.setAttribute (stringCorrectAnswers, CorrectAnswers);  
        
        }
         
        else {
                Question que=(Question) session.getAttribute ("Question");
                String input=request.getParameter("answer");
                if ( true == que.verifyAnswer(input)){
                   int CorrectAnswers=(int) session.getAttribute("CorrectAnswers");
                    session.setAttribute ("CorrectAnswers", CorrectAnswers+1);  
                }
         }
        
        // Start the game "Play" means it came from ServletCatagory
        if (request.getParameter("Play")!=null){
            List<Category> categoriesToPlay = new ArrayList<>();
            
        for (int i = 1; i <= 4; i++)
                if(request.getParameter("Catagory"+i)!=null){
                    String selection=Integer.toString(i);
                    categoriesToPlay.add(ParseHelper.parseCategory(selection));
                }
        Manager.getInsance().startPlayMode(categoriesToPlay.toArray(new Category[1]));
            
        }
        
        // Play question while the not ended
        if (!Manager.getInsance().isGameEnded()){
            Question curQuestion = Manager.getInsance().getNextQuestionForPlay(); 
            session.setAttribute ("Question", curQuestion); 
            question = ShowQuestion.getInsance().playQuestion(curQuestion);
        }
            
            
        
        // HTML
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletPlay</title>");            
            out.println("</head>");
            out.println("<body>");
            
            // Print question from ShowQuestion class
            if (!Manager.getInsance().isGameEnded())
                out.println(question);
            
            // Print Game Ended + number of correct answers
            else{
                int count=(int) session.getAttribute("CorrectAnswers");
                 out.println("<h1 align=\"center\" id=\"content\" style=\"font-weight:bold; color:#C8AC60;\"> Game Ended<br>");
                 out.println("You got "+ count +" questions correct<br>");
                 out.println("If you want to play again click <a href=\"ServletCatagory\" >here</a></h1>");
                 session.invalidate(); 
            }
            
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
