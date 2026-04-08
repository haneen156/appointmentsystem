#  Appointment Scheduling System (Backend)
🚧 Work in progress — building a production-style backend step by step

---

## 📌 Overview
A backend system for managing appointments between users and providers, focusing on **business rule enforcement**, **conflict detection**, and **clean architecture design**.

This project follows a **domain-first approach**, where core business logic and system behavior are implemented and validated before integrating frameworks such as Spring Boot and JPA.

---

## 🚧 Project Status

This project is actively under development.

### ✔ Completed
- Domain modeling and business rules
- Appointment lifecycle (cancel, reschedule, complete)
- Conflict detection logic
- Repository and service layers
- Comprehensive unit testing

### 🔄 In Progress
- Controller layer (plain Java → Spring REST)

### ⏳ Planned
- Spring Boot integration
- JPA/Hibernate persistence
- DTOs (request/response models)
- Validation annotations
- Global exception handling

---

## 🚀 Features

- Create appointments with time validation
- Prevent overlapping appointments per provider
- Cancel appointments (status-based, no deletion)
- Reschedule appointments with conflict checking
- Enforce strict appointment lifecycle rules
- Handle edge cases and invalid operations safely

---

## ⚙️ Business Rules

- No overlapping appointments for the same provider
- Appointments must have a valid time range
- Completed appointments cannot be cancelled or modified
- Rescheduling excludes the current appointment from conflict checks
- Each appointment must reference a valid user and provider
- Start time must be before end time

### 🔄 Appointment Lifecycle
- `BOOKED → CANCELLED`
- `BOOKED → COMPLETED`

---

## 🧠 System Design

### 🔄 Appointment Creation Flow
Handles conflict detection before saving an appointment.

### 🔁 Cancel Appointment Flow
Validates appointment state before allowing cancellation.

### 🔁 Reschedule Appointment Flow
Ensures no conflicts while excluding the current appointment.

---

## 🗄️ Data Model

### Core Entities
- **User**
- **Provider**
- **Appointment**

### Appointment Fields
- startTime
- endTime
- status
- userId
- providerId

### Relationships
- One User → Many Appointments
- One Provider → Many Appointments

---

## 🌐 API Design

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | /appointments | Create appointment |
| DELETE | /appointments/{id} | Cancel appointment |
| PUT    | /appointments/{id}/reschedule | Reschedule appointment |

### 📡 Responses
- `201 CREATED` → Successful creation
- `200 OK` → Successful update
- `404 NOT FOUND` → Resource does not exist
- `409 CONFLICT` → Business rule violation

---

## 🧱 Architecture

Layered architecture:

Controller → Service → Repository → Domain

### Responsibilities

- **Controller** → Handles requests and responses
- **Service** → Orchestrates business logic
- **Repository** → Abstracts data access
- **Domain** → Enforces core business rules and state transitions

---

## 🧪 Testing

Comprehensive unit testing covers:

- ✔ Happy paths
- ✔ Conflict detection logic
- ✔ State transitions
- ✔ Validation rules
- ✔ Not-found scenarios
- ✔ Edge cases (e.g., back-to-back appointments)

This ensures safe refactoring and predictable system behavior.

---

## 🧠 Design Patterns Used

### 🎯 Domain-Driven Design (DDD - Lite)
Business logic is encapsulated inside domain models:
- Appointment manages its own state transitions
- Rules are enforced at the domain level

---

### 🔌 Dependency Injection
Dependencies are injected via constructors to reduce coupling and improve testability.

---

### 🗂️ Repository Pattern
Abstracts data access logic and allows switching from in-memory to JPA without affecting business logic.

---

### ⚠️ Exception Handling Pattern
Custom exceptions represent business rule violations:
- ConflictException
- -> AppointmentAlreadyCancelledException
- -> AppointmentAlreadyCompletedException
- -> AppointmentConflictException
- -> InvalidAppointmentTimeException
- NotFoundException
- -> ResourceNotFoundException

---

### 🔄 State Management Pattern
Appointment lifecycle is controlled via explicit state transitions.

---

## 🧠 Design Decisions

- Business logic implemented before persistence to ensure correctness
- In-memory repository used initially for fast iteration and debugging
- Status-based updates used instead of deletion to preserve history
- Conflict detection handled at the service layer before persistence

---

## 🛠️ Tech Stack (Planned / In Progress)

- Java
- Spring Boot (in progress)
- Spring Data JPA (planned)
- H2 Database (testing)
- MySQL (production)



## 🎯 What This Project Demonstrates

- Strong understanding of backend architecture
- Ability to model real-world business rules
- Clean separation of concerns
- Test-driven and design-first development approach
- Readiness for real-world backend systems

---
