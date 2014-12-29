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

public class ServletCatagory extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String CatagoryID = request.getParameter("itemID");
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            HttpSession session = request.getSession(true);
            session.invalidate(); 
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StartGame</title>");            
            out.println("</head>");
            out.println("<body>");       
            out.println("<h1 align=\"center\" id=\"content\" style=\"font-weight:bold; color:#C8AC60;\">");
            out.println("Please select the category:</h1><br>");
            out.println("<h3 align=\"center\" id=\"content\">");
            out.println("<form action=\"ServletPlay\">");
            out.println("<input type=\"checkbox\" name=\"Catagory1\" value=\"General\">General<br>");
            out.println("<input type=\"checkbox\" name=\"Catagory2\" value=\"Geography\">Geography<br>");
            out.println("<input type=\"checkbox\" name=\"Catagory3\" value=\"History\">History<br>");
            out.println("<input type=\"checkbox\" name=\"Catagory4\" value=\"Sports\">Sports<br>");
            out.println("</h3>"); 
            out.println("<h2 align=\"center\" id=\"content\" style=\"font-weight:bold; color:#C8AC60;\">");
            out.println("Please select the difficulty:</h2><br>");
            out.println("<h3 align=\"center\" id=\"content\">");
            out.println("<input type=\"radio\" name=\"difficulty\" value=\"1\" checked>Easy<br>");
            out.println("<input type=\"radio\" name=\"difficulty\" value=\"2\">Medium<br>");
            out.println("<input type=\"radio\" name=\"difficulty\" value=\"3\">Hard<br>");
            out.println("<br><br><INPUT TYPE=\"SUBMIT\" name=\"Play\" VALUE=\"Play\">\n");
            out.println("</form>");
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
