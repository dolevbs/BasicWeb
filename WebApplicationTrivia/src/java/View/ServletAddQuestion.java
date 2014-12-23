
package View;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletAddQuestion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StartGame</title>");            
            out.println("</head>");
            out.println("<body>");    
            out.println("<form action=\"\">");
            //question
            out.println("<h1 align=\"center\" id=\"content\" style=\"font-weight:bold; color:#C8AC60;\">");
            out.println("Please insert the question:</h1><br>");
            out.println("<h2 align=\"center\" id=\"content\" style=\"font-weight:bold; color:#C8AC60;\">");
            out.println("Question: <input  type=\"text\"  name=\"question\" size=\"35\" placeholder=\"Write Here the Question\"></h2><br>");
            //difficulty
            out.println("<h2 align=\"center\" id=\"content\" style=\"font-weight:bold; color:#C8AC60;\">");
            out.println("Please select the difficulty:</h2>");
            out.println("<h3 align=\"center\" id=\"content\">");
            out.println("<input type=\"radio\" name=\"difficulty\" value=\"Easy\">Easy  ");
            out.println("<input type=\"radio\" name=\"difficulty\" value=\"Medium\">Medium  ");
            out.println("<input type=\"radio\" name=\"difficulty\" value=\"Hard\">Hard<br>");
            //catagory
            out.println("<h2 align=\"center\" id=\"content\" style=\"font-weight:bold; color:#C8AC60;\">");
            out.println("Please select the category:</h2>");
            out.println("<h3 align=\"center\" id=\"content\">");
            out.println("<input type=\"radio\" name=\"Catagory\" value=\"General\">General ");
            out.println("<input type=\"radio\" name=\"Catagory\" value=\"Geography\">Geography ");
            out.println("<input type=\"radio\" name=\"Catagory\" value=\"History\">History ");
            out.println("<input type=\"radio\" name=\"Catagory\" value=\"Sports\">Sports<br>");
            //type of question
            out.println("<h2 align=\"center\" id=\"content\" style=\"font-weight:bold; color:#C8AC60;\">");
            out.println("Select question type:</h2>");
            out.println("<h3 align=\"center\" id=\"content\">");
            out.println("<input type=\"radio\" name=\"type\" value=\"Open\">1. Open question<br>");
            out.println("<input type=\"radio\" name=\"type\" value=\"YesNo\">2. Yes or No question<br>");
            out.println("<input type=\"radio\" name=\"type\" value=\"ML\">3. Multiple choice question<br><br>");
            out.println("<INPUT TYPE=\"SUBMIT\" VALUE=\"Add\">\n");
            out.println("</form>");
            out.println("</h3>");       
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
