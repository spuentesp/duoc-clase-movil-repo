### Task 3: Merge de native-capabilities-show con resolución de conflictos

**Files:**
- Modify: `app/build.gradle.kts`, `app/src/main/AndroidManifest.xml`, `MainActivity.kt`, `WelcomeScreen.kt`, `docs/05-Empaquetado-y-Distribucion.md`, `docs/06-Personalizacion-de-la-App.md`, `Theme.kt`, `strings.xml`
- Source branch: `origin/native-capabilities-show`

- [ ] **Step 1: Iniciar merge con --no-ff**

```bash
git merge --no-ff origin/native-capabilities-show -m "Merge: integrar capacidades nativas + guía 08"
```

Esperado: 5+ conflictos. Listar con `git status`.

- [ ] **Step 2: Resolver conflicto en `app/build.gradle.kts`**

Edita `app/build.gradle.kts`. La rama native agrega deps para biometría, cámara, etc. Mantén TODAS las deps de ambas branches. Ejemplo de sección `dependencies` resultante:

```kotlin
dependencies {
    // Core Android
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

    // Compose
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.navigation:navigation-compose:2.7.5")

    // Capacidades nativas
    implementation("androidx.biometric:biometric:1.2.0-alpha05")
    implementation("androidx.camera:camera-core:1.3.1")
    implementation("androidx.camera:camera-camera2:1.3.1")
    implementation("androidx.camera:camera-lifecycle:1.3.1")
    implementation("androidx.camera:camera-view:1.3.1")
    implementation("com.google.android.gms:play-services-location:21.0.1")

    // Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.10.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
```

Después:
```bash
git add app/build.gradle.kts
```

- [ ] **Step 3: Resolver conflicto en `MainActivity.kt`**

Para `MainActivity.kt`, descarta el lado de `native-capabilities-show` (estamos mergeando native → main, así que `--theirs` es native). Conserva el lado de `feature/material-showcase` (el que llama al NavHost del showcase). De todos modos, en Task 5 Step 4 reescribiremos `MainActivity.kt` por completo:

```bash
git checkout --ours app/src/main/java/com/example/baseproject/MainActivity.kt
```

- [ ] **Step 4: Resolver conflicto en `WelcomeScreen.kt`**

Mantén la versión de material-showcase (se eliminará en Fase 3):

```bash
git checkout --ours app/src/main/java/com/example/baseproject/ui/screens/WelcomeScreen.kt
```

- [ ] **Step 5: Resolver conflicto en `docs/05-Empaquetado-y-Distribucion.md`**

Compara:
```bash
git diff
```
Conserva la versión más completa (generalmente la de `main` original). Si la de native es más corta, descarta el cambio de native para este archivo:

```bash
git checkout --ours docs/05-Empaquetado-y-Distribucion.md
```

- [ ] **Step 6: Restaurar `docs/06-Personalizacion-de-la-App.md`**

La rama native borró este archivo (regresión). Conserva la versión de main:

```bash
git checkout --ours docs/06-Personalizacion-de-la-App.md
```

- [ ] **Step 7: Resolver conflictos restantes (Theme.kt, strings.xml, AndroidManifest.xml)**

- `Theme.kt`: combina cambios (probable que solo haya sido tocado por native, conservar theirs).
- `strings.xml`: combina strings nuevos (los permisos).
- `AndroidManifest.xml`: combina permisos nuevos de native + permisos INTERNET si los hubiera. Resultado esperado debe incluir (verificado contra el manifest actual de `origin/native-capabilities-show`): `CAMERA`, `ACCESS_FINE_LOCATION`, `ACCESS_COARSE_LOCATION`, `VIBRATE`, `POST_NOTIFICATIONS`. **No** incluir `USE_BIOMETRIC`, `FLASHLIGHT` ni `RECEIVE_BOOT_COMPLETED` — la rama native no los trae y el código no los requiere (la biometría usa `androidx.biometric`, no un permiso propio).

```bash
# Ejemplo
git checkout --theirs app/src/main/java/com/example/baseproject/ui/theme/Theme.kt
git add app/src/main/java/com/example/baseproject/ui/theme/Theme.kt
git add app/src/main/AndroidManifest.xml
git add app/src/main/res/values/strings.xml
```

- [ ] **Step 8: Marcar merge completo**

```bash
git status  # debería decir "all conflicts fixed"
git add -A
git commit --no-edit
```

- [ ] **Step 9: Verificar compilación**

```bash
./gradlew :app:assembleDebug
```
Esperado: `BUILD SUCCESSFUL`. Si falla, revisar Manifest (faltan permisos probablemente).

- [ ] **Step 10: Verificar archivos nativos presentes**

```bash
ls app/src/main/java/com/example/baseproject/ui/screens/features/
```
Esperado: `AccelerometerScreen.kt`, `BatteryScreen.kt`, `BiometricScreen.kt`, `CameraScreen.kt`, `FlashlightScreen.kt`, `LocalStorageScreen.kt`, `LocationScreen.kt`, `NotificationsScreen.kt`, `VibrationScreen.kt`.

---

## Fase 3 — Reorganización post-merge

