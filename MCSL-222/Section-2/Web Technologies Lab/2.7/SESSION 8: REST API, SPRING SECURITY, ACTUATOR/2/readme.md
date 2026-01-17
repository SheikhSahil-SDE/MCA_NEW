expand on the basic Controller we started earlier. We will implement full Create, Read, Update, and Delete functionality using standard Spring Boot annotations.
1. The Service Layer (StudentService.java)
It is best practice to keep business logic out of the Controller. We will add an update method here to handle the logic of "fetching then updating."

Java 

package com.ignou.lab.service;

import com.ignou.lab.entity.Student;
import com.ignou.lab.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional // Ensures data integrity for CUD operations
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> listAll() {
        return studentRepository.findAll();
    }

    public Optional<Student> get(int id) {
        return studentRepository.findById(id);
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public void delete(int id) {
        studentRepository.deleteById(id);
    }
}


2. The REST Controller (StudentRestController.java)
This is the core of the assignment. We map HTTP verbs (GET, POST, PUT, DELETE) to Java methods.

Java

package com.ignou.lab.controller;

import com.ignou.lab.entity.Student;
import com.ignou.lab.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // Indicates that the return value of methods should be bound to the web response body (JSON)
@RequestMapping("/api/students") // Base URL for all endpoints
public class StudentRestController {

    @Autowired
    private StudentService studentService;

    // 1. READ ALL (GET)
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.listAll();
    }

    // 2. READ ONE (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Integer id) {
        Optional<Student> student = studentService.get(id);
        
        // Return 200 OK if found, 404 Not Found if missing
        return student.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3. CREATE (POST)
    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        // @RequestBody converts JSON to Java Object
        Student savedStudent = studentService.save(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED); // Returns HTTP 201 Created
    }

    // 4. UPDATE (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Integer id, @RequestBody Student studentDetails) {
        Optional<Student> existingStudent = studentService.get(id);

        if (existingStudent.isPresent()) {
            Student student = existingStudent.get();
            // Update fields
            student.setFirstName(studentDetails.getFirstName());
            student.setLastName(studentDetails.getLastName());
            student.setEmail(studentDetails.getEmail());
            // (Update other fields as necessary)
            
            Student updatedStudent = studentService.save(student);
            return ResponseEntity.ok(updatedStudent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. DELETE (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Integer id) {
        if (studentService.get(id).isPresent()) {
            studentService.delete(id);
            return ResponseEntity.noContent().build(); // Returns HTTP 204 No Content
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}



3. How to Test Your API
You can use Postman, Talend API Tester, or curl to verify these endpoints.

1. GET (List): GET http://localhost:8080/api/students


2. POST (Create):
    * URL: http://localhost:8080/api/students
    * Body (Raw JSON):

json

{
    "firstName": "Amit",
    "lastName": "Verma",
    "email": "amit@example.com"
}

3. PUT (Update):
    * URL: http://localhost:8080/api/students/1 (Assuming ID 1 exists)

    * Body:
json

{
    "firstName": "Amit",
    "lastName": "Kumar",
    "email": "amit.k@example.com"
}

4. DELETE: DELETE http://localhost:8080/api/students/1