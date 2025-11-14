# 4. Draw Use Case Diagram for Online Shopping System.

<!--Drafting the Actors
I'm currently working on identifying the actors for the Use Case Diagram. The primary actor will definitely be the Customer, naturally. I'm also including Admin as a key secondary actor who manages the system. Planning on defining their specific roles and interactions next.
Listing Use Cases Now
I've expanded on the actors, including the Payment Gateway and Shipping Carrier as secondary actors. I've now generated an initial list of use cases for both the Customer and Admin. I'm starting to think about relationships now, such as Checkout including Make Payment. The user must be logged in to be able to use Checkout. I think I will next focus on modeling these relationships accurately.
Modeling Relationships Refined
I've significantly simplified the relationships to focus on key interactions. I'm focusing on "include" and prerequisites now. For example, "Checkout" includes "Make Payment" and "Verify Shipping Address". The customer's "Add to Cart," "Checkout," and "View Order History" use cases all now require a prior "Login." Similar to how "Login" is a prerequisite for all admin use cases. Finally, applying coupon codes extends the "Checkout" process.
Defining Final Relationships
I've completed the Use Case Diagram for the Online Shopping System. I'm satisfied with simplifying the diagram to showcase core functionalities for the lab.  I've defined 'includes', 'extends', and the actor associations clearly. The key use cases such as customer login, shopping, and checkout, are shown, plus the admin use cases related to product and order management are there. I'm focusing on clean visualization now.
Diagram Refinement Underway
I've made significant revisions to the Use Case Diagram for the Online Shopping System. I've simplified the actors to be User, Customer, and Admin, focusing on key interactions. Use cases such as user login, product browsing, and checkout now have clear associations. I am now finalizing the relationships between them. For instance, Checkout still includes Make Payment and Verify Address but also the association with the actors.
Simplifying the Use Cases
I've simplified the diagram further. The actors are now User, Customer, and Admin, showing generalization. I've adjusted use cases, focusing on core actions. Customer-related actions include Register, Browse Products, Add to Cart, Manage Cart, Checkout, and View Order History, and Admin use cases focus on product and order management. Login is also associated. I'm satisfied with this as a concise lab diagram.
Simplifying for the Final Diagram
I've significantly simplified the actors to Customer and Admin for clarity, with a secondary Payment Gateway. I'm focusing on key use cases: Login, Register, Search/Browse, View Cart, Checkout, Make Payment, Manage Products, and Manage Orders. I'm finalizing actor associations and relationships now, ensuring a clear and concise representation for the diagram.
Simplifying for Final Touches
I've streamlined the actors to Customer, Admin, and a secondary Payment Gateway for a concise final diagram. My use cases are now Login, Register, Search/Browse, View Cart, Checkout, Make Payment, Manage Products, and Manage Orders. I'm focusing on defining core relationships, ensuring the diagram clearly represents the interactions and flow for the system's functions, particularly the Checkout process, and the dependency on a user login.-->



```mermaid```

```
graph TD
    subgraph "Online Shopping System"
        direction LR
        UC1(Login)
        UC2(Register)
        UC3(Browse Products)
        UC4(Manage Cart)
        UC5(Checkout)
        UC6(Make Payment)
        UC7(Manage Products)
        UC8(Manage Orders)

        %% Include Relationships %%
        UC5 -- "<<include>>" --> UC6
        UC5 -- "<<include>>" --> UC1
        UC4 -- "<<include>>" --> UC1
    end

    %% Actors %%
    Customer(Customer)
    Admin(Admin)
    PaymentGateway([Payment Gateway])

    %% Actor Relationships %%
    Customer -- UC1
    Customer -- UC2
    Customer -- UC3
    Customer -- UC4
    Customer -- UC5

    Admin -- UC1
    Admin -- UC7
    Admin -- UC8

    UC6 -- PaymentGateway

    %% Style %%
    classDef actor fill:#f9f,stroke:#333,stroke-width:2px;
    class Customer,Admin actor;
    classDef secondaryActor fill:#f0f0f0,stroke:#333,stroke-width:2px;
    class PaymentGateway secondaryActor;
```
