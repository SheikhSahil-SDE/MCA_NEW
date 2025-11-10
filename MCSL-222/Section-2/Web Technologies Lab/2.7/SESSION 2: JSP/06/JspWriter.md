# Write a JSP program using the following implicit objects with an example:<br>a. Out <br>b. request<br>c. response<br>d. Session <br>e. pageContext <br>f. exception

Below are JSP code examples illustrating how to use each of the requested implicit objects: ```out```, ```request```, ```response```, ```session```, ```pageContext```, and ```exception```.

**a. ```out``` Implicit Object**<br>
The out object is used to send output to the client browser. It is an instance of ```JspWriter```.<br>
```TEXT```
```
<html>
<body>
<% out.println("Hello from the out implicit object!"); %>
</body>
</html>
```
This outputs text to the web browser using the ```out``` object.

**b. ```request``` Implicit Object**<br>
The ```request``` object allows access to HTTP request data such as parameters, headers, and attributes.
```TEXT```
```
<html>
<body>
<form method="get" action="requestExample.jsp">
  Enter your name: <input type="text" name="username"/>
  <input type="submit" value="Send"/>
</form>
</body>
</html>
```

**requestExample.jsp**
~~~
<html>
<body>
<%
String username = request.getParameter("username");
out.println("Hello, " + username + "!");
%>
</body>
</html>
~~~

This reads a query parameter from the URL and displays it.


**c. ```response``` Implicit Object** <br>
The ```response``` object can be used to modify response headers or send redirects.

~~~
<%
response.setContentType("text/html");
response.setHeader("Custom-Header", "JSPDemo");
out.println("Custom header set. Refreshing in 2 seconds...");
response.setHeader("Refresh", "2; URL=index.jsp");
%>

~~~

This sets a header and causes the page to refresh after 2 seconds.

**d. ```session``` Implicit Object**<br>
The ```session``` object is used to store data across multiple requests by the same user.
~~~
<%
session.setAttribute("username", "Alice");
%>
<html>
<body>
<%
String user = (String)session.getAttribute("username");
out.println("Stored username in session: " + user);
%>
</body>
</html>
~~~
This stores a value in the session and then retrieves it.

**e. ```pageContext``` Implicit Object**<br>
The ```pageContext``` object provides access to various scoped attributes and references to other implicit objects.
```
<%
pageContext.setAttribute("color", "blue", PageContext.PAGE_SCOPE);
String c = (String)pageContext.getAttribute("color", PageContext.PAGE_SCOPE);
out.println("Color from pageContext: " + c);
%>
```
Stores and retrieves a value in the page scope.

**f. ```exception``` Implicit Object**<br>
The ```exception``` object is only valid in error pages (isErrorPage="true"). It is used to handle errors.

*error.jsp*
```
<%@ page isErrorPage="true" %>
<html>
<body>
<h2>An error occurred:</h2>
<p>Exception Type: <%= exception.getClass().getName() %></p>
<p>Message: <%= exception.getMessage() %></p>
</body>
</html>  
```

To test it, create another JSP:

*triggerError.jsp*

```
<%@ page errorPage="error.jsp" %>
<%
int num = 5/0; // This will cause an exception
%>
```

When an error occurs, the request is forwarded to ```error.jsp```, which accesses the ```exception``` object.



















































**Perplexity Link:** [https://www.perplexity.ai/search/write-a-jsp-application-using-cgMHkNGPR6eQYQtD8HUmFg#1]
