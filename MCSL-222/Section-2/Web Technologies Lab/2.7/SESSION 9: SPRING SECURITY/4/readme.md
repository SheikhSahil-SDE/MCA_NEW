we will implement a registration flow that seamlessly transitions the user into the application without asking them to log in again.
This "Auto-Login" feature involves manually interacting with the Spring Security Context to establish an authentication session immediately after the user is saved to the database.
1. The Data Transfer Object (UserRegistrationDto.java)
It is best practice to use a DTO (Data Transfer Object) for the form instead of the raw Entity. This allows us to handle validation cleanly.

Java
package com.ignou.lab.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserRegistrationDto {

    @NotEmpty(message = "Username is required")
    @Size(min = 3, message = "Username must be at least 3 characters")
    private String username;

    @NotEmpty(message = "Password is required")
    @Size(min = 4, message = "Password must be at least 4 characters")
    private String password;
    
    // Default role for new registrations
    private String role = "USER"; 

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}

2. The Service with Auto-Login Logic (UserService.java)
This is the critical part. After saving the user, we manually create an Authentication object and inject it into the SecurityContextHolder.

Java
package com.ignou.lab.service;

import com.ignou.lab.dto.UserRegistrationDto;
import com.ignou.lab.entity.User;
import com.ignou.lab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void registerAndAutoLogin(UserRegistrationDto userDto, HttpServletRequest request) {
        
        // 1. Save User to Database
        User newUser = new User();
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(userDto.getPassword()); // In production, encrypt this!
        newUser.setRole("ROLE_" + userDto.getRole());
        newUser.setEnabled(true);
        
        userRepository.save(newUser);

        // 2. Perform Auto-Login (Programmatic Authentication)
        // We create a token with the username, password, and authorities (Roles)
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                newUser.getUsername(),
                newUser.getPassword(),
                AuthorityUtils.createAuthorityList(newUser.getRole())
        );

        // 3. Set the Authentication in the Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // 4. Create a new session for the user
        request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        
        System.out.println("User " + newUser.getUsername() + " registered and auto-logged in.");
    }
}

3. The Registration Controller (RegistrationController.java)

Java
package com.ignou.lab.controller;

import com.ignou.lab.dto.UserRegistrationDto;
import com.ignou.lab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    // Show Registration Form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserRegistrationDto());
        return "registration-page";
    }

    // Process Registration
    @PostMapping("/processRegistration")
    public String registerUser(
            @Valid @ModelAttribute("userDto") UserRegistrationDto userDto,
            BindingResult bindingResult,
            HttpServletRequest request) {

        // 1. Check for Validation Errors
        if (bindingResult.hasErrors()) {
            return "registration-page";
        }

        // 2. Save and Auto-Login
        userService.registerAndAutoLogin(userDto, request);

        // 3. Redirect to the secured dashboard
        return "redirect:/api/students";
    }
}

4. The View (registration-page.html)
Create a new HTML file in src/main/resources/templates/.

html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Register</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>.error { color: red; font-size: 0.9em; }</style>
</head>
<body class="bg-light d-flex align-items-center justify-content-center" style="height: 100vh;">

<div class="card p-4 shadow" style="max-width: 400px; width: 100%;">
    <h3 class="text-center mb-3">Create Account</h3>
    
    <form action="/processRegistration" th:object="${userDto}" method="POST">
        
        <div class="mb-3">
            <label class="form-label">Username</label>
            <input type="text" th:field="*{username}" class="form-control" />
            <div th:if="${#fields.hasErrors('username')}" th:errors="*{username}" class="error"></div>
        </div>

        <div class="mb-3">
            <label class="form-label">Password</label>
            <input type="password" th:field="*{password}" class="form-control" />
            <div th:if="${#fields.hasErrors('password')}" th:errors="*{password}" class="error"></div>
        </div>

        <button type="submit" class="btn btn-success w-100">Register & Login</button>
        
        <div class="mt-3 text-center">
            <a href="/showMyLoginPage">Already have an account? Login</a>
        </div>
    </form>
</div>

</body>
</html>

5. Update Security Config
You must permit public access to the registration URLs.
File: SecurityConfig.java

Java
// Inside filterChain method:
.authorizeHttpRequests(auth -> auth
    // Add /register and /processRegistration to permitAll
    .requestMatchers("/showMyLoginPage", "/register", "/processRegistration", "/resources/**").permitAll()
    .anyRequest().authenticated()
)


How to Verify:
Start the Server.
Go to: http://localhost:8080/register (Do not log in first).
Fill the form: Enter a new user (e.g., student1 / pass123).
Submit:
Validation checks run (username length, etc.).
User is saved to DB.
User is effectively "logged in" by the service.
Result: You are immediately redirected to the Student List (a secured page) without seeing the login screen.