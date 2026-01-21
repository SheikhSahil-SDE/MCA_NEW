
Bootstrap 5 to the Login and Registration pages we created in the previous session.

This transformation will move your application from a plain HTML look to a professional, responsive interface using Cards, Form Controls, and Alerts.

1. Styled Login Page (login-page.html)
We will use the Bootstrap Grid system to center the login form vertically and horizontally. We will also use Alerts to make error messages look prominent.

File: ```src/main/resources/templates/login-page.html```

```HTML```
```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Student Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <style>
        body {
            background: linear-gradient(135deg, #71b7e6, #9b59b6);
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 10px 20px rgba(0,0,0,0.2);
        }
        .form-control:focus {
            box-shadow: none;
            border-color: #9b59b6;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-5">
            <div class="card p-4">
                <div class="text-center mb-4">
                    <h3 class="fw-bold">Welcome Back</h3>
                    <p class="text-muted">Login to access your dashboard</p>
                </div>

                <form action="/authenticateTheUser" method="POST">
                    
                    <div th:if="${param.error}" class="alert alert-danger text-center" role="alert">
                        <i class="bi bi-exclamation-circle-fill"></i> Invalid username or password.
                    </div>

                    <div th:if="${param.logout}" class="alert alert-success text-center" role="alert">
                        You have been logged out successfully.
                    </div>

                    <div class="form-floating mb-3">
                        <input type="text" name="username" class="form-control" id="floatingInput" placeholder="Username" required>
                        <label for="floatingInput">Username</label>
                    </div>

                    <div class="form-floating mb-3">
                        <input type="password" name="password" class="form-control" id="floatingPassword" placeholder="Password" required>
                        <label for="floatingPassword">Password</label>
                    </div>

                    <div class="d-grid gap-2">
                        <button type="submit" class="btn btn-primary btn-lg">Sign In</button>
                    </div>
                </form>

                <hr class="my-4">

                <div class="text-center">
                    <span class="text-muted">Don't have an account? </span>
                    <a href="/register" class="text-decoration-none fw-bold">Register Now</a>
                </div>

            </div>
        </div>
    </div>
</div>

</body>
</html>
```

2. Styled Registration Page (```registration-page.html```)
Here, we will styling the validation errors using the text-danger class so that if a user enters a short password, the feedback is immediate and red.

File: ```src/main/resources/templates/registration-page.html```

```HTML```
```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Student Registration</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <style>
        body {
            background-color: #f8f9fa;
        }
        .register-container {
            margin-top: 50px;
            max-width: 500px;
        }
    </style>
</head>
<body>

<div class="container register-container">
    <div class="card shadow-sm">
        <div class="card-header bg-dark text-white text-center py-3">
            <h4 class="mb-0">Create Account</h4>
        </div>
        
        <div class="card-body p-4">
            <form action="/processRegistration" th:object="${userDto}" method="POST">
                
                <div class="mb-3">
                    <label class="form-label">Username</label>
                    <input type="text" th:field="*{username}" 
                           class="form-control" 
                           th:classappend="${#fields.hasErrors('username')} ? 'is-invalid' : ''" 
                           placeholder="Choose a username">
                    
                    <div class="invalid-feedback" th:if="${#fields.hasErrors('username')}" th:errors="*{username}"></div>
                </div>

                <div class="mb-3">
                    <label class="form-label">Password</label>
                    <input type="password" th:field="*{password}" 
                           class="form-control" 
                           th:classappend="${#fields.hasErrors('password')} ? 'is-invalid' : ''"
                           placeholder="Create a password">
                    
                    <div class="invalid-feedback" th:if="${#fields.hasErrors('password')}" th:errors="*{password}"></div>
                </div>

                <div class="mb-3">
                    <label class="form-label">Role</label>
                    <select th:field="*{role}" class="form-select">
                        <option value="USER">Student</option>
                        <option value="ADMIN">Administrator</option>
                    </select>
                </div>

                <div class="d-grid gap-2 mt-4">
                    <button type="submit" class="btn btn-success btn-lg">Register</button>
                </div>

            </form>
        </div>
        
        <div class="card-footer text-center py-3">
            Already registered? <a href="/showMyLoginPage" class="text-primary">Login here</a>
        </div>
    </div>
</div>

</body>
</html>
```
**Key Bootstrap Classes Applied:**

* ```form-floating```: Provides a modern label animation inside the input box (used in Login).
* ```is-invalid```: Automatically highlights the input border in red if Spring Validation fails.
* ```invalid-feedback```: Displays the error message in small red text under the field.
* ```d-grid gap-2```: Makes the buttons span the full width of the card.
* ```shadow-sm & rounded```: Adds subtle depth and rounded corners to the cards.

