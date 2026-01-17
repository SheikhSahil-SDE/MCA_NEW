To fulfil the request for XML Configuration within a Spring Boot application, Sir, we have to override the default behavior of Spring Boot (which favors annotations).
We will use the @ImportResource annotation to load a legacy applicationContext.xml file. In this approach, we will remove the @Service annotation from our Service class and instead configure the Dependency Injection manually in XML.


1. The Main Application Class
We add @ImportResource to tell Spring Boot to look for an XML configuration file.
File: src/main/java/com/ignou/lab/AdmissionXmlApp.java

Java
package com.ignou.lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
// This line loads the XML configuration
@ImportResource("classpath:applicationContext.xml") 
public class AdmissionXmlApp {

    public static void main(String[] args) {
        SpringApplication.run(AdmissionXmlApp.class, args);
    }
}

2. The XML Configuration File
Create this file in src/main/resources. Here, we explicitly define the beans and enabling JPA repositories via XML, effectively replacing the auto-scanning for these specific components.
File: src/main/resources/applicationContext.xml

XML
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:jpa="http://www.springframework.org/schema/data/jpa"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/data/jpa
        http://www.springframework.org/schema/data/jpa/spring-jpa.xsd">

    <jpa:repositories base-package="com.ignou.lab.repository" />

    <bean id="studentService" class="com.ignou.lab.service.StudentService">
        <property name="repository" ref="studentRepository" />
    </bean>

</beans>


3. The Service Class (Clean POJO)
Notice we have removed the @Service and @Autowired annotations. The dependency is now injected via the XML configuration above.
File: src/main/java/com/ignou/lab/service/StudentService.java

Java 
package com.ignou.lab.service;

import com.ignou.lab.entity.Student;
import com.ignou.lab.repository.StudentRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

// No @Service annotation here! XML handles it.
@Transactional
public class StudentService {

    private StudentRepository repository;

    // Setter method required for XML Property Injection
    public void setRepository(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> listAll() {
        return repository.findAll();
    }

    public Student save(Student student) {
        return repository.save(student);
    }
    
    // Other CRUD methods...
}

4. The Controller (REST)
We keep @RestController here because configuring HTTP endpoint mappings in XML is deprecated and highly complex in Spring Boot. However, this Controller will use the XML-configured Service.
File: src/main/java/com/ignou/lab/controller/StudentXmlController.java


Java
package com.ignou.lab.controller;

import com.ignou.lab.entity.Student;
import com.ignou.lab.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/xml/students")
public class StudentXmlController {

    // Spring injects the bean defined in applicationContext.xml here
    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> getAll() {
        return studentService.listAll();
    }

    @PostMapping
    public Student add(@RequestBody Student student) {
        return studentService.save(student);
    }
}


