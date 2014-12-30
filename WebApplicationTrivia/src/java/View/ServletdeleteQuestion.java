/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import enums.*;
import logic.*;
import helpers.ParseHelper;
import models.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.Manager;

/**
 *
 * @author Aviran
 */
public class ServletdeleteQuestion extends HttpServlet {  
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Question[] questions = Manager.getInsance().getQuestions();
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StartGame</title>");            
            out.println("</head>");
            out.println("<body>");   
            
        if (request.getParameter("number") != null) {
            int num=Integer.parseInt (request.getParameter("number"));
            Manager.getInsance().deleteQuestion(questions[num - 1]);
            out.println("<h1 align=\"center\">Delete successful");
            out.println("<form action=\"StartGame\" method=\"GET\">");
            out.println("<br>");
            out.println("<INPUT TYPE=\"SUBMIT\" VALUE=\"Return\">");
            out.println("</form></h1>");
  
        }
         
        else if (questions.length == 0) {
            out.println("<h1 align=\"center\">There are no questions to delete</h1>");
        }
        else {
            out.println("<form action=\"\">");
            //question
            out.println("<h1 align=\"center\" id=\"content\" style=\"font-weight:bold; color:#2a5980;\">");
            out.println("Choose a question to delete:</h1>");
            out.println("<h2 align=\"center\" id=\"content\" style=\"font-weight:bold;\">");
            for (int i = 1; i <= questions.length; i++) {
                out.println(i + ". " + questions[i - 1].getQuestionText());
                out.println("<br>");
            }
            out.println("</h2><br><br>");
            
            out.println("<form action=\"ServletdeleteQuestion\" method=\"GET\">");
            out.println("<h2 align=\"center\" style=\"color:#2a5980;\"> Please enter the number of question you want to delete: <select name=\"number\">");
            for (int i = 1; i <= questions.length; i++)
                out.println("<option value=\""+i+"\">"+i+"</option>");
            out.println("</select>");
            out.println("<br><br>");
            out.println("<INPUT TYPE=\"SUBMIT\" VALUE=\"Delete\">");
            out.println("</form>");
            out.println("</h2>");
            
        }
            out.println("<h4 align=\"center\"> * Question will be deleted permanently only after saving changes in StartGame menu </h4>");
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
