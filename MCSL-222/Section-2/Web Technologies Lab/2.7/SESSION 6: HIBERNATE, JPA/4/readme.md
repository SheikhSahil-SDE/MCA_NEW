We will implement a Batch Update mechanism. This allows an administrator to select multiple pending student applications and approve them all in a single click, which is significantly more efficient than updating records one by one.

We will use HQL (Hibernate Query Language) to execute a single UPDATE statement for multiple records.

1. Service Layer: The Batch Logic
In StudentService.java, we add a method that accepts a list of Application IDs and updates their status to APPROVED in one go.

```java```
```
package com.ignou.lab.service;

import com.ignou.lab.entity.ApplicationStatus; // Ensure you have this Enum
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service
public class StudentService {

    @PersistenceContext
    private EntityManager em;

    // Existing methods (save, get, delete)...

    // BATCH UPDATE METHOD
    @Transactional
    public int approveBatchApplications(List<Integer> applicationIds) {
        if (applicationIds == null || applicationIds.isEmpty()) {
            return 0;
        }

        // HQL Query: Update Application Status WHERE ID is in the provided list
        String hql = "UPDATE Application a SET a.status = :newStatus WHERE a.id IN :ids";
        
        Query query = em.createQuery(hql);
        query.setParameter("newStatus", ApplicationStatus.APPROVED);
        query.setParameter("ids", applicationIds);

        // executeUpdate returns the number of entities updated
        return query.executeUpdate();
    }
    
    // Method to fetch only pending applications for the dashboard
    public List getPendingApplications() {
        return em.createQuery("FROM Application a WHERE a.status = 'SUBMITTED'", 
                              com.ignou.lab.entity.Application.class).getResultList();
    }
}

```

2. Controller Layer: Handling the List
In AdminController.java (or your existing controller), we map a POST request that receives a list of Integers. Spring MVC automatically binds checkbox values with the same name to this list.

```java```
```
package com.ignou.lab.controller;

import com.ignou.lab.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private StudentService studentService;

    // 1. Show Pending Applications
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("pendingApps", studentService.getPendingApplications());
        return "admin-dashboard";
    }

    // 2. Process Batch Approval
    @PostMapping("/approveSelected")
    public String approveSelected(@RequestParam(value = "appIds", required = false) List<Integer> appIds, 
                                  Model model) {
        
        if (appIds != null && !appIds.isEmpty()) {
            int count = studentService.approveBatchApplications(appIds);
            System.out.println("Successfully approved " + count + " applications.");
        } else {
            System.out.println("No applications selected.");
        }
        
        return "redirect:/admin/dashboard";
    }
}

```

3. View Layer: Checkboxes and "Select All"
Create admin-dashboard.jsp. We use standard HTML checkboxes. Notice the name="appIds" attribute on the checkboxes matches the @RequestParam("appIds") in the controller. 

```jsp```
```
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script>
        // Simple Script to Toggle All Checkboxes
        function toggle(source) {
            checkboxes = document.getElementsByName('appIds');
            for(var i=0, n=checkboxes.length;i<n;i++) {
                checkboxes[i].checked = source.checked;
            }
        }
    </script>
</head>
<body class="container mt-4">

    <div class="card shadow">
        <div class="card-header bg-primary text-white">
            <h3>Pending Student Approvals</h3>
        </div>
        <div class="card-body">
            
            <form action="approveSelected" method="POST">
                
                <div class="mb-3 text-end">
                    <button type="submit" class="btn btn-success">
                        Approve Selected Students
                    </button>
                </div>

                <table class="table table-hover table-bordered">
                    <thead class="table-light">
                        <tr>
                            <th style="width: 50px; text-align: center;">
                                <input type="checkbox" onClick="toggle(this)">
                            </th>
                            <th>ID</th>
                            <th>Student Name</th>
                            <th>Program</th>
                            <th>Date Applied</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="app" items="${pendingApps}">
                            <tr>
                                <td class="text-center">
                                    <input type="checkbox" name="appIds" value="${app.id}">
                                </td>
                                <td>${app.id}</td>
                                <td>${app.student.firstName} ${app.student.lastName}</td>
                                <td>${app.program.name}</td>
                                <td>${app.applicationDate}</td>
                                <td><span class="badge bg-warning text-dark">${app.status}</span></td>
                            </tr>
                        </c:forEach>
                        
                        <c:if test="${empty pendingApps}">
                            <tr>
                                <td colspan="6" class="text-center">No pending applications found.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </form>
        </div>
    </div>

</body>
</html>

```
How it works (The Batch Flow):

* Selection: You check 3 boxes (e.g., IDs 101, 102, 105) in the browser.

* Submission: The form POSTs appIds=101&appIds=102&appIds=105 to the server.

* Binding: Spring converts this comma-separated string into a List<Integer>.

* Execution: Hibernate runs:

```sql```
```
UPDATE Application SET status = 'APPROVED' WHERE application_id IN (101, 102, 105);

```

* Result: All three students are approved instantly with a single database transaction.