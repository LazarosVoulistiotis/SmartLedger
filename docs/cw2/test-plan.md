# CW2 Test Plan — Smart Ledger

## Testing Strategy

The Smart Ledger CW2 application will be tested using:
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

## Test Cases

| Test ID | Feature | Scenario | Expected Result | Actual Result | Status | Evidence |
|---|---|---|---|---|---|---|
| TC01 | Authentication | Valid Firebase login | User logs in and reaches Dashboard | Pending | Pending | Screenshot |
| TC02 | Authentication | Invalid login | Error message is shown | Pending | Pending | Screenshot |
| TC03 | Authentication | Logout | User returns to authentication flow | Pending | Pending | Screenshot |
| TC04 | Expense | Add valid expense | Expense is saved and confirmation shown | Pending | Pending | Screenshot |
| TC05 | Expense | Empty required field | Validation error is shown | Pending | Pending | Screenshot |
| TC06 | History | View expense history | User expenses are displayed | Pending | Pending | Screenshot |
| TC07 | Group Split | Split 100 between 4 members | Share per member is 25 | Pending | Pending | Unit Test / Screenshot |
| TC08 | Group Split | Submit group with empty member list | Error message is shown | Pending | Pending | Screenshot |
| TC09 | Room | Save expense locally | Expense is stored in Room | Pending | Pending | Screenshot |
| TC10 | Room | View history offline | Cached local expenses are displayed | Pending | Pending | Screenshot |
| TC11 | Room | Firestore unavailable but local save succeeds | App shows local-save/sync-pending feedback | Pending | Pending | Screenshot |
| TC12 | Biometrics | Successful biometric authentication | User reaches Dashboard | Pending | Pending | Screenshot |
| TC13 | Biometrics | Cancel biometric prompt | Access is blocked or fallback is shown | Pending | Pending | Screenshot |
| TC14 | Biometrics | Unsupported biometric device/emulator | Fallback path is handled safely | Pending | Pending | Screenshot |
| TC15 | API | Fetch currency rates | API data is displayed | Pending | Pending | Screenshot |
| TC16 | API | API/network failure | Error message and retry option are shown | Pending | Pending | Screenshot |
| TC17 | API | Retry after failed request | App attempts to fetch API data again | Pending | Pending | Screenshot |
| TC18 | Build | Run clean build | Project compiles without errors | Pending | Pending | Screenshot |
| TC19 | Unit Testing | Run unit tests | Unit tests pass successfully | Pending | Pending | Screenshot |

## Unit Tests

Planned unit tests:

### SplitCalculationUseCaseTest
- `totalAmount = 100`, `members = 4` → expected share = `25`
- `totalAmount = 99.99`, `members = 3` → expected share = `33.33`
- `members = 0` → expected exception or safe validation failure

### ExpenseMappersTest optional
To be added if Room mapping is implemented cleanly.
- `Expense` maps correctly to `ExpenseEntity`
- `ExpenseEntity` maps correctly back to `Expense`
- Required fields are preserved during mapping

## Commands Used for Testing

### Clean Build

```powershell
.\gradlew clean build
```

Expected result:

```text
BUILD SUCCESSFUL
```

### Unit Tests

```powershell
.\gradlew test
```

Expected result:

```text
BUILD SUCCESSFUL
```

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
|---|---|---|
| Build success | `docs/cw2/screenshots/setup/02_gradle_build_success.png` | Done / Pending |
| App flow screenshots | `docs/cw2/screenshots/setup/03-08_app_flow.png` | Done / Pending |
| Room package/code | `docs/cw2/screenshots/room/01_data_local_package.png` | Pending |
| Add expense local save | `docs/cw2/screenshots/room/03_add_expense_local_save.png` | Pending |
| History local data | `docs/cw2/screenshots/room/04_history_local_data.png` | Pending |
| Biometric prompt | `docs/cw2/screenshots/biometrics/01_biometric_prompt.png` | Pending |
| Biometric cancel/fallback | `docs/cw2/screenshots/biometrics/03_biometric_cancelled.png` | Pending |
| API success | `docs/cw2/screenshots/api/03_currency_screen_success.png` | Pending |
| API error/retry | `docs/cw2/screenshots/api/04_api_error_retry.png` | Pending |
| Unit test success | `docs/cw2/screenshots/testing/01_gradle_test_success.png` | Pending |
| Final build success | `docs/cw2/screenshots/testing/03_build_success_after_merge.png` | Pending |

## Notes

This file will be updated throughout the implementation days. The final report should include the completed test table with Expected vs Actual results, Pass/Fail status, and screenshot references.
