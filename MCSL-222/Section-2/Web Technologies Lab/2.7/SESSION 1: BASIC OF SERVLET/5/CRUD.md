# Write a CRUD (Create/Save, Read, Edit/Update, Delete) application using servlet. Create a Database named IGNOU, create a table named Student which must capture the student information (basics, contact, enrollment details along with courses). Make necessary assumptions required

Below is a complete, working-style CRUD example for a Student management app using Java Servlets (Jakarta/Javax), JSP, and MySQL with a database named IGNOU and a Student table that captures basic info, contact, enrollment details, and enrolled courses. It follows the common DAO + Servlet Controller + JSP pattern used in standard tutorials, adapted to this domain. Assumptions: Tomcat as the servlet container, MySQL as DB, and JDBC or JNDI DataSource for connections, modeled after widely used patterns for servlet/JSP CRUD apps and Tomcat connection pooling configuration practices.​

What I’ll build
A servlet-based web app that supports Create, Read (list + view), Update, and Delete for students.​

MySQL schema IGNOU with Student and Course tables and a junction table Student_Course to store many-to-many enrollments.​

JDBC DAO layer and a Front Controller servlet mapping routes like /new, /insert, /edit, /update, /delete, /list.​

Optional Tomcat JNDI DataSource configuration for connection pooling.​

Prerequisites: Tomcat 9/10+ and MySQL 8+ set up, and the MySQL JDBC driver in Tomcat lib or your WEB-INF/lib.​

A Dynamic Web Project or Maven WAR project with JSP/Servlet enabled.​

**1) Database setup (MySQL)**<br>
Run the SQL below to create IGNOU and the necessary tables. The Student table captures basics (name, dob, gender), contact<br> 
(email, phone, address), and enrollment details (enrollment_no, program, admission_date, semester). Courses are stored in <br>
Course, and many-to-many relations in Student_Course.<br>

```sql```
```
CREATE DATABASE IF NOT EXISTS IGNOU;
USE IGNOU;

-- Student basic, contact, and enrollment details
CREATE TABLE IF NOT EXISTS Student (
  id INT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  dob DATE,
  gender ENUM('M','F','O') DEFAULT 'O',
  email VARCHAR(100) UNIQUE,
  phone VARCHAR(20),
  address TEXT,
  enrollment_no VARCHAR(30) UNIQUE NOT NULL,
  program VARCHAR(100) NOT NULL,
  admission_date DATE,
  semester INT DEFAULT 1,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Courses master
CREATE TABLE IF NOT EXISTS Course (
  id INT AUTO_INCREMENT PRIMARY KEY,
  code VARCHAR(20) UNIQUE NOT NULL,
  title VARCHAR(200) NOT NULL,
  credits INT DEFAULT 4
) ENGINE=InnoDB;

-- Student-Course enrollments
CREATE TABLE IF NOT EXISTS Student_Course (
  student_id INT NOT NULL,
  course_id INT NOT NULL,
  enrolled_on DATE DEFAULT (CURRENT_DATE),
  PRIMARY KEY (student_id, course_id),
  CONSTRAINT fk_sc_student FOREIGN KEY (student_id) REFERENCES Student(id) ON DELETE CASCADE,
  CONSTRAINT fk_sc_course FOREIGN KEY (course_id) REFERENCES Course(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Seed some courses
INSERT IGNORE INTO Course (code, title, credits) VALUES
 ('BCS-011', 'Computer Basics', 4),
 ('BCS-012', 'Mathematics for Computing', 4),
 ('ECO-01', 'Microeconomics', 4);
```

**2) Project structure (summary)**
* src
    * model
        * Student.java
        * Course.java
    * dao
        * StudentDAO.java
        * CourseDAO.java
    * web
        * StudentController.java
    * webappWEB-INF
        * web.xml
    * JSPs
        * student-form.jsp
        * student-list.jsp
        * student-view.jsp

This mirrors common servlet/JSP CRUD tutorial structures, keeping DAO separate from controller and JSPs.​

**3) Model classes**<br>
Student with a list of course IDs for enrollment.​

```JAVA```
```
package model;

import java.util.Date;
import java.util.List;

public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private java.sql.Date dob;
    private String gender; // M/F/O
    private String email;
    private String phone;
    private String address;
    private String enrollmentNo;
    private String program;
    private java.sql.Date admissionDate;
    private int semester;
    private List<Integer> courseIds;

    public Student() {}

    // Getters and setters omitted for brevity...
    // Include all fields with standard getters and setters
}
```


**Course model:​**
```
JAVA
```
```
package model;

public class Course {
    private int id;
    private String code;
    private String title;
    private int credits;

    public Course() {}
    // Getters and setters
}

```

**4) DAO layer (JDBC)**
Pattern follows typical CRUD from established tutorials; adjust package names and imports accordingly. <br> You may use either direct JDBC with URL/username/password or JNDI DataSource lookups; both are illustrated
```JAVA```
```
package dao;

import model.Student;
import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class StudentDAO {
    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    private Connection jdbcConnection;
    private DataSource dataSource; // optional JNDI

    // Constructor for direct JDBC
    public StudentDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

    // Constructor for JNDI
    public StudentDAO(String jndiName) {
        try {
            Context init = new InitialContext();
            Context env = (Context) init.lookup("java:comp/env");
            dataSource = (DataSource) env.lookup(jndiName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void connect() throws SQLException {
        if (dataSource != null) {
            jdbcConnection = dataSource.getConnection();
        } else {
            if (jdbcConnection == null || jdbcConnection.isClosed()) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                    throw new SQLException(e);
                }
                jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            }
        }
    }

    protected void disconnect() throws SQLException {
        if (dataSource != null) {
            if (jdbcConnection != null && !jdbcConnection.isClosed()) {
                jdbcConnection.close();
            }
        } else {
            if (jdbcConnection != null && !jdbcConnection.isClosed()) {
                jdbcConnection.close();
            }
        }
    }

    public boolean insertStudent(Student s) throws SQLException {
        String sql = "INSERT INTO Student (first_name, last_name, dob, gender, email, phone, address, " +
                     "enrollment_no, program, admission_date, semester) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        connect();
        try (PreparedStatement stmt = jdbcConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, s.getFirstName());
            stmt.setString(2, s.getLastName());
            stmt.setDate(3, s.getDob());
            stmt.setString(4, s.getGender());
            stmt.setString(5, s.getEmail());
            stmt.setString(6, s.getPhone());
            stmt.setString(7, s.getAddress());
            stmt.setString(8, s.getEnrollmentNo());
            stmt.setString(9, s.getProgram());
            stmt.setDate(10, s.getAdmissionDate());
            stmt.setInt(11, s.getSemester());
            boolean ok = stmt.executeUpdate() > 0;

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    s.setId(rs.getInt(1));
                }
            }
            if (ok && s.getCourseIds() != null) {
                saveEnrollments(s.getId(), s.getCourseIds());
            }
            return ok;
        } finally {
            disconnect();
        }
    }

    public List<Student> listAll() throws SQLException {
        String sql = "SELECT * FROM Student ORDER BY created_at DESC";
        connect();
        List<Student> list = new ArrayList<>();
        try (Statement st = jdbcConnection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Student s = mapStudent(rs);
                list.add(s);
            }
        } finally {
            disconnect();
        }
        return list;
    }

    public Student get(int id) throws SQLException {
        String sql = "SELECT * FROM Student WHERE id = ?";
        connect();
        try (PreparedStatement st = jdbcConnection.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Student s = mapStudent(rs);
                    s.setCourseIds(loadEnrollments(id));
                    return s;
                }
            }
        } finally {
            disconnect();
        }
        return null;
    }

    public boolean update(Student s) throws SQLException {
        String sql = "UPDATE Student SET first_name=?, last_name=?, dob=?, gender=?, email=?, phone=?, address=?, " +
                     "enrollment_no=?, program=?, admission_date=?, semester=? WHERE id=?";
        connect();
        try (PreparedStatement stmt = jdbcConnection.prepareStatement(sql)) {
            stmt.setString(1, s.getFirstName());
            stmt.setString(2, s.getLastName());
            stmt.setDate(3, s.getDob());
            stmt.setString(4, s.getGender());
            stmt.setString(5, s.getEmail());
            stmt.setString(6, s.getPhone());
            stmt.setString(7, s.getAddress());
            stmt.setString(8, s.getEnrollmentNo());
            stmt.setString(9, s.getProgram());
            stmt.setDate(10, s.getAdmissionDate());
            stmt.setInt(11, s.getSemester());
            stmt.setInt(12, s.getId());
            boolean ok = stmt.executeUpdate() > 0;

            // refresh enrollments
            deleteEnrollments(s.getId());
            if (s.getCourseIds() != null) {
                saveEnrollments(s.getId(), s.getCourseIds());
            }
            return ok;
        } finally {
            disconnect();
        }
    }

    public boolean delete(int id) throws SQLException {
        connect();
        try {
            deleteEnrollments(id);
            String sql = "DELETE FROM Student WHERE id = ?";
            try (PreparedStatement st = jdbcConnection.prepareStatement(sql)) {
                st.setInt(1, id);
                return st.executeUpdate() > 0;
            }
        } finally {
            disconnect();
        }
    }

    private Student mapStudent(ResultSet rs) throws SQLException {
        Student s = new Student();
        s.setId(rs.getInt("id"));
        s.setFirstName(rs.getString("first_name"));
        s.setLastName(rs.getString("last_name"));
        s.setDob(rs.getDate("dob"));
        s.setGender(rs.getString("gender"));
        s.setEmail(rs.getString("email"));
        s.setPhone(rs.getString("phone"));
        s.setAddress(rs.getString("address"));
        s.setEnrollmentNo(rs.getString("enrollment_no"));
        s.setProgram(rs.getString("program"));
        s.setAdmissionDate(rs.getDate("admission_date"));
        s.setSemester(rs.getInt("semester"));
        return s;
    }

    private void saveEnrollments(int studentId, List<Integer> courseIds) throws SQLException {
        String sql = "INSERT INTO Student_Course (student_id, course_id) VALUES (?, ?)";
        try (PreparedStatement st = jdbcConnection.prepareStatement(sql)) {
            for (Integer cid : courseIds) {
                st.setInt(1, studentId);
                st.setInt(2, cid);
                st.addBatch();
            }
            st.executeBatch();
        }
    }

    private void deleteEnrollments(int studentId) throws SQLException {
        try (PreparedStatement st = jdbcConnection.prepareStatement("DELETE FROM Student_Course WHERE student_id=?")) {
            st.setInt(1, studentId);
            st.executeUpdate();
        }
    }

    private List<Integer> loadEnrollments(int studentId) throws SQLException {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT course_id FROM Student_Course WHERE student_id=?";
        try (PreparedStatement st = jdbcConnection.prepareStatement(sql)) {
            st.setInt(1, studentId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    ids.add(rs.getInt(1));
                }
            }
        }
        return ids;
    }
}

```

**CourseDAO to list courses for selection in forms:**
```JAVA```
```
package dao;

import model.Course;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class CourseDAO {
    private DataSource dataSource;
    private String jdbcURL, jdbcUsername, jdbcPassword;

    public CourseDAO(String jndiName) {
        try {
            Context init = new InitialContext();
            Context env = (Context) init.lookup("java:comp/env");
            dataSource = (DataSource) env.lookup(jndiName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public CourseDAO(String url, String user, String pass) {
        this.jdbcURL = url; this.jdbcUsername = user; this.jdbcPassword = pass;
    }

    private Connection connect() throws SQLException {
        if (dataSource != null) return dataSource.getConnection();
        try { Class.forName("com.mysql.cj.jdbc.Driver"); } catch (ClassNotFoundException e) { throw new SQLException(e); }
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    public List<Course> listAll() throws SQLException {
        String sql = "SELECT id, code, title, credits FROM Course ORDER BY code";
        try (Connection c = connect();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Course> list = new ArrayList<>();
            while (rs.next()) {
                Course k = new Course();
                k.setId(rs.getInt("id"));
                k.setCode(rs.getString("code"));
                k.setTitle(rs.getString("title"));
                k.setCredits(rs.getInt("credits"));
                list.add(k);
            }
            return list;
        }
    }
}
```
**5) Controller servlet**<br>
Uses a Front Controller pattern similar to established CRUD examples, mapping actions by path. Adjust imports for<br>
javax.servlet.* or jakarta.servlet.* based on Tomcat/Servlet API version.

```JAVA```
```
package web;

import dao.StudentDAO;
import dao.CourseDAO;
import model.Student;
import model.Course;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet("/")
public class StudentController extends HttpServlet {
    private StudentDAO studentDAO;
    private CourseDAO courseDAO;

    @Override
    public void init() {
        // Choose one approach:
        // 1) Direct JDBC from context params (web.xml)
        String jdbcURL = getServletContext().getInitParameter("jdbcURL");
        String jdbcUser = getServletContext().getInitParameter("jdbcUsername");
        String jdbcPass = getServletContext().getInitParameter("jdbcPassword");
        studentDAO = new StudentDAO(jdbcURL, jdbcUser, jdbcPass);
        courseDAO = new CourseDAO(jdbcURL, jdbcUser, jdbcPass);

        // 2) Or use JNDI DataSource (uncomment if configured)
        // studentDAO = new StudentDAO("jdbc/IGNOUDB");
        // courseDAO = new CourseDAO("jdbc/IGNOUDB");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getServletPath();
        try {
            switch (action) {
                case "/new":
                    showNewForm(req, resp);
                    break;
                case "/insert":
                    insertStudent(req, resp);
                    break;
                case "/edit":
                    showEditForm(req, resp);
                    break;
                case "/update":
                    updateStudent(req, resp);
                    break;
                case "/delete":
                    deleteStudent(req, resp);
                    break;
                case "/view":
                    viewStudent(req, resp);
                    break;
                default:
                    listStudents(req, resp);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listStudents(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        List<Student> list = studentDAO.listAll();
        req.setAttribute("listStudent", list);
        RequestDispatcher rd = req.getRequestDispatcher("student-list.jsp");
        rd.forward(req, resp);
    }

    private void viewStudent(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Student s = studentDAO.get(id);
        req.setAttribute("student", s);
        RequestDispatcher rd = req.getRequestDispatcher("student-view.jsp");
        rd.forward(req, resp);
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        loadCourses(req);
        RequestDispatcher rd = req.getRequestDispatcher("student-form.jsp");
        rd.forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Student s = studentDAO.get(id);
        loadCourses(req);
        req.setAttribute("student", s);
        RequestDispatcher rd = req.getRequestDispatcher("student-form.jsp");
        rd.forward(req, resp);
    }

    private void insertStudent(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        Student s = bindStudent(req);
        studentDAO.insertStudent(s);
        resp.sendRedirect("list");
    }

    private void updateStudent(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        Student s = bindStudent(req);
        s.setId(Integer.parseInt(req.getParameter("id")));
        studentDAO.update(s);
        resp.sendRedirect("list");
    }

    private void deleteStudent(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        studentDAO.delete(id);
        resp.sendRedirect("list");
    }

    private Student bindStudent(HttpServletRequest req) {
        Student s = new Student();
        s.setFirstName(req.getParameter("firstName"));
        s.setLastName(req.getParameter("lastName"));
        s.setDob(parseDate(req.getParameter("dob")));
        s.setGender(req.getParameter("gender"));
        s.setEmail(req.getParameter("email"));
        s.setPhone(req.getParameter("phone"));
        s.setAddress(req.getParameter("address"));
        s.setEnrollmentNo(req.getParameter("enrollmentNo"));
        s.setProgram(req.getParameter("program"));
        s.setAdmissionDate(parseDate(req.getParameter("admissionDate")));
        s.setSemester(Integer.parseInt(req.getParameter("semester")));
        String[] courseIds = req.getParameterValues("courseIds");
        if (courseIds != null) {
            List<Integer> ids = new ArrayList<>();
            for (String cid : courseIds) ids.add(Integer.parseInt(cid));
            s.setCourseIds(ids);
        }
        return s;
    }

    private java.sql.Date parseDate(String dd) {
        if (dd == null || dd.isEmpty()) return null;
        return java.sql.Date.valueOf(dd); // expects yyyy-mm-dd
    }

    private void loadCourses(HttpServletRequest req) throws SQLException {
        List<Course> courses = courseDAO.listAll();
        req.setAttribute("courses", courses);
    }
}

```
**6) JSPs**
List page to display and route to actions, modeled after typical servlet/JSP CRUD examples.

**student-list.jsp**```TEXT ```
```
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*,model.Student" %>
<html>
<head><title>Students</title></head>
<body>
<h2>Student List</h2>
<p><a href="new">Add New Student</a></p>
<table border="1" cellpadding="6" cellspacing="0">
  <tr>
    <th>ID</th><th>Name</th><th>Enrollment No</th><th>Program</th><th>Semester</th><th>Email</th><th>Actions</th>
  </tr>
  <%
    List<Student> list = (List<Student>) request.getAttribute("listStudent");
    if (list != null) {
      for (Student s : list) {
  %>
  <tr>
    <td><%= s.getId() %></td>
    <td><%= s.getFirstName() %> <%= s.getLastName() %></td>
    <td><%= s.getEnrollmentNo() %></td>
    <td><%= s.getProgram() %></td>
    <td><%= s.getSemester() %></td>
    <td><%= s.getEmail() %></td>
    <td>
      <a href="view?id=<%= s.getId() %>">View</a> |
      <a href="edit?id=<%= s.getId() %>">Edit</a> |
      <a href="delete?id=<%= s.getId() %>" onclick="return confirm('Delete this student?')">Delete</a>
    </td>
  </tr>
  <%
      }
    }
  %>
</table>
</body>
</html>

```
Form page handles both create and edit, using presence of student attribute to switch modes as demonstrated in JSP CRUD tutorials.

**student-form.jsp:**```TEXT```
```
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*,model.Student,model.Course" %>
<%
  Student s = (Student) request.getAttribute("student");
  boolean editing = (s != null && s.getId() > 0);
  List<Course> courses = (List<Course>) request.getAttribute("courses");
  List<Integer> selected = (s != null && s.getCourseIds()!=null) ? s.getCourseIds() : new ArrayList<>();
%>
<html>
<head><title><%= editing ? "Edit Student" : "New Student" %></title></head>
<body>
<h2><%= editing ? "Edit Student" : "New Student" %></h2>

<form method="post" action="<%= editing ? "update?id="+s.getId() : "insert" %>">
  <label>First Name: <input name="firstName" value="<%= editing? s.getFirstName() : "" %>" required></label><br/>
  <label>Last Name: <input name="lastName" value="<%= editing? s.getLastName() : "" %>" required></label><br/>
  <label>DOB: <input type="date" name="dob" value="<%= editing && s.getDob()!=null? s.getDob().toString() : "" %>"></label><br/>
  <label>Gender:
    <select name="gender">
      <option value="M" <%= editing && "M".equals(s.getGender()) ? "selected":"" %>>M</option>
      <option value="F" <%= editing && "F".equals(s.getGender()) ? "selected":"" %>>F</option>
      <option value="O" <%= !editing || "O".equals(s.getGender()) ? "selected":"" %>>O</option>
    </select>
  </label><br/>
  <label>Email: <input type="email" name="email" value="<%= editing? s.getEmail() : "" %>"></label><br/>
  <label>Phone: <input name="phone" value="<%= editing? s.getPhone() : "" %>"></label><br/>
  <label>Address: <textarea name="address"><%= editing? s.getAddress() : "" %></textarea></label><br/>
  <label>Enrollment No: <input name="enrollmentNo" value="<%= editing? s.getEnrollmentNo() : "" %>" required></label><br/>
  <label>Program: <input name="program" value="<%= editing? s.getProgram() : "" %>" required></label><br/>
  <label>Admission Date: <input type="date" name="admissionDate" value="<%= editing && s.getAdmissionDate()!=null? s.getAdmissionDate().toString() : "" %>"></label><br/>
  <label>Semester: <input type="number" min="1" name="semester" value="<%= editing? s.getSemester() : 1 %>" required></label><br/>

  <label>Courses:</label><br/>
  <%
    if (courses != null) {
      for (Course c : courses) {
        boolean sel = selected.contains(c.getId());
  %>
    <label>
      <input type="checkbox" name="courseIds" value="<%= c.getId() %>" <%= sel ? "checked" : "" %> />
      <%= c.getCode() %> - <%= c.getTitle() %> (Credits: <%= c.getCredits() %>)
    </label><br/>
  <%
      }
    }
  %>

  <button type="submit"><%= editing ? "Update" : "Create" %></button>
  <a href="list">Cancel</a>
</form>
</body>
</html>

```

**Detail view page:**```TEXT```
```
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*,model.Student" %>
<%
  Student s = (Student) request.getAttribute("student");
%>
<html>
<head><title>Student Detail</title></head>
<body>
<h2>Student Detail</h2>
<p><strong>Name:</strong> <%= s.getFirstName() %> <%= s.getLastName() %></p>
<p><strong>Enrollment No:</strong> <%= s.getEnrollmentNo() %></p>
<p><strong>Program:</strong> <%= s.getProgram() %></p>
<p><strong>Semester:</strong> <%= s.getSemester() %></p>
<p><strong>Email:</strong> <%= s.getEmail() %></p>
<p><strong>Phone:</strong> <%= s.getPhone() %></p>
<p><strong>Address:</strong> <%= s.getAddress() %></p>
<p><strong>DOB:</strong> <%= s.getDob() %> | <strong>Gender:</strong> <%= s.getGender() %></p>
<p><strong>Admission Date:</strong> <%= s.getAdmissionDate() %></p>

<p><a href="edit?id=<%= s.getId() %>">Edit</a> | <a href="list">Back</a></p>
</body>
</html>

```
**7) web.xml and context params**
*web.xml* for servlet mapping and JDBC params, following common patterns in CRUD tutorials. Adjust to jakarta.* if needed.
```xml```
```
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         version="3.1">
    <context-param>
        <param-name>jdbcURL</param-name>
        <param-value>jdbc:mysql://localhost:3306/IGNOU?useSSL=false&serverTimezone=UTC</param-value>
    </context-param>
    <context-param>
        <param-name>jdbcUsername</param-name>
        <param-value>root</param-value>
    </context-param>
    <context-param>
        <param-name>jdbcPassword</param-name>
        <param-value>your_password</param-value>
    </context-param>

    <servlet>
        <servlet-name>StudentController</servlet-name>
        <servlet-class>web.StudentController</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>StudentController</servlet-name>
        <url-pattern>/</url-pattern>
        <url-pattern>/list</url-pattern>
        <url-pattern>/new</url-pattern>
        <url-pattern>/insert</url-pattern>
        <url-pattern>/edit</url-pattern>
        <url-pattern>/update</url-pattern>
        <url-pattern>/delete</url-pattern>
        <url-pattern>/view</url-pattern>
    </servlet-mapping>
</web-app>

```
**8) Optional: Tomcat JNDI DataSource (Pooling)**
Using Tomcat’s JDBC Connection Pool via JNDI is recommended for production; configure in Tomcat’s context.xml or your app’s META-INF/context.xml, and then switch DAO constructors to the JNDI variant shown earlier. The pattern below follows Tomcat docs and Java Code Geeks guide; set your credentials accordingly.​

**context.xml example:​** ```xml```
```
<Context>
  <Resource name="jdbc/IGNOUDB"
            auth="Container"
            type="javax.sql.DataSource"
            maxTotal="50"
            maxIdle="10"
            maxWaitMillis="10000"
            username="root"
            password="your_password"
            driverClassName="com.mysql.cj.jdbc.Driver"
            url="jdbc:mysql://localhost:3306/IGNOU?useSSL=false&serverTimezone=UTC"/>
</Context>
```

*web.xml* reference (if needed for resource-ref):```xml```

Within code, look up via *java:comp/env/jdbc/IGNOUDB*, as shown earlier, which mirrors common JNDI lookup usage in tutorials.


**9) Build and run**
  * Place MySQL Connector/J jar in Tomcat lib or your app’s WEB-INF/lib.​
  * Deploy the WAR to Tomcat, start the server, and visit http://localhost:8080/your-app-context/list.​
  * Use “Add New Student” to create entries; Edit/Delete from the list; View to see details.​

#Perplexity link: [https://www.perplexity.ai/search/write-a-crud-create-save-read-Co4ceIYiQO2Q2OkmvlhcTg#0]





