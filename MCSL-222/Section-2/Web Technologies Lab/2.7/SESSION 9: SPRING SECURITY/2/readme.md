We will replace the default (and rather plain) Spring Security login screen with your own custom-designed HTML page.
This requires three specific changes: creating a Controller to serve the page, designing the HTML View, and updating the Security Configuration to know about this new page.

1. The Login Controller (LoginController.java)
We need a simple controller to return the login view when the user requests it.

Java

package com.ignou.lab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/showMyLoginPage")
    public String showMyLoginPage() {
        // Looks for src/main/resources/templates/login-page.html
        return "login-page"; 
    }
}


2. The Custom HTML View (login-page.html)
Create this file in src/main/resources/templates/.
We will use Thymeleaf to handle error messages (like "Bad Credentials") and Bootstrap (which you added earlier) to make it look professional.
Crucial Note: The input field names must be username and password unless you specifically change them in the config.

html

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>IGNOU Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f0f2f5; display: flex; align-items: center; justify-content: center; height: 100vh; }
        .login-card { width: 100%; max-width: 400px; padding: 2rem; border-radius: 10px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); background: white; }
        .btn-custom { background-color: #007bff; color: white; width: 100%; }
    </style>
</head>
<body>

<div class="login-card">
    <div class="text-center mb-4">
        <h3>Student Portal</h3>
        <p class="text-muted">Please sign in to continue</p>
    </div>

    <form action="/authenticateTheUser" method="POST">
        
        <div th:if="${param.error}" class="alert alert-danger">
            Invalid username or password.
        </div>

        <div th:if="${param.logout}" class="alert alert-success">
            You have been logged out.
        </div>

        <div class="mb-3">
            <label class="form-label">Username</label>
            <input type="text" name="username" class="form-control" placeholder="Enter username" required autofocus>
        </div>

        <div class="mb-3">
            <label class="form-label">Password</label>
            <input type="password" name="password" class="form-control" placeholder="Enter password" required>
        </div>

        <button type="submit" class="btn btn-custom">Sign In</button>
    </form>
</div>

</body>
</html>


3. Update Security Configuration (SecurityConfig.java)
We need to tell Spring Security three things:
Where the login page is (/showMyLoginPage).
Where to send the POST request (/authenticateTheUser).
Permit All: Allow unauthenticated users to actually see the login page (otherwise, they'd get a redirect loop).
<!-- end list -->

Java
package com.ignou.lab.config;

import com.ignou.lab.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // (Password Encoder and AuthProvider beans remain the same as previous step...)
    @Bean
    public PasswordEncoder passwordEncoder() { return NoOpPasswordEncoder.getInstance(); }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(CustomUserDetailsService userDetailsService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    // UPDATED FILTER CHAIN
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/showMyLoginPage", "/resources/**", "/webjars/**").permitAll() // Allow access to login page & CSS
                .anyRequest().authenticated() // Everything else requires login
            )
            .formLogin(form -> form
                .loginPage("/showMyLoginPage")          // 1. The Controller URL (GET)
                .loginProcessingUrl("/authenticateTheUser") // 2. The Form Action URL (POST)
                .defaultSuccessUrl("/api/students", true) // Where to go after success
                .permitAll()                            // Allow everyone to see the login page
            )
            .logout(logout -> logout.permitAll());      // Allow everyone to logout
            
        http.csrf().disable(); // Disable CSRF for simplicity in this lab
        
        return http.build();
    }
}


How to Verify:
Start the Server.
Access a Protected URL: Try to go to http://localhost:8080/api/students.
Observe: You should be redirected to your new, blue-and-white Bootstrap card login page instead of the default white screen.
Login: Enter sahil / sahil123 (from your database).
Success: You will be redirected to the JSON student list.
