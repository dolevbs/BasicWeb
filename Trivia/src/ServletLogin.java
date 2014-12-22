
package View;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletLogin extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                    
        String sname="";    
        Cookie[] cookies = request.getCookies();
        if (cookies!=null) {
         for (int i = 0; i < cookies.length; i++){
             if (cookies[i].getName().equals("Name"))
                 sname=cookies[i].getValue();
         }
        }
         if (sname=="" && request.getParameter("name")!=null){
             Cookie firstName = new Cookie("Name", request.getParameter("name"));
             response.addCookie( firstName ); 
             sname=request.getParameter("name");
         }
         
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletLogin</title>");    
            out.println("</head>");
            out.println("<body>");

         if (sname!=""){

            out.println("<h2 align=\"right\"> Welcome "+ sname + " </h2>");
        }
         else {
            out.println("<form action=\"ServletLogin\" method=\"GET\">");
            out.println("<h3 align=\"right\"> Name: <input  type=\"text\"  name=\"name\"><br> <INPUT align=\"right\" TYPE=\"SUBMIT\" VALUE=\"Log in\">");
            out.println("<input type=\"checkbox\" name=\"remember\" value=\"General\">Remember Me<br>");
            out.println("</h3> </form>");

         }
         
            out.println("</body>");
            out.println("</html>");
    }
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
