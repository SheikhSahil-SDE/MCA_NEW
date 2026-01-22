I will enhance your dashboard to display real-time session information. This is useful for auditing and providing context to the user.

We will fetch the Authentication details (Username, Role) from Spring Security, and the Client IP and Date/Time from the HTTP Request and Java Time API.

**1. Update the Controller (DashboardController.java)**

We need to capture the HttpServletRequest and Authentication objects in our controller method.

```Java```
```
package com.ignou.lab.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class DashboardController {

    @GetMapping("/api/students")
    public String showDashboard(Model model, HttpServletRequest request) {
        
        // 1. Fetch Current User Details from Security Context
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        // 2. Fetch Client IP Address
        // Note: In real production behind a proxy (like Nginx), you check "X-Forwarded-For" header
        String remoteIp = request.getRemoteAddr();
        
        // 3. Current Date and Time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        // 4. Add attributes to the Model
        model.addAttribute("username", auth.getName());
        model.addAttribute("userRoles", auth.getAuthorities());
        model.addAttribute("clientIp", remoteIp);
        model.addAttribute("serverTime", now.format(formatter));
        
        return "student-list"; // This is your dashboard view
    }
}
```

**2. Update the View (student-list.html)**
We will use a Bootstrap Card to display this information neatly at the top of the dashboard.

File: ```src/main/resources/templates/student-list.html```

```HTML```
```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
<head>
    <title>Student Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>
<body class="bg-light">

<nav class="navbar navbar-expand-lg navbar-dark bg-dark px-4">
    <a class="navbar-brand" href="#">IGNOU Portal</a>
    <div class="ms-auto">
        <form action="/logout" method="post">
            <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />
            <button class="btn btn-danger btn-sm">Logout</button>
        </form>
    </div>
</nav>

<div class="container mt-4">

    <div class="card shadow-sm mb-4 border-start border-primary border-4">
        <div class="card-body">
            <div class="row align-items-center">
                
                <div class="col-md-6">
                    <h5 class="card-title text-primary">
                        <i class="bi bi-person-circle me-2"></i>
                        <span th:text="${username}">User</span>
                    </h5>
                    <p class="card-text text-muted mb-0">
                        <strong>Role:</strong> 
                        <span th:each="authority : ${userRoles}" 
                              th:text="${authority.authority} + ' '" 
                              class="badge bg-secondary me-1">ROLE</span>
                    </p>
                </div>

                <div class="col-md-6 text-md-end mt-3 mt-md-0">
                    <p class="mb-1">
                        <i class="bi bi-laptop me-1"></i> 
                        <strong>IP:</strong> <span th:text="${clientIp}">127.0.0.1</span>
                    </p>
                    <p class="mb-0">
                        <i class="bi bi-clock me-1"></i> 
                        <strong>Login Time:</strong> <span th:text="${serverTime}">2025-01-01 10:00:00</span>
                    </p>
                </div>

            </div>
        </div>
    </div>

    <div class="card">
        <div class="card-header bg-white">
            <h4 class="mb-0">Student Records</h4>
        </div>
        <div class="card-body">
            <p>Access Level: 
                <span sec:authorize="hasRole('ADMIN')" class="text-danger fw-bold">Full Control</span>
                <span sec:authorize="hasRole('USER')" class="text-success fw-bold">Read Only</span>
            </p>
        </div>
    </div>

</div>

</body>
</html>
```

**How it works:**
* ```auth.getName()```: Extracts the logged-in username (e.g., "Sahil").
* ```auth.getAuthorities()```: Extracts the list of roles (```e.g., [ROLE_USER, ROLE_ADMIN]```). We loop through this in Thymeleaf to display badges.
* ```request.getRemoteAddr()```: If you run this on localhost, you will likely see ```0:0:0:0:0:0:0:1``` (IPv6 localhost) or ```127.0.0.1```.
* ```LocalDateTime```: Displays the server's current timestamp.
