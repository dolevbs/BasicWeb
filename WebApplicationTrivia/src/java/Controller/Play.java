/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import enums.Category;
import helpers.ParseHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logic.Manager;
import models.MultipleChoiceQuestion;
import models.Question;
import models.YesNoQuestion;

/**
 *
 * @author Aviran
 */
public class Play extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        Manager manager = (Manager) session.getAttribute("manager");
        
        String address = "";

        if (request.getParameter("NewGame") != null) {
            address = "/WEB-INF/Srevices_Play/ChooseCatagory.jsp";
            RequestDispatcher dispatcher = request.getRequestDispatcher(address);
            dispatcher.forward(request, response);
            return;

        } else if (request.getParameter("firsttime") != null) {

            String question = "";
            List<Category> categoriesToPlay = new ArrayList<>();
            String[] difficulty = new String[4];

            for (int i = 1; i <= 4; i++) {
                if (request.getParameter("Catagory" + i) != null) {
                    String selection = Integer.toString(i);
                    categoriesToPlay.add(ParseHelper.parseCategory(selection));
                    String cat = ParseHelper.parseCategory(selection).name();
                    session.setAttribute(cat, new int[2]);
                    difficulty[i - 1] = request.getParameter("difficulty" + i);
                }
            }
            if (categoriesToPlay.size() > 0) {
                manager.startPlayMode(categoriesToPlay.toArray(new Category[1]), difficulty);
                session.setAttribute("Categories", categoriesToPlay.toArray(new Category[1]));
            } else {
                address = "/WEB-INF/Srevices_Play/ErrorPagePlay.jsp";
                RequestDispatcher dispatcher = request.getRequestDispatcher(address);
                dispatcher.forward(request, response);
                return;
            }

            Integer CorrectAnswers = new Integer(0);
            String stringCorrectAnswers = new String("CorrectAnswers");
            session.setAttribute(stringCorrectAnswers, CorrectAnswers);

            Integer NumofQuestions = new Integer(0);
            String stringNumofQuestions = new String("NumofQuestions");
            session.setAttribute(stringNumofQuestions, NumofQuestions);

        } else {
            Question que = (Question) session.getAttribute("Question");
            String input = request.getParameter("answer");
            int[] Catanswers = (int[]) session.getAttribute(que.getCategory().name());
            if (true == que.verifyAnswer(input)) {
                int CorrectAnswers = (int) session.getAttribute("CorrectAnswers");
                session.setAttribute("CorrectAnswers", CorrectAnswers + 1);
                Catanswers[1]++;
            }
            Catanswers[0]++;
            int NumofQuestions = (int) session.getAttribute("NumofQuestions");
            session.setAttribute("NumofQuestions", NumofQuestions + 1);
            session.setAttribute(que.getCategory().name(), Catanswers);
        }

        Question curQuestion;
        Question LastQuestion = (Question) session.getAttribute("Question");
        String input = request.getParameter("answer");
        String result="";
        if (LastQuestion!=null){
            if (LastQuestion.verifyAnswer(input))
                result="true";
            else 
                result="false";
        }
                
        
        
        if ((curQuestion = manager.getNextQuestionForPlay()) != null && request.getParameter("end") == null) {
            session.setAttribute("Question", curQuestion);
            session.setAttribute("result", result);
            
            if (curQuestion instanceof MultipleChoiceQuestion) {
                address = "/WEB-INF/Srevices_Play/_PlayQuestionMulti.jsp";
            } else if (curQuestion instanceof YesNoQuestion) {
                address = "/WEB-INF/Srevices_Play/_PlayQuestionYesNo.jsp";
            } else if (curQuestion instanceof Question) {
                address = "/WEB-INF/Srevices_Play/_PlayQuestionOpen.jsp";
            }
        } else {
            address = "/WEB-INF/Srevices_Play/_GameEnded.jsp";
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
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
