//Create a JSP page and implement a Scripting Tag, Expression tag and Declaration tag.

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>JSP Scripting Elements Example</title>
</head>
<body bgcolor="lightyellow">
<center>
    <fieldset style="width:50%; border:2px solid green;">
        <legend><h2>JSP Scripting Elements Demo</h2></legend>
        
        <!-- Declaration Tag: Used to declare methods or variables -->
        <%! 
            String developer = "IGNOU Student";
            
            int square(int n) {
                return n * n;
            }
        %>
        
        <!-- Scriptlet Tag: Contains Java code executed inside the _jspService() method -->
        <%
            Date now = new Date();
            int number = 5;
            int result = square(number);
        %>

        <h3>Using Expression Tag:</h3>
        <!-- Expression Tag: Outputs the result directly without using out.println() -->
        <p>Welcome, <%= developer %></p>
        <p>Current Date and Time: <%= now %></p>
        <p>Square of <%= number %> is <%= result %></p>

        <hr/>
        <h4>Explanation:</h4>
        <ul style="text-align:left; margin-left:30%;">
            <li><b>Declaration Tag (&lt;%! ... %&gt;)</b>: Defines methods and variables at the class level.</li>
            <li><b>Scriptlet Tag (&lt;% ... %&gt;)</b>: Executes Java code during request processing.</li>
            <li><b>Expression Tag (&lt;%= ... %&gt;)</b>: Prints a value directly to the client.</li>
        </ul>
    </fieldset>
</center>
</body>
</html>