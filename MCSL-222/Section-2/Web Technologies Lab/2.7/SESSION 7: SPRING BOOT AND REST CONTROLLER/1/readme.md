This framework drastically simplifies the setup by removing the need for web.xml, manual DispatcherServlet configuration, and manual version management.

Although you can select these in Spring Initializr (start.spring.io), your lab exercise specifically asks you to add them manually to understand the structure.

1. The pom.xml File (Manual Dependencies)
Here is the complete pom.xml configuration. I have commented on each dependency to correspond with your list (a-g).

```xml```
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" 
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.1.5</version>
        <relativePath/> 
    </parent>

    <groupId>com.ignou.lab</groupId>
    <artifactId>admission-system-boot</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>IGNOU Admission System</name>
    <description>Session 7 Lab Project</description>

    <properties>
        <java.version>17</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>

```

2. The Main Application Class
This class bootstraps the entire application. You do not need a web.xml file anymore.
File: src/main/java/com/ignou/lab/AdmissionBootApplication.java

```java```
```
package com.ignou.lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdmissionBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdmissionBootApplication.class, args);
        System.out.println("Spring Boot Application Started Successfully...");
    }
}
```

3. Application Properties (Configuration)
Instead of creating a JpaConfig class, we define our database connection in application.properties.
File: src/main/resources/application.properties

```properties```
```
# --- g. Database Configuration (MySQL) ---
spring.datasource.url=jdbc:mysql://localhost:3306/IGNOU_Admission?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# --- b. & c. JPA/Hibernate Configuration ---
# Update the schema if entities change
spring.jpa.hibernate.ddl-auto=update
# Show SQL queries in the console for debugging
spring.jpa.show-sql=true
# Use MySQL Dialect
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# --- d. Thymeleaf Configuration ---
# Disable cache for development so changes appear immediately
spring.thymeleaf.cache=false

# --- f. Actuator Configuration ---
# Expose health endpoints to check if the app is running
management.endpoints.web.exposure.include=health,info

```

<!--
How to Run:
Right-click AdmissionBootApplication.java -> Run As -> Java Application.
Watch the console. You will see the "Spring" banner.
Once started, go to http://localhost:8080/actuator/health. You should see {"status":"UP"}, confirming dependencies (f) and (a) are working.
-->

