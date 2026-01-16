Client-side validation provides immediate feedback to the user, while Server-side validation acts as a crucial security layer to ensure data integrity even if the client-side checks are bypassed.


1. Update pom.xml for Server-Side Validation
Since you are using Maven, Sir, we need to add the Hibernate Validator dependency (the reference implementation of JSR-380) to support validation annotations.
```xml```
```
<dependency>
    <groupId>org.hibernate.validator</groupId>
    <artifactId>hibernate-validator</artifactId>
    <version>6.2.0.Final</version>
</dependency>

```

2. Add Validation Annotations to the Model
We use annotations like @NotEmpty and @Size to define our rules.

```java```

```
package com.ignou.lab.model;

import javax.validation.constraints.*;
import java.util.List;

public class Student {
    @NotEmpty(message = "First Name is required")
    @Size(min = 2, message = "Name must be at least 2 characters")
    private String firstName;

    @NotEmpty(message = "Address cannot be empty")
    private String address;

    @NotEmpty(message = "Please select a program")
    private String program;

    @NotEmpty(message = "Admission date is required")
    private String admissionDate;

    @NotNull(message = "Please select course preferences")
    @Size(min = 1, message = "Select at least one course")
    private List<String> courses;

    private boolean hostelRequired;

    // Standard Getters and Setters...
}

```
3. Update the JSP for Client & Server Validation
We combine HTML5 attributes (for client-side) with Spring Error tags (for server-side).
student-form.jsp:

```jsp```
```
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<style>.error { color: red; font-size: 0.8em; }</style>

<h2>Student Admission Form</h2>
<form:form action="processAdmission" modelAttribute="student" method="post">
    
    First Name: <form:input path="firstName" required="required" minlength="2" />
    <form:errors path="firstName" cssClass="error" /><br/><br/>

    Address: <form:textarea path="address" required="required" />
    <form:errors path="address" cssClass="error" /><br/><br/>

    Program: 
    <form:select path="program" required="required">
        <form:option value="" label="--Select Program--" />
        <form:options items="${programList}" />
    </form:select>
    <form:errors path="program" cssClass="error" /><br/><br/>

    Admission Date: <form:input path="admissionDate" type="date" required="required" />
    <form:errors path="admissionDate" cssClass="error" /><br/><br/>

    Preferred Courses: 
    <form:checkboxes path="courses" items="${courseList}" />
    <form:errors path="courses" cssClass="error" /><br/><br/>

    <input type="submit" value="Submit Admission" />
</form:form>

```
4. Update the Controller to Handle Errors
The @Valid annotation triggers the validation, and the BindingResult object catches any errors.

```java```
```
@PostMapping("/processAdmission")
public String processForm(
        @Valid @ModelAttribute("student") Student theStudent, 
        BindingResult bindingResult, 
        Model model) {

    // Check if server-side validation failed
    if (bindingResult.hasErrors()) {
        // If errors exist, reload the lists and return to the form
        model.addAttribute("programList", Arrays.asList("MCA", "BCA", "PGDIS"));
        model.addAttribute("courseList", Arrays.asList("Java", "Python", "Cloud Computing"));
        return "student-form";
    } else {
        // If valid, proceed to confirmation
        model.addAttribute("student", theStudent);
        return "admission-confirmation";
    }
}

```

Verification Summary for the Lab:

1. Client-Side Check: If you leave "First Name" empty and click submit, the browser will prevent submission and show a bubble saying "Please fill out this field."

2. Server-Side Check: If a user bypasses the browser check (e.g., using a tool like Postman), the Controller will detect the BindingResult errors and redirect the user back to the form, displaying your custom error messages in red.