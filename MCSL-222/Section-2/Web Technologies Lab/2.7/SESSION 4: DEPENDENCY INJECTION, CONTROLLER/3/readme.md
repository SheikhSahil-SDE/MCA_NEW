This program demonstrates how the DispatcherServlet intercepts a request, routes it to a specific method using @GetMapping, and finally renders a response in a View (JSP).

**1. The Controller Class**
We use the @Controller annotation to mark the class as a web handler. The @GetMapping annotation specifically handles HTTP GET requests for a given URL path.


```java```

```
package com.ignou.lab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    // Maps the URL "http://localhost:8080/welcome"
    @GetMapping("/welcome")
    public String displayWelcomePage(Model model) {
        
        // Adding data to the Model to be displayed in the View
        model.addAttribute("message", "Welcome to the Web Technologies Lab, Sir!");
        model.addAttribute("course", "MCS-220: Web Technologies");
        
        // This returns the logical name of the view (welcome.jsp)
        return "welcome"; 
    }
}
```

**2. The View (welcome.jsp**
By default, Spring looks for JSP files in the WEB-INF/view/ directory (depending on your InternalResourceViewResolver configuration). This file retrieves the data sent by the controller using Expression Language (EL).

```jsp```

```
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Spring MVC Response</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; margin-top: 50px; }
        .container { border: 2px solid #007bff; padding: 20px; display: inline-block; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Response from Spring Controller</h1>
        <p><strong>Message:</strong> ${message}</p>
        <p><strong>Course:</strong> ${course}</p>
    </div>
</body>
</html>

```
**3. Key Components Explained**
* **@Controller**: Tells the Spring Container that this class contains handler methods for web requests.
* **@GetMapping("/welcome")**: A specialized version of @RequestMapping(method = RequestMethod.GET). It maps the /welcome URL to this specific method.
* **Model Object**: Think of this as a "data bucket." You put data in it within the controller, and it becomes available to the JSP page.
* **Return String ("welcome")**: This is the Logical View Name. The Spring ViewResolver will take this string and look for a file named welcome.jsp.
