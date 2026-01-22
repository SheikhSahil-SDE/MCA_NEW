
I will implement View-Layer Security.

It is not enough to secure the URLs in the backend; the UI must also adapt. For example, a "Student" should not even see the "Delete" button or the "Admin Dashboard" link.

We achieve this using the Thymeleaf Spring Security Extras library.

**1. Add Dependency (```pom.xml```)**
To use security tags in your HTML, you must add this specific starter to your project.

```XML```
```
<dependency>
    <groupId>org.thymeleaf.extras</groupId>
    <artifactId>thymeleaf-extras-springsecurity6</artifactId>
</dependency>
(Note: If you are using an older Spring Boot 2.x, use springsecurity5 instead).
```

**2. Update the HTML View (```student-list.html```)**
We need to make three changes:
1. **Add the Namespace**: Tells Thymeleaf to understand sec: tags.
2. **Hide Content**: Use sec:authorize to hide sections.
3. **Show User Info**: Use sec:authentication to show who is logged in.

File: **src/main/resources/templates/student-list.html**

```HTML```
```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
<head>
    <title>Student List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">

    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>
            Welcome, <span sec:authentication="name" class="text-primary">User</span>
        </h3>
        
        <div>
            <form th:action="@{/logout}" method="POST" class="d-inline">
                <button type="submit" class="btn btn-outline-danger btn-sm">Logout</button>
            </form>
        </div>
    </div>

    <div sec:authorize="hasRole('ADMIN')" class="alert alert-warning">
        <strong>Admin Action:</strong> 
        <a href="/admin/dashboard" class="btn btn-warning btn-sm ms-2">Go to Admin Panel</a>
    </div>

    <div class="card shadow">
        <div class="card-header">Student Records</div>
        <div class="card-body">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>First Name</th>
                        <th>Email</th>
                        <th sec:authorize="hasRole('ADMIN')">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <tr th:each="student : ${students}">
                        <td th:text="${student.firstName}"></td>
                        <td th:text="${student.email}"></td>
                        
                        <td sec:authorize="hasRole('ADMIN')">
                            <a th:href="@{/api/students/delete/{id}(id=${student.id})}" 
                               class="btn btn-danger btn-sm"
                               onclick="return confirm('Are you sure?')">Delete</a>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>

    <div class="mt-3 text-muted small">
        Your Authorities: <span sec:authentication="principal.authorities"></span>
    </div>

</body>
</html>
```

**3. How It Works**
* ```xmlns:sec```: Imports the security dialect.
* ```sec:authorize="hasRole('ADMIN')"```: The element (and its children) will not be rendered in the final HTML if the user does not have ```ROLE_ADMIN```. It's not just hidden with CSS (display:none); it is physically removed from the source code sent to the browser.
* ```sec:authentication="name"```: Prints the username from the ```SecurityContext```.

**4. Verification**

1. Login as ```sahil``` (Role: USER):
  * You will see the table.
  * You will NOT see the "Admin Action" alert.
  * You will NOT see the "Delete" column or buttons.

2. Login as ```manager``` (Role: ADMIN):
  * You will see the "Admin Action" alert.
  * You will see the "Delete" buttons.

This implementation ensures the application is secure both on the Server Side (URL restrictions) and the Client Side (UI restrictions).
