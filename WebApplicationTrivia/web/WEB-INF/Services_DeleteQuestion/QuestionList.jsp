
<%@page import="models.Question"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1 align="center" id="content" style="font-weight:bold; color:#2a5980;">
            Choose a question to delete:</h1>
        <h2 align="center" id="content" style="font-weight:bold;">

            <jsp:useBean id="question" type="Question[]" scope="request" />


            <% int i = 1;
                for (Question q : question) {%>
            <%= i + ". " + q.getQuestionText() + "<br>"%>

            <% i++;} %>
        </h2><br><br>

        <form action="DeleteQuestion" method="GET">
            <h2 align="center" style="color:#2a5980;"> Please enter the number of question you want to delete: <select name="number">
                    <% for (i = 1; i <= question.length; i++) {%>
                    <%= "<option value=\"" + i + "\">" + i + "</option>"%>
                    <% }%>
                </select>
                <br>
                <INPUT TYPE="SUBMIT" VALUE="Delete">
            </h2>
        </form>
        <br><br>       
    </body>
</html>
