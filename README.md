# SmartLedger

SmartLedger is an Android FinTech application prototype for secure personal expense tracking, group bill splitting, and cloud-backed data management. The project was originally developed for **CN6008 – Advanced Topics in Computer Science (CW1)** using **Kotlin**, **Jetpack Compose**, **Firebase Authentication**, and **Cloud Firestore**.

The project is now being extended for **CN6008 CW2 – Final App & Testing**, where the existing prototype will be evolved into a more complete mobile application with advanced Android features, structured testing evidence, and documented group contribution.

---

## Overview

SmartLedger supports a simple but structured expense-management workflow:

- user registration and login
- personal expense creation and storage
- expense history retrieval
- group creation with equal bill splitting
- cloud-backed persistence through Firebase

The current implementation follows a layered Android structure with **Compose UI screens**, **ViewModels**, **repositories**, and a small **domain/use case** layer for split calculation.

The CW2 phase extends this foundation without rebuilding the application from scratch. The goal is to keep the existing architecture stable while adding selected advanced technologies and stronger testing documentation.

---

## Features

### Implemented CW1 Prototype Features

- **Authentication** with Firebase Authentication: register, login, and logout
- **User profile storage** in Firestore after registration
- **Dashboard navigation** for the main app flow
- **Add Expense** with validation for title, amount, category, and date
- **Expense History** for the authenticated user
- **Group / Split workflow** with equal share calculation
- **Cloud persistence** using Firestore collections such as `users`, `expenses`, `groups`, and `splits`
- **Repository-based data access** to keep UI code separate from Firebase logic
- **SplitCalculationUseCase** for isolated equal-split business logic

### CW2 Planned Extensions

The CW2 implementation will extend the current prototype with:

- **Room / SQLite Local Storage** for offline expense access
- **Biometric Authentication** as an additional local security layer
- **Retrofit External API Integration** for live financial or currency data
- **Testing Strategy** with functional test cases, expected vs actual results, screenshots, and selected unit tests
- **Contribution Matrix** to document each team member’s implementation responsibility
- **Viva preparation evidence** for code defense and live demo support

---

## Current Prototype Limitations

The current baseline has the following known limitations before CW2 feature development:

- Group members are currently entered as names in a comma-separated input rather than selected from real registered user accounts.
- The split logic currently stores the entered participant names, but not full multi-user settlement logic for all participants.
- Offline persistence is not implemented yet and will be added through Room during CW2.
- Biometric authentication is not implemented yet and will be added during CW2.
- External API integration is not implemented yet and will be added through Retrofit during CW2.
- Dependency injection and automated testing are still minimal.

---

## Architecture

SmartLedger uses a lightweight layered Android architecture:

- **UI Layer:** Jetpack Compose screens and navigation
- **Presentation Layer:** `AuthViewModel`, `ExpenseViewModel`, `GroupViewModel`, and related `UiState` classes
- **Data Layer:** `AuthRepository`, `ExpenseRepository`, `GroupRepository`
- **Domain Layer:** `SplitCalculationUseCase`
- **Backend:** Firebase Authentication and Cloud Firestore

The design supports separation of concerns by keeping UI, state management, business logic, and data access in separate layers.

### Current Project Structure

```text
app/src/main/java/com/smartledger/
├── data/
│   ├── model/
│   ├── remote/
│   └── repository/
│       ├── AuthRepository
│       ├── ExpenseRepository
│       └── GroupRepository
├── domain.usecase/
│   └── SplitCalculationUseCase
├── ui/
│   ├── navigation/
│   │   ├── Screen
│   │   └── SmartLedgerNavHost.kt
│   ├── screens/
│   │   ├── auth/
│   │   ├── dashboard/
│   │   ├── expense/
│   │   ├── group/
│   │   └── history/
│   ├── theme/
│   └── viewmodel/
│       ├── AuthUiState
│       ├── AuthViewModel
│       ├── ExpenseUiState
│       ├── ExpenseViewModel
│       ├── GroupUiState
│       └── GroupViewModel
└── MainActivity.kt
```

### Target CW2 Extension Structure

```text
app/src/main/java/com/smartledger/
├── data/
│   ├── local/                         # Room / SQLite local storage
│   │   ├── SmartLedgerDatabase.kt
│   │   ├── dao/
│   │   │   └── ExpenseDao.kt
│   │   ├── entity/
│   │   │   └── ExpenseEntity.kt
│   │   └── mapper/
│   │       └── ExpenseMappers.kt
│   ├── remote/                        # Retrofit external API integration
│   │   ├── api/
│   │   │   └── CurrencyApiService.kt
│   │   ├── dto/
│   │   │   └── CurrencyRatesResponse.kt
│   │   └── RetrofitClient.kt
│   ├── security/                      # Biometric authentication
│   │   └── BiometricAuthManager.kt
│   └── repository/
│       ├── ExpenseRepository
│       └── CurrencyRepository.kt
├── ui/
│   ├── screens/
│   │   └── api/
│   │       └── CurrencyRatesScreen.kt
│   └── viewmodel/
│       ├── CurrencyUiState.kt
│       └── CurrencyViewModel.kt
```

---

## Tech Stack

- **Language:** Kotlin
- **UI Toolkit:** Jetpack Compose + Material 3
- **Navigation:** Navigation Compose
- **Architecture:** MVVM-style separation with repositories and use cases
- **Backend / Cloud:** Firebase Authentication, Cloud Firestore
- **Local Storage:** Room / SQLite planned for CW2
- **Security:** AndroidX Biometric planned for CW2
- **Networking:** Retrofit planned for CW2 external API integration
- **Asynchronous operations:** Kotlin Coroutines
- **Build system:** Gradle Kotlin DSL
- **Project Management:** Jira Kanban board
- **Testing:** Functional testing, black-box testing, user acceptance testing, and selected unit tests

---

## Screens / Main Flow

The current app flow is:

1. **Welcome Screen**
2. **Login / Register**
3. **Dashboard**
4. **Add Expense**
5. **Expense History**
6. **Group / Split**

The intended CW2 demo flow is:

1. Open app and show Welcome Screen
2. Login through Firebase Authentication
3. Complete biometric confirmation or fallback
4. Open Dashboard
5. Add a new expense
6. Display saved expense in History
7. Demonstrate Room/offline storage
8. Create a Group Split
9. Open Currency Rates screen
10. Display external API response
11. Demonstrate error/retry handling where possible
12. Logout

---

## CW2 Development Plan

The CW2 phase is organised around three main technical responsibilities:

| Team Member | Main Implementation | Main Packages / Files |
|---|---|---|
| Λάζαρος | Room local database and offline expense access | `data/local`, `ExpenseRepository`, `ExpenseViewModel`, `ExpenseUiState`, `HistoryScreen`, `AddExpenseScreen` |
| Γιάννης | Biometric authentication | `data/security`, `LoginScreen`, `AuthViewModel`, `AuthUiState` |
| Ιάσωνας | Retrofit external API integration | `data/remote`, `CurrencyRepository`, `CurrencyViewModel`, `CurrencyUiState`, `CurrencyRatesScreen`, navigation |
| Όλοι | Integration, screenshots, testing, report, and viva | `docs`, README, report, PowerPoint, final build evidence |

The final CW2 project story is:

```text
SmartLedger securely authenticates the user through Firebase,
protects access with biometrics,
stores financial data in the cloud,
keeps expenses available offline with Room,
and enriches the experience with live financial API data through Retrofit.
```

---

## CW2 Documentation and Evidence

The repository includes a dedicated CW2 documentation folder:

```text
docs/
└── cw2/
    ├── day1-baseline.md
    ├── test-plan.md
    ├── contribution-matrix.md
    └── screenshots/
        ├── setup/
        ├── room/
        ├── biometrics/
        ├── api/
        ├── testing/
        └── viva/
```

The `docs/cw2` folder stores:

- baseline setup evidence
- Git status screenshots
- Gradle build success screenshots
- existing app flow screenshots
- current code architecture screenshots
- Jira board screenshots
- Room implementation evidence
- biometric authentication evidence
- Retrofit/API evidence
- testing screenshots
- viva preparation evidence

---

## Jira / Kanban Workflow

The CW2 workflow is managed using a Jira Kanban board with the following columns:

```text
To Do → In Progress → Code Review → Testing → Done
```

The board is used to track:

- Day 1 setup and baseline documentation
- Room local storage implementation
- Biometric authentication implementation
- Retrofit API implementation
- testing tasks
- report tasks
- viva preparation tasks

This provides evidence of organised project management and supports the final CW2 report.

---

## Testing Strategy

The CW2 testing strategy will include:

- **Functional Testing:** checking whether each feature works as expected
- **Black-box Testing:** testing the app from the user’s perspective
- **User Acceptance Testing:** confirming that the app supports the main user workflows
- **Unit Testing:** selected tests for isolated business logic, such as equal split calculation

The test plan is maintained in:

```text
docs/cw2/test-plan.md
```

Example planned test areas:

- valid and invalid Firebase login
- adding a valid expense
- displaying expense history
- equal group split calculation
- Room local expense storage
- offline history access
- biometric success, cancel, and fallback cases
- Retrofit API success and error/retry handling

---

## Diagrams

The repository also includes design artefacts created for the coursework:

- `Smart Ledger - System Architecture Diagram.png`
- `Smart Ledger - Use Case Diagram.png`

These diagrams support the architectural and functional explanation of the prototype.

---

## Setup Instructions

### Prerequisites

- Android Studio
- JDK 17
- Android SDK matching the project configuration
- A Firebase project
- Git

### Steps

1. Clone the repository:

   ```bash
   git clone https://github.com/LazarosVoulistiotis/SmartLedger.git
   ```

2. Open the project in Android Studio.

3. Create your own Firebase project.

4. Enable:

   - **Firebase Authentication** with Email/Password
   - **Cloud Firestore**

5. Add your local `google-services.json` file inside:

   ```text
   app/google-services.json
   ```

   > Note: this file is intentionally **not included** in the public repository.

6. Sync Gradle.

7. Run a clean build:

   ```powershell
   .\gradlew clean build
   ```

8. Run the app on an emulator or Android device.

---

## Git Workflow for Team Development

For CW2, feature branches are recommended so that each team member works independently without breaking the stable `main` branch.

Example:

```bash
git checkout main
git pull
git checkout -b feature/room-offline-storage
```

Suggested branches:

```text
feature/room-offline-storage
feature/biometric-auth
feature/external-finance-api
feature/testing-and-polish
```

Suggested commit messages:

```text
Add Room offline expense storage
Add biometric login confirmation
Add Retrofit financial API integration
Add CW2 testing evidence and documentation
Polish SmartLedger final CW2 demo flow
```

---

## Example Firestore Collections

The current prototype uses the following collection model:

- `users`
- `expenses`
- `groups`
- `splits`

---

## Why this project matters

SmartLedger demonstrates:

- secure handling of user access
- separation of UI and business logic
- cloud-based storage of financial records
- equal bill splitting for group expenses
- an extendable Android architecture suitable for further development
- a clear transition from prototype design to tested final mobile application

---

## Future Improvements

Possible next steps beyond the CW2 scope include:

- real multi-user group membership using registered accounts
- richer group settlement and balance tracking
- edit/delete expense flows
- recurring expenses
- analytics dashboard and charts
- receipt scanning or OCR
- automatic categorisation using AI
- push notifications
- dependency injection using Hilt
- expanded UI and integration testing
- production-ready sync strategy between Room and Firestore

---

## Academic Context

This project was developed as part of:

**CN6008 – Advanced Topics in Computer Science**

It aligns with the coursework goals of system design, Android implementation, backend integration, UML-based planning, software testing, contribution tracking, and viva demonstration.

---

## Author / Team

**CW1 Author:** [Lazaros Voulistiotis](https://github.com/LazarosVoulistiotis)

**CW2 Team:** [Lazaros Voulistiotis](https://github.com/LazarosVoulistiotis), [John Kakasas](https://github.com/V0idW1re), [Jason Lykakis](https://github.com/jaylyk)

---
