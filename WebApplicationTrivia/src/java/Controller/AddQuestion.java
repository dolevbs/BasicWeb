/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import enums.Category;
import enums.Difficulty;
import helpers.ParseHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;
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
public class AddQuestion extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Manager manager = (Manager) session.getAttribute("manager");
        String address = "";

        if (request.getParameter("answer") != null) {
            Question question = null;

            String questionText = request.getParameter("questionText");
            Difficulty difficulty = ParseHelper.parseDifficulty(request.getParameter("difficulty"));
            Category category = ParseHelper.parseCategory(request.getParameter("category"));
            String typeofquestion = request.getParameter("typeofquestion");

            switch (typeofquestion) {
                case "Open":
                    String answer = request.getParameter("answer");
                    question = new Question(difficulty, category, questionText, answer);
                    if (answer.equals("")) {
                        address = "/WEB-INF/Services_AddQuestion/Error Page.jsp";
                        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
                        dispatcher.forward(request, response);
                        return;
                    }
                    break;
                case "YesNo":
                    boolean isTrue;
                    if (request.getParameter("answer").equals("Yes")) {
                        isTrue = true;
                    } else {
                        isTrue = false;
                    }
                    question = new YesNoQuestion(difficulty, category, questionText, isTrue);
                    break;
                case "ML":
                    int NOQ = Integer.parseInt(request.getParameter("NOQ"));
                    int correctanswer = Integer.parseInt(request.getParameter("answer"));

                    ArrayList<String> options = new ArrayList<>();

                    for (int i = 1; i < NOQ + 1; i++) {
                        if (request.getParameter("answer" + i).equals("")) {
                            address = "/WEB-INF/Services_AddQuestion/Error Page.jsp";
                            RequestDispatcher dispatcher = request.getRequestDispatcher(address);
                            dispatcher.forward(request, response);
                            return;
                        }
                        options.add(request.getParameter("answer" + i));
                    }

                    question = new MultipleChoiceQuestion(difficulty, category, questionText, options, correctanswer);
                    break;

            }
            if (question != null) {
                manager.addQuestion(question);
            }

            address = "/WEB-INF/Services_AddQuestion/AddSuccessful.jsp";
            RequestDispatcher dispatcher = request.getRequestDispatcher(address);
            dispatcher.forward(request, response);
            return;
        } else if (request.getParameter("add") != null) {
            address = "/WEB-INF/Services_AddQuestion/Menu_AddQuestion.jsp";
        } else if (request.getParameter("question") == ""
                || request.getParameter("difficulty") == null
                || request.getParameter("category") == null
                || request.getParameter("type") == null) {
            address = "/WEB-INF/Services_AddQuestion/Error Page.jsp";

        } else {
            request.setAttribute("question", request.getParameter("question"));
            request.setAttribute("difficulty", request.getParameter("difficulty"));
            request.setAttribute("category", request.getParameter("category"));
            request.setAttribute("type", request.getParameter("type"));
            switch (request.getParameter("type")) {
                case "Open":
                    address = "/WEB-INF/Services_AddQuestion/AnswersOpen_AddQuestion.jsp";
                    break;
                case "YesNo":
                    address = "/WEB-INF/Services_AddQuestion/AnswersYesNo_AddQuestion.jsp";
                    break;
                case "ML":
                    if (request.getParameter("numofanswers") == "" || !Pattern.matches("[0-9]+", request.getParameter("numofanswers"))) {
                        address = "/WEB-INF/Services_AddQuestion/Error Page.jsp";
                    } else {
                        int NOQ = Integer.parseInt(request.getParameter("numofanswers"));
                        request.setAttribute("numofanswers", NOQ);
                        address = "/WEB-INF/Services_AddQuestion/AnswersMulti_AddQuestion.jsp";
                    }
                    break;
            }

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
