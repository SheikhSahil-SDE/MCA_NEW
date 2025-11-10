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
login.jsp form submits to LoginServlet

Servlet checks credentials, sets session attribute, redirects to welcome.jsp

Session/cookie techniques to maintain/authenticate user​

2. Student CRUD with JDBC
studentForm.jsp for add/edit, submits to StudentServlet

listStudents.jsp lists all students using JDBC, with edit/delete links

StudentServlet has logic for handling all CRUD functional URLs (action=add, action=edit, etc.)

Uses a database named IGNOU with a Student table​

3. Session Management & Exception Handling
Demonstrates HTTP session usage for login tracking, storing user/role data

Uses cookies for session tracking

Exception handling is implemented with error.jsp mapped as error page for the app or for a specific servlet/JSP​

4. Use of JSP Scripting, Expression, Declaration Tags
* In a sample JSP file, demonstrates all three scripting tags:

* ~~~<% %>~~~ scripting

* ~~~<%= %>~~~ expression

* ```<%! %>``` declaration

5. JSTL Usage
Sample JSP imports JSTL library and demonstrates use of <c:out>, <c:if>, <c:forEach>, <c:choose>, <c:when>, <c:otherwise>, <c:url>, and <c:redirect>

6. Action Elements and Implicit Objects
Separate example JSPs demonstrate the action elements (~~~jsp:forward~~~, jsp:include, ~~~jsp:useBean~~~, ~~~jsp:setProperty~~~, ~~~jsp:getProperty~~~) and various implicit objects as previously required.


# Perplexity Lin [https://www.perplexity.ai/search/write-a-jsp-application-using-cgMHkNGPR6eQYQtD8HUmFg#2]
