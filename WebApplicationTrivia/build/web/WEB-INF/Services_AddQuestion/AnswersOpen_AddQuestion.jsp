<%-- 
    Document   : Answers_AddQuestion
    Created on : Jan 15, 2015, 10:06:01 PM
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
            <jsp:useBean id="question" type="String" scope="request" />
            <jsp:useBean id="difficulty" type="String" scope="request" />
            <jsp:useBean id="category" type="String" scope="request" />
            <jsp:useBean id="type" type="String" scope="request" />
            
            <input type="hidden" name="questionText" value='<%= question %>'>
            <input type="hidden" name="difficulty" value="<%= difficulty %>">
            <input type="hidden" name="category" value="<%= category %>">
            <input type="hidden" name="typeofquestion" value="<%= type %>">
            
            <h2 align="center" id="content" style="font-weight:bold; color:#2a5980;">
                Answer: <input  type="text"  name="answer" size="35" placeholder="Write Here the Answer"><br>
                <INPUT TYPE="SUBMIT" VALUE="Add">
            </h2></form>
        <form action="AddQuestion?add=true" method="POST">
            <h2 align="center" id="content" style="font-weight:bold; color:#C8AC60;">
                <INPUT TYPE="SUBMIT" VALUE="Return">
            </h2></form>

    </body>
</html>
