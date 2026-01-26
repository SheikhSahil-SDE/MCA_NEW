# Session-6

# 15. State Chart Diagram for Online Fund Transfer through Netbanking Banking System.

**🎯 Objectives Covered**
✔ Draw State Chart Diagram
✔ Understand life cycle of fund transfer
✔ Relate states to code logic and database status

**📌 What this Diagram Represents**

This State Chart Diagram models the life cycle of a fund transfer transaction in net-banking.

**👉 It answers:**
_“In which states does a fund transfer transaction exist, and how does it change?”_

**🏦 Online Fund Transfer – State Chart Diagram**

<img src="https://github.com/SheikhSahil-SDE/MCA_NEW/blob/main/MCSL-222/Section-1/OOAD%20LAB/1.5/Session-06/15/Session-06_15.png" width="" alt="Logo"/>

** States of Fund Transfer Transaction**
1. Initiated
   – User selects fund transfer option
2. Authenticated
  – User login & authentication successful
3. Details Entered
  – Beneficiary, amount, and account details entered
4. OTP Generated
  – System generates OTP
5. OTP Verified
  – User enters correct OTP
6. Processing
  – Bank processes transaction
7. Completed
  – Amount transferred successfully
8. Failed (optional path)
  – OTP incorrect / insufficient balance / network issue

**🔁 State Transitions with Events**
| From State                 | Event             | To State        |
| -------------------------- | ----------------- | --------------- |
| Initiated                  | loginSuccess()    | Authenticated   |
| Authenticated              | enterDetails()    | Details Entered |
| Details Entered            | generateOTP()     | OTP Generated   |
| OTP Generated              | verifyOTP()       | OTP Verified    |
| OTP Verified               | processTransfer() | Processing      |
| Processing                 | transferSuccess() | Completed       |
| OTP Generated / Processing | errorOccured()    | Failed          |


**✍️ Written Explanation**
_The state chart diagram for online fund transfer through net-banking shows the different states of a transaction from initiation to completion. The transaction moves through authentication, OTP verification, and processing stages. State transitions occur based on events such as OTP verification and transfer success or failure. This diagram helps in understanding the dynamic behavior of a fund transfer transaction._


**💻 Implementation Hint**
State chart diagrams directly translate into transaction status handling in code:

```java```
```
enum TransferState {
    INITIATED,
    AUTHENTICATED,
    DETAILS_ENTERED,
    OTP_GENERATED,
    OTP_VERIFIED,
    PROCESSING,
    COMPLETED,
    FAILED
}

```

**🗄 Class → Database Mapping**
Table: ```FUND_TRANSFER```
| Column           | Description         |
| ---------------- | ------------------- |
| transaction_id   | Primary key         |
| from_account     | Sender account      |
| to_account       | Beneficiary account |
| amount           | Transfer amount     |
| state            | Transaction state   |
| transaction_date | Date & time         |

👉 ```state``` column is directly derived from the State Chart Diagram




















