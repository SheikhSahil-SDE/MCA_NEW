# Session-04

# 11. Draw a Collaboration diagram for Employee Management System.

**Assumptions**
* Authenticated Session: The Administrator is already logged in and has the necessary privileges to promote staff.
* Existing Records: The Employee being promoted already exists in the system database.
* Data Validity: The input data (new salary/designation) complies with company rules (e.g., salary cannot be negative).
* Network Stability: The connection between the Application Layer (Controller) and Data Layer (Database) is stable.

**UML Collaboration Diagram (Pictorial)**
The diagram below uses numbered labeling (e.g., 1.0, 2.0) to indicate the order of operation, which is the standard notation for Collaboration Diagrams.


```
flowchart TD
    %% Define Objects (Nodes)
    Admin((:Administrator))
    UI[:EMS_Dashboard]
    Ctrl[:Promotion_Controller]
    Emp[:Employee_Entity]
    DB[(:Database)]

    %% Define Links (Edges) - The layout represents the structure
    Admin --- UI
    UI --- Ctrl
    Ctrl --- Emp
    Ctrl --- DB

    %% Define Messages (Text on Links)
    %% Note: In manual drawing, these labels go ALONG the lines.
    
    linkStyle 0 stroke:black,stroke-width:2px;
    linkStyle 1 stroke:black,stroke-width:2px;
    linkStyle 2 stroke:black,stroke-width:2px;
    linkStyle 3 stroke:black,stroke-width:2px;

    %% We simulate the message flow visually here
    Admin -- "1: requestPromotion(empID, newRole)" --> UI
    UI -- "2: validateRequest()" --> Ctrl
    
    Ctrl -- "2.1: findEmployee(empID)" --> DB
    DB -- "2.2: return(EmpData)" --> Ctrl
    
    Ctrl -- "3: setDesignation(newRole)" --> Emp
    Ctrl -- "4: updateSalary(newAmt)" --> Emp
    
    Ctrl -- "5: saveChanges()" --> DB
    
    UI -- "6: showSuccess()" --> Admin

    %% Styling to mimic UML Object Notation
    classDef object fill:#FFFFE0,stroke:#333,stroke-width:2px;
    class UI,Ctrl,Emp,DB object;
    classDef actor fill:#D3D3D3,stroke:#333,stroke-width:2px;
    class Admin actor;
```

**Message Sequence Explanation**
When you draw this in your record, ensure the arrows point in the direction of the message.

1. ```1: requestPromotion()```: The Admin submits the form.
2. ```2: validateRequest()```: The UI passes data to the Controller.
    * ```2.1: findEmployee()```: The Controller queries the Database to find the specific employee.
    * ```2.2: return(EmpData)```: The Database returns the object data.
3. ```3: setDesignation()```: The Controller updates the Employee object's designation property.
4. ```4: updateSalary()```: The Controller updates the Employee object's salary property.
5. ```5: saveChanges()```: The Controller commits the modified object back to the Database.
6. ```6: showSuccess()```: The UI confirms the action to the Admin.





