# 22. Implement the following Associations using C++/Java.

<img src="https://github.com/SheikhSahil-SDE/MCA_NEW/blob/main/MCSL-222/Section-1/OOAD%20LAB/1.5/Figure%201.17.jpg" alt="Figure 1.17" width=""/>


📌 Understanding the UML Diagram
Classes:
1. Person
2. BankAccount

Association:
* One Person (1) → Many BankAccounts (*)
* A BankAccount belongs to exactly one Person

**👉 This is a simple association (not aggregation/composition).**

🔗 Association Mapping in Code
* Person maintains a list of BankAccount objects
* BankAccount is added via a method in Person

**✅ JAVA IMPLEMENTATION**

1️⃣ BankAccount.java
```
public class BankAccount {

    private String accNo;
    private double accBalance;

    public BankAccount(String accNo, double accBalance) {
        this.accNo = accNo;
        this.accBalance = accBalance;
    }

    public void credit(double amount) {
        accBalance += amount;
        System.out.println("Amount credited. Balance = " + accBalance);
    }

    public void withdraw(double amount) {
        if (amount <= accBalance) {
            accBalance -= amount;
            System.out.println("Amount withdrawn. Balance = " + accBalance);
        } else {
            System.out.println("Insufficient balance");
        }
    }

    public String getAccNo() {
        return accNo;
    }

    public double getAccBalance() {
        return accBalance;
    }
}
```
2️⃣ Person.java
```
import java.util.ArrayList;
import java.util.List;

public class Person {

    private String personID;
    private String name;

    private List<BankAccount> accounts = new ArrayList<>();

    public Person(String personID, String name) {
        this.personID = personID;
        this.name = name;
    }

    public void addAccount(BankAccount account) {
        accounts.add(account);
        System.out.println("Account added for " + name);
    }

    public List<BankAccount> getAccounts() {
        return accounts;
    }
}
```


**🔁 How UML Is Implemented**
| UML Element        | Java Code           |
| ------------------ | ------------------- |
| Person             | `Person` class      |
| BankAccount        | `BankAccount` class |
| 1 → * relationship | `List<BankAccount>` |
| has association    | `addAccount()`      |


_The Person–BankAccount association is implemented using Java classes. One Person can have multiple BankAccount objects, which is represented using a collection. The BankAccount class provides operations for credit and withdrawal. This implementation follows the multiplicity and association shown in the UML diagram._
