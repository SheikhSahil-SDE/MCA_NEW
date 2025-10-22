<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*, java.util.*, java.text.*" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Auto Refresh JSP Example</title>
</head>
<body>
<center>
    <fieldset style="width:40%; background-color:#f0fff0; border:2px solid #008000;">
        <legend><h2>Auto Refresh JSP Page</h2></legend>
        
        <%
            // Set the auto-refresh interval (in seconds)
            response.setIntHeader("Refresh", 3);

            // Get current date, time, and timestamp
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = dateFormat.format(now);

            long timestamp = now.getTime();

            out.println("<h3>Current Date and Time:</h3>");
            out.println("<p>" + formattedDate + "</p>");
            
            out.println("<h3>Current Timestamp:</h3>");
            out.println("<p>" + timestamp + "</p>");
        %>
        
        <hr>
        <p>Page automatically refreshes every 3 seconds.</p>
    </fieldset>
</center>
</body>
</html>