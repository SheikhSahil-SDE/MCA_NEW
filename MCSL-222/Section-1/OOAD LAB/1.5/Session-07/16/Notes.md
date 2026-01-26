# Session-7 
# 16. Draw Component Diagram for Online Examination System.

🎯 Objectives Covered

✔ Draw UML Component Diagram
✔ Understand system modular structure
✔ Relate components to implementation & deployment

📌 What is a Component Diagram?
A Component Diagram shows:
* High-level software components
* Dependencies between components
* How the system is physically structured

👉 It answers:
“Which software modules make up the system and how do they interact?”

🎓 Online Examination System – Component Diagram

<img src="images/logo.png" width="350" alt="Logo"/>



🧩 Major Components Identified

1. User Interface Component
  * Login page
  * Exam dashboard
  * Result view
2. Authentication Component
  * User validation
  * Session management
3. Exam Management Component
  * Question paper generation
  * Timer handling
  * Exam scheduling
4. Evaluation Component
  * Answer checking
  * Score calculation
5. Result Component
  * Result generation
  * Result publication
6. Database Component
  * Stores users, exams, questions, results



🔗 Component Dependencies

* User Interface → Authentication
* User Interface → Exam Management
* Exam Management → Evaluation
* Evaluation → Result
* All components → Database


**✍️ Written Explanation**

_The component diagram for the Online Examination System represents the physical organization of software components. The user interface component interacts with authentication and exam management components. The evaluation component processes submitted answers, and the result component publishes the results. All components depend on the database component for data storage. This diagram helps in understanding the modular structure of the system._


**💻 Implementation Hint**

Each component can be mapped to packages or modules in code:

```sql```
```
ui/
auth/
exam/
evaluation/
result/
database/

```

👉 Component diagram helps during coding and deployment planning.


**🗄 Component → Database Mapping**

| Component       | Database Tables |
| --------------- | --------------- |
| Authentication  | USER            |
| Exam Management | EXAM, QUESTION  |
| Evaluation      | ANSWER, SCORE   |
| Result          | RESULT          |



















