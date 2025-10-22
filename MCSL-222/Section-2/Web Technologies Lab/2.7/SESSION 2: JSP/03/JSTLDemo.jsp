// Import JSTL library in JSP Page and use its following tags: a. out b. if c. forEach d. choice, when and otherwise e. url and redirect


<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>JSTL Core Tags Demo</title>
</head>
<body bgcolor="lightyellow">
<center>
    <fieldset style="width:60%; border:2px solid green;">
        <legend><h2>JSTL Core Tag Examples</h2></legend>

        <!-- Example 1: c:out -->
        <h3>1. Using &lt;c:out&gt; Tag</h3>
        <c:set var="studentName" value="IGNOU Student"/>
        <p>Welcome, <c:out value="${studentName}"/></p>

        <hr/>

        <!-- Example 2: c:if -->
        <h3>2. Using &lt;c:if&gt; Tag</h3>
        <c:set var="marks" value="85"/>
        <c:if test="${marks >= 50}">
            <p>Result: <b>Pass</b></p>
        </c:if>
        <c:if test="${marks < 50}">
            <p>Result: <b>Fail</b></p>
        </c:if>

        <hr/>

        <!-- Example 3: c:forEach -->
        <h3>3. Using &lt;c:forEach&gt; Tag</h3>
        <c:set var="courses" value="${['Java', 'Python', 'C++', 'HTML', 'JSP']}"/>
        <p>Course List:</p>
        <ul>
            <c:forEach var="c" items="${courses}">
                <li><c:out value="${c}"/></li>
            </c:forEach>
        </ul>

        <hr/>

        <!-- Example 4: c:choose, c:when, c:otherwise -->
        <h3>4. Using &lt;c:choose&gt;, &lt;c:when&gt;, and &lt;c:otherwise&gt;</h3>
        <c:set var="grade" value="B"/>
        <c:choose>
            <c:when test="${grade == 'A'}">
                <p>Your performance: Excellent</p>
            </c:when>
            <c:when test="${grade == 'B'}">
                <p>Your performance: Good</p>
            </c:when>
            <c:otherwise>
                <p>Your performance: Needs Improvement</p>
            </c:otherwise>
        </c:choose>

        <hr/>

        <!-- Example 5: c:url and c:redirect -->
        <h3>5. Using &lt;c:url&gt; and &lt;c:redirect&gt; Tags</h3>
        <c:url var="googleURL" value="https://www.google.com"/>
        <p>Click below to visit: <a href="${googleURL}">Google</a></p>

        <!-- Uncomment to automatically redirect -->
        <%-- <c:redirect url="https://www.ignou.ac.in"/> --%>

        <hr/>
        <p style="color:gray;">*Page demonstrates JSTL core tag usage.*</p>
    </fieldset>
</center>
</body>
</html>