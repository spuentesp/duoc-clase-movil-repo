### Task 4: Renombrar paquetes de screens/ → material/, native/

**Files:**
- Move: `app/src/main/java/com/example/baseproject/ui/screens/buttons/` → `app/src/main/java/com/example/baseproject/ui/screens/material/buttons/`
- Move: `app/src/main/java/com/example/baseproject/ui/screens/cards/` → `.../material/cards/`
- (análogamente para los otros paquetes material)
- Move: `app/src/main/java/com/example/baseproject/ui/screens/features/` → `.../native/features/`
- Move: `app/src/main/java/com/example/baseproject/ui/navigation/ShowcaseNavigation.kt` → `app/src/main/java/com/example/baseproject/ui/navigation/MaterialNav.kt`
- Move: `app/src/main/java/com/example/baseproject/navigation/NavGraph.kt` → `app/src/main/java/com/example/baseproject/ui/navigation/NativeNav.kt`
- Delete: `app/src/main/java/com/example/baseproject/ui/screens/WelcomeScreen.kt`

- [ ] **Step 1: Crear directorios material/ y native/**

```bash
cd app/src/main/java/com/example/baseproject/ui/screens
mkdir -p material native
mv buttons cards dialogs lists icons appbars bottomsheets navigation textfields theming selectioncontrols material/
mv features native/
ls material/
ls native/features/
```

Esperado: en `material/` los 11 paquetes; en `native/features/` los 9 archivos de pantallas nativas.

- [ ] **Step 2: Mover MainScreen y ShowcaseCategory a material/**

```bash
mv MainScreen.kt ShowcaseCategory.kt material/
ls material/
```

Esperado: `MainScreen.kt`, `ShowcaseCategory.kt`, y los 11 sub-paquetes.

- [ ] **Step 3: Mover FeaturesMenuScreen a native/**

```bash
mv FeaturesMenuScreen.kt native/
```

- [ ] **Step 4: Renombrar archivos de navegación**

```bash
cd /home/sebastian/orca/duoc-clase-movil-repo
git mv app/src/main/java/com/example/baseproject/ui/navigation/ShowcaseNavigation.kt app/src/main/java/com/example/baseproject/ui/navigation/MaterialNav.kt
git mv app/src/main/java/com/example/baseproject/navigation/NavGraph.kt app/src/main/java/com/example/baseproject/ui/navigation/NativeNav.kt
git mv app/src/main/java/com/example/baseproject/navigation/Screen.kt app/src/main/java/com/example/baseproject/ui/navigation/Screen.kt
```

Después del mv, renombra también las funciones internas para que coincidan con el nombre del archivo (necesario para que `RootNav` las encuentre):

```bash
# Renombrar fun ShowcaseNavigation → fun MaterialNav
sed -i 's/^fun ShowcaseNavigation(/fun MaterialNav(/' app/src/main/java/com/example/baseproject/ui/navigation/MaterialNav.kt

# Renombrar fun NavGraph → fun NativeNav
sed -i 's/^fun NavGraph(/fun NativeNav(/' app/src/main/java/com/example/baseproject/ui/navigation/NativeNav.kt

# Corregir package en Screen.kt (queda desincronizado con su nueva ubicación)
sed -i 's|^package com.example.baseproject.navigation$|package com.example.baseproject.ui.navigation|' app/src/main/java/com/example/baseproject/ui/navigation/Screen.kt

# Ajustar imports que apuntaban al paquete antiguo en NavGraph renombrado
sed -i 's|^import com.example.baseproject.navigation\.|import com.example.baseproject.ui.navigation.|' app/src/main/java/com/example/baseproject/ui/navigation/NativeNav.kt
```

- [ ] **Step 5: Eliminar paquete `navigation/` huérfano si quedó**

```bash
ls app/src/main/java/com/example/baseproject/
```
Si `navigation/` quedó vacío:
```bash
rmdir app/src/main/java/com/example/baseproject/navigation/
```

- [ ] **Step 6: Actualizar imports en archivos renombrados**

Los archivos en `material/` y `native/` ahora están en paquetes distintos. Necesitamos actualizar los `package` y los imports que los referencian.

Para archivos en `material/`:
```bash
find app/src/main/java/com/example/baseproject/ui/screens/material -name '*.kt' -exec sed -i 's|^package com.example.baseproject.ui.screens$|package com.example.baseproject.ui.screens.material|; s|^package com.example.baseproject.ui.screens\.|package com.example.baseproject.ui.screens.material.|' {} +
```

Para archivos en `native/`:
```bash
find app/src/main/java/com/example/baseproject/ui/screens/native -name '*.kt' -exec sed -i 's|^package com.example.baseproject.ui.screens$|package com.example.baseproject.ui.screens.native|; s|^package com.example.baseproject.ui.screens\.|package com.example.baseproject.ui.screens.native.|' {} +
```

Actualizar `MaterialNav.kt` y `NativeNav.kt` para que apunten a los nuevos paquetes:
```bash
grep -rln 'com.example.baseproject.ui.screens.MainScreen\|com.example.baseproject.ui.screens.ShowcaseCategory\|com.example.baseproject.ui.screens.FeaturesMenuScreen' app/src/
# Editar manualmente cada match para que apunten a .material. o .native.
```

- [ ] **Step 7: Eliminar WelcomeScreen**

```bash
git rm app/src/main/java/com/example/baseproject/ui/screens/WelcomeScreen.kt
```

- [ ] **Step 8: Verificar compilación**

```bash
./gradlew :app:assembleDebug 2>&1 | tail -30
```

Esperado: `BUILD SUCCESSFUL`. Si hay errores de imports no resueltos, corregir manualmente.

- [ ] **Step 9: Commit**

```bash
git add -A
git commit -m "refactor: reorganizar screens/ en material/ y native/"
```

---

