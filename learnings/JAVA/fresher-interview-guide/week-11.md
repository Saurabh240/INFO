### Week 11: Resume + GitHub + System Design Basics - Detailed Teaching Guide

---

#### **Day 1: Create GitHub Portfolio with Mini Projects**

**Objective:** Establish a professional GitHub profile showcasing Java + Spring Boot skills.

**Topics:**

* GitHub profile optimization
* Uploading projects with README.md

**Teaching Flow:**

1. **Profile Setup:**

   * Add a bio, profile picture, and pinned repositories
2. **Project Guidelines:**

   * Use meaningful project names
   * Each repo should have:

     * `README.md` (project description, features, tech stack, how to run)
     * `LICENSE`, `.gitignore`, folder structure

**Practice:**

* Upload 2–3 projects (e.g., Student API, Contact Book CLI)
* Write polished README with screenshots/code samples

---

#### **Day 2: Create a Solid Java + Spring Boot Resume**

**Objective:** Build a concise, ATS-friendly resume tailored for entry-level Java roles.

**Sections to Cover:**

* Header: Name, contact info, GitHub, LinkedIn
* Summary: Focused on Java + Spring Boot + DB skills
* Skills: Core Java, Spring Boot, REST API, PostgreSQL, Git, Maven, etc.
* Projects:

  * Title, description, tech stack, key features
* Experience (if any): internships, freelance
* Education & Certifications

**Practice:**

* Use modern templates (e.g., Novoresume, Canva)
* Export in PDF, ensure formatting consistency

---

#### **Day 3: System Design Basics — Monolith vs Microservice, REST vs SOAP**

**Objective:** Understand architectural choices and API paradigms.

**Topics:**

* Monolithic vs Microservices: pros, cons, use cases
* REST vs SOAP APIs: structure, format, examples

**Teaching Flow:**

1. **Monolith vs Microservices:**

```text
Monolith: Single codebase, tightly coupled
Microservices: Independent modules, loosely coupled
```

2. **REST vs SOAP:**

* REST: JSON, HTTP verbs, stateless
* SOAP: XML, WSDL, more secure for enterprise

**Practice:**

* Draw architecture diagrams
* Discuss which style fits for Student API

---

#### **Day 4: MVC Architecture, Controller-Service-Repo Flow**

**Objective:** Understand MVC and its role in Spring Boot applications.

**Topics:**

* Model-View-Controller pattern
* Role of Controller, Service, and Repository

**Teaching Flow:**

1. **MVC Diagram Explanation:**

   * Model: Entity + DTOs
   * View: Not applicable in REST, replaced by JSON
   * Controller: HTTP endpoint handler
   * Service: Business logic
   * Repository: DB interaction

2. **Sample Flow:**

```text
POST /students -> Controller -> Service -> Repository -> DB
```

**Practice:**

* Explain the flow in your own project
* Add JavaDoc comments to illustrate architecture

---

#### **Day 5–7: Mock Test 2 + Resume Review + GitHub Polish**

**Objective:** Simulate interview and finalize career assets.

**Mock Test Structure:**

* 2–3 coding questions (DSA or Java logic)
* 4–5 theory questions (OOP, exception handling, collections)

**Resume Review Checklist:**

* Technical keywords included
* No grammatical errors
* Action verbs for achievements
* Relevant skills highlighted

**GitHub Polish Tasks:**

* Add GitHub Actions badge or CI workflow
* Tag project releases
* Improve README formatting (e.g., add tech stack badges, live demo links)

**Practice:**

* Conduct peer or mentor review of resume and GitHub
* Prepare elevator pitch based on your portfolio
