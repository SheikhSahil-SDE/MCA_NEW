To fulfill the request of retrieving data using Hibernate and printing it to the console, here is a standalone Java program. This program loads the Spring configuration you created earlier (JpaConfig), retrieves the EntityManager, and fetches a student record.
Java Application: TestHibernateRetrieval.java
Create this class in your src/main/java/com/ignou/lab package (or a test sub-package).

```java```
```
package com.ignou.lab.test;

import com.ignou.lab.config.JpaConfig;
import com.ignou.lab.entity.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class TestHibernateRetrieval {

    public static void main(String[] args) {
        System.out.println("--- Starting Hibernate Retrieval Test ---");

        // 1. Load the Spring Configuration containing Hibernate/JPA setup
        ApplicationContext context = new AnnotationConfigApplicationContext(JpaConfig.class);

        // 2. Retrieve the EntityManagerFactory bean managed by Spring
        EntityManagerFactory emf = context.getBean(EntityManagerFactory.class);

        // 3. Create an EntityManager (The interface to the persistence context)
        EntityManager em = emf.createEntityManager();

        try {
            // 4. Begin Transaction (Optional for Read, but good practice)
            em.getTransaction().begin();

            // 5. Retrieve Student with ID = 1 using Hibernate's find() method
            // This generates: SELECT * FROM Student WHERE student_id = 1
            int studentIdToFind = 1;
            Student foundStudent = em.find(Student.class, studentIdToFind);

            // 6. Print the value in the console
            if (foundStudent != null) {
                System.out.println("==========================================");
                System.out.println("DATA RETRIEVED SUCCESSFULLY");
                System.out.println("==========================================");
                System.out.println("Student ID    : " + foundStudent.getId());
                System.out.println("First Name    : " + foundStudent.getFirstName());
                System.out.println("Last Name     : " + foundStudent.getLastName());
                System.out.println("Email         : " + foundStudent.getEmail());
                System.out.println("==========================================");
            } else {
                System.out.println("No Student found with ID: " + studentIdToFind);
            }

            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 7. Always close the EntityManager
            em.close();
            // In a real app, you wouldn't close the factory until the app shuts down
            // ((AnnotationConfigApplicationContext) context).close();

        }
    }
}
```

How to Run:
Ensure your MySQL database (IGNOU_Admission) is running.
Make sure you have at least one record in the Student table (ID 1).
Run this class as a Java Application (Right-click -> Run As -> Java Application).


Console Output:

```java```

```
--- Starting Hibernate Retrieval Test ---
Hibernate: select student0_.student_id as student_1_0_0_, student0_.email as email2_0_0_, ... from Student student0_ where student0_.student_id=?
==========================================
DATA RETRIEVED SUCCESSFULLY
==========================================
Student ID    : 1
First Name    : Sheikh
Last Name     : Sahil
Email         : sahil@example.com
==========================================
```