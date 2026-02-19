# 💳 simplified-elo-api

A robust payment API inspired by the **Elo** ecosystem, developed to demonstrate mastery over advanced architectural patterns and modern software engineering best practices.

This project evolves traditional backend challenge concepts by implementing a rigorous separation of contexts and a highly testable, decoupled structure.

---

## 🏛️ Architecture & Design Patterns

The project is built following **Clean Architecture** and **Hexagonal Architecture (Ports and Adapters)** principles, ensuring the business logic remains independent of frameworks, databases, or external services.

* **DDD (Domain-Driven Design):** Organized into clear Bounded Contexts (**Users** and **Accounts**).
* **Hexagonal Architecture:** The application core communicates with the external world via Ports, making it easy to swap technologies (e.g., changing databases or external APIs) without affecting the domain.
* **Clean Code:** Focus on readability, semantic naming, and the Single Responsibility Principle (SRP).
* **Layered Security:** Robust authentication and authorization to protect financial transactions.

---

## 🛠️ Tech Stack

- **Java 17+** ☕
- **Spring Boot 3** 🌱
- **Docker & Docker Compose** 🐋
- **AWS** (Cloud Infrastructure) ☁️
- **JWT** (JSON Web Token) 🔐
- **RestClient** (Synchronous Communication) 📡
- **JUnit 5 & Mockito** (Unit & Integration Testing) 🧪
- **Clean Architecture / Hexagonal / DDD** 🏛️

---

## 🔄 System Workflow

The API is divided into two main contexts that interact automatically:

### 1. Bounded Context: Users
Manages user identity and profiles.
- **Trigger:** When a new user is created (`POST /users`), the system automatically triggers a request via **RestClient** to the Accounts context.
- **Security:** Integrated with **JWT** to ensure only authenticated users access sensitive resources.

### 2. Bounded Context: Accounts
Manages the financial ledger and balances.
- **Automated Provisioning:** Receives the call from the Users context and instantly creates a linked account.
- **Transactions:** Allows users to perform transfers between their receiving and payment accounts (P2P).
- **Validation:** Strict business rules to prevent negative balances or unauthorized transactions.
