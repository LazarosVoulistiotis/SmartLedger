# SmartLedger

SmartLedger is an Android FinTech application prototype for secure personal expense tracking, group bill splitting, cloud-backed data management, and offline expense availability. The project was originally developed for **CN6008 – Advanced Topics in Computer Science (CW1)** using **Kotlin**, **Jetpack Compose**, **Firebase Authentication**, and **Cloud Firestore**.

The project is now being extended for **CN6008 CW2 – Final App & Testing**, where the existing prototype is being evolved into a more complete mobile application with advanced Android features, structured testing evidence, and documented group contribution.

---

## Current CW2 Status

| Area | Status | Notes |
|---|---:|---|
| CW1 baseline app | ✅ Completed | Firebase login, expense tracking, history, and group split workflow are available. |
| CW2 Day 1 setup | ✅ Completed | CW2 documentation folders, test plan skeleton, contribution matrix, Jira board, and setup evidence were prepared. |
| Room / SQLite local storage | ✅ Completed | Expenses are saved locally through Room and then synced to Firestore when available. |
| Unit testing | ✅ Completed for Day 2 | `SplitCalculationUseCaseTest` validates equal split calculation and invalid zero-member input. |
| Biometric authentication | Planned | Assigned to Γιάννης for CW2. |
| Retrofit external API | Planned | Assigned to Ιάσωνας for CW2. |
| Final integration, report, and viva | Planned | To be completed after all technology integrations are merged. |

---

## Overview

SmartLedger supports a simple but structured expense-management workflow:

- user registration and login
- personal expense creation and storage
- local Room storage for offline expense availability
- cloud synchronisation through Firestore
- expense history retrieval
- group creation with equal bill splitting
- testing evidence for build, unit tests, and feature validation

The current implementation follows a layered Android structure with **Compose UI screens**, **ViewModels**, **repositories**, **Room local data access**, **Firebase services**, and a small **domain/use case** layer for split calculation.

The CW2 phase extends the CW1 foundation without rebuilding the application from scratch. The goal is to keep the architecture stable while adding selected advanced technologies and stronger testing documentation.

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

### Implemented CW2 Features

#### Room / SQLite Local Storage — Completed

SmartLedger now includes Room local database support inside the `data/local` package. The Room implementation provides offline-first expense persistence:

```text
Add Expense
→ save to Room first
→ attempt Firestore save
→ update UI feedback based on sync result
```

Implemented files:

```text
app/src/main/java/com/smartledger/data/local/
├── SmartLedgerDatabase.kt
├── dao/
│   └── ExpenseDao.kt
├── entity/
│   └── ExpenseEntity.kt
└── mapper/
    └── ExpenseMappers.kt
```

Updated files:

```text
app/src/main/java/com/smartledger/data/repository/ExpenseRepository.kt
app/src/main/java/com/smartledger/ui/viewmodel/ExpenseViewModel.kt
app/src/main/java/com/smartledger/ui/viewmodel/ExpenseUiState.kt
app/src/main/java/com/smartledger/ui/screens/expense/AddExpenseScreen.kt
app/src/main/java/com/smartledger/ui/screens/history/HistoryScreen.kt
```

User feedback messages include:

```text
Expense saved locally and synced to cloud.
Expense saved locally. Sync will be attempted when online.
Showing saved expenses from local storage when cloud data is unavailable.
```

### Remaining CW2 Planned Extensions

The next CW2 implementation steps will add:

- **Biometric Authentication** as an additional local security layer
- **Retrofit External API Integration** for live financial or currency data
- **Final Testing Strategy** with functional test cases, expected vs actual results, screenshots, and selected unit tests
- **Contribution Matrix** to document each team member’s implementation responsibility
- **Viva preparation evidence** for code defense and live demo support

---

## Current Prototype Limitations

The current CW2 state has the following known limitations:

- Group members are currently entered as names in a comma-separated input rather than selected from real registered user accounts.
- The split logic currently stores the entered participant names, but not full multi-user settlement logic for all participants.
- Room local storage has been implemented for expenses, but a production-grade background sync engine, such as WorkManager-based retry synchronisation, is outside the current Day 2 scope.
- Biometric authentication is not implemented yet and will be added during CW2.
- External API integration is not implemented yet and will be added through Retrofit during CW2.
- Dependency injection is still minimal to avoid unnecessary refactoring during coursework implementation.
- Automated testing currently includes selected unit testing; broader UI and integration tests are planned for final testing.

---

## Architecture

SmartLedger uses a lightweight layered Android architecture:

- **UI Layer:** Jetpack Compose screens and navigation
- **Presentation Layer:** `AuthViewModel`, `ExpenseViewModel`, `GroupViewModel`, and related `UiState` classes
- **Data Layer:** `AuthRepository`, `ExpenseRepository`, `GroupRepository`, Room DAO, and Firebase data access
- **Domain Layer:** `SplitCalculationUseCase`
- **Backend:** Firebase Authentication and Cloud Firestore
- **Local Storage:** Room / SQLite for offline expense availability

The design supports separation of concerns by keeping UI, state management, business logic, local database access, and cloud data access in separate layers.

### Current Project Structure After Room Integration

```text
app/src/main/java/com/smartledger/
├── data/
│   ├── local/
│   │   ├── SmartLedgerDatabase.kt
│   │   ├── dao/
│   │   │   └── ExpenseDao.kt
│   │   ├── entity/
│   │   │   └── ExpenseEntity.kt
│   │   └── mapper/
│   │       └── ExpenseMappers.kt
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

### Target Final CW2 Extension Structure

```text
app/src/main/java/com/smartledger/
├── data/
│   ├── local/                         # Completed: Room / SQLite local storage
│   ├── remote/                        # Planned: Retrofit external API integration
│   │   ├── api/
│   │   │   └── CurrencyApiService.kt
│   │   ├── dto/
│   │   │   └── CurrencyRatesResponse.kt
│   │   └── RetrofitClient.kt
│   ├── security/                      # Planned: Biometric authentication
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
- **Local Storage:** Room / SQLite for offline expense persistence
- **Security:** AndroidX Biometric planned for CW2
- **Networking:** Retrofit planned for CW2 external API integration
- **Asynchronous operations:** Kotlin Coroutines
- **Build system:** Gradle Kotlin DSL
- **Annotation processing:** KSP for Room compiler
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

The current Room-enhanced expense flow is:

1. User opens **Add Expense**.
2. User enters title, amount, category, date, and optional description.
3. App saves the expense locally through Room.
4. App attempts to sync the same expense to Firestore.
5. UI displays whether the expense was saved locally and synced to cloud, or saved locally for later sync.
6. User opens **Expense History** and sees saved expenses.

The intended final CW2 demo flow is:

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

| Team Member | Main Implementation | Main Packages / Files | Status |
|---|---|---|---:|
| Λάζαρος | Room local database and offline expense access | `data/local`, `ExpenseRepository`, `ExpenseViewModel`, `ExpenseUiState`, `HistoryScreen`, `AddExpenseScreen` | ✅ Completed |
| Γιάννης | Biometric authentication | `data/security`, `LoginScreen`, `AuthViewModel`, `AuthUiState` | Planned |
| Ιάσωνας | Retrofit external API integration | `data/remote`, `CurrencyRepository`, `CurrencyViewModel`, `CurrencyUiState`, `CurrencyRatesScreen`, navigation | Planned |
| Όλοι | Integration, screenshots, testing, report, and viva | `docs`, README, report, PowerPoint, final build evidence | In progress |

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

### Room Evidence Screenshots

Room implementation evidence is stored in:

```text
docs/cw2/screenshots/room/
```

Current Room evidence includes:

```text
01_data_local_package.png
02_room_database_code.png
03_add_expense_form_filled.png
03_add_expense_local_save_success.png
04_history_local_data.png
05_unit_test_success.png
```

Recommended report captions:

| Screenshot | Suggested Caption |
|---|---|
| `01_data_local_package.png` | Room local storage package structure showing database, DAO, entity, and mapper classes. |
| `02_room_database_code.png` | `SmartLedgerDatabase` configuration showing the Room database entity and DAO access. |
| `03_add_expense_form_filled.png` | Add Expense form populated with test expense data for Room validation. |
| `03_add_expense_local_save_success.png` | Successful local save and Firestore sync message after adding an expense. |
| `04_history_local_data.png` | Expense History displaying the locally stored expense record. |
| `05_unit_test_success.png` | Gradle unit test and clean build success after Room integration. |

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

Room-specific Jira tasks completed during Day 2:

```text
ROOM-01 Add Room dependencies
ROOM-02 Create ExpenseEntity
ROOM-03 Create ExpenseDao
ROOM-04 Create SmartLedgerDatabase
ROOM-05 Create Expense mappers
ROOM-06 Update ExpenseRepository for local storage
ROOM-07 Update HistoryScreen for offline data
ROOM-08 Add Room/offline screenshots
```

This provides evidence of organised project management and supports the final CW2 report.

---

## Testing Strategy

The CW2 testing strategy includes:

- **Functional Testing:** checking whether each feature works as expected
- **Black-box Testing:** testing the app from the user’s perspective
- **User Acceptance Testing:** confirming that the app supports the main user workflows
- **Unit Testing:** selected tests for isolated business logic, such as equal split calculation

The test plan is maintained in:

```text
docs/cw2/test-plan.md
```

Implemented Day 2 test evidence:

```text
app/src/test/java/com/smartledger/domain/usecase/SplitCalculationUseCaseTest.kt
```

The current unit test validates:

```text
totalAmount = 100, members = 4 → share = 25
members = 0 → throws IllegalArgumentException
```

Example final CW2 test areas:

- valid and invalid Firebase login
- adding a valid expense
- displaying expense history
- equal group split calculation
- Room local expense storage
- offline history access
- biometric success, cancel, and fallback cases
- Retrofit API success and error/retry handling

---

## Build and Test Validation

The Room integration has been validated with:

```powershell
.\gradlew test --no-configuration-cache
.\gradlew clean build --no-configuration-cache
```

Both commands completed successfully during Day 2 validation.

> Note: The project currently uses `android.disallowKotlinSourceSets=false` in `gradle.properties` as a compatibility workaround for AGP 9.1.0 built-in Kotlin and KSP-generated Room sources.

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
   .\gradlew clean build --no-configuration-cache
   ```

8. Run unit tests:

   ```powershell
   .\gradlew test --no-configuration-cache
   ```

9. Run the app on an emulator or Android device.

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

Room stores local expense records in the local `expenses` table through `ExpenseEntity`.

---

## Viva Defense Notes

### Room / SQLite Local Storage — Λάζαρος

> I implemented Room inside the `data/local` package to add local SQLite-based persistence for expenses. The app now saves an expense locally first and then attempts to sync it with Firestore. This means that the History screen can still display saved expenses even when cloud access is unavailable. I connected Room through `ExpenseRepository`, so the UI and ViewModel do not directly depend on the database. This keeps the architecture clean and supports offline access for financial records.

Key points to explain:

- why Room was selected for local storage
- difference between Firestore and Room
- how `ExpenseEntity` and `Expense` are mapped
- how `ExpenseDao` exposes database operations
- how `ExpenseRepository` coordinates Room and Firestore
- how Add Expense provides local/cloud feedback
- how History can display saved expense records
- how the unit test supports business-logic correctness

---

## Why this project matters

SmartLedger demonstrates:

- secure handling of user access
- separation of UI and business logic
- cloud-based storage of financial records
- Room-based local storage for offline expense availability
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
- WorkManager-based background sync strategy between Room and Firestore

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
