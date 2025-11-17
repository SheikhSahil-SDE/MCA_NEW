<!--
Developing the Diagram's Scope
I'm now zeroing in on defining the precise bounds of the diagram. The "Student Registration Process" is complex, 
and I'm actively determining which specific interactions need to be included to maintain clarity and usefulness. 
I'm focusing on the essential actors and the core message flows that illustrate the process's key aspects, 
trimming away the unnecessary details. The goal is a highly focused and useful collaboration diagram.

Outlining the Core Flow
I've just distilled the essence of the "Student Registration" scenario into a clear, stepwise flow. 
It's becoming apparent that the key interactions are between the Student, RegistrationForm, Department, Accounts,
and Database. Now, I'm focusing on refining each step of this interaction, ensuring the collaboration diagram 
captures the critical details without getting bogged down in minutiae. 
The aim is to create a dynamic visual tool to illustrate the core registration process.

Visualizing Object Interactions
I'm presently building the collaboration diagram using a logical layout that shows how objects interact. 
I've mapped the Student's initial interaction with the RegistrationUI, which then queries the Department and Accounts objects. 
I'm focusing on the message flow using a numbered system, illustrating how the RegistrationUI interacts with the Database. 
I'm using a nested decimal numbering scheme to represent the detailed interactions.

Constructing Diagram Elements
I'm presently building the diagram with specific interaction details. 
The UI interacts with the Accounts object to initiate a transaction, then saves the registration details. 
Subsequently, the UI displays a success message to the Student. 
I'm adapting the UI to include a Masters program's Bachelor's degree validation within the Department verification step. 
I'm focusing on how the UI communicates with the database to complete the registration.

Defining the Visual Structure
I'm now adapting the previous approach with Mermaid to a flowchart-like structure. 
I'm focusing on defining the nodes for each element—Student, RegForm, DeptControl, Accounts, 
and DB—and I'm starting to build out the numbered message links between them, like Student -- RegForm (Message 1: login/submit). 
I'm starting to visualize this in Mermaid. Also, I'm integrating a UML example.

\n\n
I've transitioned to using Mermaid's graph or flowchart functionality for the diagram. 
I've successfully defined nodes for the system components: Student, RegForm, DeptControl, Accounts, and DB. 
I'm now drafting the initial message flows, starting with the Student's interaction with the RegForm via "login/submit" message, 
moving to RegForm interacting with DeptControl to check eligibility, then with Accounts to pay fees, and finally with the DB to save the record. 
Additionally, I've incorporated the [Image of UML collaboration diagram example] tag to provide a visual reference.

Detailing Message Sequences
I'm presently building out the full message flow, ensuring it aligns with the MCSL222 context you requested,
Sir. I'm focusing on incorporating the specific "login/submit" interaction, the eligibility check with the Department,
payment processing, and database save operations. I am also working to organize it into a table to improve readability, 
ensuring it stays concise. I am planning on presenting this as the final output.
-->


# A Collaboration Diagram for Student Registration Process in Masters Program of a University.

**A Sequence Diagram emphasizes the time order of messages, a Collaboration Diagram emphasizes the structural organization of the objects that send and receive messages.**

**1. Objects Involved**
For a Master's program registration, we need specific validations (like checking Bachelor's degree eligibility).

```
Object Name,Class Type,Responsibility
:Student,Actor,The applicant initiating the registration.
:RegistrationForm,Boundary (UI),The interface where data is entered.
:Department,Control,"Verifies eligibility (Bachelor's marks, degree validity)."
:Accounts,Control,Handles the semester fee payment.
:Database,Entity,Stores the permanent student record.
```

**2. Collaboration Diagram Logic**

In a Collaboration Diagram, we use numbered labeling (e.g., 1.0, 1.1, 2.0) on the links between objects to show the sequence.

The Flow:
1. Student enters details.
2. System verifies eligibility with the Department.
3. Student pays the fee via Accounts.
4. System saves the confirmed registration.

**3. Collaboration Diagram for Student Registration**

Below is the representation. In a drawing tool (like StarUML), you would draw these objects as rectangles and connect them with solid lines, placing the arrows and text next to the lines.


```Code snippet```
```
flowchart TD
    %% Define Nodes (Objects)
    Student((:Student))
    Form[:RegistrationForm]
    Dept[:Department]
    Acct[:Accounts]
    DB[(:Database)]

    %% Define Links and Messages
    %% The text indicates the message direction and order

    Student -- "1: submitDetails()" --> Form
    Form -- "4: displayConfirmation()" --> Student

    Form -- "2: verifyEligibility(marks)" --> Dept
    Dept -- "2.1: return(eligible)" --> Form

    Form -- "3: payFees(amount)" --> Acct
    Acct -- "3.1: return(paymentSuccess)" --> Form

    Form -- "3.2: saveRecord(studentData)" --> DB

    %% Styling to mimic UML Object Notation
    classDef object fill:#FFFFE0,stroke:#333,stroke-width:2px;
    class Form,Dept,Acct,DB object;
    classDef actor fill:#D3D3D3,stroke:#333,stroke-width:2px;
    class Student actor;
```

**4. Detailed Message Description (For Lab Record)**

When explaining this diagram in your Viva or Lab Record, describe the messages in numerical order:

  1. 1: submitDetails(): The Student fills out the form (Name, Bachelor's Marks, Stream) and clicks submit.
  2. 2: verifyEligibility(): The Form sends the data to the Department object to ensure the student meets the Master's criteria (e.g., >50% in UG).
     * 2.1: return(eligible): The Department confirms the student is allowed to register.\
  3. 3: payFees(): The Form invokes the Accounts object to process the admission fee.
     *3.1: return(paymentSuccess): Accounts confirms the transaction is done.
  4. 3.2: saveRecord(): Once eligibility and payment are clear, the Form saves the data into the Database.
  5. 4: displayConfirmation(): The Form shows the final "Registration Successful" message to the Student.























