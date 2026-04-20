# SmartLedger

SmartLedger is an Android FinTech prototype for secure personal expense tracking, group bill splitting, and cloud-based data management. The project was developed for **CN6008 – Advanced Topics in Computer Science** and focuses on a clean prototype implementation using **Kotlin**, **Jetpack Compose**, **Firebase Authentication**, and **Cloud Firestore**.

## Overview

The application supports a simple but structured expense-management workflow:
- user registration and login
- personal expense creation and storage
- expense history retrieval
- group creation with equal bill splitting
- cloud-backed persistence through Firebase

The current implementation follows a layered structure with **Compose UI screens**, **ViewModels**, **repositories**, and a small **domain/use case** layer for split calculation.

## Features

### Implemented
- **Authentication** with Firebase Authentication (register, login, logout)
- **User profile storage** in Firestore after registration
- **Dashboard navigation** for the main prototype flow
- **Add Expense** with validation for title, amount, category, and date
- **Expense History** for the authenticated user
- **Group / Split workflow** with equal share calculation
- **Cloud persistence** using Firestore collections such as `users`, `expenses`, `groups`, and `splits`

### Current prototype limitations
- Group members are currently entered as names in a comma-separated input rather than selected from real registered user accounts.
- The split logic currently stores the entered participant names, but not full multi-user settlement logic for all participants.
- There is no offline persistence layer yet (for example Room).
- Dependency injection and automated testing are still minimal.

## Architecture

SmartLedger uses a lightweight layered Android architecture:

- **UI Layer**: Compose screens and navigation
- **Presentation Layer**: `AuthViewModel`, `ExpenseViewModel`, `GroupViewModel`
- **Data Layer**: `AuthRepository`, `ExpenseRepository`, `GroupRepository`
- **Domain Layer**: `SplitCalculationUseCase`
- **Backend**: Firebase Authentication + Cloud Firestore

### Project structure

```text
app/src/main/java/com/smartledger/
├── data/
│   ├── model/
│   └── repository/
├── domain/
│   └── usecase/
├── ui/
│   ├── navigation/
│   ├── screens/
│   │   ├── auth/
│   │   ├── dashboard/
│   │   ├── expense/
│   │   ├── group/
│   │   └── history/
│   ├── theme/
│   └── viewmodel/
└── MainActivity.kt
```

## Tech Stack

- **Language:** Kotlin
- **UI Toolkit:** Jetpack Compose + Material 3
- **Navigation:** Navigation Compose
- **Architecture:** MVVM-style separation with repositories
- **Backend / Cloud:** Firebase Authentication, Cloud Firestore
- **Asynchronous operations:** Kotlin Coroutines
- **Build system:** Gradle Kotlin DSL

## Screens / Main Flow

1. **Welcome Screen**
2. **Login / Register**
3. **Dashboard**
4. **Add Expense**
5. **Expense History**
6. **Group / Split**

## Diagrams

The repository also includes the design artefacts created for the coursework:
- `Smart Ledger - System Architecture Diagram.png`
- `Smart Ledger - Use Case Diagram.png`

These diagrams support the architectural and functional explanation of the prototype.

## Setup Instructions

### Prerequisites
- Android Studio
- JDK 17
- A Firebase project
- Android SDK matching the project configuration

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/LazarosVoulistiotis/SmartLedger.git
   ```
2. Open the project in Android Studio.
3. Create your own Firebase project.
4. Enable:
   - **Firebase Authentication** (Email/Password)
   - **Cloud Firestore**
5. Add your local `google-services.json` file inside:
   ```text
   app/google-services.json
   ```
   > Note: this file is intentionally **not included** in the public repository.
6. Sync Gradle.
7. Run the app on an emulator or Android device.

## Example Firestore Collections

The current prototype uses the following collection model:
- `users`
- `expenses`
- `groups`
- `splits`

## Why this project matters

SmartLedger was designed as a prototype that demonstrates:
- secure handling of user access
- separation of UI and business logic
- cloud-based storage of financial records
- an extendable foundation for future coursework improvements

## Future Improvements

Possible next steps for a more complete version:
- real multi-user group membership using registered accounts
- Room database for offline support
- dependency injection (for example Hilt)
- more advanced validation and error handling
- analytics dashboard and charts
- recurring expenses
- edit/delete expense flows
- unit tests and UI tests
- biometric authentication

## Academic Context

This project was developed as part of:

**CN6008 – Advanced Topics in Computer Science**

It aligns with the coursework goals of system design, Android implementation, backend integration, UML-based planning, and viva demonstration.

## Author

**Lazaros Voulistiotis**

---
