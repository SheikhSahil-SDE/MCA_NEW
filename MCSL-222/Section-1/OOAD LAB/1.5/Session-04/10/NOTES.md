# A Collaboration Diagram for Online Banking System.

**1. Objects Involved**

The collaboration diagram consists of three main objects interacting with each other:
* **Customer**: The actor initiating the transaction.
* **ATM Machine**: The interface and control object.
* **Account**: The entity object where the verification and transaction processing occur.

**2. Message Flow**

The interaction follows this specific numerical sequence :
* **Insert ATM Card**: Customer to ATM Machine.
* **Request PIN**: ATM Machine to Customer.
* **PIN Entered**: Customer to ATM Machine.
* **Verify PIN**: ATM Machine to Account.
* **PIN ok**: Account to ATM Machine.
* **Request option**: ATM Machine to Customer.
* **Option entered**: Customer to ATM Machine.
* **Request amount**: ATM Machine to Customer.
* **Amount entered**: Customer to ATM Machine.
* **Process transaction**: ATM Machine to Account.
* **Transaction successful**: Account to ATM Machine.
* **Dispense cash**: ATM Machine to Customer.
* **Request to take cash**: ATM Machine to Customer.
* **Take cash**: Customer to ATM Machine.
* **Request continuation**: ATM Machine to Customer.
* **Terminate**: Customer to ATM Machine.
* **Print receipt**: ATM Machine to Customer.

**3. Collaboration Diagram**

```Code snippet```

```
flowchart TD
    %% Nodes
    C((Customer))
    ATM[ATM Machine]
    Acc[Account]

    %% Links and Message Groups to mimic Collaboration Diagram style
    
    %% Customer <-> ATM Machine interactions
    C --- ATM
    linkStyle 0 stroke-width:2px,fill:none,stroke:black;

    %% ATM Machine <-> Account interactions
    ATM --- Acc
    linkStyle 1 stroke-width:2px,fill:none,stroke:black;

    %% Note: In a true Collaboration diagram, messages are written along the lines. 
    %% Since Mermaid Flowchart doesn't support distinct messages on one line easily, 
    %% the flow is described below:
    
    %% Messages for Customer <-> ATM
    %% 1. Insert ATM Card (->)
    %% 2. Request PIN (<-)
    %% 3. PIN Entered (->)
    %% 6. Request option (<-)
    %% 7. Option entered (->)
    %% 8. Request amount (<-)
    %% 9. Amount entered (->)
    %% 12. Dispense cash (<-)
    %% 13. Request to take cash (<-)
    %% 14. Take cash (->)
    %% 15. Request continuation (<-)
    %% 16. Terminate (->)
    %% 17. Print receipt (<-)

    %% Messages for ATM <-> Account
    %% 4. Verify PIN (->)
    %% 5. PIN ok (<-)
    %% 10. Process transaction (->)
    %% 11. Transaction successful (<-)
```

**Diagram Description:**

 * The Customer is linked to the ATM Machine, exchanging inputs (Card, PIN, Amount) and receiving outputs (Cash, Receipt, Requests).
 * The ATM Machine acts as the intermediary.
 * The ATM Machine is linked to the Account to perform backend verification (PIN check) and the actual financial transaction.




























