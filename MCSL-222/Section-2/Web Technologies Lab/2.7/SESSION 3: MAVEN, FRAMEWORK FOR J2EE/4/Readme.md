Below is the complete implementation using Annotation-based configuration, which is the modern standard for Spring development.

# 1. Define the Teacher Interface
First, we create an interface that defines the contract for any teacher implementation.

```java```
```
package com.ignou.lab.service;

public interface Teacher {
    String getTeacherName();
    String getFavoriteCourse();
}

```

# 2. Create the New Teacher Implementation
Next, we create a specific implementation. Given your focus on Web Technologies, Sir, we will create an ITTeacher class. We use the @Component annotation to tell Spring that this class is a "Bean" that it should manage.

```java```
```
package com.ignou.lab.service;

import org.springframework.stereotype.Component;

@Component("myTeacher") // This name is used to retrieve the bean from the container
public class ITTeacher implements Teacher {

    @Override
    public String getTeacherName() {
        return "Sheikh Sahil"; // Using your name from our records, Sir.
    }

    @Override
    public String getFavoriteCourse() {
        return "MCS-220: Web Technologies";
    }
}

```

# 3. Configuration Class
We need a configuration class to tell Spring where to look for these components. The @ComponentScan annotation directs Spring to scan the specified package for classes marked with @Component.

```java```
```
package com.ignou.lab.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.ignou.lab")
public class AppConfig {
    // No manual bean definitions needed here thanks to @ComponentScan
}

```

# 4. Retrieving Information (The Main Class)
Finally, we create the driver class. Instead of using the new keyword to create the teacher object, we ask the Spring Application Context to provide it for us. This is the essence of Inversion of Control.

```java```
```
package com.ignou.lab;

import com.ignou.lab.config.AppConfig;
import com.ignou.lab.service.Teacher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringIoCApp {
    public static void main(String[] args) {
        // 1. Initialize the Spring IoC Container
        AnnotationConfigApplicationContext context = 
                new AnnotationConfigApplicationContext(AppConfig.class);

        // 2. Retrieve the Teacher bean from the container (Inversion of Control)
        Teacher theTeacher = context.getBean("myTeacher", Teacher.class);

        // 3. Call methods on the bean to retrieve information
        System.out.println("Teacher Name: " + theTeacher.getTeacherName());
        System.out.println("Favorite Course: " + theTeacher.getFavoriteCourse());

        // 4. Close the context
        context.close();
    }
}

```

# How this fulfills the task requirements:

* **Teacher Interface**: We defined a clear contract with getFavoriteCourse().  
New Implementation: We provided ITTeacher as a new concrete implementation.  
* **Inversion of Control**: The object creation is handled by Spring's AnnotationConfigApplicationContext rather than the Main class.
* **Information Retrieval**: We successfully retrieved the teacher's name and favorite course details from the Spring-managed bean.  