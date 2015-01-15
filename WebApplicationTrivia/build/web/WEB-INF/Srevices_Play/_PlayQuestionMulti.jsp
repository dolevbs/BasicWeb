<%-- 
    Document   : _PlayQuestionMulti
    Created on : Jan 15, 2015, 11:44:30 PM
    Author     : Aviran
--%>

<%@page import="models.Question"%>
<%@page import="java.util.List"%>
<%@page import="models.MultipleChoiceQuestion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="questions.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <% Question que = (Question) session.getAttribute("Question");
        Question lastque = (Question) session.getAttribute("LastQuestion");

            if (request.getParameter("firsttime") == null) {
                String input = request.getParameter("answer");
                if (true == lastque.verifyAnswer(input)) {%>
        <%="<h1 align=\"center\" style=\"font-weight:bold;\"> Correct answer!<br>"%>
        <%="<img align=\"center\" src=\"\\Images\\correct.png\" width=\"62\" height=\"62\">"%> 
        <%="</h1>"%> 
        <%} else {%>
        <%="<h1 align=\"center\" style=\"font-weight:bold;\"> Wrong answer!<br>"%> 
        <%="<img align=\"center\" src=\"\\Images\\incorrect.jpg\" width=\"80\" height=\"62\">"%>
        <%="</h1>"%> 
        <%}%>
        <%= "<h1 align=\"center\">"%>
        <%="<br><img align=\"center\" src=\"\\Images\\nextQuestionRed.png\" width=\"304\" height=\"57\"><br>"%>
        <%="</h1>"%>
        <%}%>

        <form action="Play" method="GET">
            <h1 align="center" id="question"> <%=que.getQuestionText()%> </h1>
                <%List<String> options = (( MultipleChoiceQuestion)que).getOptions();
                for (int i = 0; i < options.size(); i++) {%>
                <%="<h2 align=\"center\" id=\"answers\" >"%>
                <%="<input type=\"radio\" name=\"answer\" value="+(i+1)+">"+(i + 1) + ". " + options.get(i)  + "  <br></h2>"%>
            <%}%>
            
            <h2 align="center" ><INPUT TYPE="SUBMIT" VALUE="Continue"></h2>
        </form>

        <h1 align="center" style="font-weight:bold; color:black;font-family: 'HelveticaRegular',Helvetica,Arial;">
            <br>If you want to end the game click <a href="Play?end=true" >here</a></h1>
    </body>
</html>
