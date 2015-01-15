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
import javax.servlet.http.HttpSession;
import logic.Manager;


public class StartGame extends HttpServlet {
    Manager manager;
    
    protected String getname(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Cookie[] cookies = request.getCookies();
        HttpSession session = request.getSession(true);
        String sname="";
        
        if (cookies==null && session.getAttribute("username")==null)
            return "";
        
         if ( cookies != null ) {
            for (Cookie c : cookies)
                if (c.getName().equals("Name"))
                    sname=c.getValue();
         } 
         if (sname.equals("") && session.getAttribute("username")!=null) {         
             sname=(String)session.getAttribute("username");
         }
         return sname;
        
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

                
        HttpSession session = request.getSession(true);
        manager= (Manager) session.getAttribute ("manager");
        if (manager==null) {
              manager=new Manager(request.getServletContext().getRealPath("/"));
              session.setAttribute ("manager", manager);
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StartGame</title>");            
            out.println("</head>");
            out.println("<body>");       
            
            String sname=getname(request, response);
            if (!sname.equals("")){
                out.println("<h1 align=\"center\" id=\"content\" style=\"font-weight:bold; color:#2a5980;\">");
                out.println("Please choose from the following options:</h1><br><br><br>");
                out.println("<h2 align=\"center\" id=\"content\">");
                out.println("<a href=\"Play?NewGame=true\">" + "Play Game</a><br><br>");
                out.println("<a href=\"AddQuestion?add=true\">"+"Add Question</a><br><br>");
                out.println("<a href=\"DeleteQuestion\">"+"Delete Question</a><br><br>");
                out.println("<a href=\"Save\">"+"Save Changes</a><br><br>");
                out.println("</h2>");  
            }
            
            else {
                out.println("<h1 align=\"center\" id=\"content\" style=\"font-weight:bold; color:#ff9933;\">");
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
