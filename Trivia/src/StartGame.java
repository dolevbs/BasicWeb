package View;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import enums.*;
import helpers.ParseHelper;
import models.*;
import TriviaConsole.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import logic.Manager;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class StartGame extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String itemID = request.getParameter("itemID");
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StartGame</title>");            
            out.println("</head>");
            out.println("<body>");       
            out.println("<h1 align=\"center\" id=\"content\" style=\"font-weight:bold; color:#C8AC60;\">");
            out.println("Please choose from the following options:</h1><br><br><br>");
            out.println("<h2 align=\"center\" id=\"content\">");
            out.println("<a href=\"ServletCatagory?itemID="+MainMenuOptions.Play.ordinal()+"\">" + MainMenuOptions.Play.ordinal() + ". Play Game</a><br><br>");
            out.println("<a href=\"ServletAddQuestion?itemID="+MainMenuOptions.AddQuestion.ordinal()+"\">"+MainMenuOptions.AddQuestion.ordinal() + ". Add Question</a><br><br>");
            out.println("<a href=\"StartGame?itemID="+MainMenuOptions.DeleteQuestion.ordinal()+"\">"+MainMenuOptions.DeleteQuestion.ordinal() + ". Delete Question</a><br><br>");
            out.println("<a href=\"ServletSave?itemID="+MainMenuOptions.Save.ordinal()+"\">"+MainMenuOptions.Save.ordinal() + ". Save Changes</a><br><br>");
            out.println("<a href=\"StartGame?itemID="+MainMenuOptions.Quit.ordinal()+"\">"+MainMenuOptions.Quit.ordinal() + ". Quit</a><br><br>");
            out.println("</h2>");       
            out.println("</body>");
            out.println("</html>");
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}

