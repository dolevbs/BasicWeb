package View;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import enums.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class StartGame extends HttpServlet {
    
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
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String itemID = request.getParameter("itemID");
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StartGame</title>");            
            out.println("</head>");
            out.println("<body>");       
            
            String sname=getname(request, response);
            if (sname!=""){
                out.println("<h1 align=\"center\" id=\"content\" style=\"font-weight:bold; color:#2a5980;\">");
                out.println("Please choose from the following options:</h1><br><br><br>");
                out.println("<h2 align=\"center\" id=\"content\">");
                out.println("<a href=\"ServletCatagory\">" + "Play Game</a><br><br>");
                out.println("<a href=\"ServletAddQuestion?add=true\">"+"Add Question</a><br><br>");
                out.println("<a href=\"ServletdeleteQuestion\">"+"Delete Question</a><br><br>");
                out.println("<a href=\"ServletSave\">"+"Save Changes</a><br><br>");
                out.println("</h2>");  
            }
            
            else {
                out.println("<h1 align=\"center\" id=\"content\" style=\"font-weight:bold; color:red;\">");
                out.println("Please sign in above before starting the game</h1>");
            }
     
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
