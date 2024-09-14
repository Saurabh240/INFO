
//Design Principles

What are Software Design Principles?
    Software design principles represent a set of guidelines that helps us to avoid having a bad design.
    According to Robert Martin there are 3 important characteristics of a bad design that should be avoided:
      - Rigidity
      - Fragility
      - Immobility


What are SOLID principles?
    S -> Single Responsibility Principle
    O -> Open/closed Principle
    L -> Liskov substitution principle
    I -> Interface Segregation Principle
    D -> Dependency Inversion Principle.

#### S-> Single Responsibility Principle

-> Every Software components should have one and only one responsibility

Cohesion

-> Cohesion is the degree to which the various parts of a software component are related
-> Aim for High Cohesion

Coupling

-> Coupling is defined as the level of inter dependency between various software components
-> Loose Coupling helps attain better adherence to the Single Responsibility Principle
-> Aim for Loose Coupling

#### O-> Open Closed Principle

-> Software Components should be closed for modification, but open for extension

Closed for Modification

-> New features getting added to the software component, should NOT have to modify the existing code

Open for Extension

-> A software component should be extendable to add a new feature or to add a new behaviour to it

-> SOLID principles are all interwined and interdependent

-> SOLID principles are most effective when they are combined together

-> It is important to get a wholesome view of all the SOLID principles

#### L-> Liskov Substitution Principle

-> Objects should be replaceable with their subtypes without affecting the correctness of the program

1-> Break the hierarchy if it fails the substitution test
2-> Tell , Do not ask

#### I-> Interface Segregation Principle

-> No client should be forced to depend on the methods it does not use

-> Techniques to Identify Violation of ISP

1. Fat Interfaces.
2. Interfaces with Low Cohesion.
3. Empty Method Implementations.

-> SOLID Principles complement each other and work together in unison to achieve the common purpose of well designed software

#### D-> Dependency Inversion Principle

-> High-level modules should not depend on low-level modules. Both should depend on abstractions.

-> Abstractions should not depend on details, Details should depend on abstractions.
