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

            out.println("<form action=\"ServletPlay\">");

            out.println("<h1 align=\"center\" id=\"content\">");
            out.println("<INPUT TYPE=\"SUBMIT\" name=\"Play\" VALUE=\"Play\" style=\" width:150px;height:80px;border:2px solid #ccc;\">\n<br><h1>");
            
            String[] s={"General","Geography","History","Sports"};
            
            for (int i=1;i<=4;i++){
                out.println("<h2 align=\"center\" id=\"content\" style=\"font-weight:bold; color:orange;border-top: 3px solid #ff9933;\">");
                out.println("<br><input type=\"checkbox\" name=\"Catagory"+i+"\" value=\"General\">"+s[i-1]+"<br><br></h2>");
                out.println("<h2 align=\"center\" style=\"color:orangered;\">Please select the difficulty:<br></h2>");
                out.println("<h4 align=\"center\" id=\"content\" style=\"font-weight:bold; color:black;\">");
                out.println("<input type=\"radio\" name=\"difficulty"+i+"\" value=\"1\" checked>Easy<br>");
                out.println("<input type=\"radio\" name=\"difficulty"+i+"\" value=\"2\">Medium<br>");
                out.println("<input type=\"radio\" name=\"difficulty"+i+"\" value=\"3\">Hard<br></h4>");
            }

            
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
