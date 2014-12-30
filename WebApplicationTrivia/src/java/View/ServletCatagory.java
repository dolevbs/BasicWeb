package View;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logic.Manager;

public class ServletCatagory extends HttpServlet {
    Manager manager;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
         HttpSession session = request.getSession(true);
         manager= (Manager) session.getAttribute ("manager");
         if (manager==null) {
              manager=new Manager();
              session.setAttribute ("manager", manager);
          }
         
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StartGame</title>");            
            out.println("</head>");
            out.println("<body>");       

            out.println("<form action=\"ServletPlay\">");
            out.println("<input type=\"hidden\" name=\"firsttime\" value=\"true\">");
            out.println("<h1 align=\"center\" id=\"content\">");
            out.println("<button type=\"submit\" name=\"Play\" VALUE=\"Play\"><img src=\"\\Images\\Play.png\" border=\"0\" style=\" width:150px;height:150px;border:2px solid #ccc;\"/></button><h1>");
            
            
            String[] s={"General","Geography","History","Sports"};
            
            for (int i=1;i<=4;i++){
                out.println("<h2 align=\"center\" id=\"content\" style=\"font-weight:bold; color:#2a5980;border-top: 3px solid #ff9933;\">");
                out.println("<br><input type=\"checkbox\" name=\"Catagory"+i+"\" value=\"General\">"+s[i-1]+"<br><br></h2>");
                out.println("<h2 align=\"center\" style=\"color:#2a5912;\">Please select the difficulty:<br></h2>");
                out.println("<h4 align=\"center\" id=\"content\" style=\"font-weight:bold; color:black;\">");
                out.println("<input type=\"radio\" name=\"difficulty"+i+"\" value=\"1\" checked>Easy<br>");
                out.println("<input type=\"radio\" name=\"difficulty"+i+"\" value=\"2\">Medium<br>");
                out.println("<input type=\"radio\" name=\"difficulty"+i+"\" value=\"3\">Hard<br></h4>");
            }

            
            out.println("</form><br><br><br>");
            out.println("</h3>");       
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
