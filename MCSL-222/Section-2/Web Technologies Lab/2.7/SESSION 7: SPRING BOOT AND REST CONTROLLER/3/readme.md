In Spring Boot Data JPA, you don't need to write the implementation for data access. You simply define an interface extending JpaRepository, and Spring generates the code at runtime.

We need three repositories for our three entities: Student, Program, and Application.

1. Student Repository
This handles operations for the Student table. We add a custom method to find students by email to prevent duplicate registrations.

**File: src/main/java/com/ignou/lab/repository/StudentRepository.java**

```java```
```
package com.ignou.lab.repository;

import com.ignou.lab.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    
    // Custom Derived Query: Automatically generates "SELECT * FROM Student WHERE email = ?"
    Optional<Student> findByEmail(String email);
}

```
2. Program Repository
This handles the Program table. We might want to find programs by their code (e.g., "MCA").
**File: src/main/java/com/ignou/lab/repository/ProgramRepository.java**

```java```
```
package com.ignou.lab.repository;

import com.ignou.lab.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Integer> {
    
    // Custom Derived Query: Finds program by code (e.g., "MCA")
    Program findByProgramCode(String programCode);
}
```

3. Application Repository

This is the most critical repository for the lifecycle. We need to filter applications by their status (e.g., "SUBMITTED") for the Admin Dashboard.

**File: src/main/java/com/ignou/lab/repository/ApplicationRepository.java**

```java```
```
package com.ignou.lab.repository;

import com.ignou.lab.entity.Application;
import com.ignou.lab.entity.ApplicationStatus; // Ensure this Enum is imported
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    // 1. Fetch all applications with a specific status (e.g., "SUBMITTED")
    List<Application> findByStatus(ApplicationStatus status);

    // 2. Fetch all applications made by a specific student
    List<Application> findByStudent_Id(int studentId);

    // 3. Custom JPQL Query (Optional Example): Count applications per program
    @Query("SELECT a.program.name, COUNT(a) FROM Application a GROUP BY a.program.name")
    List<Object[]> countApplicationsPerProgram();
}
```
Why this is powerful:

* No Implementation Code: You didn't write a single line of JDBC code or SQL. findByStatus works automatically because Spring analyzes the method name.

* Standard CRUD: Methods like save(), findAll(), findById(), and delete() are inherited automatically from JpaRepository.