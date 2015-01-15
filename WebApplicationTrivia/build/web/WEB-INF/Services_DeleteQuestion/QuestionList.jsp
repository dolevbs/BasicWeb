
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
            <% Question[] questions = (Question[]) request.getAttribute("question");

                    for (int i = 1; i <= questions.length; i++) {%>
            <%= i + ". " + questions[i - 1].getQuestionText() + "<br>"%>
            <% } %>
        </h2><br><br>

        <form action="DeleteQuestion" method="GET">
            <h2 align="center" style="color:#2a5980;"> Please enter the number of question you want to delete: <select name="number">
                    <% for (int i = 1; i <= questions.length; i++) {%>
                    <%= "<option value=\"" + i + "\">" + i + "</option>"%>
                    <% }%>
                </select>
                <br>
                <INPUT TYPE="SUBMIT" VALUE="Delete">
            </h2>
        </form>
        <h4 align="center"> * Question will be deleted permanently only after saving changes in StartGame menu </h4>
        <br><br>       
    </body>
</html>
