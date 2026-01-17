To complete the CRUD operations using Spring MVC and Hibernate, Sir, we will build upon the Student entity and JpaConfig we created in the previous steps.

We need three main components: a Service to handle database logic, a Controller to handle web requests, and JSP Views for the user interface
1. The Service Layer (StudentService.java)
This class handles the transaction logic. We use @Transactional so Hibernate handles opening/closing connections automatically.

```java```
```
package com.ignou.lab.service;

import com.ignou.lab.entity.Student;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class StudentService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public List<Student> getStudents() {
        return em.createQuery("from Student", Student.class).getResultList();
    }

    @Transactional
    public void saveStudent(Student theStudent) {
        // merge() handles both Save (Insert) and Update
        em.merge(theStudent); 
    }

    @Transactional
    public Student getStudent(int id) {
        return em.find(Student.class, id);
    }

    @Transactional
    public void deleteStudent(int id) {
        Student theStudent = em.find(Student.class, id);
        if (theStudent != null) {
            em.remove(theStudent);
        }
    }
}

```
2. The Controller Layer (StudentCrudController.java)
This controller maps the URLs for List, Add, Update, and Delete actions.

```java```
```
package com.ignou.lab.controller;

import com.ignou.lab.entity.Student;
import com.ignou.lab.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentCrudController {

    @Autowired
    private StudentService studentService;

    // 1. READ: List all students
    @GetMapping("/list")
    public String listStudents(Model model) {
        List<Student> students = studentService.getStudents();
        model.addAttribute("students", students);
        return "student-list";
    }

    // 2. CREATE: Show form for adding
    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        Student theStudent = new Student();
        model.addAttribute("student", theStudent);
        return "student-form";
    }

    // 3. UPDATE: Show form with existing data
    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("studentId") int theId, Model model) {
        Student theStudent = studentService.getStudent(theId);
        model.addAttribute("student", theStudent);
        return "student-form";
    }

    // 4. SAVE: Handle the submission (Used for both Create and Update)
    @PostMapping("/saveStudent")
    public String saveStudent(@ModelAttribute("student") Student theStudent) {
        studentService.saveStudent(theStudent);
        return "redirect:/student/list";
    }

    // 5. DELETE: Remove student
    @GetMapping("/delete")
    public String deleteStudent(@RequestParam("studentId") int theId) {
        studentService.deleteStudent(theId);
        return "redirect:/student/list";
    }
}

```
3. The Views (JSP Files)
A. The List View (student-list.jsp)
This displays the data and provides links to Edit and Delete.

```jsp```
```
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Student List</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="container mt-4">

    <h2>IGNOU Student Directory</h2>
    <hr>
    
    <input type="button" value="Add Student" 
           onclick="window.location.href='showFormForAdd'; return false;"
           class="btn btn-primary mb-3" />

    <table class="table table-bordered table-striped">
        <thead class="table-dark">
            <tr>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="tempStudent" items="${students}">
                
                <c:url var="updateLink" value="/student/showFormForUpdate">
                    <c:param name="studentId" value="${tempStudent.id}" />
                </c:url>

                <c:url var="deleteLink" value="/student/delete">
                    <c:param name="studentId" value="${tempStudent.id}" />
                </c:url>

                <tr>
                    <td>${tempStudent.firstName}</td>
                    <td>${tempStudent.lastName}</td>
                    <td>${tempStudent.email}</td>
                    <td>
                        <a href="${updateLink}" class="btn btn-sm btn-info">Update</a>
                        <a href="${deleteLink}" class="btn btn-sm btn-danger"
                           onclick="return confirm('Are you sure you want to delete this student?');">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>

```
B. The Form View (student-form.jsp)
This single form handles both adding new records and updating existing ones. The key is the Hidden ID field.

```jsp```
```
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Save Student</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="container mt-4">

    <h3>Student Record</h3>
    <hr>

    <form:form action="saveStudent" modelAttribute="student" method="POST">

        <form:hidden path="id" />

        <div class="mb-3">
            <label>First Name</label>
            <form:input path="firstName" class="form-control" />
        </div>

        <div class="mb-3">
            <label>Last Name</label>
            <form:input path="lastName" class="form-control" />
        </div>

        <div class="mb-3">
            <label>Email</label>
            <form:input path="email" class="form-control" />
        </div>

        <button type="submit" class="btn btn-success">Save</button>
        <a href="list" class="btn btn-secondary">Cancel</a>

    </form:form>
</body>
</html>

```
How to Run this:

Deploy your WAR file to Tomcat.
Navigate to http://localhost:8080/your-app/student/list.
You will see the list. Click "Add Student" to create a record.
Click "Update" on that record to modify it.
Click "Delete" to remove it.
This completes the full CRUD cycle using the Service-Repository pattern with Hibernate and Spring MVC.