package Controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logic.Manager;

/**
 *
 * @author Aviran
 */
@WebServlet(urlPatterns = {"/Menu"})
public class Menu extends HttpServlet {

    Manager manager;

    protected String getname(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();
        HttpSession session = request.getSession(true);
        String sname = "";

        if (cookies == null && session.getAttribute("username") == null) {
            return "";
        }

        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("Name")) {
                    sname = c.getValue();
                }
            }
        }
        if (sname.equals("") && session.getAttribute("username") != null) {
            sname = (String) session.getAttribute("username");
        }
        return sname;

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        manager = (Manager) session.getAttribute("manager");
        if (manager == null) {
            manager = new Manager();
            session.setAttribute("manager", manager);
        }
        
        String sname=getname(request, response);
        
        String address="";
        
        if (sname.equals(""))
            address = "/WEB-INF/Services_Menu/ErrorLogin.jsp";
        
        else if (request.getParameter("play")!=null)
            address = "/WEB-INF/Services_Menu/Menu.jsp";
        else if (request.getParameter("similargames")!=null)
            address = "/WEB-INF/Services_Menu/SimilarGames.jsp";
        else
            address = "/WEB-INF/Services_Menu/about.jsp";
        
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
