While Bootstrap handles the layout and standard components, custom CSS is often needed for specific branding, colors, or unique visual effects.

1. Create the CSS File
First, we need a dedicated place to store static resources. In a standard Maven Web App structure, create the following directory and file:
Path: src/main/webapp/resources/css/custom.css

```css```

```
/* Custom Background for the Body */
body.custom-bg {
    background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
    min-height: 100vh;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

/* Custom Card Styling (Overrides Bootstrap default) */
.custom-card {
    border: none;
    border-radius: 15px;
    box-shadow: 0 10px 20px rgba(0,0,0,0.19), 0 6px 6px rgba(0,0,0,0.23);
    transition: all 0.3s cubic-bezier(.25,.8,.25,1);
}

.custom-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 14px 28px rgba(0,0,0,0.25), 0 10px 10px rgba(0,0,0,0.22);
}

/* Custom Header Gradient */
.custom-header {
    background: linear-gradient(to right, #0062E6, #33AEFF);
    color: white;
    border-radius: 15px 15px 0 0 !important; /* !important to override Bootstrap if needed */
    padding: 20px;
}

/* Input Field Focus Effect */
.form-control:focus {
    border-color: #33AEFF;
    box-shadow: 0 0 0 0.2rem rgba(51, 174, 255, 0.25);
}

```
2. Configure Resource Mapping
Spring MVC needs to know where to find these files. Ensure your WebConfig class (or dispatcher-servlet.xml) maps the /resources/** URL pattern to your folder.
Java Configuration (WebConfig.java):
```java```
```
@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // Mapping for WebJars (Bootstrap)
    registry.addResourceHandler("/webjars/**")
            .addResourceLocations("/webjars/");
            
    // Mapping for Custom Static Resources (CSS, JS, Images)
    registry.addResourceHandler("/resources/**")
            .addResourceLocations("/resources/");
}

```
3. Link CSS in the JSP View
Update your student-form.jsp to include the new stylesheet. We use the JSTL Core tag <c:url> to ensure the context path (e.g., /your-app-name) is always correct.
student-form.jsp:

```jsp```
```
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
    <title>Student Admission</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/5.3.0/css/bootstrap.min.css" />
    
    <link rel="stylesheet" href="<c:url value='/resources/css/custom.css' />" />
</head>

<body class="custom-bg">

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card custom-card">
                
                <div class="card-header custom-header text-center">
                    <h3 class="mb-0">IGNOU Student Portal</h3>
                    <small>Admission Form 2025</small>
                </div>

                <div class="card-body p-4">
                    <form:form action="processAdmission" modelAttribute="student" method="post">
                        <div class="mb-3">
                            <label class="form-label fw-bold">First Name</label>
                            <form:input path="firstName" cssClass="form-control" placeholder="Enter full name" />
                            <form:errors path="firstName" cssClass="text-danger small" />
                        </div>
                        
                        <div class="d-grid gap-2 mt-4">
                            <input type="submit" value="Submit Application" class="btn btn-primary btn-lg rounded-pill" />
                        </div>

                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
```

Why this implementation works:

Organization: We separated the "Structure" (HTML/JSP), the "Framework Styling" (Bootstrap), and your "Custom Branding" (custom.css).

Resource Handler: The addResourceHandlers method is critical. Without it, Spring's DispatcherServlet would try to map /resources/css/custom.css to a controller and fail with a 404 error.

Specific Overrides: Classes like .custom-card allow you to keep Bootstrap's grid system while changing the visual look (adding shadows and hover effects).