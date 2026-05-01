# CW2 Day 1 Baseline and Day 2 Progress — Smart Ledger

## Project
Smart Ledger — CN6008 CW2 Final App & Testing

## Team
- **Λάζαρος** — Room / SQLite Local Storage
- **Γιάννης** — Biometric Authentication
- **Ιάσωνας** — Retrofit External API / Client-Server Communication

## Existing CW1 Foundation
The current Smart Ledger prototype already includes:

- Firebase Authentication
- Cloud Firestore
- Register / Login / Logout
- Dashboard
- Add Expense
- Expense History
- Group / Split workflow
- Kotlin + Jetpack Compose
- Navigation Compose
- ViewModels and UiState classes
- Repository pattern
- `SplitCalculationUseCase`

## CW2 Extension Plan
The CW2 implementation extends the current architecture without rebuilding the project from scratch.

New packages/features:

- Room local database under `data/local` — **completed in Day 2**
- Biometric security under `data/security` — planned for Day 3
- Retrofit API integration under existing `data/remote` — planned for Day 4
- Currency/API UI screen under `ui/screens/api` — planned for Day 4
- Currency ViewModel and UiState under `ui/viewmodel` — planned for Day 4

---

# Day 1 — Baseline Status

## Day 1 Git Check

Command used:

```powershell
git status
```

Result:

```text
On branch main
Your branch is up to date with 'origin/main'.
nothing to commit, working tree clean
```

Evidence:

```text
docs/cw2/screenshots/setup/01_git_status_clean.png
```

## Day 1 Build Check

Command used:

```powershell
.\gradlew clean build
```

Result:

```text
BUILD SUCCESSFUL
```

Evidence:

```text
docs/cw2/screenshots/setup/02_gradle_build_success.png
```

## Existing App Flow Checked

| Flow | Status | Evidence / Notes |
|---|---:|---|
| Welcome Screen | Pass | `docs/cw2/screenshots/setup/03_welcome_screen.png` |
| Register | Pass | Existing Firebase registration flow confirmed during baseline testing |
| Login | Pass | `docs/cw2/screenshots/setup/04_login_screen.png` |
| Dashboard | Pass | `docs/cw2/screenshots/setup/05_dashboard_screen.png` |
| Add Expense | Pass | `docs/cw2/screenshots/setup/06_add_expense_screen.png` |
| Expense History | Pass | `docs/cw2/screenshots/setup/07_history_screen.png` |
| Group Split | Pass | `docs/cw2/screenshots/setup/08_group_split_screen.png` |
| Logout | Pass / N/A | Confirmed as part of the core authentication flow where applicable |

## Known Issues Before CW2 Features

- No blocking issue was identified during the Day 1 baseline.
- The existing app was suitable for incremental CW2 extension.
- Offline persistence, biometrics, and Retrofit API integration were not yet implemented at the Day 1 baseline stage.

## Day 1 Folder Structure

Created CW2 documentation and evidence structure:

```text
docs/
└── cw2/
    ├── contribution-matrix.md
    ├── day1-baseline.md
    ├── test-plan.md
    └── screenshots/
        ├── api/
        ├── biometrics/
        ├── room/
        ├── setup/
        ├── testing/
        └── viva/
```

Evidence:

```text
docs/cw2/screenshots/setup/09_docs_cw2_structure.png
```

## Current Architecture Evidence

Current code architecture screenshot:

```text
docs/cw2/screenshots/setup/10_current_code_architecture.png
```

This screenshot shows the baseline package structure, including:

- `data/model`
- `data/remote`
- `data/repository`
- `domain/usecase`
- `ui/navigation`
- `ui/screens`
- `ui/viewmodel`
- `MainActivity.kt`

## Jira / Kanban Evidence

CW2 Jira/Kanban board screenshot:

```text
docs/cw2/screenshots/setup/11_jira_cw2_board.png
```

Board columns:

```text
To Do → In Progress → Code Review → Testing → Done
```

## Day 1 Evidence Screenshots Checklist

| Evidence | File Path | Status |
|---|---|---:|
| Git clean status | `docs/cw2/screenshots/setup/01_git_status_clean.png` | Done |
| Gradle build success | `docs/cw2/screenshots/setup/02_gradle_build_success.png` | Done |
| Welcome screen | `docs/cw2/screenshots/setup/03_welcome_screen.png` | Done |
| Login screen | `docs/cw2/screenshots/setup/04_login_screen.png` | Done |
| Dashboard screen | `docs/cw2/screenshots/setup/05_dashboard_screen.png` | Done |
| Add Expense screen | `docs/cw2/screenshots/setup/06_add_expense_screen.png` | Done |
| History screen | `docs/cw2/screenshots/setup/07_history_screen.png` | Done |
| Group Split screen | `docs/cw2/screenshots/setup/08_group_split_screen.png` | Done |
| CW2 folder structure | `docs/cw2/screenshots/setup/09_docs_cw2_structure.png` | Done |
| Current code architecture | `docs/cw2/screenshots/setup/10_current_code_architecture.png` | Done |
| Jira CW2 board | `docs/cw2/screenshots/setup/11_jira_cw2_board.png` | Done |

## Package Decisions Locked for CW2

| Technology / Feature | Target Package / Files | Status |
|---|---|---:|
| Room / SQLite Local Storage | `data/local` | Completed in Day 2 |
| Room DAO | `data/local/dao` | Completed in Day 2 |
| Room Entity | `data/local/entity` | Completed in Day 2 |
| Room Mappers | `data/local/mapper` | Completed in Day 2 |
| Biometrics | `data/security` | Planned Day 3 |
| Retrofit API | `data/remote` | Planned Day 4 |
| Currency/API screen | `ui/screens/api` | Planned Day 4 |
| API ViewModel and UiState | `ui/viewmodel` | Planned Day 4 |
| Navigation updates | `ui/navigation/Screen` and `SmartLedgerNavHost.kt` | Planned Day 4 |

## Day 1 Completion Criteria

- [x] Git status was clean or controlled.
- [x] The project built successfully with `./gradlew clean build` / `.\gradlew clean build`.
- [x] The existing app opened and the basic CW1 flow was checked.
- [x] `docs/cw2` structure exists.
- [x] `day1-baseline.md` exists.
- [x] `test-plan.md` exists.
- [x] `contribution-matrix.md` exists.
- [x] Jira/Kanban board exists.
- [x] Day 1 screenshots are saved.
- [x] Team responsibilities are confirmed.

---

# Day 2 Progress Update — Room / SQLite Local Storage

## Day 2 Goal

Day 2 focused on implementing **Technology 2: Local Data Storage** by adding Room / SQLite local persistence for Smart Ledger expenses.

The implementation goal was:

```text
Add Expense
→ save locally through Room
→ attempt Firestore save
→ update ExpenseUiState

History
→ load Room/local data first or fall back to it
→ optionally refresh from Firestore
→ display saved expenses in History
```

## Day 2 Implementation Status

| Item | Status | Notes |
|---|---:|---|
| Room dependencies added | Done | Room runtime, Room KTX, Room compiler and KSP added |
| `data/local` package created | Done | Room-specific local data layer added |
| `ExpenseEntity.kt` created | Done | Includes `isSynced` field for cloud-sync status |
| `ExpenseDao.kt` created | Done | Insert, bulk insert, query and sync-status update functions added |
| `SmartLedgerDatabase.kt` created | Done | Room database singleton created |
| `ExpenseMappers.kt` created | Done | Maps `Expense` ↔ `ExpenseEntity` |
| `ExpenseRepository.kt` updated | Done | Local-first save and Firestore sync attempt implemented |
| `ExpenseViewModel.kt` updated | Done | Handles local/cloud result messages |
| `ExpenseUiState.kt` updated | Done | Added `localInfoMessage` |
| `AddExpenseScreen.kt` updated | Done | Displays local-save/sync feedback |
| `HistoryScreen.kt` updated | Done | Displays local/history message and loads expenses through updated repository |
| `SplitCalculationUseCaseTest.kt` added | Done | Unit tests added for equal-split logic |
| Build validation | Done | `./gradlew clean build --no-configuration-cache` passed |
| Unit test validation | Done | `./gradlew test --no-configuration-cache` passed |
| Runtime demo | Done | Add Expense and History demo confirmed |

## Day 2 Evidence Screenshots

| Evidence | File Path | Status |
|---|---|---:|
| Room package structure | `docs/cw2/screenshots/room/01_data_local_package.png` | Done |
| Room database code | `docs/cw2/screenshots/room/02_room_database_code.png` | Done |
| Add Expense form filled | `docs/cw2/screenshots/room/03_add_expense_form_filled.png` | Done |
| Local-save success message | `docs/cw2/screenshots/room/03_add_expense_local_save_success.png` | Done |
| History local data | `docs/cw2/screenshots/room/04_history_local_data.png` | Done |
| Unit test success | `docs/cw2/screenshots/room/05_unit_test_success.png` | Done |

## Day 2 Notes

- The implementation follows the existing architecture and does not directly connect UI code to Room.
- `ExpenseRepository` now coordinates local Room storage and Firestore cloud sync.
- `ExpenseViewModel` remains responsible for UI state and user-facing messages.
- The app shows `Expense saved locally and synced to cloud.` when local save and Firestore sync both succeed.
- The local-first design means the record is saved on-device before the Firestore save is attempted.
- A full production-grade background sync engine, such as WorkManager-based retry sync, remains a future improvement and was intentionally not added during Day 2 to avoid over-refactoring.

## Updated Day 2 Viva Statement — Λάζαρος

> I implemented Room inside the `data/local` package to add local SQLite-based persistence for expenses. The app now saves an expense locally first and then attempts to sync it with Firestore. This means that the History screen can still display saved expenses even when cloud access is unavailable. I connected Room through `ExpenseRepository`, so the UI and ViewModel do not directly depend on the database. This keeps the architecture clean and supports offline access for financial records.

## Day 2 Completion Decision

Day 2 is considered **complete** when the following are committed and pushed:

- [x] Room code implementation
- [x] ViewModel and UI updates
- [x] Unit test
- [x] Build/test evidence
- [x] Room screenshots
- [x] Jira ROOM cards moved to Done
- [ ] Feature branch pushed and ready for PR / merge

Recommended commit:

```powershell
git add .
git commit -m "Add Room offline expense storage"
git push -u origin feature/room-offline-storage
```

## Final Notes

Day 1 established the project baseline. Day 2 successfully extended the baseline with Room local persistence while keeping the existing architecture stable. The next workstream should start from the updated branch or from `main` after the Room feature is merged.
