  # A Sequence diagram for Employee Management System.
  _A Sequence Diagram illustrates the order of interactions between objects in a specific scenario, showing how operations are carried out over time._

 ** 1. Scenario**
 
For the Employee Management System, a common and critical scenario is "**Admin Adds a New Employee**".

**Scenario**: An administrator logs in, navigates to the employee management section, and successfully adds a new employee's details to the system.

**2. Lifelines (Objects)**

The objects (or "lifelines") involved in this scenario are:

* :Admin: The actor (Librarian) who initiates the process.
* :LoginModule: Handles user authentication.
* :EmployeeManagementSystem: The main controller for employee-related operations.
* :Employee: The new employee object being created.
* :Database: The persistent storage for employee records.

**3. Sequence Diagram for "Admin Adds a New Employee"**
**Diagram Logic & Flow (Mermaid)**

This diagram includes an ```alt``` fragment for login success/failure and an ```opt``` fragment for input validation.

```Code snippet```
```
sequenceDiagram
    actor Admin
    participant LoginModule
    participant EmployeeManagementSystem
    participant Employee
    participant Database

    %% 1. Admin Logs In
    Admin->>LoginModule: 1. login(username, password)
    activate LoginModule
    LoginModule-->>Admin: 2. return authToken / status
    deactivate LoginModule

    alt [Login Success]
        %% 3. Admin requests to add employee
        Admin->>EmployeeManagementSystem: 3. requestAddEmployee()
        activate EmployeeManagementSystem
        EmployeeManagementSystem-->>Admin: 4. displayEmployeeForm()

        %% 5. Admin submits employee details
        Admin->>EmployeeManagementSystem: 5. submitEmployeeDetails(details)

        %% 6. Input Validation (Optional, but good practice)
        opt [Validate Details]
            EmployeeManagementSystem->>EmployeeManagementSystem: 6. validateInput(details)
            alt [Validation Failed]
                EmployeeManagementSystem-->>Admin: 7a. displayError("Invalid details")
            else [Validation Success]
                %% 7. Create Employee Object
                EmployeeManagementSystem->>Employee: 7b. createEmployee(details)
                activate Employee
                Employee-->>EmployeeManagementSystem: 8b. return newEmployeeObject
                deactivate Employee

                %% 8. Save to Database
                EmployeeManagementSystem->>Database: 9b. saveEmployee(newEmployeeObject)
                activate Database
                Database-->>EmployeeManagementSystem: 10b. return saveStatus
                deactivate Database

                alt [Save Success]
                    EmployeeManagementSystem-->>Admin: 11a. displaySuccess("Employee Added")
                else [Save Failed]
                    EmployeeManagementSystem-->>Admin: 11b. displayError("Database error")
                end
            end
        end
        deactivate EmployeeManagementSystem
    else [Login Failed]
        LoginModule-->>Admin: 3. displayErrorMessage("Invalid Credentials")
    end
```
