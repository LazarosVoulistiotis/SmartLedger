# CW2 Day 1 Baseline — Smart Ledger

## Project
Smart Ledger — CN6008 CW2 Final App & Testing

## Team
- Λάζαρος — Room / SQLite Local Storage
- Γιάννης — Biometric Authentication
- Ιάσωνας — Retrofit External API / Client-Server Communication

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
- SplitCalculationUseCase

## CW2 Extension Plan
The CW2 implementation will extend the current architecture without rebuilding the project from scratch.

New packages/features:
- Room local database under `data/local`
- Biometric security under `data/security`
- Retrofit API integration under existing `data/remote`
- Currency/API UI screen under `ui/screens/api`
- Currency ViewModel and UiState under `ui/viewmodel`

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

| Flow | Status | Notes |
|---|---|---|
| Welcome Screen | Pass / Fail | |
| Register | Pass / Fail | |
| Login | Pass / Fail | |
| Dashboard | Pass / Fail | |
| Add Expense | Pass / Fail | |
| Expense History | Pass / Fail | |
| Group Split | Pass / Fail | |
| Logout | Pass / Fail / N/A | |

## Known Issues Before CW2 Features

- None / Add here if any issue is found during baseline testing.

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

This screenshot should show the current package structure, including:
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

Recommended board columns:
- To Do
- In Progress
- Code Review
- Testing
- Done

## Evidence Screenshots Checklist

| Evidence | File Path | Status |
|---|---|---|
| Git clean status | `docs/cw2/screenshots/setup/01_git_status_clean.png` | Done / Pending |
| Gradle build success | `docs/cw2/screenshots/setup/02_gradle_build_success.png` | Done / Pending |
| Welcome screen | `docs/cw2/screenshots/setup/03_welcome_screen.png` | Done / Pending |
| Login screen | `docs/cw2/screenshots/setup/04_login_screen.png` | Done / Pending |
| Dashboard screen | `docs/cw2/screenshots/setup/05_dashboard_screen.png` | Done / Pending |
| Add Expense screen | `docs/cw2/screenshots/setup/06_add_expense_screen.png` | Done / Pending |
| History screen | `docs/cw2/screenshots/setup/07_history_screen.png` | Done / Pending |
| Group Split screen | `docs/cw2/screenshots/setup/08_group_split_screen.png` | Done / Pending |
| CW2 folder structure | `docs/cw2/screenshots/setup/09_docs_cw2_structure.png` | Done / Pending |
| Current code architecture | `docs/cw2/screenshots/setup/10_current_code_architecture.png` | Done / Pending |
| Jira CW2 board | `docs/cw2/screenshots/setup/11_jira_cw2_board.png` | Done / Pending |

## Package Decisions Locked for CW2

| Technology / Feature | Target Package / Files |
|---|---|
| Room / SQLite Local Storage | `data/local` |
| Room DAO | `data/local/dao` |
| Room Entity | `data/local/entity` |
| Room Mappers | `data/local/mapper` |
| Biometrics | `data/security` |
| Retrofit API | `data/remote` |
| Currency/API screen | `ui/screens/api` |
| API ViewModel and UiState | `ui/viewmodel` |
| Navigation updates | `ui/navigation/Screen` and `SmartLedgerNavHost.kt` |

## Day 1 Completion Criteria

Day 1 is considered complete only when:

- [ ] Git status is clean or controlled.
- [ ] The project builds successfully with `./gradlew clean build` or `.\gradlew clean build`.
- [ ] The existing app opens and the basic CW1 flow works.
- [ ] `docs/cw2` structure exists.
- [ ] `day1-baseline.md` exists.
- [ ] `test-plan.md` exists.
- [ ] `contribution-matrix.md` exists.
- [ ] Jira/Kanban board exists.
- [ ] Day 1 screenshots are saved.
- [ ] Team responsibilities are confirmed.

## Final Day 1 Notes

The purpose of Day 1 is to create a stable baseline before adding Room, Biometrics, and Retrofit. No advanced feature implementation should start until the baseline build, documentation, screenshots, and contribution plan are complete.
