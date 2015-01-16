<%-- 
    Document   : Error Page
    Created on : Jan 16, 2015, 2:55:19 PM
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
        <form action="AddQuestion?add=true" method="POST">
            <h2 align="center" id="content" style="font-weight:bold; color:red;">
                <img align="center" src="\Images\Status-Not-completed.png"><br><br>
                You didn't enter a valid character or the the field is empty<br>
                <INPUT TYPE="SUBMIT" VALUE="Return">
                </h2></form>
    </body>
</html>
