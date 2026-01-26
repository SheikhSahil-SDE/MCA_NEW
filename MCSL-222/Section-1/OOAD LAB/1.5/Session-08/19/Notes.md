# Session-8 
# 19. Implement the following Class Diagram in C++/Java.

<img src="https://github.com/SheikhSahil-SDE/MCA_NEW/blob/main/MCSL-222/Section-1/OOAD%20LAB/1.5/Figure%201.15.jpg" alt="Figure 1.15" width=""/>

**📌 Classes in the UML Diagram**

From the diagram, we have:

1. University
2. School
3. Programme
4. Student
5. Faculty
6. Course

**✅ JAVA IMPLEMENTATION**
**1️⃣ Student.java**
```
public class Student {
    private int stuID;
    private String name;
    private String address;

    public Student(int stuID, String name, String address) {
        this.stuID = stuID;
        this.name = name;
        this.address = address;
    }

    public int getStuID() {
        return stuID;
    }

    public String getName() {
        return name;
    }
}
```

**2️⃣ Course.java**
```
import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseCode;
    private String courseName;

    public Course(String courseCode, String courseName) {
        this.courseCode = courseCode;
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }
}
```

**3️⃣ Programme.java**
```
import java.util.ArrayList;
import java.util.List;

public class Programme {
    private String programID;
    private String programName;
    private List<Course> courses = new ArrayList<>();

    public Programme(String programID, String programName) {
        this.programID = programID;
        this.programName = programName;
    }

    public void addCourse(Course c) {
        courses.add(c);
    }

    public List<Course> getCourses() {
        return courses;
    }
}
```

**4️⃣ Faculty.java**
```
public class Faculty {
    private String facultyID;
    private String facultyName;
    private String schoolName;

    public Faculty(String facultyID, String facultyName, String schoolName) {
        this.facultyID = facultyID;
        this.facultyName = facultyName;
        this.schoolName = schoolName;
    }

    public void teachCourse(Course c) {
        System.out.println(facultyName + " teaches " + c.getCourseName());
    }
}
```

**5️⃣ School.java**
```
import java.util.ArrayList;
import java.util.List;

public class School {
    private String name;
    private List<Faculty> faculties = new ArrayList<>();
    private List<Programme> programmes = new ArrayList<>();

    public School(String name) {
        this.name = name;
    }

    public void addFaculty(Faculty f) {
        faculties.add(f);
    }

    public void removeFaculty(Faculty f) {
        faculties.remove(f);
    }

    public void addProgramme(Programme p) {
        programmes.add(p);
    }

    public void removeProgramme(Programme p) {
        programmes.remove(p);
    }
}
```

**6️⃣ University.java**
```
import java.util.ArrayList;
import java.util.List;

public class University {
    private String name;
    private String address;
    private int phone;

    private List<School> schools = new ArrayList<>();
    private List<Student> students = new ArrayList<>();

    public University(String name, String address, int phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public void addSchool(School s) {
        schools.add(s);
    }

    public void removeSchool(School s) {
        schools.remove(s);
    }

    public void addStudent(Student s) {
        students.add(s);
    }

    public void removeStudent(Student s) {
        students.remove(s);
    }

    public List<School> getAllSchool() {
        return schools;
    }

    public List<Student> getStudent() {
        return students;
    }
}
```

**🔗 How UML Relationships Are Implemented**
| UML Relationship                   | Code Representation |
| ---------------------------------- | ------------------- |
| University — School (1..*)         | `List<School>`      |
| University — Student (aggregation) | `List<Student>`     |
| Student — Programme (enroll)       | Object reference    |
| Programme — Course (1..*)          | `List<Course>`      |
| School — Faculty                   | `List<Faculty>`     |
| Faculty — Course (teaches)         | Method call         |


**✍️ Viva / Exam Explanation**

_This program implements the Student Registration System class diagram using Java. Aggregation and association relationships are implemented using object references and collections. The University class manages schools and students, School manages faculty and programmes, Programme contains courses, and Faculty teaches courses. This implementation directly follows the UML structure shown in Figure 1.15._





























