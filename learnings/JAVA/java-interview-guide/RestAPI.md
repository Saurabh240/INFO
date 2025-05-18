### 1. What is REST?

- **Definition**: REST (Representational State Transfer) is an architectural style for designing networked applications using HTTP methods (POST, GET, PUT, DELETE) to perform CRUD operations.
- **Principles**:
  - **Uniform Interface**: Perform CRUD operations via standard HTTP methods.
  - **Easy Access**: Resources are identified by URIs.
  - **Multiple Formats**: Supports various formats like XML, JSON, and text.
- **Summary**: REST uses HTTP methods and URIs to perform CRUD operations and should support multiple data formats.

### 2. HTTP PUT vs POST and PATCH

- **POST**: Used for creating new resources.
- **PUT**: Used for updating or replacing an entire resource.
- **PATCH**: Used for partial updates to a resource.

### 3. How to Create a REST API with Spring Boot

- **Steps**:
  - Create a Spring Boot project and add dependencies (Spring Boot Starter Web, Spring Data JPA/JDBC).
  - Create a `@RestController` and map it with `@RequestMapping`.
  - Bind methods to HTTP methods using `@PostMapping`, `@GetMapping`, `@PutMapping`, etc.
  - Handle path variables with `@PathVariable`.

### 4. Create Coupon Service REST API

- **Steps**:
  - Add Spring Boot Starter Web dependency.
  - Create a `CouponRestController` with `@RestController` and `@RequestMapping("/api")`.
  - Implement methods for creating and retrieving coupons using `@PostMapping` and `@GetMapping`.
  - Configure context path and port in `application.properties`.

### 5. Create Product Service REST API

- **Steps**:
  - Add Spring Boot Starter Web dependency.
  - Create a `ProductRestController` with `@RestController` and `@RequestMapping("/api")`.
  - Implement a method to create a product using `@PostMapping`.
  - Integrate with the Coupon Service using `RestTemplate`.

### 6. Use RestTemplate

- **Steps**:
  - Define a `RestTemplate` bean in the application configuration.
  - Inject `RestTemplate` into the controller.
  - Use `RestTemplate` methods (`getForObject`, `postForObject`) to call external services.
  - Handle responses by creating DTOs.

### 7. Test End-to-End

- **Steps**:
  - Verify the coupon service endpoint.
  - Create a product via Postman, ensuring it applies discounts correctly.
  - Check database for correct product entries and applied discounts.
