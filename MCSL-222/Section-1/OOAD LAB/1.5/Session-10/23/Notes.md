# Session-10 

# 23. Do mapping of the following Classes into database tables

<img src="https://github.com/SheikhSahil-SDE/MCA_NEW/blob/main/MCSL-222/Section-1/OOAD%20LAB/1.5/Figure%201.18.jpg" alt="Figure 1.18" width=""/>

📌 Classes Given in UML Diagram

1. Customer
2. Order
3. Product
4. OrderLine (Association Class)

👉 Since Order–Product is a many-to-many relationship, it is mapped using a separate table (OrderLine).

🧩 Database Mapping Rules Used

* Each class → one table
* Primary key (PK) for each table
* Foreign keys (FK) for associations
* Association class → separate table
* Many-to-many → resolved using association table

✅ Database Tables

1️⃣ CUSTOMER Table
```
CUSTOMER
---------
Customer_ID (PK)
C_Name
C_Phone
C_Address
C_Pin
C_Email
```

🔗 Relationship:

One Customer places many Orders

2️⃣ ORDER Table
```
ORDER
------
Order_ID (PK)
OrderDate
ProductSoldBy
ProductOrderCost
Customer_ID (FK)
```

🔗 Foreign Key:
* ```Customer_ID``` → CUSTOMER(Customer_ID)

3️⃣ PRODUCT Table
```
PRODUCT
--------
Product_ID (PK)
P_Name
P_Manufacturer
UnitPrice
Units_in_Stock
```

🔗 Relationship:
* One Product can appear in many Orders

4️⃣ ORDERLINE Table (Association Class)
```
ORDERLINE
----------
OrderLine_ID (PK)
Order_ID (FK)
Product_ID (FK)
Quantity
UnitSalePrice
```

🔗 Foreign Keys:
* ```Order_ID``` → ORDER(Order_ID)
* ```Product_ID``` → PRODUCT(Product_ID)

👉 This table resolves the many-to-many relationship between Order and Product.

🔁 Summary Mapping Table
| UML Class                     | Database Table |
| ----------------------------- | -------------- |
| Customer                      | CUSTOMER       |
| Order                         | ORDER          |
| Product                       | PRODUCT        |
| OrderLine (Association Class) | ORDERLINE      |

_The classes Customer, Order, Product, and OrderLine are mapped into database tables. Each class is represented by a separate table with a primary key. The many-to-many relationship between Order and Product is resolved using the association class OrderLine, which is mapped to a separate table containing foreign keys of Order and Product along with additional attributes such as quantity and unit sale price._


