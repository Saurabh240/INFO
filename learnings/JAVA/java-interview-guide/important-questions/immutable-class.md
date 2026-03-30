### 🔹 **Characteristics of an Immutable Class**
1. **Class should be declared `final`** (so it cannot be subclassed).  
2. **Fields should be `private` and `final`** (so values cannot change once assigned).  
3. **No setters** (don’t let outsiders change your fields).  
4. **Initialize all fields via constructor**.  
5. **For mutable fields (like `Date`, `List`, or custom objects)** → make *defensive copies* on input and output so no sneaky modifications can happen outside.  

---

### 🔹 Example: Immutable Class
```java
public final class Employee {
    private final String name;
    private final int id;
    private final List<String> skills;  // mutable field!

    public Employee(String name, int id, List<String> skills) {
        this.name = name;
        this.id = id;
        // defensive copy to protect immutability
        this.skills = new ArrayList<>(skills);
    }

    public String getName() { return name; }
    public int getId() { return id; }

    public List<String> getSkills() {
        // return copy instead of original
        return new ArrayList<>(skills);
    }
}
```

✅ Once you create an `Employee`, you can’t change its `name`, `id`, or even sneakily modify its `skills` list.  

---

### 🔹 Benefits of Immutable Classes
- **Thread safety** → No synchronization needed.  
- **Cache-friendly** → Often reused safely (like wrapper classes `String`, `Integer`, etc.).  
- **Safe as map/set keys** → Since hashcode won’t change after creation.  

---

### 🔹 Quick Real-world Examples
- **`String`** in Java (your immutable BFF).  
- All wrapper classes (`Integer`, `Long`, `Boolean`, etc.).  
- `LocalDate`, `LocalTime` (new Date/Time API).