### Java 9

1. Private Methods in Interfaces

   - Definition: Starting from Java 9, interfaces can have private methods.
   - Purpose: To reuse code across default and static methods within the interface.
   - Types:
     - Regular Private Methods: Used in default methods.
     - Static Private Methods: Used in static methods.

2. Immutable Collections

   - Creation:
     - Pre-Java 9: Use `Collections.unmodifiableList()` etc.
     - Java 9: New `List.of()`, `Set.of()`, and `Map.of()` methods to create immutable collections.

3. Stream API Updates

   - New Methods:
     - `takeWhile(Predicate<T> predicate)`: Takes elements while the predicate is true.
     - `dropWhile(Predicate<T> predicate)`: Drops elements while the predicate is true.
     - `ofNullable(T t)`: Creates a stream containing the given value or an empty stream if the value is null.

4. Enhanced Try-With-Resources
   - Improvement: Resources can now be declared outside the try block and used in the try-with-resources statement.

### Java 10

1. Release Cadence

   - Change: Introduced a six-month release cycle with smaller, incremental updates.

2. `var` for Local Variables

   - Definition: `var` allows for local variable type inference.
   - Restrictions:
     - Not a Keyword: To avoid conflicts with existing code (e.g., `int var`).
     - Cannot be Used:
       - For lambda parameters.
       - For class fields.

3. Collectors API Updates
   - New Methods:
     - `Collectors.toUnmodifiableList()`
     - `Collectors.toUnmodifiableSet()`
     - `Collectors.toUnmodifiableMap()`

### Java 11

1. Updates
   - String API: New methods added:
     - `isBlank()`: Checks if a string is blank.
     - `lines()`: Returns a stream of lines in the string.
     - `strip()`, `stripLeading()`, `stripTrailing()`: Trims whitespace, including Unicode spaces.
     - `repeat(int count)`: Repeats the string a given number of times.
   - File API: New methods:
     - `Files.writeString(Path path, CharSequence content)`: Writes a string to a file.
     - `Files.readString(Path path)`: Reads a string from a file.
   - Optional API: Added `isEmpty()` method.

### Java 12

1. String API Updates

   - New Methods:
     - `indent(int n)`: Adds or removes indentation based on the integer value.
     - `transform(Function<String, R> transformer)`: Applies a function to a string and returns the result.

2. Compact Number Format

   - Definition: `NumberFormat.getCompactNumberInstance()` formats numbers using compact notation (e.g., "1K" for 1000).

3. More Unicode Characters

   - Support: Added support for new Unicode characters, including chess symbols.

4. Collectors API Updates
   - New Method: `Collectors.teeing()`
     - Function: Merges the results of two downstream collectors using a merger function.

### Java 15

1. Sealed Classes

   - Definition: Sealed classes and interfaces restrict which classes can implement or extend them using the `sealed` keyword and `permits` clause.

2. Record Enhancements
   - Improvements:
     - Support for Sealed Types: Records can implement sealed interfaces or extend sealed classes.
     - Custom Annotations: Use custom annotations on fields in a record.
