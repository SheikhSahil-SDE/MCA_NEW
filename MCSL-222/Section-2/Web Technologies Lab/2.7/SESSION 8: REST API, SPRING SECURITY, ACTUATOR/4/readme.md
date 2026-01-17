By simply adding the Spring Security dependency, Spring Boot's auto-configuration immediately secures all HTTP endpoints. It provides a default login form and generates a temporary password in the console at startup.

1. Update pom.xml
Add the security starter to your dependencies list.

XML

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
</dependencies>


2. Configure Default Credentials (application.properties)
By default, Spring generates a random password every time you restart the server, which can be annoying during development. Let's configure a fixed username and password.

File: src/main/resources/application.properties

properties

# ... existing database config ...

# ===============================
# SPRING SECURITY CONFIGURATION
# ===============================
# Default Username
spring.security.user.name=admin

# Default Password
spring.security.user.password=password123

# Default Role (Optional)
spring.security.user.roles=USER

3. How to Verify
Restart your Application.
Browser Test:
Navigate to any URL, e.g., http://localhost:8080/api/students.
Instead of seeing the JSON data, you will be automatically redirected to a Login Page (/login).
Enter the credentials:
User: admin
Password: password123
Upon success, you will see your JSON data.
Actuator Test:
Navigate to http://localhost:8080/actuator.
This is now also secured and requires login.
Postman (REST API) Test:
If you try a simple GET request, you will get a 401 Unauthorized error.
To fix this, go to the Authorization tab in Postman.
Select Type: Basic Auth.
Enter admin and password123.
Send the request again to see the data.



