1. Add Bootstrap Dependencies (pom.xml)
While you can use a CDN, the lab environment often prefers local assets. We can use WebJars to manage client-side dependencies via Maven.
``````

```
<dependencies>
    <dependency>
        <groupId>org.webjars</groupId>
        <artifactId>bootstrap</artifactId>
        <version>5.3.0</version>
    </dependency>
    <dependency>
        <groupId>org.webjars</groupId>
        <artifactId>jquery</artifactId>
        <version>3.6.4</version>
    </dependency>
    </dependencies>
```
2. The Controller (StudentController.java)
The controller uses @ModelAttribute to bridge the gap between the HTTP request and the Java object.
```java```
```
package com.ignou.lab.controller;

import com.ignou.lab.entity.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/student")
public class StudentController {

    // 1. Show the form - Initialize the Entity Bean
    @GetMapping("/showForm")
    public String showForm(Model model) {
        // Create a new Student object to bind form data
        Student theStudent = new Student();
        model.addAttribute("student", theStudent);
        return "student-form";
    }

    // 2. Process the form - Auto-bind data to the Entity Bean
    @PostMapping("/saveStudent")
    public String saveStudent(@ModelAttribute("student") Student theStudent) {
        
        // At this point, 'theStudent' is fully populated with form data
        System.out.println("Binding successful for: " + theStudent.getFirstName());
        
        // Logic to save the entity via Hibernate/JPA would go here
        // studentService.save(theStudent);

        return "confirmation-page";
    }
}

```

3. The View (student-form.jsp)
We use Spring's form tags. The modelAttribute in the form must match the name used in the controller.

```jsp```
```
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head><title>Student Binding</title></head>
<body>
    <h2>Student Registration</h2>

    <form:form action="saveStudent" modelAttribute="student" method="POST">
        
        First Name: <form:input path="firstName" /><br/><br/>
        
        Last Name: <form:input path="lastName" /><br/><br/>
        
        Email: <form:input path="email" /><br/><br/>

        <input type="submit" value="Submit" />
    </form:form>
</body>
</html>

```


``````
```
```

Key Concepts for your Lab Report:

Direct Binding: The path attribute in <form:input> maps directly to the private fields of the Student entity. Spring calls the corresponding setter methods behind the scenes.

@ModelAttribute: This annotation on the controller method parameter tells Spring: "Look for an object in the model named 'student' and fill it with the incoming request parameters."

State Management: By binding to an @Entity, the object is immediately ready to be passed to an EntityManager or Session to be persisted into the IGNOU database you created earlier.