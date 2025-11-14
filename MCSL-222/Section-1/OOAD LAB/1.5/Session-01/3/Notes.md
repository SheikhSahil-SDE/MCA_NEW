
 * Class Diagram (like we did before) is the blueprint (e.g., the idea of a "Customer").
 * Object Diagram (which you've asked for now) is a snapshot of the system at a specific moment, showing actual instances (e.g., the specific customer "Mr. Ajay Kumar").

**Let's design one**
**1. Scenario (The "Snapshot")**
To draw an object diagram, we must first define the specific moment in time we are capturing.

**Scenario**
 * A customer named Ajay Kumar has an active session.
 * He has two accounts with the bank "Apex Bank": a Savings Account (S-101) and a Loan Account (L-202).
 * We are capturing the moment he checks his savings balance.

**2. Identification of Objects and their State**

Based on this scenario, we have the following objects (instances) and their current data (state).
| Object Name (Instance) | Class | State (Attributes at this moment) |
|---|---|---|
| ajay | Customer | name = "Ajay Kumar" 
 customerId = "C-123" |
| s_acct_101 | SavingsAccount | accountNo = "S-101" 
 balance = 50000.00 |
| l_acct_202 | LoanAccount | accountNo = "L-202" 
 balance = -250000.00 |
| bank | Bank | bankName = "Apex Bank" |
3. Object Diagram for Online Banking System
This diagram shows the links between these specific objects. The object name is underlined in standard UML, but in text/Mermaid, we show it as objectName : ClassName.
Diagram Logic & Relationships (Mermaid)
 * The bank object has a link to the ajay (Customer) object.
 * The ajay (Customer) object is linked to both of his accounts.
 * This visualizes the "has-a" relationship (Association) in practice at a single point in time.
<!-- end list -->
graph TD
    subgraph "Online Banking System (Object Diagram Snapshot)"
        %% Define the Objects %%
        Bank["<b>bank : Bank</b><br>bankName = 'Apex Bank'"]
        Customer["<b>ajay : Customer</b><br>name = 'Ajay Kumar'<br>customerId = 'C-123'"]
        Savings["<b>s_acct_101 : SavingsAccount</b><br>accountNo = 'S-101'<br>balance = 50000.00"]
        Loan["<b>l_acct_202 : LoanAccount</b><br>accountNo = 'L-202'<br>balance = -250000.00"]

        %% Define the Links (Relationships) %%
        Bank -- "manages" --> Customer
        Customer -- "owns" --> Savings
        Customer -- "owns" --> Loan
    end

    %% Style the nodes to look like UML objects %%
    classDef object fill:#FFFFE0,stroke:#333,stroke-width:2px;
    class Bank,Customer,Savings,Loan object;

Explanation of the Links
 * bank -- "manages" --> ajay: The "Apex Bank" object holds a reference to its customer, "Ajay Kumar."
 * ajay -- "owns" --> s_acct_101: The "Ajay Kumar" object is linked to its "Savings Account" object.
 * ajay -- "owns" --> l_acct_202: The same customer object is also linked to its "Loan Account" object.
This diagram proves that the Class Diagram (where Customer has a 1..* relationship with Account) is working correctly, as one customer instance (ajay) is successfully linked to two different account instances.
Would you like me to draw the "State Diagram" for the Account class (e.g., Active, Dormant, Closed)?
