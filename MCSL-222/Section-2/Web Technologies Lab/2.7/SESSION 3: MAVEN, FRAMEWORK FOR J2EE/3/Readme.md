This approach replaces manual JDBC code with Object-Relational Mapping (ORM), allowing you to interact with the IGNOU database using Java objects rather than SQL strings.

# 1. Maven Project Structure

Your project directory should follow the standard Maven layout to ensure all dependencies are managed correctly:

* **src/main/java**: Contains your Entity classes and DAO logic.
* **src/main/resources**: Contains META-INF/persistence.xml, which defines your database connection.
* **pom.xml**: The project object model file for managing dependencies.

# 2. Configure pom.xml

Add the following dependencies to your pom.xml to include the Hibernate core, JPA API, and the MySQL connector:

```xml```
```
<dependencies>
    <dependency>
        <groupId>javax.persistence</groupId>
        <artifactId>javax.persistence-api</artifactId>
        <version>2.2</version>
    </dependency>
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>5.6.15.Final</version>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
    </dependency>
</dependencies>

```

# 3. JPA Configuration (persistence.xml)
Create a folder named META-INF under src/main/resources and add persistence.xml. This file tells the application how to connect to your IGNOU database:

```xml```
```
<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="http://xmlns.jcp.org/xml/ns/persistence" version="2.2">
    <persistence-unit name="IGNOU_PU">
        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
        <properties>
            <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/IGNOU"/>
            <property name="javax.persistence.jdbc.user" value="root"/>
            <property name="javax.persistence.jdbc.password" value="your_password"/>
            <property name="javax.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>
            
            <property name="hibernate.dialect" value="org.hibernate.dialect.MySQL8Dialect"/>
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.hbm2ddl.auto" value="update"/>
        </properties>
    </persistence-unit>
</persistence>
```

# 4. Entity Classes with JPA Annotations
Instead of manual mapping, we use annotations to define the table structure directly in the Java class:

**Student Entity:**

```java```
```
package model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "enrollment_no", unique = true)
    private String enrollmentNo;

    private String program;
    private int semester;

    // Getters and Setters...
}
```

# 5. CRUD Operations with EntityManager
In JPA, the EntityManager is used to perform database operations. This replaces the complex PreparedStatement logic from your previous JDBC example:

```java```
```
package dao;

import model.Student;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class StudentJPA {
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("IGNOU_PU");

    public void createStudent(Student student) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(student); // Saves the student object to the database
        em.getTransaction().commit();
        em.close();
    }

    public Student findStudent(int id) {
        EntityManager em = factory.createEntityManager();
        Student s = em.find(Student.class, id);
        em.close();
        return s;
    }

    public void deleteStudent(int id) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Student s = em.find(Student.class, id);
        if (s != null) em.remove(s);
        em.getTransaction().commit();
        em.close();
    }
}

```

By using this Maven-based JPA setup,I have successfully automated the data handling process as required by our lab syllabus.
