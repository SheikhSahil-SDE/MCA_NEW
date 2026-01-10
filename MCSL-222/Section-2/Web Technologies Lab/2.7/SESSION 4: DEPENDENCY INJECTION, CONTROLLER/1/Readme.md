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