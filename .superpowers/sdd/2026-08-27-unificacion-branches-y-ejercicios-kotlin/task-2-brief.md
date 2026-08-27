### Task 2: Merge de feature/material-showcase

**Files:**
- Modify: múltiples (resultado del merge)
- Source branch: `origin/feature/material-showcase`

- [ ] **Step 1: Iniciar merge con --no-ff**

```bash
git merge --no-ff origin/feature/material-showcase -m "Merge: integrar Material Design showcase + tests + docs 07"
```

Si hay conflictos, anota cuáles y resuélvelos uno por uno con `git status` y editando los archivos. Archivos que probablemente entren en conflicto (pero la branch no se ha tocado desde, así que probablemente no):
- `app/build.gradle.kts`: combinar deps.
- `MainActivity.kt`, `WelcomeScreen.kt`: descartar lado de material (los reescribiremos en Fase 3).

- [ ] **Step 2: Verificar compilación de la app**

```bash
./gradlew :app:assembleDebug
```
Esperado: `BUILD SUCCESSFUL`. Si falla, leer el error y corregir. Los conflictos más comunes serían duplicados de dependencias o NavHost.

- [ ] **Step 3: Verificar que el proyecto sigue funcionando**

```bash
ls app/src/main/java/com/example/baseproject/ui/screens/
```
Esperado: existen `buttons/`, `cards/`, `dialogs/`, `lists/`, `icons/`, `appbars/`, `bottomsheets/`, `navigation/`, `textfields/`, `theming/`, `selectioncontrols/`, además de `MainScreen.kt`, `ShowcaseCategory.kt`.

- [ ] **Step 4: Verificar docs 07 presente**

```bash
ls docs/07-Componentes-UI-Material-Design.md
```
Esperado: archivo existe.

---

## Fase 2 — Merge `native-capabilities-show`

