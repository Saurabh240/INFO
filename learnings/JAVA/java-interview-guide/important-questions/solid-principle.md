## 🔹 S – **Single Responsibility Principle (SRP)**
*A class should have only one reason to change.*  
- In other words: one class = one job. Don’t make your `User` class also responsible for sending emails, writing logs, and moonlighting as your dinner chef.  

**Bad:**  
```java
class UserService {
    void createUser(User user) {
        // save to DB
        // send welcome email
        // log details
    }
}
```

**Better:**  
```java
class UserRepository { void save(User user) { /* DB ops */ } }
class EmailService { void sendWelcome(User user) { /* email ops */ } }
class UserService {
    void createUser(User user) {
        userRepository.save(user);
        emailService.sendWelcome(user);
    }
}
```

---

## 🔹 O – **Open/Closed Principle (OCP)**
*Software entities should be open for extension, but closed for modification.*  
- Translation: don’t keep editing old classes every time requirements change. Instead, design them so behavior can be extended.  

**Bad:**  
```java
class PaymentProcessor {
    void process(String type) {
        if ("card".equals(type)) { /* card logic */ }
        else if ("paypal".equals(type)) { /* PayPal logic */ }
        // ... and so on forever 🤦
    }
}
```

**Better:**  
```java
interface PaymentMethod { void pay(); }
class CardPayment implements PaymentMethod { public void pay() {/* card */} }
class PayPalPayment implements PaymentMethod { public void pay() {/* PayPal */} }

class PaymentProcessor {
    void process(PaymentMethod method) { method.pay(); }
}
```

---

## 🔹 L – **Liskov Substitution Principle (LSP)**
*Objects of a superclass should be replaceable with objects of subclasses without breaking functionality.*  
- Simply: if `B` extends `A`, then `B` should behave like `A` without surprises.  

**Bad Example:** A `Square` that breaks `Rectangle` expectations.  
```java
class Rectangle {
    void setWidth(int w) {}
    void setHeight(int h) {}
}
class Square extends Rectangle {
    void setWidth(int w) { super.setHeight(w); } // breaks contract
}
```

This violates LSP because code relying on `Rectangle` may malfunction with `Square`.  

---

## 🔹 I – **Interface Segregation Principle (ISP)**
*Clients should not be forced to depend on methods they do not use.*  
- Don’t build one mega-interface; build smaller, focused ones.  

**Bad:**  
```java
interface Machine {
    void print();
    void scan();
    void fax();
}
```

If you just want a printer, why depend on `scan()` and `fax()`?  

**Better:**  
```java
interface Printer { void print(); }
interface Scanner { void scan(); }
interface Fax { void fax(); }
```
A class implements only what it needs.  

---

## 🔹 D – **Dependency Inversion Principle (DIP)**
*Depend on abstractions, not concretions.*  
- High-level modules shouldn’t directly depend on low-level modules; both depend on interfaces/abstractions.  

**Bad:**  
```java
class ReportService {
    private FileReportDao dao = new FileReportDao(); // fixed concrete class
}
```

**Better:**  
```java
interface ReportDao { void save(Report report); }
class FileReportDao implements ReportDao { /* ... */ }
class DatabaseReportDao implements ReportDao { /* ... */ }

class ReportService {
    private ReportDao dao;
    ReportService(ReportDao dao) { this.dao = dao; }
}
```

Now you can inject the `dao` at runtime (Spring loves this).  

---

## 🔹 Why SOLID Matters (aka the cheat sheet one-liner)
- **S** → Easier to test & maintain.  
- **O** → Scales better with new features.  
- **L** → Your inheritance doesn’t surprise people.  
- **I** → Smaller, focused contracts = happier code.  
- **D** → Looser coupling = easier swapping dependencies.  

---

Think of SOLID as your **architectural hygiene kit**: keeps your design smelling fresh instead of turning into a legacy swamp.  
