<%-- 
    Document   : ChooseCatagory
    Created on : Jan 15, 2015, 11:05:34 PM
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
        <form action="Play">
            <input type="hidden" name="firsttime" value="true">
            <h1 align="center" id="content">
                <button type="submit" name="Play" VALUE="Play"><img src="\Images\Play.png" border="0" style=" width:150px;height:150px;border:2px solid #ccc;"/></button></h1>
            <h2 align="center" id="content" style="font-weight:bold; color:#2a5980;border-top: 3px solid #ff9933;">
                <br><input type="checkbox" name="Catagory1" value=General>General<br><br></h2>
            <h2 align="center" style="color:#2a5912;">Please select the difficulty:<br></h2>
            <h4 align="center" id="content" style="font-weight:bold; color:black;">
                <input type="radio" name="difficulty1" value="1" checked>Easy<br>
                <input type="radio" name="difficulty1" value="2">Medium<br>
                <input type="radio" name="difficulty1" value="3">Hard<br></h4>
            <h2 align="center" id="content" style="font-weight:bold; color:#2a5980;border-top: 3px solid #ff9933;">
                <br><input type="checkbox" name="Catagory2" value=Geography>Geography<br><br></h2>
            <h2 align="center" style="color:#2a5912;">Please select the difficulty:<br></h2>
            <h4 align="center" id="content" style="font-weight:bold; color:black;">
                <input type="radio" name="difficulty2" value="1" checked>Easy<br>
                <input type="radio" name="difficulty2" value="2">Medium<br>
                <input type="radio" name="difficulty2" value="3">Hard<br></h4>
            <h2 align="center" id="content" style="font-weight:bold; color:#2a5980;border-top: 3px solid #ff9933;">
                <br><input type="checkbox" name="Catagory3" value=History>History<br><br></h2>
            <h2 align="center" style="color:#2a5912;">Please select the difficulty:<br></h2>
            <h4 align="center" id="content" style="font-weight:bold; color:black;">
                <input type="radio" name="difficulty3" value="1" checked>Easy<br>
                <input type="radio" name="difficulty3" value="2">Medium<br>
                <input type="radio" name="difficulty3" value="3">Hard<br></h4>
            <h2 align="center" id="content" style="font-weight:bold; color:#2a5980;border-top: 3px solid #ff9933;">
                <br><input type="checkbox" name="Catagory4" value=Sports>Sports<br><br></h2>
            <h2 align="center" style="color:#2a5912;">Please select the difficulty:<br></h2>
            <h4 align="center" id="content" style="font-weight:bold; color:black;">
                <input type="radio" name="difficulty4" value="1" checked>Easy<br>
                <input type="radio" name="difficulty4" value="2">Medium<br>
                <input type="radio" name="difficulty4" value="3">Hard<br></h4>
        </form><br><br><br>

</body>
</html>
