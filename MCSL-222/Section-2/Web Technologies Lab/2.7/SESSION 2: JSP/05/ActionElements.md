# Write a JSP application using following Action Elements a. jsp:forward b. jsp:include c. set & getProperty d. jsp:useBean

Here is a sample JSP application that demonstrates four requested JSP action elements: <jsp:forward>, <jsp:include>, <jsp:setProperty>, <jsp:getProperty>, and <jsp:useBean>. Each section below describes the purpose and use of each tag, followed by code examples with explanations.

jsp:useBean, setProperty, and getProperty Example
**First, create a simple JavaBean:**```Details.java```
```
package com.example;
public class Details {
    private String username;
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

```
**This bean must be compiled and placed in the right build path (usually WEB-INF/classes/com/example/Details.class).**```index.jsp``` (Form to input values)

```
<html>
<body>
<form action="userdetails.jsp" method="post">
    User Name: <input name="username" type="text"/><br/>
    Password: <input name="password" type="password"/><br/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>

```

```userdetails.jsp``` (Uses the bean, sets properties, gets properties) 
```TEXT```
```
<jsp:useBean id="userinfo" class="com.example.Details"></jsp:useBean>
<jsp:setProperty name="userinfo" property="*"/>
You have entered the following details:<br/>
Username: <jsp:getProperty name="userinfo" property="username"/>
<br/>
Password: <jsp:getProperty name="userinfo" property="password"/>
```

This uses ```jsp:useBean``` to create/access the bean, ```jsp:setProperty``` assigns form values to bean properties, and jsp:getProperty displays them.​

**jsp:forward Example**

Suppose you want to forward from one JSP to another upon a condition. Example: forwarding from ```index.jsp``` to ```displayDate.jsp.```

```
index.jsp
```
```
<html>
<body>
<h2>The request will be forwarded</h2>
<jsp:forward page="displayDate.jsp"/>
</body>
</html>
```

```
displayDate.jsp
```
```
<html>
<body>
<h2>This is jsp:forward action example</h2>
Current Date : <%=java.util.Calendar.getInstance().getTime() %>
</body>
</html>
```
When you open ```index.jsp```, its whole request is passed to ```displayDate.jsp```, which shows the current date.​

**jsp:include Example**

Here’s how to include a reusable header/footer or another dynamic page.

```
header.jsp
```
```
<html>
<body>
<h2>This is the header (included content)</h2>
</body>
</html>
```

```
main.jsp
```
```
<html>
<body>
<jsp:include page="header.jsp"/>
<h3>Main Content Goes Here</h3>
</body>
</html>
```
```jsp:include``` dynamically includes the output of ```header.jsp``` into ```main.jsp``` at request time.

These examples collectively demonstrate:
  * Bean instantiation and property access with ```jsp:useBean```, ```jsp:setProperty```, and ```jsp:getProperty```.
  * Forwarding request processing with ```jsp:forward```.
  * Including content dynamically with ```jsp:include```.
  * Each tag offers modular, reusable, and maintainable components for JSP applications.





Perplexity Link: [https://www.perplexity.ai/search/write-a-jsp-application-using-cgMHkNGPR6eQYQtD8HUmFg#0]

















