# CW2 Test Plan — Smart Ledger

## Testing Strategy

The Smart Ledger CW2 application is tested using:

- Functional testing
- Black-box testing
- User Acceptance Testing
- Basic unit testing for isolated business logic

The goal is to confirm that the final app works correctly, handles invalid input, supports the selected advanced technologies, and remains stable during normal user flows.

The testing strategy focuses on the existing CW1 functionality and the CW2 extension features:

- Firebase Authentication
- Expense creation
- Expense history
- Group split calculation
- Room / SQLite offline storage
- Biometric Authentication
- Retrofit external API communication

## Current Test Progress

| Area | Status | Notes |
|---|---:|---|
| Day 1 baseline build and app flow | Completed | Existing app flow checked and documented |
| Room / SQLite local storage | Completed | Implemented and runtime-tested in Day 2 |
| Split calculation unit test | Completed | `SplitCalculationUseCaseTest` added and passed |
| Biometric Authentication | Pending | Planned for Day 3 |
| Retrofit external API | Pending | Planned for Day 4 |
| Final regression test | Pending | Planned after all features are merged |

---

## Testing Scope

### In Scope

- Login and registration flow
- Dashboard navigation
- Add Expense functionality
- Expense History functionality
- Group Split functionality
- Room local expense storage
- Offline/local history display
- Biometric success, failure, cancel, and fallback paths
- Retrofit API loading, success, error, and retry states
- Basic unit tests for isolated logic

### Out of Scope

- Real financial payments
- OCR receipt scanning
- AI-based auto-categorisation
- Push notifications
- Production-level security penetration testing
- Production-grade background sync engine

---

## Test Cases

| Test ID | Feature | Scenario | Expected Result | Actual Result | Status | Evidence |
|---|---|---|---|---|---:|---|
| TC01 | Authentication | Valid Firebase login | User logs in and reaches Dashboard | User login flow worked during baseline/demo checks | Pass | `docs/cw2/screenshots/setup/04_login_screen.png`, `docs/cw2/screenshots/setup/05_dashboard_screen.png` |
| TC02 | Authentication | Invalid login | Error message is shown | Not executed yet in documented CW2 evidence | Pending | Screenshot pending |
| TC03 | Authentication | Logout | User returns to authentication flow | Not executed yet in documented CW2 evidence | Pending | Screenshot pending |
| TC04 | Expense | Add valid expense | Expense is saved and confirmation shown | Expense saved with message: `Expense saved locally and synced to cloud.` | Pass | `docs/cw2/screenshots/room/03_add_expense_local_save_success.png` |
| TC05 | Expense | Empty required field | Validation error is shown | Existing validation exists; documented evidence still pending | Pending | Screenshot pending |
| TC06 | History | View expense history | User expenses are displayed | Newly saved `Team Lunch` expense displayed in History | Pass | `docs/cw2/screenshots/room/04_history_local_data.png` |
| TC07 | Group Split | Split 100 between 4 members | Share per member is 25 | Unit test returned 25.0 | Pass | `docs/cw2/screenshots/room/05_unit_test_success.png` |
| TC08 | Group Split | Submit group with empty member list | Error message is shown | Not executed yet in documented CW2 evidence | Pending | Screenshot pending |
| TC09 | Room | Save expense locally | Expense is stored locally before Firestore sync attempt | Local-first repository logic implemented; runtime save succeeded | Pass | `docs/cw2/screenshots/room/03_add_expense_local_save_success.png` |
| TC10 | Room | View history from local storage | Cached/saved expenses are displayed through History flow | Saved expense displayed in History after Room integration | Pass | `docs/cw2/screenshots/room/04_history_local_data.png` |
| TC11 | Room | Firestore unavailable but local save succeeds | App shows local-save/sync-pending feedback | Code path implemented; full network-off evidence still pending for final integration test | Pending / Implemented | To capture during final regression if time allows |
| TC12 | Biometrics | Successful biometric authentication | User reaches Dashboard | Pending Day 3 | Pending | Screenshot pending |
| TC13 | Biometrics | Cancel biometric prompt | Access is blocked or fallback is shown | Pending Day 3 | Pending | Screenshot pending |
| TC14 | Biometrics | Unsupported biometric device/emulator | Fallback path is handled safely | Pending Day 3 | Pending | Screenshot pending |
| TC15 | API | Fetch currency rates | API data is displayed | Pending Day 4 | Pending | Screenshot pending |
| TC16 | API | API/network failure | Error message and retry option are shown | Pending Day 4 | Pending | Screenshot pending |
| TC17 | API | Retry after failed request | App attempts to fetch API data again | Pending Day 4 | Pending | Screenshot pending |
| TC18 | Build | Run clean build | Project compiles without errors | `BUILD SUCCESSFUL` after Room integration | Pass | `docs/cw2/screenshots/room/05_unit_test_success.png` |
| TC19 | Unit Testing | Run unit tests | Unit tests pass successfully | `SplitCalculationUseCaseTest` passed | Pass | `docs/cw2/screenshots/room/05_unit_test_success.png` |

---

## Unit Tests

### Implemented Unit Tests

#### `SplitCalculationUseCaseTest`

Implemented cases:

- `totalAmount = 100`, `members = 4` → expected share = `25`
- `members = 0` → expected `IllegalArgumentException`

Evidence:

```text
docs/cw2/screenshots/room/05_unit_test_success.png
```

Command:

```powershell
./gradlew test --no-configuration-cache
```

Observed result:

```text
BUILD SUCCESSFUL
```

### Optional Unit Tests

#### `ExpenseMappersTest`

Optional future test:

- `Expense` maps correctly to `ExpenseEntity`
- `ExpenseEntity` maps correctly back to `Expense`
- Required fields are preserved during mapping

This remains optional because the implemented mapper functions are simple, direct conversions and the Day 2 distinction-level unit evidence is already covered through `SplitCalculationUseCaseTest`.

---

## Commands Used for Testing

### Clean Build

```powershell
./gradlew clean build --no-configuration-cache
```

Expected result:

```text
BUILD SUCCESSFUL
```

Observed result after Day 2:

```text
BUILD SUCCESSFUL
```

### Unit Tests

```powershell
./gradlew test --no-configuration-cache
```

Expected result:

```text
BUILD SUCCESSFUL
```

Observed result after Day 2:

```text
BUILD SUCCESSFUL
```

---

## Evidence Screenshot Folders

```text
docs/cw2/screenshots/setup/
docs/cw2/screenshots/room/
docs/cw2/screenshots/biometrics/
docs/cw2/screenshots/api/
docs/cw2/screenshots/testing/
docs/cw2/screenshots/viva/
```

## Testing Evidence Checklist

| Evidence | File Path | Status |
|---|---|---:|
| Build success | `docs/cw2/screenshots/setup/02_gradle_build_success.png` | Done |
| App flow screenshots | `docs/cw2/screenshots/setup/03-08_app_flow.png` | Done / Partial |
| Room package structure | `docs/cw2/screenshots/room/01_data_local_package.png` | Done |
| Room database code | `docs/cw2/screenshots/room/02_room_database_code.png` | Done |
| Add expense form filled | `docs/cw2/screenshots/room/03_add_expense_form_filled.png` | Done |
| Add expense local save | `docs/cw2/screenshots/room/03_add_expense_local_save_success.png` | Done |
| History local data | `docs/cw2/screenshots/room/04_history_local_data.png` | Done |
| Unit test success | `docs/cw2/screenshots/room/05_unit_test_success.png` | Done |
| Biometric prompt | `docs/cw2/screenshots/biometrics/01_biometric_prompt.png` | Pending Day 3 |
| Biometric cancel/fallback | `docs/cw2/screenshots/biometrics/03_biometric_cancelled.png` | Pending Day 3 |
| API success | `docs/cw2/screenshots/api/03_currency_screen_success.png` | Pending Day 4 |
| API error/retry | `docs/cw2/screenshots/api/04_api_error_retry.png` | Pending Day 4 |
| Final build success after merge | `docs/cw2/screenshots/testing/03_build_success_after_merge.png` | Pending final integration |

---

## Day 2 Room Test Summary

Day 2 validated the Room local storage workstream through code evidence, runtime app evidence and Gradle test/build evidence.

Validated behaviour:

```text
Add Expense
→ Room/local save
→ Firestore sync attempt
→ user-facing success feedback
→ History displays saved expense
```

Validated message:

```text
Expense saved locally and synced to cloud.
```

Important note:

- The current Day 2 runtime evidence confirms successful local-first save and cloud sync.
- A full network-off/airplane-mode demonstration can still be captured during final integration if additional evidence is desired for TC11.

---

## Notes for Final Report

The final report should include:

- Completed test table with Expected vs Actual results.
- Screenshot references for each major feature.
- Explanation that Room was integrated through the data layer rather than directly in the UI.
- Explanation that `ExpenseRepository` coordinates local Room storage and Firestore sync.
- Critical reflection noting that a future production version could use WorkManager for robust background sync.
