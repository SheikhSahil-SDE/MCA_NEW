# 20. Implement the Class Diagram of figure 1.18, in C++ or Java.

<img src="https://github.com/SheikhSahil-SDE/MCA_NEW/blob/main/MCSL-222/Section-1/OOAD%20LAB/1.5/Figure%201.18.jpg" alt="Figure 1.18" width=""/>

📌 Classes in the Diagram

1. Customer
2. Order
3. Product
4. OrderLine (Association Class between Order and Product)

🔗 Understanding the UML Relationships (Exam Point ⭐)

A Customer places 0.. Orders*

* Each Order belongs to exactly 1 Customer
* An Order contains 1.. Products*
* A Product can appear in 0.. Orders*
* OrderLine stores:
  * Quantity
  * UnitSalePrice(extra information about Order–Product relationship)
 
**✅ JAVA IMPLEMENTATION**

1️⃣ Customer.java
```
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String cName;
    private String cPhone;
    private String cAddress;
    private String cPin;
    private String cEmail;

    private List<Order> orders = new ArrayList<>();

    public Customer(String cName, String cPhone, String cAddress,
                    String cPin, String cEmail) {
        this.cName = cName;
        this.cPhone = cPhone;
        this.cAddress = cAddress;
        this.cPin = cPin;
        this.cEmail = cEmail;
    }

    public void placeOrder(Order order) {
        orders.add(order);
    }

    public List<Order> getOrders() {
        return orders;
    }
}
```

2️⃣ Product.java
```
public class Product {
    private String productID;
    private String pName;
    private String pManufacturer;
    private double unitPrice;
    private int unitsInStock;

    public Product(String productID, String pName,
                   String pManufacturer, double unitPrice, int unitsInStock) {
        this.productID = productID;
        this.pName = pName;
        this.pManufacturer = pManufacturer;
        this.unitPrice = unitPrice;
        this.unitsInStock = unitsInStock;
    }

    public String getProductID() {
        return productID;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
}
```

3️⃣ OrderLine.java (Association Class)
```
public class OrderLine {
    private Product product;
    private int quantity;
    private double unitSalePrice;

    public OrderLine(Product product, int quantity, double unitSalePrice) {
        this.product = product;
        this.quantity = quantity;
        this.unitSalePrice = unitSalePrice;
    }

    public double getLineTotal() {
        return quantity * unitSalePrice;
    }
}
```
4️⃣ Order.java
```
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Order {
    private LocalDate orderDate;
    private String productSoldBy;
    private double productOrderCost;

    private List<OrderLine> orderLines = new ArrayList<>();

    public Order(LocalDate orderDate, String productSoldBy) {
        this.orderDate = orderDate;
        this.productSoldBy = productSoldBy;
    }

    public void addOrderLine(OrderLine line) {
        orderLines.add(line);
        productOrderCost += line.getLineTotal();
    }

    public double getProductOrderCost() {
        return productOrderCost;
    }
}
```


**🔁 How UML Is Implemented in Code**

| UML Concept                    | Code Mapping               |
| ------------------------------ | -------------------------- |
| Customer → Order (1..*)        | `List<Order>`              |
| Order → Product (many-to-many) | `OrderLine`                |
| Association Class              | Separate `OrderLine` class |
| contains relationship          | `List<OrderLine>`          |








