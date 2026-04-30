# CW2 Contribution Matrix — Smart Ledger

## Team Contribution Overview

| Team Member | Main Implementation | Main Files / Packages | Testing Contribution | Report / Viva Contribution |
|---|---|---|---|---|
| Λάζαρος | Room local database and offline expense access | `data/local`, `ExpenseRepository`, `ExpenseViewModel`, `ExpenseUiState`, `HistoryScreen`, `AddExpenseScreen` | Room/offline tests, split unit test | Room implementation evaluation, screenshots, viva defense |
| Γιάννης | Biometric authentication | `data/security`, `LoginScreen`, `AuthViewModel`, `AuthUiState` | Biometric success/failure/cancel tests | Biometrics evaluation, screenshots, viva defense |
| Ιάσωνας | Retrofit external API / client-server communication | `data/remote`, `CurrencyRepository`, `CurrencyViewModel`, `CurrencyUiState`, `CurrencyRatesScreen`, navigation | API success/error/retry tests | API evaluation, screenshots, viva defense |
| Όλοι | Integration, polish, report and viva | README, docs, screenshots, final build | Full app UAT and final regression testing | Group demo and final evaluation |

## Individual Responsibilities

### Λάζαρος — Room / SQLite Local Storage

Main responsibility:
- Implement Room as the local storage layer for Smart Ledger expenses.
- Ensure expenses can be stored locally and accessed from local history.
- Keep UI separate from database logic by working through repository and ViewModel layers.

Main expected files/packages:
- `data/local/SmartLedgerDatabase.kt`
- `data/local/dao/ExpenseDao.kt`
- `data/local/entity/ExpenseEntity.kt`
- `data/local/mapper/ExpenseMappers.kt`
- `data/repository/ExpenseRepository`
- `ui/viewmodel/ExpenseViewModel`
- `ui/viewmodel/ExpenseUiState`
- `ui/screens/history/HistoryScreen.kt`
- `ui/screens/expense/AddExpenseScreen.kt`

Testing responsibility:
- Room/local storage functional testing.
- Offline/local history evidence.
- `SplitCalculationUseCaseTest`.
- Optional `ExpenseMappersTest`.

Viva focus:
- Explain why Room was selected.
- Explain the difference between Firestore and Room.
- Explain DAO, Entity, Database, and Mapper roles.
- Explain how repository keeps the UI independent from the storage implementation.

Suggested viva sentence:

> I implemented Room inside the data layer, so expenses can be stored locally and shown even when cloud access is unavailable. The UI does not communicate directly with Room; it goes through the ViewModel and repository, preserving the project architecture.

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

## Final Report Alignment

This contribution matrix must remain consistent with:
- The final academic report.
- The GitHub repository evidence.
- The screenshots included in the appendix.
- The viva/code defense explanations.

## Notes

Each member must be able to explain the code they implemented. According to the CW2 requirements, the viva evaluates each member individually through code defense, so the final implementation and presentation must clearly separate responsibilities.
