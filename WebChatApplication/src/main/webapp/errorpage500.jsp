<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 02.05.2015
  Time: 19:19
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isErrorPage="true" %>
<% response.setStatus(500); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>500 error</title>
</head>
<body >

<h1  style=" color:#FF0000">OOps... Something wrong with server. It's our fault :( </h1>
<img  src = "http://images.vector-images.com/clipart/xl/176/robot3.jpg"/>
<h3 style="  color:#FF0000">We are really sorry</h3>
<br>
<h3 style="  color:#FF0000">What about firing our developer for this mistake?</h3>
<button  onclick="garold()" style="position: absolute; width:25%; height:10%; color: #0081c2 "><h2>Yeah, kick out this dumbhead</h2></button>
<img style="visibility: hidden; position: absolute; right:1%; top:1%" id="img" src="http://301-1.ru/gen-mems/img_mems/19bc21c24680f9c289c864d31706618a.jpg">
<h1 id="gar" style="visibility: hidden; position:absolute; bottom: 10%; left:70%;  color:#FF0000 ">You did it!!!</h1>
<SCRIPT LANGUAGE="JavaScript">

    function garold()
    {
        document.getElementById("img").style.visibility = "visible";
        document.getElementById("gar").style.visibility = "visible";
    }


</SCRIPT>
</body>
</html>

