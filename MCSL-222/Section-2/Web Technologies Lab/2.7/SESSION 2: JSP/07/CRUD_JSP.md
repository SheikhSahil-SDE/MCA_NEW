**Project Structure and Flow**
* HTML/JSP Pages
  * ```login.jsp```: Login Form UI
  * ```welcome.jsp```: Post login page with navigation
  * ```studentForm.jsp```: Form for adding/updating student info
  * ```listStudents.jsp```: Displays students from DB, with CRUD options
  * ```error.jsp```: Custom error page (exception handling)
  * Additional JSPs for demonstration (auto-refresh, JSTL, scripting tags, etc.)

* Servlets
  * LoginServlet.java: Authenticates user, creates session, redirects
  * LogoutServlet.java: Ends session
  * StudentServlet.java: Handles all CRUD operations (Create, Read, Update, Delete)
  * SessionTrackerServlet.java: Demonstrates session management and cookies

* Supporting Beans/Classes
  * Student.java: JavaBean for student Entity
  * DBUtil.java: DB utility/helper for JDBC

**Key Implemented Features**

1. Login System

    * login.jsp form submits to LoginServlet

    * Servlet checks credentials, sets session attribute, redirects to ```welcome.jsp```

    * Session/cookie techniques to maintain/authenticate user​

2. Student CRUD with JDBC
studentForm.jsp for add/edit, submits to StudentServlet

    * listStudents.jsp lists all students using JDBC, with edit/delete links

    * StudentServlet has logic for handling all CRUD functional URLs (action=add, action=edit, etc.)

    * Uses a database named IGNOU with a Student table​

3. Session Management & Exception Handling
Demonstrates HTTP session usage for login tracking, storing user/role data

   * Uses cookies for session tracking

   * Exception handling is implemented with error.jsp mapped as error page for the app or for a specific servlet/JSP​

4. Use of JSP Scripting, Expression, Declaration Tags
* In a sample JSP file, demonstrates all three scripting tags:

* ```<% %>``` scripting

* ```<%= %>``` expression

* ```<%! %>``` declaration

5. JSTL Usage
Sample JSP imports JSTL library and demonstrates use of ```<c:out>```, ```<c:if>```, ```<c:forEach>```, ```<c:choose>```, ```<c:when>```, ```<c:otherwise>```, ```<c:url>```, and ```<c:redirect>```

6. Action Elements and Implicit Objects
Separate example JSPs demonstrate the action elements (```jsp:forward```, ```jsp:include```, ```jsp:useBean```, ```jsp:setProperty```, ```jsp:getProperty```) and various implicit objects as previously required.

**Example File/Code Snippets**
_login.jsp_
```
<form method="post" action="LoginServlet">
    Username: <input type="text" name="username">
    Password: <input type="password" name="password">
    <input type="submit" value="Login">
</form>
<% String error = (String)request.getAttribute("error"); if(error != null){ %>
    <div style="color:red"><%= error %></div>
<% } %>

```
_LoginServlet.java_
```
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String user = request.getParameter("username");
    String pass = request.getParameter("password");
    // TODO: real authentication
    if("admin".equals(user) && "admin123".equals(pass)) {
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        Cookie loginCookie = new Cookie("user", user);
        response.addCookie(loginCookie);
        response.sendRedirect("welcome.jsp");
    } else {
        request.setAttribute("error", "Invalid login!");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}

```
_StudentServlet.java (Partial CRUD Example_

```JAVA```
```
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // action=[list|add|edit|update|delete]
    String action = request.getParameter("action");
    if(action == null || action.equals("list")) {
        // fetch students from DB and forward to listStudents.jsp
    } else if(action.equals("edit")) {
        // select student by id, forward to studentForm.jsp
    }
    // Implement add, update, delete in doPost or doGet as needed
}
```

_Session Management Example (in ```welcome.jsp``` or a servlet)_
```TEXT```
```
<%
String user = (String)session.getAttribute("user");
if(user == null){
    response.sendRedirect("login.jsp");
} else {
    out.println("Welcome, " + user);
}
%>

```


_```error.jsp``` (Exception Handling)_

```text```
```
<%@ page isErrorPage="true" %>
<h2>Error Occurred</h2>
<p>Type: <%= exception.getClass().getName() %></p>
<p>Message: <%= exception.getMessage() %></p>
```

**Database**

  * Database: ```IGNOU```
  * Table: ```Student``` (id, name, contact, enrollment, courses, ...)


```sql```
```
CREATE TABLE Student (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    contact VARCHAR(20),
    enrollment VARCHAR(50),
    courses VARCHAR(255)
);
```

**Complete Guides and References**
* [Student Management System Servlet JSP Example][https://github.com/kishan0725/Student-Management-System]

*[CRUD App with JSP, Servlet, JDBC][https://www.codejava.net/coding/jsp-servlet-jdbc-mysql-create-read-update-delete-crud-example][https://www.javaguides.net/2019/03/jsp-servlet-jdbc-mysql-crud-example-tutorial.html]

* [Session Handling][https://www.digitalocean.com/community/tutorials/java-session-management-servlet-httpsession-url-rewriting][https://studylib.net/doc/27521054/slot-8-9-10-sessions][https://www.geeksforgeeks.org/java/servlet-httpsession-login-and-logout-example/]

* [Error Handling in Servlet/JSP][https://www.digitalocean.com/community/tutorials/servlet-exception-and-error-handling-example-tutorial]


# Perplexity Link [https://www.perplexity.ai/search/write-a-jsp-application-using-cgMHkNGPR6eQYQtD8HUmFg#2]
