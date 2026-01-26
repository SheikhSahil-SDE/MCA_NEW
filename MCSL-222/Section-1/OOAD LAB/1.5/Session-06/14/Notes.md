# Session-6

# 14. State Chart Diagram for Online Examination System.

**🎯 Objective Covered**

✔ Draw UML Statechart Diagram
✔ Understand object life cycle
✔ Relate diagram to implementation & database state

**📌 What is a State Chart Diagram?**
A State Chart (State Machine) Diagram shows:
* Different states of an object
* Transitions between states
* Events that cause state changes

**👉 It answers:**
_“How does an object behave over time?”_

**🎓 Online Examination System – State Chart Diagram**

<img src="" width="500" alt="Session-06_14">

**🔄 States of the Examination**
1. Created – Exam is created by admin
2. Scheduled – Exam date & time fixed
3. Active – Exam is open for students
4. In-Progress – Student is attempting exam
5. Submitted – Student submits answers
6. Evaluated – System evaluates answers
7. Result Published – Result available to students

**🔁 Transitions with Events**

| From State  | Event             | To State         |
| ----------- | ----------------- | ---------------- |
| Created     | scheduleExam()    | Scheduled        |
| Scheduled   | startExam()       | Active           |
| Active      | loginStudent()    | In-Progress      |
| In-Progress | submitExam()      | Submitted        |
| Submitted   | evaluateAnswers() | Evaluated        |
| Evaluated   | publishResult()   | Result Published |



**✍️ Written Explanation**

_The state chart diagram for the Online Examination System represents the life cycle of an examination. It starts from the creation of the exam and proceeds through scheduling, activation, submission, evaluation, and result publication. Each state change occurs due to specific events. The diagram helps in understanding the dynamic behavior of the examination object._



**💻 Implementation Hint (Objective-2)**
Statechart diagrams directly map to state handling in code:
```java```
```
enum ExamState {
    CREATED,
    SCHEDULED,
    ACTIVE,
    IN_PROGRESS,
    SUBMITTED,
    EVALUATED,
    RESULT_PUBLISHED
}

```

**🗄 Class → Database Mapping (Objective-3)**
Table: ```EXAM```

| Column    | Description        |
| --------- | ------------------ |
| exam_id   | Primary key        |
| exam_name | Name of exam       |
| exam_date | Scheduled date     |
| state     | Current exam state |
| duration  | Time limit         |


**👉 state column reflects Statechart Diagram**







