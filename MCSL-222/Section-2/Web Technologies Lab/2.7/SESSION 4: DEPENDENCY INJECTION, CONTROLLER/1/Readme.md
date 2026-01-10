Dependency Injection is a design pattern where an object's dependencies are "injected" into it by an external container (like the Spring IoC container) rather than the object creating them itself. This promotes loose coupling and makes code easier to test and maintain.  

# Implementation Example: Teacher and CourseService
In this example, a Teacher class depends on a CourseService to provide course details. We will use Constructor Injection, which is the recommended method in Spring.  
**1. Define the Dependency (Service Interface)**
First, we define an interface for the service that will be injected.

```java```
```
package com.ignou.lab.service;

public interface CourseService {
    String getCourseDetails();
}

```


**2. Create the Service Implementation**
We mark this class with @Component so Spring can manage it as a bean.  

```java```
```
package com.ignou.lab.service;

import org.springframework.stereotype.Component;

@Component
public class WebTechCourseService implements CourseService {
    @Override
    public String getCourseDetails() {
        return "MCS-220: Web Technologies - 4 Credits";
    }
}

```
**3. Create the Dependent Class (Using Injection)**
The Teacher class does not create an instance of CourseService. Instead, it "asks" for one through its constructor using the @Autowired annotation.  

```java```
```
package com.ignou.lab.component;

import com.ignou.lab.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Teacher {
    private final CourseService courseService;

    // Dependency is injected here via the constructor
    @Autowired
    public Teacher(CourseService courseService) {
        this.courseService = courseService;
    }

    public void displayAssignment() {
        System.out.println("Teacher is assigning: " + courseService.getCourseDetails());
    }
}

```

**4. Spring Configuration**
We use a configuration class to enable component scanning in your project package.  

```java```
```
package com.ignou.lab.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.ignou.lab")
public class AppConfig {
}

```
**5. Running the Application**

We retrieve the Teacher bean from the Spring container. Notice that we never manually instantiated the WebTechCourseService; Spring handled the injection automatically. 


```java```
```
package com.ignou.lab;

import com.ignou.lab.config.AppConfig;
import com.ignou.lab.component.Teacher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        // Initialize Spring container
        AnnotationConfigApplicationContext context = 
                new AnnotationConfigApplicationContext(AppConfig.class);

        // Retrieve the Teacher bean
        Teacher myTeacher = context.getBean(Teacher.class);

        // Use the bean
        myTeacher.displayAssignment();

        context.close();
    }
}

```

**Key Takeaways for the Lab Report:**

* **Decoupling**: The Teacher class is not tied to a specific implementation of CourseService. You could swap WebTechCourseService for a DataScienceService without changing any code in the Teacher class.  

* **Control**: The Spring container manages the lifecycle and wiring of these objects.  

* **Annotations**: @Component registers the class as a bean, and @Autowired triggers the injection.  