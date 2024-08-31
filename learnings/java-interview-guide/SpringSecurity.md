**1) What are the Components of Spring Security?**

- **What is the flow and key components of Spring Security?**
  - **Authentication Filter:** Intercepts requests to check if they are authenticated.
  - **Authentication Manager:** Uses an Authentication Provider to handle authentication logic.
  - **Authentication Provider:** Utilizes a UserDetailsService and PasswordEncoder.
  - **UserDetailsService:** Fetches user details from a database or LDAP.
  - **PasswordEncoder:** Encodes passwords.
  - **Authentication Success Handler:** Stores authentication details in a SecurityContext if successful.
  - **Authentication Failure Handler:** Handles failed authentication attempts.

**2) How did you secure your REST APIs?**

- **What methods did you use to secure your RESTful microservices?**
  - Used OAuth with JWT (JSON Web Tokens) for securing microservices.

**3) What is OAuth?**

- **What is OAuth?**
  - OAuth (Open Authorization) allows an application to access user data from another application without directly sharing credentials.

**4) What are the Key Components in OAuth?**

- **What are the key components in OAuth architecture?**
  - **Authorization Server:** Authenticates users and issues tokens.
  - **Resource Server:** Stores the secure resources.
  - **Resource Owner:** Grants permissions to access resources.
  - **Client:** Requests access to resources from the resource server.

**5) What is the OAuth Workflow?**

- **Can you explain the typical OAuth workflow?**
  - Client requests access from the user.
  - User provides credentials to the Authorization Server.
  - Authorization Server issues a token to the client.
  - Client uses the token to access resources from the Resource Server.

**6) What are the OAuth Grant Types?**

- **What are OAuth grant types and their use cases?**
  - **Authorization Code:** Redirects user to Authorization Server, receives a token after authentication.
  - **Password:** Client collects username and password directly and exchanges them for a token.
  - **Client Credentials:** Used for server-to-server communication without user involvement.
  - **Refresh Token:** Allows obtaining a new token without re-authentication after the initial token expires.

**7) What are the Different Grant Types?**

- **Can you explain the different OAuth grant types?**
  - **Authorization Code:** Redirects user to authorize and receive a token.
  - **Password:** Uses direct credentials exchange for a token.
  - **Client Credentials:** Used for internal application communication.
  - **Refresh Token:** Refreshes expired tokens without user re-authentication.

**8) What is JWT?**

- **What is JWT and its role in OAuth?**
  - JWT (JSON Web Token) contains user and role information, eliminating the need for additional server validation.
  - JWTs are signed by the Authorization Server, allowing Resource Servers to verify token validity using signatures.

**9) How to configure JWT?**

- **What are the steps to configure JWT in Spring Security?**
  - Use JWT Access Token Converter in the Authorization Server.
  - Tokens can be signed using symmetric or asymmetric keys.
  - Symmetric: Both servers use the same key.
  - Asymmetric: Use a key pair (private for signing, public for verification).

**10) How to rotate tokens?**

- **How often should you rotate tokens in OAuth applications?**
  - For high-security applications (e.g., financial), rotate tokens every 5-10 minutes.
  - For less sensitive applications (e.g., read-only feeds), rotate tokens less frequently (e.g., every 6 months or year).

**11) How to use Tokens with Frontends?**

- **Where do you store OAuth tokens in front-end applications?**
  - Store tokens on the server; if sent to the browser, use TLS for security.

**12) What is CSRF?**

- **What is CSRF?**
  - CSRF (Cross-Site Request Forgery) occurs when an attacker uses a user's cookies from one site to make unauthorized requests to another site.

**13) How to prevent CSRF?**

- **How does Spring Security prevent CSRF?**
  - Spring Security uses a CSRF filter that includes a token in each page. The token must be sent back with requests; otherwise, the request is rejected.

**14) What is CORS?**

- **What is CORS and how is it used?**
  - CORS (Cross-Origin Resource Sharing) allows resources on one domain to be accessed by frontends on different domains.
  - Configured using annotations like `@CrossOrigin` to specify allowed origins, methods, and headers.

---

Feel free to adjust any details as needed!
