<%-- 
    Document   : _GameEnded
    Created on : Jan 15, 2015, 11:15:06 PM
    Author     : Aviran
--%>

<%@page import="enums.Category"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:useBean id="CorrectAnswers" type="Integer" scope="session" />
        <jsp:useBean id="NumofQuestions" type="Integer" scope="session" />
        
        <br><h2 align="center" style="font-weight:bold;font-family: 'HelveticaRegular',Helvetica,Arial;background-color:lightgreen;border: 2px solid #286e87;margin: 7px auto 7px auto;width: 200px;border: 2px solid #286e87;border-radius: 10px;">
            <% if (NumofQuestions > 0) {%>
            <%= "Score: " + (CorrectAnswers * 100 / NumofQuestions) + "%"%> <%}%>
        </h2><br>
        <h1 align="center" style="font-weight:bold; color:orange;font-family: 'HelveticaRegular',Helvetica,Arial;"> Game Ended<br></h1>
        <h1 align="center" style="font-weight:bold; color:#2a5980;font-family: 'HelveticaRegular',Helvetica,Arial;">
            You got <%=CorrectAnswers%> of <%=NumofQuestions%> questions correct<br><br></h1>
        <h1 align="center" style="font-weight:bold; color:#bbd9ee;font-family: 'HelveticaRegular',Helvetica,Arial;">
            Selected Categories:<br></h1>
        <h1 align="center" style="font-weight:bold; color:#2a5980;font-family: 'HelveticaRegular',Helvetica,Arial;">
            <jsp:useBean id="Categories" type="Category[]" scope="session" />
            <% 
                            for (int i = 0; i < Categories.length; i++) {
                                int[] correctanswers = (int[]) session.getAttribute(Categories[i].name());%> 
            <%=Categories[i].name() + ": You got " + correctanswers[1] + " of " + correctanswers[0] + " correct<br>"%> 
            <%session.removeAttribute(Categories[i].name());
                }
                session.removeAttribute("Categories");
                session.removeAttribute("count");
                session.removeAttribute("numberquestions");
                session.removeAttribute("Question");
            %>
        </h1>
        <h1 align="center" style="font-weight:bold; color:black;font-family: 'HelveticaRegular',Helvetica,Arial;">
            <br>If you want to play again click <a href="Play?NewGame=true" >here</a></h1>
        <br><br><br>
    </body>
</html>
