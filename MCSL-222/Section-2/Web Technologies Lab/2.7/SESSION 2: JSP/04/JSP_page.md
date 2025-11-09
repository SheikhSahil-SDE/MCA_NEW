# Create a JSP Page for database connectivity using JDBC and show the students details from the database created in the prompt

Here is a JSP page that directly connects to the MySQL database (as set up previously with the IGNOU and Student table) using JDBC and displays all student details in an HTML table. This style follows patterns used in many JSP-JDBC tutorials and sample projects, and demonstrates database connectivity and result display from within a single JSP file. Proper connection/resource closing and exception handling are included for reliability

```TEXT```
```
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Student Details - IGNOU</title>
</head>
<body>
    <h2>Student Details</h2>
    <table border="1" cellpadding="5">
        <tr>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>DOB</th>
            <th>Gender</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Address</th>
            <th>Enrollment No</th>
            <th>Program</th>
            <th>Admission Date</th>
            <th>Semester</th>
        </tr>
        <%
            // Database connection parameters
            String url = "jdbc:mysql://localhost:3306/IGNOU?useSSL=false&serverTimezone=UTC";
            String user = "root";
            String password = "your_password"; // Replace with your real password

            Connection con = null;
            Statement stmt = null;
            ResultSet rs = null;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(url, user, password);
                stmt = con.createStatement();
                String query = "SELECT * FROM Student";
                rs = stmt.executeQuery(query);

                while(rs.next()) {
        %>
                <tr>
                    <td><%= rs.getInt("id") %></td>
                    <td><%= rs.getString("first_name") %></td>
                    <td><%= rs.getString("last_name") %></td>
                    <td><%= rs.getDate("dob") %></td>
                    <td><%= rs.getString("gender") %></td>
                    <td><%= rs.getString("email") %></td>
                    <td><%= rs.getString("phone") %></td>
                    <td><%= rs.getString("address") %></td>
                    <td><%= rs.getString("enrollment_no") %></td>
                    <td><%= rs.getString("program") %></td>
                    <td><%= rs.getDate("admission_date") %></td>
                    <td><%= rs.getInt("semester") %></td>
                </tr>
        <%
                }
            } catch(Exception e) {
                // Print simple error message to user
        %>
                <tr><td colspan="12">Error: <%= e.getMessage() %></td></tr>
        <%
            } finally {
                try { if(rs != null) rs.close(); } catch(Exception e) {}
                try { if(stmt != null) stmt.close(); } catch(Exception e) {}
                try { if(con != null) con.close(); } catch(Exception e) {}
            }
        %>
    </table>
</body>
</html>

```

* Update user and password with your MySQL credentials.
* Make sure the MySQL JDBC driver (Connector/J) is in your server’s classpath or in your project's WEB-INF/lib folder.
* This script directly connects, executes SQL, and prints results as specified in widely used JSP+JDBC how-tos.

This page will display all student records from your IGNOU.Student table in tabular format each time it is loaded. Adjust your columns/labels as needed for more or fewer student detailsls.










































Perplexity: [https://www.perplexity.ai/search/write-a-crud-create-save-read-Co4ceIYiQO2Q2OkmvlhcTg#1]
