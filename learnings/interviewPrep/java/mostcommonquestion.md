##### Basic Concept

1.  How many types of operators are present in java?
    There are : 1. Arithmetic Operators 2. Unary Operators 3. Assignment Operator 4. Relational Operators 5. Logical Operators 6. Bitwise Operators 7. Shift Operators 8. Ternary Operator
2.  Can we add two short values to a short type?
    No we cannot. Addition of two short type numbers will be int. If we Try to assign it too short we
    will get an error.
    👉🏻 Any operation between two integer type variables which are smaller than int, will always results in
    an int.
3.  What is difference between equal to (==) and .equals() ?
    The equality operator is a binary operator which is provided by java to compare primitives and objects.
    Whereas .equals() is a method defined by Object class, which is used to compare objects.
    In order to compare objects:
    Equality operator (==) returns true only if both objects references points to the same object.
    while equals() returns true if both have same value.
4.  What is difference between Logical OR and Bitwise OR?
    While operating, The logical OR operator does not check second condition if first condition is true.
    It checks second condition only if first condition is false.
    Whereas the bitwise OR operator always checks both conditions, whether first condition is true or not.
5.  What is the difference between >>(Bitwise right shift operator) and >>>(bitwise zero fill right shift)?
    This '>>>' is also used to shift the bits towards right. But it is different from the regular '>>',
    as it does not protect the sign bit of the number, while '>>' protects the sign bit.
    👉🏻 '>>>' this always fills 0 in the sign bit.
6.  What is instanceof operator?
    This operator is used to check if an object is an instance of a Class or a subclass.
    object instanceof class/subclass/interface
    -> This operator is used for Type Checking.

##### OOPS Concept

1.  How many access modifiers are there in java? 1. public 2. private 3. protected 4. <default>
    public:
    public members can be accessed anywhere, in any class, present in any Package.
    default:
    When there is no modifier used, then the component has default accessibilities.
    -> Classes with no modifier said to be default classes. The scope for the default classes
    and default Class members is within their Package.
    private:
    Private members cannot be accessed anywhere except in the Class itself where they are declared.
    protected:
    Protected members can be accessed within the same Package and in its subclass,
    but it cannot be accessed in the other packages excepts its child classes.
    So their scope is limited to its Package and its subclasses.
2.  What do you mean by abstract classes?
    A Class with abstract specifier is a abstract class.
    If a Class is abstract it is not fully implemented and if a method is abstract, it does not have
    any implementation.
    👉🏻 abstract methods can only be defined in abstract class.
3.  Can we instantiate abstract classes?
    Answer is No, since abstract classes are not fully implemented that is why we cannot create objects
    of them.
4.  Can we declare final variable without initialization?
    Yes, we can declare final variable without initialisation.
    -> Such variables are called as Blank Final Variable.
    Blank final variable can be static or non-static..
    This is how we declare blank final variables:
    private static final int blank_final;
    We have to initialize blank final varables before any usage.
    static blank final variables can be initialised in static block..

5.  What are the core concepts of oops?
    Data Hiding : hiding the internal data, Securing the internal data
    Abstraction : way to segregate implementations from other entities (Hiding internal implementation)
    Encapsulation : Grouping of data member and member functions together
    Inheritance : Inheritance is the process of creating a new Class from the existing class(Inheriting properties from a class)
    Polymorphism : a particular method that behaves different in different contexts
6.  What is difference between Abstract Class and Interface?

    1. In abstract class, we can have both abstract and concrete methods where as in Interface,
       we can only have abstract methods, they cannot have concrete methods.
       However we can have static, default and private methods in interface.
    2. We can extend only one Abstract Class at a time where as in case of interfaces, we can implement
       any number of interfaces at a time.
    3. In abstract class ‘abstract’ keyword is used to declare a method as abstract, where as in Interface
       all the methods are abstract by default, so no keyword is required to declare methods.
    4. Abstract Class can have static, final or static final variables with any access specifier
       where as in Interface, we can have only static final variable by default.

    So we can use interfaces, when we want to create a service requirement specification for any class.
    and we can use abstract classes to provide a base for subclasses to extend and implement the abstract
    methods and use the implemented methods which are defined in abstract class.

7.  What is Diamond Problem in inheritance?
    In case of multiple inheritance, if a Class extends two classes, then there is chance of ambiguity
    problem. This ambiguity problem is called as Diamond Access problem.
8.  What is the difference between Late Binding and Early Binding?
    Binding is the association of a method call with the method definition.
    i.e, when a method is called in java, the program control binds to the memory address where that
    method is defined.

    There are two types of binding in java,
    -> Early Binding | Static Binding
    -> Late Binding | Dynamic Binding

    👉🏻 The Early Binding happens at Compile time, and late binding at Runtime.

    👉🏻 In early binding the method definition and the method call are linked during compile time.
    And that can only happen when all information needed to call a method is available at the compile
    time only.
    -> private, static, and final methods - at compile time.

    👉🏻 In early binding, the Reference Type information is used to resolve method calls, whereas in
    Late binding object information is used.

    👉🏻 As method calls are resolved before run time, Static Binding results in faster execution of a
    program while Dynamic binding results in somewhat slower execution of code.

    However the major advantage of Dynamic binding or method overriding is its flexibility, as a single
    method can handle different type of objects at run time.
    This reduces the size of base code and makes code more readable.

9.  Can we overload and override static methods?
    Yes we can overload static methods. But we cannot override them. We can define same method with same
    method signature in other Class but that will not be Method overriding.
    Because static method calls are resolved statically, i.e, at compile time.
    And in method overriding, method calls are resolved dynamically.
10. What is super keyword?
    Super keyword is used to refer to Parent Class objects.
    -> When a Derived Class and Base Class has same data members then we may use super keyword to access
    the parent classMembers. same with the methods, we use super keyword with method calls to specify
    that parent Class method will be invoked.
11. Can we overload main method?
    Apart from the fact that JVM always looks for the main method to launch the program, main method is
    just like other methods.
    We can overload main method too, But JVM never gonna call that overloaded method.
    -> To execute that method we need to call that from the main method only.

        public static void main(){
        //any implementation
        }

        public static void main(String[] args){
        	obj.main();
        }

12. Can a final method be overloaded in java?
    Yes, final method can be overloaded but cannot be overridden.

13. What is IS-A and HAS-A relationship?
    IS-A relationship implies inheritance. i.e, if class 'A' extends class 'B', then A is-a B.
    For example,

    -> There is a 'Teacher' Class which extends a 'Person'. So here a person is a teacher. and it is
    transitive, like if another class 'MathTeacher' extends 'Teacher' class, then also
    MathTeacher is-a Person.

    -> when a class 'A' has-a member reference variable of type 'B' then A has-a B. for Example,
    College has-a Teacher. This is also known as Aggregation.

14. How OOPs is different than Procedural programming?
    -> Procedural language is based on functions but object oriented language is based on objects.

    -> Procedural language exposes the data for that program, whereas Object oriented language
    encapsulates the data.

    -> Procedural language follows top down programming paradigm, but OOp language follows bottom up
    programming paradigm.

    -> Procedural language is complex in nature, so it is difficult to modify, extend and maintain.
    but OOP language is less complex in nature so it is easier to modify, extend and maintain.

    -> Procedural language provides less scope of code reuse but object oriented language provides more
    scope of code reuse.

15. What is Constructor Chaining in java?
    In java, we can call one constructor from another. This is called Constructor Chaining.
    We have this and super keywords for that.
    -> this() is used to call another constructor from a constructor.
    -> super() is used to call the constructor of the super class from the constructor of base class.
16. What is this keyword in java?
    'this' Keyword in java is a reference variable that refers to the current object.
    It holds the current state and behaviour for an object.
