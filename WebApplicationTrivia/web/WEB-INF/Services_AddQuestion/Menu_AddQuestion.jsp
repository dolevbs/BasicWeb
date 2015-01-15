<%-- 
    Document   : Menu_AddQuestion
    Created on : Jan 15, 2015, 9:45:23 PM
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
        <form action="AddQuestion" method="GET">
            <h1 align="center" id="content" style="font-weight:bold; color:#2a5980;">
                Please insert the question:</h1><br>
            <h2 align="center" id="content" style="font-weight:bold; color:#2a5980;">
                Question: <input  type="text"  name="question" size="35" placeholder="Write Here the Question"></h2><br>
            <h2 align="center" id="content" style="font-weight:bold; color:#2a5980;">
                Please select the difficulty:</h2>
            <h3 align="center" id="content">
                <input type="radio" name="difficulty" value="1">Easy  
                <input type="radio" name="difficulty" value="2">Medium  
                <input type="radio" name="difficulty" value="3">Hard<br></h3>
            <h2 align="center" id="content" style="font-weight:bold; color:#2a5980;">
                Please select the category:</h2>
            <h3 align="center" id="content">
                <input type="radio" name="category" value="1">General 
                <input type="radio" name="category" value="2">Geography 
                <input type="radio" name="category" value="3">History 
                <input type="radio" name="category" value="4">Sports<br></h3>
            <h2 align="center" id="content" style="font-weight:bold; color:#2a5980;">
                Select question type:</h2>
            <h3 align="center" id="content">
                <input type="radio" name="type" value="Open">1. Open question<br>
                <input type="radio" name="type" value="YesNo">2. Yes or No question<br>
                <input type="radio" name="type" value="ML">3. Multiple choice question
                -Number of Answers: <input  type="text"  name="numofanswers" size="1"><br><br>
                <INPUT TYPE="SUBMIT" VALUE="Add">
            </h3>
        </form>
        <br><h4 align="center"> * Question will be added permanently only after saving changes in StartGame menu </h4>
    </body>
</html>
