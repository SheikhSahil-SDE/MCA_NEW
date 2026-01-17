we will design a robust database schema to handle the Student Admission Lifecycle and configure the Hibernate/JPA layer within your Spring MVC project.
This setup moves beyond simple CRUD to managing relationships and processes (Application -> Review -> Admission).

1. Database Design (Admission Lifecycle)
We need a normalized schema to track students, the programs they apply to, and the status of their application.
SQL Script (Run this in your MySQL Workbench):

```sql```

```
CREATE DATABASE IF NOT EXISTS IGNOU_Admission;
USE IGNOU_Admission;

-- 1. Programs Available for Admission
CREATE TABLE Program (
    program_id INT AUTO_INCREMENT PRIMARY KEY,
    program_code VARCHAR(10) UNIQUE NOT NULL, -- e.g., MCA, BCA
    program_name VARCHAR(100) NOT NULL,
    duration_years INT NOT NULL,
    fees DECIMAL(10,2) NOT NULL
);

-- 2. Student Personal Profile (The Applicant)
CREATE TABLE Student (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    dob DATE NOT NULL,
    address TEXT
);

-- 3. Admission Application (The Lifecycle Entity)
CREATE TABLE Application (
    application_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    program_id INT,
    application_date DATE DEFAULT (CURRENT_DATE),
    status ENUM('SUBMITTED', 'UNDER_REVIEW', 'APPROVED', 'REJECTED') DEFAULT 'SUBMITTED',
    comments TEXT,
    FOREIGN KEY (student_id) REFERENCES Student(student_id),
    FOREIGN KEY (program_id) REFERENCES Program(program_id)
);

-- Seed Data
INSERT INTO Program (program_code, program_name, duration_years, fees) VALUES 
('MCA', 'Master of Computer Applications', 2, 48000.00),
('BCA', 'Bachelor of Computer Applications', 3, 42000.00);

```
2. Spring MVC + JPA/Hibernate Configuration
Since this is a Spring MVC project (not yet Spring Boot), we must manually configure the EntityManagerFactory, DataSource, and TransactionManager.
Create a new configuration class: JpaConfig.java

```java```

```
package com.ignou.lab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement // Enables @Transactional annotation
@EnableJpaRepositories(basePackages = "com.ignou.lab.repository") // Scans for Repositories
public class JpaConfig {

    // 1. Configure DataSource (Database Connection)
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/IGNOU_Admission?useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("your_password");
        return dataSource;
    }

    // 2. Configure EntityManagerFactory (JPA Interface)
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.ignou.lab.entity"); // Where your @Entity classes are

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());

        return em;
    }

    // 3. Configure TransactionManager (Handles Commit/Rollback)
    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    // Hibernate Specific Properties
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.put("hibernate.show_sql", "true"); // Logs SQL to console
        properties.put("hibernate.hbm2ddl.auto", "update"); // Updates schema automatically
        return properties;
    }
}
```

3. Entity Classes (The "V" in MVC + Model)
We map the database tables to Java classes using JPA annotations.
A. Program Entity

```java```

```
package com.ignou.lab.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Program")
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_id")
    private int id;

    @Column(name = "program_code")
    private String code;

    @Column(name = "program_name")
    private String name;

    private double fees;

    // One Program has many Applications
    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL)
    private List<Application> applications;

    // Getters and Setters...
}

```
B. Student Entity

```java```

```
package com.ignou.lab.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    // One Student can have multiple Applications (e.g., applied to BCA then MCA)
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Application> applications;

    // Getters and Setters...
}
```
C. Application Entity (The Lifecycle Link)
This entity links the Student to the Program and tracks the status

```java```

```
package com.ignou.lab.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Application")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private int id;

    // Many Applications belong to One Student
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    // Many Applications belong to One Program
    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status; // Enum: SUBMITTED, APPROVED, etc.

    @Temporal(TemporalType.DATE)
    @Column(name = "application_date")
    private Date applicationDate;

    // Lifecycle methods to auto-set date
    @PrePersist
    protected void onCreate() {
        applicationDate = new Date();
        if (status == null) status = ApplicationStatus.SUBMITTED;
    }

    // Getters and Setters...
}

// Enum for Status
enum ApplicationStatus {
    SUBMITTED, UNDER_REVIEW, APPROVED, REJECTED
}

```
Summary of Setup

* Normalization: We separated Student data from Program data, linking them via the Application table.
* Configuration: JpaConfig handles the "plumbing" so you don't need persistence.xml.
* Relationships: We used @OneToMany and @ManyToOne to accurately represent that one student applies to a program.
