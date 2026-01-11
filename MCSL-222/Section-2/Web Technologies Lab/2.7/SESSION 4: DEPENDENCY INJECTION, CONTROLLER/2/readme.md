This demonstrates how Spring can handle complex dependencies, such as injecting a service that contains business logic (random selection) into another object. 

 
**1. The Service Interface**

We define the CourseService with the required getRandom() method.  

```java```
```
package com.ignou.lab.service;

public interface CourseService {
    String getRandom();
}
```

**2. Service Implementation with Course Array**

We define an array of three courses within the implementation. The getRandom() method uses Java's Random class to pick an index from the array.  