# Session-7

# 18. Diagram Deployment Diagram for Online Student Admission System.

🎯 Objectives Covered

✔ Draw UML Deployment Diagram
✔ Understand physical deployment of software
✔ Relate software components to hardware nodes


📌 What is a Deployment Diagram?

A Deployment Diagram shows:

* Hardware nodes (servers, client machines)
* Software artifacts deployed on those nodes
* Communication links between nodes

👉 It answers:
“Where does the software run physically?”

**🎓 Online Student Admission System – Deployment Diagram**
<img src="https://github.com/SheikhSahil-SDE/MCA_NEW/blob/main/MCSL-222/Section-1/OOAD%20LAB/1.5/Session-07/18/Session-07_18.png" width="" alt="Logo"/>

🖥 Nodes Identified

1️⃣ Client Node
* Student computer / mobile
* Runs:
  * Web browser
* Used for:
  * Registration
  * Form submission
  * Status checking
 
2️⃣ Web Server
* Hosts:
  * Admission portal (UI)
* Handles:
  * HTTP requests
  * Form validation

3️⃣ Application Server
* Hosts business logic:
  * Admission processing
  * Eligibility verification
  * Merit list generation

4️⃣ Database Server
* Stores:
  * Student details
  * Application forms
  * Admission status
  * Payment records
 
**🔗 Communication Links**
* Client ↔ Web Server (HTTP/HTTPS)
* Web Server ↔ Application Server
* Application Server ↔ Database Server


**✍️ Written Explanation**
_The deployment diagram for the Online Student Admission System shows the physical architecture of the system. The client node accesses the system using a web browser. The web server hosts the admission portal, while the application server processes admission logic. The database server stores all student and admission-related data. Communication links represent interaction between nodes._


**💻 Implementation Hint**

Deployment diagram helps in:
  * Deciding server responsibilities
  * Separating UI, logic, and data
  * Planning scalability and security

**🗄 Deployment Mapping**
| Node               | Data Stored                   |
| ------------------ | ----------------------------- |
| Database Server    | STUDENT, APPLICATION, PAYMENT |
| Application Server | Admission logic               |
| Web Server         | UI files                      |





























