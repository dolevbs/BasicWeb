<%-- 
    Document   : AnswersMulti_AddQuestion
    Created on : Jan 15, 2015, 10:09:00 PM
    Author     : Aviran
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="AddQuestion" method="POST">
            <input type="hidden" name="questionText" value='<%= request.getParameter("question")%>'>
            <input type="hidden" name="difficulty" value="<%= request.getParameter("difficulty")%>">
            <input type="hidden" name="category" value="<%= request.getParameter("category")%>">
            <input type="hidden" name="typeofquestion" value="<%= request.getParameter("type")%>">
            <input type="hidden" name="NOQ" value="<%= (Integer) request.getAttribute("numofanswers")%>">
            <h2 align="center" id="content" style="font-weight:bold; color:#2a5980;">
                

                <%  int NOQ = (Integer) request.getAttribute("numofanswers");
                    for (int i = 1; i <= NOQ; i++) {%>
                <%= "Answer " + i + ": <input  type=\"text\"  name=\"answer" + i + "\" size=\"35\" placeholder=\"Write Here the Answer\"><br>"%>
                <%}%>
            </h2>
            <br><h2 align="center"> The Correct answer: <select name="answer">
                    <% for (int i = 1; i <= NOQ; i++) {%>
                    <%= "<option value=\"" + i + "\">" + i + "</option>"%>
                    <%}%>
                </select>
                <br>             
                <INPUT TYPE="SUBMIT" VALUE="Add">
            </h2></form>
        <form action="AddQuestion?add=true" method="POST">
            <h2 align="center" id="content" style="font-weight:bold; color:#C8AC60;">
                <INPUT TYPE="SUBMIT" VALUE="Return">
            </h2></form>
    </body>
</html>
