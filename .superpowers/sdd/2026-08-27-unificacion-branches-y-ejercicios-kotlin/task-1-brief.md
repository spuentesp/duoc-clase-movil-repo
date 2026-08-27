### Task 1: Backup, mover epubs, actualizar `.gitignore`

**Files:**
- Modify: `.gitignore`
- Move: `docs/Programming Android with Kotlin _ achieving structured -- Pierre-Olivier Laurence, Amanda Hinchman-Dominguez, G_ Blake.epub` → `docs/books/`
- Move: `docs/Programming Android with Kotlin _ achieving structured -- Pierre-Olivier Laurence, Amanda Hinchman-Dominguez, G_ Blake (1).epub` → `docs/books/`

- [ ] **Step 1: Crear backup del repo como bundle**

```bash
mkdir -p .git-backup
git bundle create .git-backup/backup-pre-merge.bundle --all
```

Verifica que el archivo existe:
```bash
ls -la .git-backup/
```
Esperado: `backup-pre-merge.bundle` presente.

- [ ] **Step 2: Asegurar main actualizado y limpio**

```bash
git checkout main
git fetch --all
git status
```
Esperado: branch `main`, working tree limpio salvo los 2 archivos epub sin trackear.

- [ ] **Step 3: Mover epubs a docs/books/**

```bash
mkdir -p docs/books
mv 'docs/Programming Android with Kotlin _ achieving structured -- Pierre-Olivier Laurence, Amanda Hinchman-Dominguez, G_ Blake.epub' docs/books/
mv 'docs/Programming Android with Kotlin _ achieving structured -- Pierre-Olivier Laurence, Amanda Hinchman-Dominguez, G_ Blake (1).epub' docs/books/
ls docs/books/
```
Esperado: 2 archivos `.epub` listados.

- [ ] **Step 4: Actualizar `.gitignore` para ignorar epubs**

Edita `.gitignore` y agrega al final:
```
# Libros de referencia (no se versionan; cada alumno los trae localmente)
docs/books/
*.epub

# Builds del módulo exercises
exercises/build/
exercises/.gradle/
```

- [ ] **Step 5: Verificar que git ya no lista los epubs**

```bash
git status
```
Esperado: working tree limpio (epubs están ahora ignorados, no aparecen como untracked). Si aparecen listados, revisa que el patrón `docs/books/` esté al inicio de la línea sin espacios.

- [ ] **Step 6: Commit**

```bash
git add .gitignore
git commit -m "chore: mover epubs a docs/books y agregarlos a gitignore"
```

---

## Fase 1 — Merge `feature/material-showcase`

