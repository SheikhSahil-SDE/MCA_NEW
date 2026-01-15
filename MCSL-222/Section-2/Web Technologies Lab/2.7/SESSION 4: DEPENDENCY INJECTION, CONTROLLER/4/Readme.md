To complete Session 4, Task 4 of your lab manual, Sir, we will create a Spring MVC form using the dedicated Spring Form Tag Library. These tags provide seamless data binding between your HTML form and your Java model.

1. The Student Model
We define a Student class with attributes that match the requested form fields. Note the use of a List for checkboxes and a boolean for the true/false field.

```java```
```
package com.ignou.lab.model;

import java.util.List;

public class Student {
    private String firstName;
    private String lastName;
    private String address;
    private String program;
    private String admissionDate;
    private boolean hostelRequired; // True/False field
    private List<String> courses;   // Checkbox field (Multiple selections)

    // Getters and Setters are required for Spring binding to work
}
```

2. The Controller with @PostMapping
The controller handles the two-step process: serving the blank form and capturing the submitted data via ```POST```.

```java```

```
package com.ignou.lab.controller;

import com.ignou.lab.model.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
public class AdmissionController {

    @GetMapping("/admissionForm")
    public String showForm(Model model) {
        // Create object to bind form data
        model.addAttribute("student", new Student());
        
        // Prepare lists for dropdown and checkboxes
        model.addAttribute("programList", Arrays.asList("MCA", "BCA", "PGDIS"));
        model.addAttribute("courseList", Arrays.asList("Java", "Python", "Cloud Computing"));
        
        return "student-form";
    }

    @PostMapping("/processAdmission")
    public String processForm(@ModelAttribute("student") Student theStudent, Model model) {
        // 'theStudent' now contains all the form data
        model.addAttribute("student", theStudent);
        return "admission-confirmation";
    }
}

```

3. The View (JSP with Spring Form Tags)
In the JSP, we use the form prefix. The path attribute in the tags must exactly match the field names in the Student class.

```student-form.jsp```:
```java```
```
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head><title>Student Admission</title></head>
<body>
    <h2>Student Admission Form</h2>
    <form:form action="processAdmission" modelAttribute="student" method="post">
        
        First Name: <form:input path="firstName" /><br/><br/>
        Address: <form:textarea path="address" rows="3" /><br/><br/>

        Program: 
        <form:select path="program">
            <form:option value="" label="--Select Program--" />
            <form:options items="${programList}" />
        </form:select><br/><br/>

        Admission Date: <form:input path="admissionDate" type="date" /><br/><br/>

        Hostel Required: 
        <form:radiobutton path="hostelRequired" value="true" label="Yes" />
        <form:radiobutton path="hostelRequired" value="false" label="No" /><br/><br/>

        Preferred Courses: 
        <form:checkboxes path="courses" items="${courseList}" /><br/><br/>

        <input type="submit" value="Register Student" />
    </form:form>
</body>
</html>
```

4. Verification
The confirmation page simply displays the values captured by the @PostMapping method.

```admission-confirmation.jsp```:
```java```
```
<html>
<body>
    <h2>Registration Successful, Sir!</h2>
    <p>Student: ${student.firstName} ${student.lastName}</p>
    <p>Program: ${student.program}</p>
    <p>Admission Date: ${student.admissionDate}</p>
    <p>Hostel Required: ${student.hostelRequired ? "Yes" : "No"}</p>
    <p>Selected Courses: ${student.courses}</p>
</body>
</html>
```

Summary of Implementation:
* Form Binding: The modelAttribute="student" attribute links the entire form to a Java object.
* Field Mapping: Tags like <form:input path="firstName"> automatically call setFirstName() when the form is submitted.
* Verb usage: The @PostMapping ensures that the data is handled securely as a state-changing operation, as per J2EE best practices.
