# CW2 Contribution Matrix — Smart Ledger

## Team Contribution Overview

| Team Member | Main Implementation | Main Files / Packages | Testing Contribution | Report / Viva Contribution | Current Status |
|---|---|---|---|---|---:|
| Λάζαρος | Room local database and offline expense access | `data/local`, `ExpenseRepository`, `ExpenseViewModel`, `ExpenseUiState`, `HistoryScreen`, `AddExpenseScreen` | Room/local storage functional testing, local-save evidence, History evidence, `SplitCalculationUseCaseTest` | Room implementation evaluation, screenshots, viva defense | **Completed Day 2** |
| Γιάννης | Biometric authentication | `data/security`, `LoginScreen`, `AuthViewModel`, `AuthUiState` | Biometric success/failure/cancel tests | Biometrics evaluation, screenshots, viva defense | Planned Day 3 |
| Ιάσωνας | Retrofit external API / client-server communication | `data/remote`, `CurrencyRepository`, `CurrencyViewModel`, `CurrencyUiState`, `CurrencyRatesScreen`, navigation | API success/error/retry tests | API evaluation, screenshots, viva defense | Planned Day 4 |
| Όλοι | Integration, polish, report and viva | README, docs, screenshots, final build | Full app UAT and final regression testing | Group demo and final evaluation | Ongoing |

---

## Individual Responsibilities

### Λάζαρος — Room / SQLite Local Storage

Main responsibility:

- Implement Room as the local storage layer for Smart Ledger expenses.
- Ensure expenses can be stored locally and accessed from local history.
- Keep UI separate from database logic by working through repository and ViewModel layers.
- Add visible local-save feedback in the Add Expense flow.
- Add screenshot and test evidence for the Room workstream.

Implementation status: **Completed in Day 2**

Main implemented files/packages:

- `app/src/main/java/com/smartledger/data/local/SmartLedgerDatabase.kt`
- `app/src/main/java/com/smartledger/data/local/dao/ExpenseDao.kt`
- `app/src/main/java/com/smartledger/data/local/entity/ExpenseEntity.kt`
- `app/src/main/java/com/smartledger/data/local/mapper/ExpenseMappers.kt`
- `app/src/main/java/com/smartledger/data/repository/ExpenseRepository.kt`
- `app/src/main/java/com/smartledger/ui/viewmodel/ExpenseViewModel.kt`
- `app/src/main/java/com/smartledger/ui/viewmodel/ExpenseUiState.kt`
- `app/src/main/java/com/smartledger/ui/screens/history/HistoryScreen.kt`
- `app/src/main/java/com/smartledger/ui/screens/expense/AddExpenseScreen.kt`
- `app/src/test/java/com/smartledger/domain/usecase/SplitCalculationUseCaseTest.kt`

Technical contribution summary:

- Added Room / SQLite local persistence.
- Added `ExpenseEntity` as the Room representation of an expense.
- Added `ExpenseDao` with insert, bulk insert, query and sync-status update operations.
- Added `SmartLedgerDatabase` as the Room database singleton.
- Added mapper functions between `Expense` and `ExpenseEntity`.
- Updated `ExpenseRepository` so Add Expense saves locally first and then attempts Firestore sync.
- Updated `ExpenseViewModel` and `ExpenseUiState` to support local/cloud feedback messages.
- Updated `AddExpenseScreen` to show local-save confirmation.
- Updated `HistoryScreen` to display local/history feedback.
- Added JUnit unit tests for `SplitCalculationUseCase`.

Testing responsibility completed:

- Room/local storage functional testing.
- Add Expense local-save runtime evidence.
- History screen evidence showing saved expense data.
- `SplitCalculationUseCaseTest` with:
  - `100 / 4 = 25`
  - `members = 0` throws `IllegalArgumentException`
- Gradle test validation.
- Full build validation.

Evidence completed:

```text
docs/cw2/screenshots/room/01_data_local_package.png
docs/cw2/screenshots/room/02_room_database_code.png
docs/cw2/screenshots/room/03_add_expense_form_filled.png
docs/cw2/screenshots/room/03_add_expense_local_save_success.png
docs/cw2/screenshots/room/04_history_local_data.png
docs/cw2/screenshots/room/05_unit_test_success.png
```

Build/test validation:

```powershell
./gradlew test --no-configuration-cache
./gradlew clean build --no-configuration-cache
```

Expected/observed result:

```text
BUILD SUCCESSFUL
```

Viva focus:

- Explain why Room was selected.
- Explain the difference between Firestore and Room.
- Explain DAO, Entity, Database, and Mapper roles.
- Explain why `isSynced` is useful in the local entity.
- Explain how repository keeps the UI independent from the storage implementation.
- Explain why this is local-first rather than UI-direct database access.

Suggested viva sentence:

> I implemented Room inside the `data/local` package to add local SQLite-based persistence for expenses. The app now saves an expense locally first and then attempts to sync it with Firestore. This means that the History screen can still display saved expenses even when cloud access is unavailable. I connected Room through `ExpenseRepository`, so the UI and ViewModel do not directly depend on the database. This keeps the architecture clean and supports offline access for financial records.

---

### Γιάννης — Biometric Authentication

Main responsibility:

- Implement Android biometric authentication as an additional device-level security step.
- Integrate biometric confirmation after Firebase login or as a protected access step.
- Handle success, cancel, failure, and unsupported-device cases.

Main expected files/packages:

- `data/security/BiometricAuthManager.kt`
- `ui/screens/auth/LoginScreen.kt`
- `ui/viewmodel/AuthViewModel`
- `ui/viewmodel/AuthUiState`

Testing responsibility:

- Biometric success test.
- Biometric cancel/failure test.
- Unsupported device or emulator fallback test.
- Screenshots of biometric prompt and fallback/error handling.

Current status: **Planned for Day 3**

Viva focus:

- Explain AndroidX Biometric.
- Explain that Firebase Authentication validates the account, while biometrics confirm the local device user.
- Explain how cancellation/failure is handled.
- Explain why biometrics are appropriate for a financial app.

Suggested viva sentence:

> Firebase Authentication verifies the account credentials, while biometric authentication adds a local device-level confirmation. This is important because Smart Ledger handles personal financial data.

---

### Ιάσωνας — Retrofit External API

Main responsibility:

- Implement client-server communication through Retrofit.
- Fetch external financial/currency data as JSON.
- Display the data through a Compose UI screen using ViewModel state.
- Handle loading, success, error, and retry states.

Main expected files/packages:

- `data/remote/api/CurrencyApiService.kt`
- `data/remote/dto/CurrencyRatesResponse.kt`
- `data/remote/RetrofitClient.kt`
- `data/repository/CurrencyRepository.kt`
- `ui/viewmodel/CurrencyUiState.kt`
- `ui/viewmodel/CurrencyViewModel.kt`
- `ui/screens/api/CurrencyRatesScreen.kt`
- `ui/navigation/Screen`
- `ui/navigation/SmartLedgerNavHost.kt`
- `ui/screens/dashboard/DashboardScreen.kt`

Testing responsibility:

- API success test.
- API network failure/error test.
- Retry behaviour test.
- Screenshots of loading, success, and error states.

Current status: **Planned for Day 4**

Viva focus:

- Explain Retrofit.
- Explain DTOs and JSON parsing.
- Explain repository and ViewModel separation.
- Explain loading/success/error UI states.

Suggested viva sentence:

> I used Retrofit to connect the app with an external financial API. The JSON response is mapped to Kotlin DTO classes and exposed through a repository and ViewModel, so the UI remains separate from networking logic.

---

## Shared Team Responsibilities

All team members contribute to:

- Final integration.
- Clean build verification.
- Manual testing and user acceptance testing.
- Screenshots and evidence organisation.
- Report writing and proofreading.
- Critical reflection.
- PowerPoint / viva preparation.
- Live demo rehearsal.

## Current Implementation Timeline

| Day | Focus | Status |
|---|---|---:|
| Day 1 | Baseline, documentation, Jira, setup evidence | Completed |
| Day 2 | Room / SQLite local storage — Λάζαρος | Completed |
| Day 3 | Biometric authentication — Γιάννης | Planned |
| Day 4 | Retrofit external API — Ιάσωνας | Planned |
| Day 5 | Integration, final testing, report, viva | Planned |

## Final Report Alignment

This contribution matrix must remain consistent with:

- The final academic report.
- The GitHub repository evidence.
- The screenshots included in the appendix.
- The viva/code defense explanations.
- The final Jira/Kanban board status.

## Notes

Each member must be able to explain the code they implemented. According to the CW2 requirements, the viva evaluates each member individually through code defense, so the final implementation and presentation must clearly separate responsibilities.
