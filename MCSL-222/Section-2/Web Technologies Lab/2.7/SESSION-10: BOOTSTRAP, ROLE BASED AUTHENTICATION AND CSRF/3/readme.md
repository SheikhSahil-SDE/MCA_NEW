
I will now enforce the rules. We will configure Spring Security to ensure that only users with ROLE_ADMIN can access administrative pages, while ROLE_USER is restricted to student pages.

There are two main ways to do this: URL-based security (in the config file) and Annotation-based security (directly on the methods).

**1. Update Security Configuration (SecurityConfig.java)**

We will modify the filterChain to add specific rules using hasRole().

Crucial Note: Spring Security automatically adds the ROLE_ prefix. So, if your database says ROLE_ADMIN, in the config you just write ADMIN.

```Java```
```
package com.ignou.lab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // ... (Encoder and Provider beans remain the same) ...

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // 1. PUBLIC Access
                .requestMatchers("/", "/register", "/processRegistration", "/showMyLoginPage", "/resources/**").permitAll()
                
                // 2. ADMIN ONLY Access (e.g., deleting students, viewing logs)
                .requestMatchers("/admin/**", "/systems/**").hasRole("ADMIN")
                
                // 3. USER & ADMIN Access (e.g., viewing student lists)
                .requestMatchers("/api/students/**").hasAnyRole("USER", "ADMIN")
                
                // 4. Catch-all for any other request
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/showMyLoginPage")
                .loginProcessingUrl("/authenticateTheUser")
                .defaultSuccessUrl("/api/students", true) // Redirect here after login
                .permitAll()
            )
            .logout(logout -> logout.permitAll())
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/access-denied") // Custom 403 Page
            );

        http.csrf().disable();
        return http.build();
    }
}
```

**2. Create a Restricted Controller (AdminController.java)**

Let's create endpoints to test these rules.

```Java```
```
package com.ignou.lab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public String showAdminDashboard() {
        return "admin-dashboard"; // Only ROLE_ADMIN can reach here
    }
}
3. Handle "Access Denied" (403 Error)
If a "Student" tries to access the "Admin" page, Spring throws a 403 error. Let's make a nice page for that.

Controller:

Java
// Add to LoginController or AdminController
@GetMapping("/access-denied")
public String showAccessDenied() {
    return "access-denied";
}
View (src/main/resources/templates/access-denied.html):

HTML
<!DOCTYPE html>
<html>
<head>
    <title>Access Denied</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5 text-center">
    <div class="alert alert-danger">
        <h1 class="display-4">403 - Forbidden</h1>
        <p class="lead">Sorry, you do not have permission to access this page.</p>
        <hr>
        <a href="/api/students" class="btn btn-primary">Go Back to Dashboard</a>
    </div>
</body>
</html>
```

**4. Hide Buttons in UI (Thymeleaf Security Extras)**

Ideally, a student shouldn't even see the "Admin Dashboard" button. We can hide HTML elements based on roles.

First, add the Thymeleaf Security dependency to pom.xml:

```XML```
```
<dependency>
    <groupId>org.thymeleaf.extras</groupId>
    <artifactId>thymeleaf-extras-springsecurity6</artifactId>
</dependency>
Now, update your Navbar (in student-list.html):

HTML
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/extras/spring-security">

<nav class="navbar navbar-dark bg-dark p-3">
    <a class="navbar-brand" href="#">IGNOU Portal</a>

    <div>
        <span sec:authorize="hasRole('ADMIN')">
            <a href="/admin/dashboard" class="btn btn-warning btn-sm">Admin Panel</a>
        </span>

        <a href="/logout" class="btn btn-danger btn-sm">Logout</a>
    </div>
</nav>
```

**How to Verify:**

1. Register two users:
  * sahil (Role: USER)
  * manager (Role: ADMIN) - You may need to manually update the DB to give this user ROLE_ADMIN if your registration form defaults to USER.

2. Login as sahil:
  * Try accessing http://localhost:8080/admin/dashboard.
  * Result: You are redirected to the "Access Denied" page.
  
3. Login as manager:
   * Try accessing http://localhost:8080/admin/dashboard.
   * Result: You see the admin page.
   * You will also see the yellow "Admin Panel" button in the navbar.
