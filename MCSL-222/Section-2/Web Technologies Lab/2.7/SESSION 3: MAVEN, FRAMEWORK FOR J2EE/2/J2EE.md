Creating a foundational, Maven-based J2EE (now Jakarta EE) application for Spring MVC involves setting up the correct project structure, a pom.xml for dependencies, and the necessary XML configuration files to bootstrap the Spring DispatcherServlet.
Here is a comprehensive guide to building the classic, XML-configured Spring MVC application from scratch.


## Project Structure
First, you'll need to create a standard Maven web application directory structure. It will look like this:

```
my-spring-mvc-app/
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── example/
        │           └── controller/
        │               └── HelloController.java
        └── webapp/
            └── WEB-INF/
                ├── views/
                │   └── home.jsp
                ├── spring-servlet.xml
                └── web.xml
```

# 1. The Maven POM (pom.xml)

This file is the heart of your project. It defines your project's dependencies and how it should be built. The most important part is setting <packaging>war</packaging> which tells Maven to build a Web Application Archive (WAR) file.
 
```xml```

```
<project xmlns="http://maven.apache.org/POM/4.0.0" 
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
                             http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>my-spring-mvc-app</artifactId>
    <packaging>war</packaging>
    <version>1.0-SNAPSHOT</version>
    <name>My Spring MVC App</name>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
        <spring.version>5.3.31</spring.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>4.0.1</version>
            <scope>provided</scope>
        </dependency>

        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
        </dependency>
    </dependencies>

    <build>
        <finalName>my-spring-mvc-app</finalName>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>${maven.compiler.source}</source>
                    <target>${maven.compiler.target}</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-war-plugin</artifactId>
                <version>3.3.2</version>
                <configuration>
                    <failOnMissingWebXml>true</failOnMissingWebXml>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```


# 2. The Deployment Descriptor (web.xml)
This file (src/main/webapp/WEB-INF/web.xml) is the entry point for your web application. It tells the servlet container (like Tomcat) to load Spring's DispatcherServlet.

```xml```
```
<!DOCTYPE web-app PUBLIC
 "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
 "http://java.sun.com/dtd/web-app_2_3.dtd" >

<web-app>
    <display-name>My Spring MVC Application</display-name>

    <servlet>
        <servlet-name>spring</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>/WEB-INF/spring-servlet.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>

    <servlet-mapping>
        <servlet-name>spring</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

</web-app>
```

# 3. The Spring Configuration (spring-servlet.xml)
This file (src/main/webapp/WEB-INF/spring-servlet.xml) configures Spring MVC. It tells Spring where to find your controllers and how to resolve view names into actual files.

<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc
        http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <mvc:annotation-driven />

    <context:component-scan base-package="com.example.controller" />

    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/views/" />
        <property name="suffix" value=".jsp" />
    </bean>

</beans>

## 4. The Java Controller (HelloController.java)
This is your first Spring controller. It's a simple Java class that responds to web requests.
Place this file in src/main/java/com/example/controller/HelloController.java.
package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    /**
     * This method handles requests to the root URL ("/").
     * It returns the string "home", which the ViewResolver
     * will map to "/WEB-INF/views/home.jsp".
     */
    @RequestMapping("/")
    public String sayHello(Model model) {
        // Add a message to the model to be displayed in the view
        model.addAttribute("message", "Welcome to Spring MVC!");
        
        // Return the logical view name
        return "home";
    }
}

## 5. The View (home.jsp)
This is the JSP file that will be rendered. Place it in src/main/webapp/WEB-INF/views/home.jsp.
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Spring MVC App</title>
</head>
<body>
    <h2>My First Spring MVC Application</h2>
    <hr>
    <h3>${message}</h3>
</body>
</html>

## 6. How to Build and Run
 * Build the Project: Open a terminal in the project's root directory (my-spring-mvc-app/) and run:
   mvn clean package

   This will compile your code and create a my-spring-mvc-app.war file inside a new target/ directory.
 * Deploy the WAR file: You can now deploy this .war file to any J2EE servlet container, such as Apache Tomcat.
This classic, XML-based setup is the foundation of Spring MVC.
A Note on Modern Spring Development
Sir, it is important to note that while this XML configuration is the traditional "J2EE" way and excellent for learning the fundamentals, almost all modern Spring development is done using Spring Boot.
Spring Boot removes the need for all this manual XML setup (web.xml, spring-servlet.xml) and includes an embedded Tomcat server, allowing you to run your application directly as a simple Java application.
Would you like to see how to build this same application using Spring Boot, which is the current industry standard?


[Gemini Link](https://g.co/gemini/share/f44836a7efda)
