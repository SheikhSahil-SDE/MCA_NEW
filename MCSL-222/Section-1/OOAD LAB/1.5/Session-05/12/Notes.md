
# Session-5

# 12. Activity Diagram Online Banking System.

**📌 What is an Activity Diagram?**

An **Activity Diagram** represents the flow of activities in a system from start to end, including:
* Sequence of actions
* Decisions
* Parallel activities (if any)

**👉 It answers:**
_“How does the Online Banking System work step by step?”_

**🏦 Online Banking System – Activity Diagram**

<img src="https://github.com/SheikhSahil-SDE/MCA_NEW/blob/main/MCSL-222/Section-1/OOAD%20LAB/1.5/Session-05/Session-05_12.png" width="500" alt="Session-05_12">


**🔄 Activity Flow (Step-by-Step)**
1. Start
2. User opens banking website/app
3. User enters User ID & Password
4. System validates credentials
  * ❌ If invalid → display error → end
  * ✅ If valid → continue
5. Display banking menu

6. User selects operation:
  * View balance
  * Fund transfer
  * Pay bills
  * Mini statement

7. System processes selected transaction
8. Update account details
9. Display transaction status
10. Logout
11. End

**🧩 UML Symbols Used**

| Symbol            | Meaning      |
| ----------------- | ------------ |
| ●                 | Initial node |
| ◎                 | Final node   |
| Rounded rectangle | Activity     |
| ◇                 | Decision     |
| Arrow             | Control flow |


**✍️ Written Explanation**
_The activity diagram for the Online Banking System shows the workflow of banking operations starting from user login to logout. After successful authentication, the user can perform various banking activities such as viewing balance or transferring funds. Decision nodes are used to validate login credentials. The diagram clearly represents the sequential flow of actions and system behavior.

_

