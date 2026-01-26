
# Session-07

# 17. Draw Component Diagram for Order Processing Application.

🎯 Objectives Covered

✔ Draw UML Component Diagram
✔ Identify major software modules
✔ Understand dependencies between components

📌 What this Diagram Shows

A Component Diagram represents the high-level architecture of an Order Processing Application and shows how different software components interact to process an order.

**👉 It answers:**
_“Which components handle ordering, payment, inventory, and delivery?”_


**🛒 Order Processing Application – Component Diagram**

<img src="[images/logo.png](https://github.com/SheikhSahil-SDE/MCA_NEW/blob/main/MCSL-222/Section-1/OOAD%20LAB/1.5/Session-07/17/Session-07_17.png)" width="" alt="Logo"/>

🧩 Main Components

1. User Interface Component
  
  * Order placement
  * Order status view

2. Order Management Component

  * Create order
  * Update order status

3. Payment Processing Component

  * Payment validation
  * Transaction handling

4. Inventory Management Component

  * Stock checking
  * Stock update

5. Shipping Component

  * Shipment creation
  * Delivery tracking

6. Notification Component

  * Email / SMS alerts

7. Database Component
  
  * Stores orders, payments, products

🔗 Component Dependencies

* User Interface → Order Management

* Order Management → Payment Processing

* Order Management → Inventory Management

* Order Management → Shipping

* Order Management → Notification

* All components → Database

**✍️ Written Explanation**
_The component diagram for the Order Processing Application shows the modular structure of the system. The user interface component interacts with the order management component to place orders. The payment processing component handles transactions, while the inventory management component updates stock. The shipping component manages delivery, and the notification component sends updates to users. All components use the database component for data storage._


**💻 Implementation Hint**
Each component maps to separate modules/packages:
```pgsql```
```
ui/
order/
payment/
inventory/
shipping/
notification/
database/

```

This supports maintainability and scalability.

**🗄 Component → Database Mapping**
| Component            | Database Tables |
| -------------------- | --------------- |
| Order Management     | ORDER           |
| Payment Processing   | PAYMENT         |
| Inventory Management | PRODUCT, STOCK  |
| Shipping             | SHIPMENT        |
| Notification         | NOTIFICATION    |

