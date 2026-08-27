# Task 6 Report

## Summary

Created the exercises UI scaffolding: a reusable `MiniAppScaffold` with top-bar toggle between enunciado and solución, a `CodeBlock` composable that renders monospaced code with vertical scroll, and replaced the `ExercisesMenuScreen` stub with the full list of 8 mini-apps as `LazyColumn` of `Card`s.

## Files

- Created: `app/src/main/java/com/example/baseproject/ui/screens/exercises/MiniAppScaffold.kt`
- Created: `app/src/main/java/com/example/baseproject/ui/screens/exercises/CodeBlock.kt`
- Modified: `app/src/main/java/com/example/baseproject/ui/screens/exercises/ExercisesMenuScreen.kt`

## Deviation from brief

`ExercisesMenuScreen` uses `Card(onClick = ...)`, which is `@ExperimentalMaterial3Api` in the project's Material3 version (compose-bom 2023.10.01). The brief's literal code did not compile (`e: ... This material API is experimental and is likely to change or to be removed in the future.`). Added a minimal `@OptIn(ExperimentalMaterial3Api::class)` annotation to the composable function (same pattern already used in `MiniAppScaffold` in this same task) plus the matching `import`. No logic, layout, or copy changed — every value from the brief remains verbatim.

## Verification

`gradlew :app:assembleDebug` — `BUILD SUCCESSFUL in 2s` (incremental after the first run that surfaced the experimental-API error).

## Commit sweep

`git add -A` (verbatim per brief Step 5) also staged `.git-backup/backup-pre-merge.bundle` and the entire `.superpowers/sdd/...` tree from earlier tasks, which were untracked on this branch. This produced 22 files in a single commit (3 app files + 19 planning/backups artifacts). Pattern matches Step 5 of the brief exactly; if the project wants these excluded from feature commits, the brief's `git add -A` should be replaced with a scoped `git add app/...` in a future task.

## Commits

- `8a49931` — `feat: MiniAppScaffold + CodeBlock + lista de mini-apps`
- `473b70b` — `docs: add task 6 report` (follow-up; report file didn't exist when `8a49931` was created)

## Status

STATUS: DONE_WITH_CONCERNS
COMMITS: 8a49931, 473b70b
TEST_SUMMARY: gradlew :app:assembleDebug: BUILD SUCCESSFUL in 2s
CONCERNS: (1) Added `@OptIn(ExperimentalMaterial3Api::class)` to `ExercisesMenuScreen` so the brief's `Card(onClick = ...)` compiles; brief should be amended to include this in a future task. (2) Commit `8a49931` also swept in `.git-backup/backup-pre-merge.bundle` and `.superpowers/sdd/...` because the brief's `git add -A` is unscoped; same pattern likely affected previous task commits.
