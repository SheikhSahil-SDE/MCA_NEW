We simply need to configure the HttpSecurity chain to intercept a specific URL (usually /logout), invalidate the user's session, and redirect them back to the login page.


1. Update Security Configuration (SecurityConfig.java)
Modify your existing filterChain method. We will add the .logout() configuration block.

Java

package com.ignou.lab.config;

// ... imports ...

@Configuration
public class SecurityConfig {

    // ... (Encoder and Provider beans remain the same) ...

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/showMyLoginPage", "/resources/**", "/webjars/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/showMyLoginPage")
                .loginProcessingUrl("/authenticateTheUser")
                .defaultSuccessUrl("/api/students", true)
                .permitAll()
            )
            // --- ADD THIS LOGOUT BLOCK ---
            .logout(logout -> logout
                .logoutUrl("/logout") // 1. The URL that triggers the logout
                .logoutSuccessUrl("/showMyLoginPage?logout") // 2. Where to go after logout
                .invalidateHttpSession(true) // 3. Kill the session
                .deleteCookies("JSESSIONID") // 4. Clean up cookies
                .permitAll()
            );
            
        http.csrf().disable(); // Keeping disabled for this lab exercise
        
        return http.build();
    }
}

2. Add Logout Button to Your View
You need a way for the user to trigger this. Add a logout button to your main application page (e.g., the page that displays the student list).
Since we disabled CSRF in the config above (http.csrf().disable()), we can use a simple link (<a>). If CSRF were enabled, this would need to be a POST form.
File: src/main/resources/templates/student-list.html (or wherever you list your students)

html

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Student Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Welcome, <span th:text="${#authentication.name}">User</span>!</h2>
        
        <a href="/logout" class="btn btn-danger">Logout</a>
    </div>

    <div class="card">
        <div class="card-body">
            <h5 class="card-title">Student Data</h5>
            <p>Your content goes here...</p>
            </div>
    </div>

</body>
</html>

3. Verify the Login Page Message
In the previous exercise, we already added this block to your login-page.html. This ensures the user gets feedback when they are redirected back.

html
<div th:if="${param.logout}" class="alert alert-success">
    You have been logged out.
</div>

How to Test:
Login with sahil / sahil123.
You will land on the student page.
Click the red Logout button.
Result: The server destroys your session and redirects you to the Login Page with a green message: "You have been logged out."
Verify: Try to hit the "Back" button on your browser. You should not be able to access the secured page again without logging in.
