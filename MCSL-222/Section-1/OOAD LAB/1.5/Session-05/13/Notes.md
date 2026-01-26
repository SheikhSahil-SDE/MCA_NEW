# Session-5
# 13. Draw Activity Diagram for Online Examination System.


**1️⃣ Activity Diagram**
🔹 Purpose
An Activity Diagram represents:
  * Workflow of activities
  * Decision making
  * Parallel processing (fork/join)

**Activities (Flow)**
1. Start
2. Login
3. Validate user
4. Display question paper
5. Attempt questions
6. Submit exam
7. Evaluate answers
8. Display result
9. End
   
**Key UML Symbols (Exam Favorite ⭐)**
* ● Initial node
* ◎ Final node
* ⬭ Activity
* ◇ Decision
* ── Control flow
* ║ Fork / Join

**🔹 Simple Explanation (Viva-ready)**

  _“Activity diagram shows the workflow of an online examination system from login to result declaration, including decision points and sequence of actions.”_


**2️⃣ Statechart Diagram**
🔹 Purpose
A Statechart Diagram shows:
* Different states of an object
* Transitions due to events
* Actions during state changes

**It answers:**
_🟢 “In which states can an object exist?”_

**🔹 States of Exam Object**
1. Created
2. Scheduled
3. Active
4. In-Progress
5. Submitted
6. Evaluated
7. Result Published


**🔹 Transitions (with events)**
* scheduleExam() → Scheduled
* startExam() → Active
* submit() → Submitted
* evaluate() → Evaluated

**🔹 Viva-ready Explanation**

_“Statechart diagram represents the life cycle of the examination object and shows how it changes state based on events.”_

**3️⃣ Implementation Hint (Objective-2)**

Even if full coding is not asked, you should explain logic like this:

```java```

```
enum ExamState {
    CREATED, SCHEDULED, ACTIVE, SUBMITTED, EVALUATED
}

```

**➡ This enum directly comes from the Statechart Diagram.**

**4️⃣ Class → Database Mapping (Objective-3)**
🔹 Example Table: ```EXAM```

**| Column     | Description                      |
| ---------- | -------------------------------- |
| exam_id    | Primary key                      |
| subject    | Exam subject                     |
| state      | Current state (CREATED, ACTIVE…) |
| start_time | Exam start                       |
| end_time   | Exam end                         |
**




















