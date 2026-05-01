# CN6008 CW2 — Smart Ledger Final App Completion Plan

**Project:** Smart Ledger  
**Course:** CN6008 — Advanced Topics in Computer Science  
**Coursework:** CW2 / CWRK2 — Final App & Testing  
**Team:** Λάζαρος, Γιάννης, Ιάσωνας  
**Working Goal:** Να ολοκληρωθεί το Smart Ledger ως τελική Android FinTech εφαρμογή, με καθαρή αρχιτεκτονική, 3 προηγμένες τεχνολογίες, τεκμηριωμένο testing, report και σωστή προετοιμασία viva.

---

## 1. Executive Summary

Το Smart Ledger ξεκίνησε στο CW1 ως Android FinTech prototype για ασφαλή καταγραφή προσωπικών εξόδων, ιστορικό συναλλαγών και ομαδικό διαμοιρασμό λογαριασμών. Στο CW2 ο στόχος είναι να το εξελίξουμε σε πιο ολοκληρωμένη mobile εφαρμογή, κρατώντας την υπάρχουσα καθαρή αρχιτεκτονική και προσθέτοντας 3 προηγμένες τεχνολογίες:

1. **Room / SQLite Local Storage** — Λάζαρος  
2. **Biometric Authentication** — Γιάννης  
3. **Retrofit External API / Client-Server Communication** — Ιάσωνας  

Η τελική ιστορία του project πρέπει να είναι ενιαία:

```text
Smart Ledger authenticates the user through Firebase,
adds local device-level protection with biometrics,
stores expense records in Firestore,
keeps them available offline through Room,
and enriches the FinTech experience through live API data using Retrofit.
```

Αυτή η φράση πρέπει να καθοδηγεί το report, το PowerPoint, το live demo και το viva.

**Current progress:** Day 1 has been completed and merged into `main`. Day 2, assigned to Λάζαρος, has implemented Room / SQLite local storage on the `feature/room-offline-storage` branch, with build, unit test and runtime evidence completed.

---

## 2. CW2 Assessment Strategy

| Κριτήριο Αξιολόγησης | Βάρος | Στρατηγική για Άριστα |
|---|---:|---|
| Βασική Υλοποίηση & Ποιότητα Κώδικα | 30% | Καθαρό UI, σωστή πλοήγηση, ViewModels, repositories, error handling, χωρίς crashes |
| Ενσωμάτωση Τεχνολογιών | 20% | Room/offline storage, Biometrics, Retrofit API να λειτουργούν απρόσκοπτα |
| Στρατηγική Ελέγχου | 15% | Test Plan με test cases, Expected vs Actual, screenshots, 1–2 unit tests |
| Κριτική Αξιολόγηση & Report | 15% | 1500-word report, implementation evaluation, reflection, Harvard references, contribution matrix |
| Viva Voce | 20% | Κάθε μέλος να εξηγεί καθαρά το δικό του κομμάτι και να το δείχνει στο live demo |

---

## 3. Current Project Foundation

Το υπάρχον Smart Ledger έχει ήδη:

- Firebase Authentication
- Cloud Firestore
- User registration/login/logout
- Add Expense
- Expense History
- Group / Split workflow
- Kotlin + Jetpack Compose
- Navigation Compose
- ViewModels
- UiState classes
- Repository pattern
- `SplitCalculationUseCase` για equal split logic
- Room / SQLite offline expense storage implemented in Day 2 on `feature/room-offline-storage`

Η βασική τωρινή αρχιτεκτονική είναι:

```text
UI Screens → ViewModels / UiState → Repositories → Firebase / Data Sources
```

Άρα στο CW2 **δεν κάνουμε rebuild από την αρχή**. Επεκτείνουμε προσεκτικά την υπάρχουσα δομή.

---

## 4. Current Code Architecture

Η τωρινή δομή του project είναι περίπου:

```text
com.smartledger/
├── data/
│   ├── model/
│   │   ├── Expense
│   │   ├── Group
│   │   ├── SplitRecord
│   │   └── UserProfile
│   ├── remote/
│   └── repository/
│       ├── AuthRepository
│       ├── ExpenseRepository
│       └── GroupRepository
│
├── domain.usecase/
│   └── SplitCalculationUseCase
│
├── ui/
│   ├── navigation/
│   │   ├── Screen
│   │   └── SmartLedgerNavHost.kt
│   ├── screens/
│   │   ├── auth/
│   │   │   ├── LoginScreen.kt
│   │   │   ├── RegisterScreen.kt
│   │   │   └── WelcomeScreen.kt
│   │   ├── dashboard/
│   │   │   └── DashboardScreen.kt
│   │   ├── expense/
│   │   │   └── AddExpenseScreen.kt
│   │   ├── group/
│   │   │   └── GroupScreen.kt
│   │   └── history/
│   │       └── HistoryScreen.kt
│   ├── theme/
│   └── viewmodel/
│       ├── AuthUiState
│       ├── AuthViewModel
│       ├── ExpenseUiState
│       ├── ExpenseViewModel
│       ├── GroupUiState
│       └── GroupViewModel
│
└── MainActivity
```

---

## 5. Architecture Rule for CW2

### Do not over-refactor.

Για να κρατήσουμε το project ασφαλές, καθαρό και εύκολο για viva:

- Δεν αλλάζουμε μαζικά τη δομή.
- Δεν μεταφέρουμε υπάρχοντα files χωρίς λόγο.
- Δεν βάζουμε πολύπλοκο dependency injection τώρα, εκτός αν είναι απολύτως απαραίτητο.
- Δεν κάνουμε περίπλοκο sync engine.
- Δεν χαλάμε το υπάρχον navigation.
- Δεν μπερδεύουμε UI code με data access.
- Προσθέτουμε μόνο μικρά, καθαρά packages για τις νέες τεχνολογίες.

---

## 6. Target Architecture After CW2

```text
com.smartledger/
├── data/
│   ├── model/
│   │   ├── Expense
│   │   ├── Group
│   │   ├── SplitRecord
│   │   ├── UserProfile
│   │   └── CurrencyRate.kt                  [NEW optional]
│   │
│   ├── local/                               [IMPLEMENTED Day 2 - Λάζαρος]
│   │   ├── SmartLedgerDatabase.kt
│   │   ├── dao/
│   │   │   └── ExpenseDao.kt
│   │   ├── entity/
│   │   │   └── ExpenseEntity.kt
│   │   └── mapper/
│   │       └── ExpenseMappers.kt
│   │
│   ├── remote/                              [EXISTING - Ιάσωνας uses this]
│   │   ├── api/
│   │   │   └── CurrencyApiService.kt
│   │   ├── dto/
│   │   │   └── CurrencyRatesResponse.kt
│   │   └── RetrofitClient.kt
│   │
│   ├── security/                            [NEW - Γιάννης]
│   │   └── BiometricAuthManager.kt
│   │
│   └── repository/
│       ├── AuthRepository
│       ├── ExpenseRepository                [IMPLEMENTED Day 2: Firestore + Room]
│       ├── GroupRepository
│       └── CurrencyRepository.kt            [NEW]
│
├── domain.usecase/
│   ├── SplitCalculationUseCase
│   └── CurrencyConversionUseCase.kt         [NEW optional]
│
├── ui/
│   ├── navigation/
│   │   ├── Screen                           [UPDATED with API route]
│   │   └── SmartLedgerNavHost.kt            [UPDATED with new screen]
│   │
│   ├── screens/
│   │   ├── auth/
│   │   │   ├── LoginScreen.kt               [UPDATED for biometric flow]
│   │   │   ├── RegisterScreen.kt
│   │   │   └── WelcomeScreen.kt
│   │   ├── dashboard/
│   │   │   └── DashboardScreen.kt           [UPDATED with API button/card]
│   │   ├── expense/
│   │   │   └── AddExpenseScreen.kt          [IMPLEMENTED Day 2: local-save feedback]
│   │   ├── group/
│   │   │   └── GroupScreen.kt
│   │   ├── history/
│   │   │   └── HistoryScreen.kt             [IMPLEMENTED Day 2: offline/local data]
│   │   └── api/                             [NEW]
│   │       └── CurrencyRatesScreen.kt
│   │
│   └── viewmodel/
│       ├── AuthUiState                      [UPDATED]
│       ├── AuthViewModel                    [UPDATED]
│       ├── ExpenseUiState                   [IMPLEMENTED Day 2]
│       ├── ExpenseViewModel                 [IMPLEMENTED Day 2]
│       ├── GroupUiState
│       ├── GroupViewModel
│       ├── CurrencyUiState.kt               [NEW]
│       └── CurrencyViewModel.kt             [NEW]
│
└── MainActivity
```

---

## 7. Team Responsibilities

| Μέλος | Technology | Main Responsibility | Main Packages / Files |
|---|---|---|---|
| Λάζαρος | Technology 2 — Local Data Storage | Room / SQLite local storage, offline expense access | `data/local`, `ExpenseRepository`, `ExpenseViewModel`, `ExpenseUiState`, `HistoryScreen`, `AddExpenseScreen` |
| Γιάννης | Technology 6 — Biometric Security | Fingerprint / Face biometric login confirmation | `data/security`, `AuthViewModel`, `AuthUiState`, `LoginScreen` |
| Ιάσωνας | Technology 1 — Client/Server Communication | Retrofit external API, JSON parsing, financial/currency data | `data/remote`, `CurrencyRepository`, `CurrencyViewModel`, `CurrencyUiState`, `CurrencyRatesScreen`, `Screen`, `SmartLedgerNavHost` |
| Όλοι | Testing / Report / Viva | Integration, screenshots, documentation, presentation | `test`, `docs`, README, report, PowerPoint |

---

## 8. Git Workflow

Recommended branches:

```text
feature/room-offline-storage
feature/biometric-auth
feature/external-finance-api
feature/testing-and-polish
```

Before starting work:

```bash
git status
git pull
./gradlew clean build
```

After finishing a feature:

```bash
git status
git add .
git commit -m "Add Room offline expense storage"
git push
```

Suggested commit messages:

```text
Add Room offline expense storage
Add biometric login confirmation
Add Retrofit financial API integration
Add CW2 testing evidence and documentation
Polish Smart Ledger final CW2 demo flow
```
---

## 8.1 README and GitHub Repository Polish

After Day 1, the project README was updated to reflect the CW2 phase.

README updates should include:

```text
✅ CW1-to-CW2 transition
✅ Planned Room / SQLite offline storage
✅ Planned biometric authentication
✅ Planned Retrofit API integration
✅ CW2 documentation and evidence folder
✅ Jira/Kanban workflow
✅ Testing strategy
✅ Team Git workflow
✅ Author/team section with GitHub profile links
```

Recommended Git workflow for README-only updates:

```bash
git checkout main
git pull
git checkout -b docs/update-readme-cw2
git add README.md
git commit -m "Update README with CW2 development plan"
git push -u origin docs/update-readme-cw2
```

This is a documentation-only branch and should be merged into `main` through a pull request.


---

# 9. 5-Day Completion Plan

---

## Day 1 — Architecture Baseline and CW2 Preparation

### Goal

Να επιβεβαιωθεί ότι το υπάρχον project λειτουργεί σωστά και να οργανωθεί το CW2 με βάση την τωρινή δομή.

### Tasks

1. Pull latest repository.
2. Run clean build.
3. Capture screenshot of current package structure.
4. Check current app flow:
   - Welcome
   - Register
   - Login
   - Dashboard
   - Add Expense
   - History
   - Group Split
5. Create `docs/cw2` structure.
6. Create Jira/Kanban CW2 board.
7. Confirm package decisions:
   - Room → `data/local`
   - Retrofit → existing `data/remote`
   - Biometrics → `data/security`
   - API screen → `ui/screens/api`
   - API state → `ui/viewmodel/CurrencyUiState.kt`
8. Create initial contribution matrix.
9. Create initial test plan skeleton.

### Commands

```bash
git status
git pull
./gradlew clean build
```

### Documentation Structure

```text
docs/
└── cw2/
    ├── test-plan.md
    ├── contribution-matrix.md
    ├── report-draft.md
    └── screenshots/
        ├── setup/
        ├── room/
        ├── biometrics/
        ├── api/
        ├── testing/
        └── viva/
```

### Deliverables

```text
✅ Existing project builds successfully
✅ Current architecture screenshot captured
✅ Jira board created
✅ Team contribution matrix drafted
✅ Test plan skeleton created
```

### Evidence

Final Day 1 evidence files saved in the repository:

```text
docs/cw2/screenshots/setup/01_git_status_clean.png
docs/cw2/screenshots/setup/02_gradle_build_success.png
docs/cw2/screenshots/setup/03_welcome_screen.png
docs/cw2/screenshots/setup/04_login_screen.png
docs/cw2/screenshots/setup/05_dashboard_screen.png
docs/cw2/screenshots/setup/06_add_expense_screen.png
docs/cw2/screenshots/setup/07_history_screen.png
docs/cw2/screenshots/setup/08_group_split_screen.png
docs/cw2/screenshots/setup/09_docs_cw2_structure.png
docs/cw2/screenshots/setup/10_current_code_architecture.png
docs/cw2/screenshots/setup/11_jira_cw2_board.png
```

---

### Day 1 Completion Status

Day 1 has been completed and merged into the `main` branch.

Completed items:

```text
✅ Git clean baseline confirmed
✅ Gradle clean build completed successfully
✅ Existing Smart Ledger app flow tested
✅ CW2 documentation folder created
✅ day1-baseline.md created
✅ test-plan.md created
✅ contribution-matrix.md created
✅ Jira/Kanban board created
✅ Setup screenshots saved
✅ Feature branch created
✅ Pull request opened and merged
✅ main branch updated and clean
```

Git branch used:

```text
feature/day1-cw2-setup
```

Final Day 1 Git result:

```text
On branch main
Your branch is up to date with 'origin/main'.

nothing to commit, working tree clean
```

Jira workflow used:

```text
To Do → In Progress → Code Review → Testing → Done
```

Final Day 1 evidence files:

```text
docs/cw2/screenshots/setup/01_git_status_clean.png
docs/cw2/screenshots/setup/02_gradle_build_success.png
docs/cw2/screenshots/setup/03_welcome_screen.png
docs/cw2/screenshots/setup/04_login_screen.png
docs/cw2/screenshots/setup/05_dashboard_screen.png
docs/cw2/screenshots/setup/06_add_expense_screen.png
docs/cw2/screenshots/setup/07_history_screen.png
docs/cw2/screenshots/setup/08_group_split_screen.png
docs/cw2/screenshots/setup/09_docs_cw2_structure.png
docs/cw2/screenshots/setup/10_current_code_architecture.png
docs/cw2/screenshots/setup/11_jira_cw2_board.png
```

Day 1 is now closed. The next implementation branch should start from the updated `main` branch.

Recommended next branch:

```bash
git checkout main
git pull
git checkout -b feature/room-offline-storage
```


---

## Day 2 — Λάζαρος: Room / SQLite Local Storage ✅ COMPLETED

### Goal

Να ενσωματωθεί Room local database ώστε τα έξοδα να αποθηκεύονται και τοπικά και να εμφανίζονται ακόμη και χωρίς σύνδεση.

### Main Feature

**Technology 2: Local Data Storage**

### Completion Status

Day 2 has been completed on the feature branch:

```text
feature/room-offline-storage
```

The Room / SQLite implementation has been added successfully, the app builds without errors, the unit tests pass, and the runtime demo confirmed that a new expense can be saved locally and synced to Firestore.

Final validation completed:

```text
✅ ./gradlew test --no-configuration-cache → BUILD SUCCESSFUL
✅ ./gradlew clean build --no-configuration-cache → BUILD SUCCESSFUL
✅ App runtime demo completed on emulator
✅ Add Expense flow confirmed
✅ History screen confirmed
✅ Room screenshots captured
✅ Jira ROOM tasks ready to move to Done
```

Acceptable warnings observed during build:

```text
android.disallowKotlinSourceSets=false is experimental
Unable to strip the following libraries...
```

These are warnings, not build failures. They do not block submission or demo evidence.

---

### New Package Implemented

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

Purpose of each file:

| File | Purpose |
|---|---|
| `ExpenseEntity.kt` | Room-specific local representation of an expense record |
| `ExpenseDao.kt` | DAO layer for inserting, updating and reading local expenses |
| `SmartLedgerDatabase.kt` | Central Room database class and singleton database instance |
| `ExpenseMappers.kt` | Converts between `Expense` and `ExpenseEntity` |

---

### Existing Files Updated

```text
app/build.gradle.kts
build.gradle.kts
gradle/libs.versions.toml
gradle.properties
app/src/main/java/com/smartledger/data/repository/ExpenseRepository.kt
app/src/main/java/com/smartledger/ui/viewmodel/ExpenseViewModel.kt
app/src/main/java/com/smartledger/ui/viewmodel/ExpenseUiState.kt
app/src/main/java/com/smartledger/ui/screens/expense/AddExpenseScreen.kt
app/src/main/java/com/smartledger/ui/screens/history/HistoryScreen.kt
app/src/test/java/com/smartledger/domain/usecase/SplitCalculationUseCaseTest.kt
```

### Final Implementation Logic

```text
Add Expense
→ create Expense model
→ save to Room first with isSynced = false
→ attempt Firestore save
→ if Firestore succeeds, update local isSynced = true
→ update ExpenseUiState with local/cloud feedback message

History
→ repository reads local Room expenses first
→ attempts Firestore refresh
→ if Firestore succeeds, refresh Room cache
→ if Firestore fails, display saved Room expenses
```

### Final User Messages Implemented

```text
Expense saved locally and synced to cloud.
Expense saved locally. Sync will be attempted when online.
Showing saved expenses from local storage when cloud data is unavailable.
```

### Unit Test Implemented

```text
SplitCalculationUseCaseTest
✅ totalAmount = 100, members = 4 → share = 25
✅ members = 0 → throws IllegalArgumentException
```

The optional `ExpenseMappersTest` was not required for Day 2 and can be added later if extra testing depth is needed.

---

### Deliverables Completed

```text
✅ Room database added
✅ ExpenseEntity added
✅ ExpenseDao added
✅ SmartLedgerDatabase added
✅ ExpenseRepository updated for local-first persistence
✅ ExpenseViewModel updated for Room-aware repository
✅ ExpenseUiState updated with localInfoMessage
✅ AddExpenseScreen shows local/cloud save feedback
✅ HistoryScreen reads local/offline expenses through repository fallback
✅ SplitCalculationUseCase unit test added
✅ Gradle test passed
✅ Full clean build passed
✅ Runtime demo confirmed
✅ Room evidence screenshots captured
```

### Jira ROOM Cards Status

Move the following Jira cards to **Done**:

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

If additional Jira cards exist for `AddExpenseScreen feedback`, `ExpenseViewModel`, `ExpenseUiState`, `Unit test`, or `Build/Test validation`, these can also be moved to **Done**.

---

### Evidence Captured

Final evidence files for Day 2:

```text
docs/cw2/screenshots/room/01_data_local_package.png
docs/cw2/screenshots/room/02_room_database_code.png
docs/cw2/screenshots/room/03_add_expense_form_filled.png
docs/cw2/screenshots/room/03_add_expense_local_save_success.png
docs/cw2/screenshots/room/04_history_local_data.png
docs/cw2/screenshots/room/05_unit_test_success.png
```

Recommended report captions:

| Screenshot | Caption |
|---|---|
| `01_data_local_package.png` | `data/local` package structure created for Room offline storage. |
| `02_room_database_code.png` | `SmartLedgerDatabase` configuration showing Room database setup and DAO access. |
| `03_add_expense_form_filled.png` | Add Expense screen filled with test data before saving. |
| `03_add_expense_local_save_success.png` | Local-first save confirmation showing Room and Firestore sync feedback. |
| `04_history_local_data.png` | Expense History screen displaying saved local expense data. |
| `05_unit_test_success.png` | Gradle test and clean build passing after Room integration. |

---

### Suggested Commit

Before commit, ensure there are no untracked screenshots:

```bash
git status
git add .
git status
git commit -m "Add Room offline expense storage"
git push -u origin feature/room-offline-storage
```

After the branch is pushed, open a pull request into `main`. Once reviewed/merged, Day 2 is officially closed and the next feature branch can start.

Recommended next branch after merge:

```bash
git checkout main
git pull
git checkout -b feature/biometric-auth
```

---

### Viva Defense — Λάζαρος

> I implemented Room inside the `data/local` package to add local SQLite-based persistence for expenses. The app now saves an expense locally first and then attempts to sync it with Firestore. This means that the History screen can still display saved expenses even when cloud access is unavailable. I connected Room through `ExpenseRepository`, so the UI and ViewModel do not directly depend on the database. This keeps the architecture clean and supports offline access for financial records.

### Key Viva Points — Λάζαρος

Must be able to explain:

```text
✅ Why Room was selected for local persistence
✅ Difference between Firestore and Room
✅ Why ExpenseEntity is separate from Expense
✅ What isSynced means
✅ What ExpenseDao does
✅ How SmartLedgerDatabase exposes the DAO
✅ How ExpenseRepository keeps UI independent from database details
✅ How Add Expense works with local-first storage
✅ How History can still show locally saved data if cloud access fails
✅ How SplitCalculationUseCaseTest supports testing evidence
```

### Short Viva Answer

> Room was added to the data layer so the UI remains independent from the source of data. This improves offline availability and makes the app more robust as a mobile finance application.

---

## Day 3 — Γιάννης: Biometric Authentication

### Goal

Να προστεθεί biometric authentication ως δεύτερο επίπεδο ασφάλειας μετά το Firebase login.

### Main Feature

**Technology 6: Biometric Security**

### New Package

```text
data/security/
└── BiometricAuthManager.kt
```

### Existing Files to Update

```text
ui/screens/auth/LoginScreen.kt
ui/viewmodel/AuthViewModel
ui/viewmodel/AuthUiState
```

### Implementation Logic

```text
Firebase login succeeds
→ check biometric availability
→ show biometric prompt
→ success navigates to Dashboard
→ failure/cancel shows error or blocks access
→ unsupported device uses fallback
```

### Important Viva Explanation

```text
Firebase Authentication = verifies the account credentials
Biometric Authentication = confirms the local device user
```

### Edge Cases

```text
Biometric hardware unavailable
No fingerprint/face enrolled
User cancels authentication
Biometric authentication fails
Biometric authentication succeeds
Fallback works on emulator
```

### Deliverables

```text
✅ AndroidX Biometric dependency added
✅ BiometricAuthManager added
✅ Login flow updated
✅ AuthViewModel/AuthUiState updated if needed
✅ Fallback for unsupported devices
✅ Error handling for cancel/failure
```

### Suggested Commit

```bash
git add .
git commit -m "Add biometric login confirmation"
```

### Evidence

```text
docs/cw2/screenshots/biometrics/01_biometric_prompt.png
docs/cw2/screenshots/biometrics/02_biometric_success.png
docs/cw2/screenshots/biometrics/03_biometric_cancelled.png
docs/cw2/screenshots/biometrics/04_biometric_fallback.png
```

### Viva Defense — Γιάννης

> I implemented biometric authentication as an additional local security layer. Firebase confirms the account credentials, while biometrics confirm that the person using the device is authorised to open the financial app.

---

## Day 4 — Ιάσωνας: Retrofit External API

### Goal

Να αξιοποιηθεί το υπάρχον `data/remote` package για client/server communication μέσω Retrofit και JSON parsing.

### Main Feature

**Technology 1: Client/Server Communication**

### Suggested API Feature

Προτείνεται external financial API για:

- Currency exchange rates
- Currency conversion
- EUR to USD/GBP rates
- Simple live finance data

This fits naturally with Smart Ledger as a FinTech app.

### Updated `data/remote`

```text
data/remote/
├── api/
│   └── CurrencyApiService.kt
├── dto/
│   └── CurrencyRatesResponse.kt
└── RetrofitClient.kt
```

### New Files

```text
data/repository/CurrencyRepository.kt
ui/viewmodel/CurrencyUiState.kt
ui/viewmodel/CurrencyViewModel.kt
ui/screens/api/CurrencyRatesScreen.kt
```

### Existing Files to Update

```text
ui/navigation/Screen
ui/navigation/SmartLedgerNavHost.kt
ui/screens/dashboard/DashboardScreen.kt
```

### Implementation Logic

```text
Dashboard
→ user taps Currency Rates
→ CurrencyViewModel calls CurrencyRepository
→ CurrencyRepository calls Retrofit API
→ JSON response maps to DTO
→ UI displays loading/success/error state
```

### Required UI States

```text
Loading
Success
Network error
Invalid response
Retry
```

### Deliverables

```text
✅ Retrofit dependency added
✅ Gson converter added
✅ CurrencyApiService added
✅ CurrencyRatesResponse DTO added
✅ RetrofitClient added
✅ CurrencyRepository added
✅ CurrencyViewModel / CurrencyUiState added
✅ CurrencyRatesScreen added
✅ Navigation updated
✅ Error/retry handling implemented
```

### Suggested Commit

```bash
git add .
git commit -m "Add Retrofit financial API integration"
```

### Evidence

```text
docs/cw2/screenshots/api/01_data_remote_package.png
docs/cw2/screenshots/api/02_currency_screen_loading.png
docs/cw2/screenshots/api/03_currency_screen_success.png
docs/cw2/screenshots/api/04_api_error_retry.png
docs/cw2/screenshots/api/05_dashboard_api_button.png
```

### Viva Defense — Ιάσωνας

> I used the existing `data/remote` package to implement Retrofit client/server communication. The API response is received as JSON, mapped to DTO classes and exposed through a repository and ViewModel, keeping the UI separate from networking logic.

---

## Day 5 — Integration, Testing, Report and Viva

### Goal

Να ενοποιηθούν όλες οι λειτουργίες και να ετοιμαστεί πλήρες evidence για report και viva.

### Full Integration Checklist

```text
✅ App builds successfully
✅ Register works
✅ Login works
✅ Biometric flow works or fallback works
✅ Add Expense works
✅ Expense saves locally through Room [validated in Day 2]
✅ Expense saves remotely through Firestore [validated in Day 2]
✅ History shows local/offline expenses [validated in Day 2]
✅ Group Split works
✅ Currency API screen works
✅ API error/retry state works
✅ Logout works
```

### Commands

```bash
./gradlew clean build
./gradlew test
```

### Test Plan Table

| Test ID | Feature | Scenario | Expected Result | Actual Result | Status | Evidence |
|---|---|---|---|---|---|---|
| TC01 | Authentication | Valid Firebase login | User logs in | User logged in | Pass | Screenshot |
| TC02 | Biometrics | Successful fingerprint/face | Dashboard opens | Dashboard opened | Pass | Screenshot |
| TC03 | Biometrics | User cancels prompt | Access blocked or fallback shown | Correct result shown | Pass | Screenshot |
| TC04 | Room | Add expense | Expense saved locally | Saved in Room | Pass | Screenshot |
| TC05 | Room | Open history offline | Cached expenses shown | Expenses shown | Pass | Screenshot |
| TC06 | API | Fetch currency rates | JSON parsed and displayed | Rates displayed | Pass | Screenshot |
| TC07 | API | Network/API failure | Error and retry shown | Error shown | Pass | Screenshot |
| TC08 | Group Split | 100 split by 4 | Share is 25 | Share is 25 | Pass | Unit Test |
| TC09 | Validation | Empty expense title | Error message shown | Error shown | Pass | Screenshot |
| TC10 | Logout | User logs out | User returns to auth flow | Correct behaviour | Pass | Screenshot |

### Testing Evidence

```text
docs/cw2/screenshots/testing/01_gradle_test_success.png
docs/cw2/screenshots/testing/02_split_calculation_unit_test.png
docs/cw2/screenshots/testing/03_build_success_after_merge.png
docs/cw2/screenshots/testing/04_full_app_demo_flow.png
```

---

# 10. CW2 Report Structure

Target: approximately 1500 words.

```text
1. Introduction and CW1-to-CW2 Transition
2. Current Code Architecture
3. Final Implementation Overview
4. Technology Integration
   4.1 Room Local Storage — Λάζαρος
   4.2 Biometric Authentication — Γιάννης
   4.3 Retrofit External API — Ιάσωνας
5. Testing Strategy and Results
6. Implementation Evaluation
7. Critical Reflection
8. Contribution Matrix
9. Conclusion
References
Appendix: Screenshots
```

---

## Suggested Report Content

### 1. Introduction and CW1-to-CW2 Transition

Explain that Smart Ledger evolved from a CW1 prototype into a more complete Android FinTech application.

### 2. Current Code Architecture

Mention:

- Kotlin
- Jetpack Compose
- Firebase Authentication
- Cloud Firestore
- MVVM-style architecture
- Repositories
- ViewModels
- UiState
- Domain use case

### 3. Technology Integration

Each member writes their own subsection:

- What was implemented
- Why it was useful
- How it was integrated
- How it was tested
- Which screenshots prove it

### 4. Testing Strategy and Results

Include:

- Black-box testing
- User acceptance testing
- Expected vs Actual table
- Screenshots
- Unit tests

### 5. Critical Reflection

Discuss:

- Integration difficulties
- Emulator/device limitations
- API error handling
- Offline storage trade-offs
- Future improvements

### 6. Contribution Matrix

Include clear individual contribution.

---

# 11. Contribution Matrix Template

| Team Member | Main Implementation | Main Files / Packages | Testing Contribution | Report / Viva Contribution |
|---|---|---|---|---|
| Λάζαρος | Room local database and offline expense access | `data/local`, `ExpenseRepository`, `ExpenseViewModel`, `ExpenseUiState`, `HistoryScreen`, `AddExpenseScreen` | Room/offline tests, split unit test | Room evaluation, screenshots, viva defense |
| Γιάννης | Biometric security | `data/security`, `LoginScreen`, `AuthViewModel`, `AuthUiState` | Biometric success/failure/cancel tests | Biometrics evaluation, screenshots, viva defense |
| Ιάσωνας | Retrofit external API | `data/remote`, `CurrencyRepository`, `CurrencyViewModel`, `CurrencyUiState`, `CurrencyRatesScreen`, navigation | API success/error/retry tests | API evaluation, screenshots, viva defense |
| Όλοι | Integration, polish, report and viva | README, docs, screenshots, final build | Full app UAT | Group demo and final evaluation |

---

# 12. PowerPoint / Viva Structure

Recommended: 8 slides.

```text
Slide 1 — Smart Ledger CW2 Final App
Slide 2 — CW1 Foundation and CW2 Goal
Slide 3 — Current Code Architecture
Slide 4 — Technology 2: Room Offline Storage — Λάζαρος
Slide 5 — Technology 6: Biometrics — Γιάννης
Slide 6 — Technology 1: Retrofit API — Ιάσωνας
Slide 7 — Testing Strategy and Results
Slide 8 — Live Demo Flow and Contribution Matrix
```

---

# 13. Live Demo Flow

Recommended order:

```text
1. Open app → Welcome Screen
2. Login with Firebase
3. Complete biometric confirmation
4. Open Dashboard
5. Add new expense
6. Show saved expense in History
7. Explain Room/offline storage
8. Create Group Split
9. Open Currency Rates screen
10. Show API response
11. Optionally show error/retry handling
12. Logout
```

---

# 14. Individual Viva Preparation

## Λάζαρος — Room / Offline Storage

Must be able to explain:

- Why Room was selected
- Difference between Firestore and Room
- How `ExpenseEntity` and `Expense` are mapped
- How `ExpenseDao` works
- How `ExpenseRepository` separates UI from storage logic
- How offline access is demonstrated
- How unit tests support correctness

Key phrase:

> Room was added to the data layer so the UI remains independent from the source of data. This improves offline availability and makes the app more robust as a mobile finance application.

---

## Γιάννης — Biometrics

Must be able to explain:

- What AndroidX Biometric does
- Difference between Firebase Authentication and Biometrics
- Why biometrics improve security
- How unsupported devices are handled
- What happens if user cancels the prompt
- How this protects financial data

Key phrase:

> Biometrics act as a second device-level confirmation after Firebase login. This is useful because Smart Ledger handles personal financial data.

---

## Ιάσωνας — Retrofit API

Must be able to explain:

- What Retrofit is
- What JSON parsing means
- Why external financial API fits the app
- What DTO classes are
- How loading/success/error states work
- How retry/error handling improves reliability

Key phrase:

> Retrofit allows the app to communicate with an external financial API and convert JSON responses into Kotlin data classes, making the Smart Ledger more dynamic and connected.

---

# 15. Final Checklist Before Submission

## Code

```text
✅ App compiles without errors
✅ App runs on emulator/device
✅ Firebase login works
✅ Firestore storage works
✅ Room storage works [Day 2 completed]
✅ Offline history works [Day 2 completed]
✅ Biometrics works or fallback works
✅ Retrofit API works
✅ Error handling implemented
✅ No obvious crashes
```

## Testing

```text
✅ Test plan completed
✅ Expected vs Actual table completed
✅ Screenshots captured
✅ Unit tests run successfully
✅ Build/test screenshots saved
```

## Report

```text
✅ 1500-word academic report
✅ Testing strategy included
✅ Implementation evaluation included
✅ Critical reflection included
✅ Contribution matrix included
✅ Harvard references included
✅ Screenshots included
✅ Exported as PDF
```

## Viva

```text
✅ PowerPoint ready
✅ Live demo script ready
✅ Each member can explain their code
✅ Each member has 2–3 prepared answers
✅ Backup screenshots/video ready in case live demo fails
```

## Submission

```text
✅ Source code zipped
✅ Report exported as PDF
✅ Only one group representative submits
✅ All names and student IDs included
✅ GitHub link included if allowed
```

---

# 16. 4-Day Emergency Version

If time becomes limited:

| Day | Focus |
|---|---|
| Day 1 | Setup, Jira, architecture update, Room start |
| Day 2 | Finish Room + implement Biometrics |
| Day 3 | Retrofit API + merge + build |
| Day 4 | Testing, screenshots, report, PPT, viva rehearsal |

The 5-day version is recommended for a stronger final result.

---

# 17. Final Priority Order

If something goes wrong, follow this priority order:

```text
1. App must compile
2. Core login/add/history/group flow must work
3. Room/offline feature must be demo-able
4. Biometrics or fallback must be demo-able
5. Retrofit API must be demo-able
6. Test plan must be complete
7. Screenshots must exist
8. Report and contribution matrix must be clear
9. Viva answers must be prepared
```

---

# 18. Final Rule for the Team

```text
Keep the existing architecture stable.
Extend it carefully.
Do not rebuild the project from scratch.
Every new technology must have:
- code package
- UI evidence
- screenshot
- test case
- viva explanation
```

Final presentation story:

```text
Smart Ledger securely authenticates the user,
protects access with biometrics,
stores financial data in the cloud,
keeps expenses available offline with Room,
and enriches the experience with live financial API data.
```

This story must appear consistently in the report, screenshots, PowerPoint and viva.
