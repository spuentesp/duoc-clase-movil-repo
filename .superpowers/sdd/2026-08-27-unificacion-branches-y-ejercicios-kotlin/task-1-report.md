# Task 1 Report — Backup, mover epubs, actualizar `.gitignore`

**Ejecutor**: implementer subagent
**Rama**: `feature/unificacion-ejercicios`
**Worktree**: `/home/sebastian/orca/duoc-clase-movil-repo/.worktrees/unificacion-ejercicios`

## Estado inicial verificado
- `git branch --show-current` → `feature/unificacion-ejercicios` ✓
- `git status` inicial → working tree limpio salvo `.superpowers/` (untracked, esperado).
- No se ejecutó `git checkout main` porque ya estamos aislados en el worktree sobre la rama correcta (adaptación según briefing del Task).

## Step 1: Crear backup bundle — DONE
```
mkdir -p .git-backup
git bundle create .git-backup/backup-pre-merge.bundle --all
ls -la .git-backup/
# -rw-r--r-- 1 sebastian sebastian 267742 ago 27 11:58 backup-pre-merge.bundle
```
Bundle creado (~267 KB). Verificado.

## Step 2: Asegurar main actualizado y limpio — ADAPTADO
- NO se ejecutó `git checkout main && git fetch --all` (el briefing del Task me instruyó sustituirlo por `git status` y verificar rama).
- Resultado: rama `feature/unificacion-ejercicios`, working tree limpio salvo `.superpowers/` untracked (esperado).

## Step 3: Mover epubs a docs/books/ — NO EJECUTADO (bloqueante soft)
**Descubrimiento**: los 2 archivos epub referenciados en el brief **no existen en el worktree** (ni tracked ni untracked ni filesystem). Verificación:
- `ls -la docs/` → no contiene ningún `.epub`
- `glob **/*.epub` desde worktree → 0 resultados
- `git ls-files | grep -i epub` → 0 resultados
- `git ls-tree -r feature/unificacion-ejercicios --name-only | grep -i epub` → 0 resultados
- `git ls-tree -r main --name-only | grep -i epub` (desde repo principal) → 0 resultados
- `git branch -a` → solo existen `main`, `feature/unificacion-ejercicios`, `origin/{main,feature/material-showcase,native-capabilities-show}`, ninguno contiene epubs en su history.

Sí ejecuté `mkdir -p docs/books` → directorio creado (vacío). Convencionalmente queda como lugar para los libros de referencia locales.

## Step 4: Actualizar `.gitignore` — DONE (verbatim del brief)
Añadí al final del archivo, tal cual el brief:
```
# Libros de referencia (no se versionan; cada alumno los trae localmente)
docs/books/
*.epub

# Builds del módulo exercises
exercises/build/
exercises/.gradle/
```
Diff final de `.gitignore` verificado — solo se agregaron 8 líneas nuevas, no se tocó nada previo.

## Step 5: Verificar `git status` — DONE
- Output después del cambio en .gitignore (antes del commit): solo `.gitignore` modificado, `.git-backup/` y `.superpowers/` untracked.
- `git check-ignore -v docs/books/` → `.gitignore:19:docs/books/ docs/books/` → patrón matchea, dir correctamente ignorado.
- No hay epubs untracked para que el ignore tuviera que ocultarlos (porque no existen).

## Step 6: Commit — DONE
```
git add .gitignore
git commit -m "chore: mover epubs a docs/books y agregarlos a gitignore"
# → [feature/unificacion-ejercicios 539f7e3]
```

`git log --oneline -3`:
```
539f7e3 chore: mover epubs a docs/books y agregarlos a gitignore
6f51665 fix(plan): aplicar 9 correcciones del pre-flight review
b72b559 chore: ignorar .worktrees/
```

---
