**1) What is SOAP?**

- SOAP (Simple Object Access Protocol) is a standard for web services.
- It enables communication between applications in different languages and operating systems using HTTP and XML.
- SOAP uses predefined XML elements for message exchange.

**2) Java EE Web Service Standards**

- **SOAP:** Java API for XML Web Services (JAX-WS).
- **REST:** Java API for RESTful Web Services (JAX-RS).
- These standards are implemented by frameworks like Apache CXF, Jersey, etc., which help developers write consistent code across different frameworks.

**3) Two Types of SOAP Design**

- **WSDL First (Top-Down):** Start with WSDL file, generate stubs, and then implement the service.
- **Java First (Bottom-Up):** Start with Java classes, annotate them, and generate WSDL from these classes.

**4) What is WSDL?**

- WSDL (Web Services Description Language) is an XML-based file that describes a web service's contract.
- It allows for generating client and server code for SOAP web services.

**5) WSDL Structure**

- **Abstract Section:** Defines data types, messages, and operations.
- **Physical Section:** Specifies bindings and service endpoints.
- **Binding:** Connects abstract definitions with physical service implementations.

**6) Top-Down Approach**

- Create WSDL file first.
- Use code generation tools (e.g., Apache CXF) to generate stubs.
- Implement the service by overriding methods and annotating them with JAX-WS annotations.

**7) Bottom-Up Design**

- Create Java interface and annotate it with JAX-WS annotations.
- Implement the interface in a class.
- Frameworks generate WSDL from annotated classes.

**8) SOAP Client**

- Obtain WSDL file from the service provider.
- Generate client stubs using tools (e.g., Apache CXF).
- Use the stubs to invoke the web service.

**9) MTOM (Message Transmission Optimization Mechanism)**

- Used to send attachments or files in SOAP web services.
- Enable MTOM in frameworks like Apache CXF for efficient file handling.
- Use `DataHandler` for file upload/download.

**10) SOAP vs. REST**

- **REST:** Simpler, lightweight, supports multiple data formats, stateless, and scalable. Preferred for most public APIs.
- **SOAP:** More complex, XML-based, includes built-in features for security and reliability. Still used in scenarios requiring strict contracts and message-level security.
