/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logic.Manager;
import models.Question;


public class DeleteQuestion extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
         HttpSession session = request.getSession(true);
         Manager manager = (Manager) session.getAttribute("manager");
         Question[] questions = manager.getQuestions();
         
         String address;
         
        if (request.getParameter("number") != null) {
            int num=Integer.parseInt (request.getParameter("number"));
            manager.deleteQuestion(questions[num - 1]);
            address = "/WEB-INF/Services_DeleteQuestion/DeleteSuccessful.jsp";
        }   
        else if (questions.length == 0) {
            address = "/WEB-INF/Services_DeleteQuestion/NoQuestion.jsp";
        }
        
        else {
            request.setAttribute("question", questions);
            address = "/WEB-INF/Services_DeleteQuestion/QuestionList.jsp";
        }
        RequestDispatcher dispatcher =request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
      
    
        
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
