# ***Spring Security***
### **Easy Questions:**

1. **What are the components of Spring Security?**
   - Key components include **Authentication Filter**, **Authentication Manager**, **Authentication Provider**, **UserDetailsService**, **PasswordEncoder**, **Authentication Success Handler**, and **Authentication Failure Handler**.

2. **What is CSRF (Cross-Site Request Forgery)?**
   - CSRF is an attack where unauthorized commands are transmitted from a user that the web application trusts. It uses the user's authenticated session (via cookies) to perform unwanted actions on their behalf.

3. **How does Spring Security prevent CSRF?**
   - Spring Security includes a CSRF filter that generates a token for each session. The token is sent with requests, and if it's missing or invalid, the request is rejected.

4. **What is CORS (Cross-Origin Resource Sharing)?**
   - CORS is a mechanism that allows restricted resources on a web page to be requested from another domain. It is enabled by using annotations like `@CrossOrigin` in Spring Security to specify allowed origins, methods, and headers.

5. **How did you secure your REST APIs?**
   - RESTful APIs can be secured using OAuth with JWT (JSON Web Tokens), API keys, or basic authentication.

6. **What is JWT (JSON Web Token)?**
   - JWT is a compact, URL-safe token format that includes claims about a user or system. It is used in OAuth for secure communication, and it contains user information and roles.

7. **How to configure JWT in Spring Security?**
   - Use a **JWT Access Token Converter** in the Authorization Server. You can sign tokens with symmetric or asymmetric keys, depending on your security needs.

8. **What is OAuth?**
   - OAuth (Open Authorization) is a standard protocol that allows third-party applications to access user data without exposing credentials. It issues tokens for authorization.

9. **What are the components of OAuth architecture?**
   - The key components are:
     - **Authorization Server**: Issues tokens.
     - **Resource Server**: Stores the protected resources.
     - **Client**: Requests access to resources.
     - **Resource Owner**: Grants permission to the client.

### **Intermediate Questions:**

10. **What are the OAuth grant types?**
    - **Authorization Code**: For user-agent-based applications.
    - **Password**: Used in trusted applications where credentials are directly exchanged for tokens.
    - **Client Credentials**: Used for server-to-server communication.
    - **Refresh Token**: Used to refresh an access token when it expires without user re-authentication.

11. **What is the OAuth workflow?**
    - The OAuth flow typically involves:
      1. Client requests access to resources.
      2. The user grants permission to the client.
      3. The Authorization Server issues an access token.
      4. The Client uses the token to access resources from the Resource Server.

12. **What is the role of `UserDetailsService` in Spring Security?**
    - `UserDetailsService` is a core interface in Spring Security that loads user-specific data for authentication. It retrieves the user’s credentials and authorities from a data source (e.g., a database or LDAP).

13. **How is a `PasswordEncoder` used in Spring Security?**
    - `PasswordEncoder` is used to encode plain-text passwords when they are saved to the database and to verify the passwords during authentication.

14. **How does Spring Security handle authentication and authorization?**
    - Authentication is handled by **Authentication Manager**, which delegates to **Authentication Providers**. Authorization is managed using roles, authorities, and access control rules in configuration classes or annotations.

15. **What is the difference between `@PreAuthorize` and `@Secured` in Spring Security?**
    - **`@PreAuthorize`**: Allows more complex security rules with SpEL expressions, supporting method-level security.  
    - **`@Secured`**: Checks for specific roles, simpler but less flexible compared to `@PreAuthorize`.

16. **How can you customize authentication success and failure handlers in Spring Security?**
    - You can define custom **AuthenticationSuccessHandler** and **AuthenticationFailureHandler** to handle login success or failure scenarios, such as redirecting users or logging activities.

### **Advanced Questions:**

17. **What is the role of an `AuthenticationProvider` in Spring Security?**
    - `AuthenticationProvider` handles the actual authentication logic by processing the credentials provided and interacting with `UserDetailsService` to load user details.

18. **How does Spring Security work with OAuth and JWT?**
    - Spring Security integrates with OAuth to handle authentication flows and issues JWTs for stateless authentication. JWTs are signed and used by Resource Servers to verify the token's integrity.

19. **What is token rotation, and how is it handled in OAuth?**
    - Token rotation refers to regularly refreshing tokens to maintain security. **Refresh Tokens** allow clients to get new access tokens without re-authenticating the user.

20. **How to secure microservices using OAuth 2.0 and JWT?**
    - Secure microservices by configuring OAuth 2.0 authorization with JWT tokens. Each service validates incoming JWT tokens by verifying their signatures, and the tokens can contain user roles for authorization.

21. **How does Spring Security handle stateless authentication for REST APIs?**
    - Stateless authentication is typically achieved using JWT tokens. Each request carries a token, which is validated by the server without maintaining session state.

22. **How do you store and use OAuth tokens in front-end applications?**
    - Tokens can be stored securely on the server side. If they must be stored on the client side (e.g., in single-page applications), use secure storage like cookies with HTTPOnly and Secure flags enabled.

23. **How to implement role-based access control (RBAC) with Spring Security?**
    - Implement RBAC by assigning roles to users and securing methods or endpoints using annotations like `@Secured`, `@PreAuthorize`, or configuring access rules in a security configuration class.

24. **How to handle CORS in Spring Security?**
    - CORS is configured in Spring Security by overriding the `addCorsMappings()` method in `WebMvcConfigurer` or by using the `@CrossOrigin` annotation on controllers.

25. **What is method-level security in Spring Security, and how is it implemented?**
    - Method-level security allows securing individual methods with annotations such as `@PreAuthorize`, `@Secured`, or `@PostAuthorize`. It is enabled by adding `@EnableGlobalMethodSecurity` to your configuration class.

26. **What is the difference between session-based and token-based authentication in Spring Security?**
    - **Session-based Authentication**: Uses server-side sessions to track authenticated users, requiring session management.  
    - **Token-based Authentication**: Uses stateless JWT tokens, which the client sends with each request, eliminating the need for server-side session management.