@Controller which returned a JSP view (HTML), a @RestController returns data objects directly. Spring Boot (via the Jackson library) automatically converts these Java objects into JSON format.

1. The REST Controller (StudentRestController.java)
Create this class in your controller package. We will inject the StudentRepository we created in the previous step.

```java```
```
package com.ignou.lab.controller;

import com.ignou.lab.entity.Student;
import com.ignou.lab.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // 1. Defines this as a REST API handler (Returns JSON, not Views)
@RequestMapping("/api/students") // 2. Base URL for this API
public class StudentRestController {

    @Autowired
    private StudentRepository studentRepository;

    // Endpoint 1: Get All Students
    // URL: GET http://localhost:8080/api/students
    @GetMapping
    public List<Student> getAllStudents() {
        // Jackson automatically serializes this List<Student> to JSON
        return studentRepository.findAll();
    }

    // Endpoint 2: Get Single Student by ID
    // URL: GET http://localhost:8080/api/students/1
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable int id) {
        Optional<Student> student = studentRepository.findById(id);

        if (student.isPresent()) {
            // Return 200 OK with the JSON body
            return ResponseEntity.ok(student.get());
        } else {
            // Return 404 Not Found if ID doesn't exist
            return ResponseEntity.notFound().build();
        }
    }
    
    // Endpoint 3: Create a Student via REST (JSON Input)
    // URL: POST http://localhost:8080/api/students
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        // @RequestBody converts incoming JSON to a Java Object
        return studentRepository.save(student);
    }
}
```

2. How to Verify (The JSON Output)

Since this is a REST API, you don't need a JSP page to view the result.
Start your Application.
Open your Browser (or a tool like Postman).

Navigate to: http://localhost:8080/api/students

Expected JSON Response:
The browser will display the raw data array:

```json```
```
[
  {
    "id": 1,
    "firstName": "Sheikh",
    "lastName": "Sahil",
    "email": "sahil@example.com",
    "program": "MCA",
    "applications": []
  },
  {
    "id": 2,
    "firstName": "Rahul",
    "lastName": "Sharma",
    "email": "rahul@example.com",
    "program": "BCA",
    "applications": []
  }
]

```
Key Technical Points:

* @RestController: A convenience annotation that combines @Controller and  @ResponseBody. It eliminates the need to annotate every method with @ResponseBody.

* @RequestBody: Maps the JSON sent in the HTTP POST request body to the Student Java object.

* ResponseEntity: Allows us to control the HTTP status codes (like sending a 404 error if a student isn't found), which is a best practice in REST API design.
