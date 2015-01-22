<%-- 
    Document   : _PlayQuestionYesNo
    Created on : Jan 15, 2015, 11:44:41 PM
    Author     : Aviran
--%>

<%@page import="models.Question"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="questions.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <jsp:useBean id="Question" type="models.Question" scope="session" />
        <jsp:useBean id="result" type="String" scope="session" />
        <%

            if (request.getParameter("firsttime") == null) {
                String input = request.getParameter("answer");
        if (result.equals("true")) {%>
        <%="<h1 align=\"center\" style=\"font-weight:bold;\"> Correct answer!<br>"%>
        <%="<img align=\"center\" src=\"\\Images\\correct.png\" width=\"62\" height=\"62\">"%> 
        <%="</h1>"%> 
        <%} else if (result.equals("false")){%>
        <%="<h1 align=\"center\" style=\"font-weight:bold;\"> Wrong answer!<br>"%> 
        <%="<img align=\"center\" src=\"\\Images\\incorrect.jpg\" width=\"80\" height=\"62\">"%>
        <%="</h1>"%> 
        <%}%>
        <%= "<h1 align=\"center\">"%>
        <%="<br><img align=\"center\" src=\"\\Images\\nextQuestionRed.png\" width=\"304\" height=\"57\"><br>"%>
        <%="</h1>"%>
        <%}%>

        <form action="Play" method="GET">
            <h1 align="center" id="question"> ${Question.questionText}</h1>
            <h2 align="center" id="answers" >
                Answer: <input type="radio" name="answer" value="Yes" checked>Yes </h2>
            <h2 align="center" id="answers" >
                <input type="radio" name="answer" value="No">No <br>
            </h2>
            <h2 align="center" ><INPUT TYPE="SUBMIT" VALUE="Continue"></h2>
        </form>

        <h1 align="center" style="font-weight:bold; color:black;font-family: 'HelveticaRegular',Helvetica,Arial;">
            <br>If you want to end the game click <a href="Play?end=true" >here</a></h1>


    </body>
</html>
