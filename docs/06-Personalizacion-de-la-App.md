# Personalización de tu App Android

Esta guía te enseñará a personalizar tu aplicación Android para que tenga tu propia identidad visual y configuración.

---

## Índice

1. [Cambiar el Nombre de la App](#cambiar-el-nombre-de-la-app)
2. [Cambiar el Application ID](#cambiar-el-application-id)
3. [Personalizar Iconos](#personalizar-iconos)
4. [Crear Splash Screen](#crear-splash-screen)
5. [Convenciones de Nomenclatura](#convenciones-de-nomenclatura)
6. [Checklist de Personalización](#checklist-de-personalización)

---

## Cambiar el Nombre de la App

El nombre de la app es lo que los usuarios ven en el launcher (pantalla de inicio) y en la lista de aplicaciones instaladas.

### Ubicación del Archivo

Edita el archivo: **`app/src/main/res/values/strings.xml`**

### Ejemplo

```xml
<resources>
    <string name="app_name">Mi App Increíble</string>
</resources>
```

### Consideraciones

- **Máximo 30 caracteres** (recomendado 12-15 para mejor visualización)
- Evita caracteres especiales que puedan causar problemas
- Debe ser descriptivo pero conciso
- Considera que aparecerá debajo del icono en pantallas pequeñas

### Ejemplos de Buenos Nombres

✅ Nombres efectivos:
- "Tareas Fácil"
- "Mi Finanzas"
- "Recetas Chef"
- "Pokédex Pro"
- "StudyTime"

❌ Nombres a evitar:
- "AppMegaSuperGenialmuybuenaparatusnegocios" (demasiado largo)
- "app_de_ventas_2024_final_v2" (técnico, poco atractivo)
- "😎 Cool App 🚀" (emojis pueden causar problemas)

### Nombres Multiidioma

Puedes tener diferentes nombres según el idioma del dispositivo:

**`values/strings.xml`** (español por defecto):
```xml
<resources>
    <string name="app_name">Mi App</string>
</resources>
```

**`values-en/strings.xml`** (inglés):
```xml
<resources>
    <string name="app_name">My App</string>
</resources>
```

**`values-pt/strings.xml`** (portugués):
```xml
<resources>
    <string name="app_name">Meu App</string>
</resources>
```

---

## Cambiar el Application ID

El **Application ID** (antes llamado Package Name) es el identificador único de tu app en Google Play Store y en el dispositivo.

### ¿Qué es el Application ID?

- Es como el "nombre de dominio" de tu app
- **Debe ser único** en todo Google Play Store
- **No puede cambiarse** después de publicar en Play Store
- Formato: `com.empresa.aplicacion`

### Convención de Nomenclatura

```
com.[tu_empresa].[nombre_app]
```

**Ejemplos**:
```
com.duoc.pokemongame
com.miconsultora.ventas
com.juanperez.tareas
com.startupchilena.finanzas
```

### Paso 1: Editar build.gradle.kts

Abre: **`app/build.gradle.kts`**

```kotlin
android {
    namespace = "com.tuempresa.tuapp"  // Cambiar aquí

    defaultConfig {
        applicationId = "com.tuempresa.tuapp"  // Y aquí
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        // ...
    }
}
```

### Paso 2: Refactorizar el Paquete Java/Kotlin

1. **Cerrar todos los archivos** abiertos en Android Studio

2. **Cambiar a vista Project**
   - En el panel izquierdo, cambia de "Android" a "Project"

3. **Navegar a la estructura de paquetes**
   ```
   app/src/main/java/com/example/baseproject/
   ```

4. **Refactorizar cada nivel**

   a) Click derecho en `com` → `Refactor` → `Rename`
      - Cambiar a tu empresa (ej: `duoc`)
      - ☑️ "Search in comments and strings"
      - ☑️ "Search for text occurrences"
      - Click en `Refactor`

   b) Click derecho en `example` → `Refactor` → `Rename`
      - Cambiar a tu categoría (ej: `mobile`)
      - Aplicar los mismos checks
      - Click en `Refactor`

   c) Click derecho en `baseproject` → `Refactor` → `Rename`
      - Cambiar al nombre de tu app (ej: `pokemongame`)
      - Aplicar los mismos checks
      - Click en `Refactor`

5. **Sincronizar proyecto**
   - `File` → `Sync Project with Gradle Files`

6. **Reconstruir proyecto**
   - `Build` → `Clean Project`
   - `Build` → `Rebuild Project`

### Verificar el Cambio

Abre `MainActivity.kt` y verifica que el import sea correcto:

```kotlin
package com.tuempresa.tuapp

import android.os.Bundle
// ...
```

### ⚠️ Advertencias Importantes

1. **No uses guiones**: `com.mi-app` ❌ → `com.miapp` ✅
2. **No uses mayúsculas**: `com.MiApp` ❌ → `com.miapp` ✅
3. **No uses números al inicio**: `com.123app` ❌ → `com.app123` ✅
4. **Usa solo letras, números y puntos**: Sin espacios, sin caracteres especiales
5. **Mínimo dos segmentos**: `com.app` ✅ pero mejor `com.empresa.app` ✅
6. **No uses palabras reservadas**: `com.android.miapp` ❌

---

## Personalizar Iconos

Los iconos son la primera impresión de tu app. Android requiere iconos en múltiples tamaños y formatos.

### Tipos de Iconos

1. **Launcher Icon**: Icono principal que aparece en el home screen
2. **Adaptive Icon**: Icono que se adapta a diferentes formas (círculo, cuadrado, etc.)
3. **Round Icon**: Icono redondo para dispositivos que lo requieran
4. **Notification Icon**: Icono para notificaciones (monocromático)

### Requisitos de Imagen

**Para mejores resultados**:
- Formato: PNG con transparencia
- Tamaño inicial: **1024x1024 px** (se escalará a todos los tamaños)
- Estilo: Simple, reconocible, contraste alto
- Fondo: Considera que puede ser transparente

### Método 1: Image Asset Studio (Recomendado)

1. **Abrir Asset Studio**
   - Click derecho en `res` (carpeta de recursos)
   - `New` → `Image Asset`

2. **Configurar Icono**

   **Icon Type**: Launcher Icons (Adaptive and Legacy)

   **Foreground Layer** (capa principal):
   - **Source Asset Type**:
     - `Image` (para tu logo/imagen)
     - `Clip Art` (iconos prediseñados)
     - `Text` (letra o texto)
   - **Path**: Selecciona tu imagen
   - **Trim**: ☑️ Activar para recortar transparencias
   - **Resize**: Ajusta el tamaño (50-70% recomendado)
   - **Color**: Tinte de color (opcional)

   **Background Layer** (fondo):
   - **Source Asset Type**: `Color` o `Image`
   - **Color**: Elige un color sólido de tu marca
     - Ejemplo: `#2196F3` (azul Material)

   **Legacy** (Android 7.1 y anterior):
   - **Shape**: Ninguna (usa tu forma)
   - **Effect**: Opcional (sombra)

3. **Preview**
   - Verás cómo se ve en:
     - Círculo (Google Pixel)
     - Cuadrado redondeado (Samsung)
     - Squircle (otros fabricantes)
   - Ajusta el **Resize** si el icono se ve cortado

4. **Confirmar**
   - Click en `Next`
   - Verifica los archivos que se crearán
   - Click en `Finish`

### Archivos Generados

Android Studio crea automáticamente:

```
res/
├── mipmap-mdpi/          # 48x48 px
│   ├── ic_launcher.png
│   └── ic_launcher_round.png
├── mipmap-hdpi/          # 72x72 px
│   ├── ic_launcher.png
│   └── ic_launcher_round.png
├── mipmap-xhdpi/         # 96x96 px
│   ├── ic_launcher.png
│   └── ic_launcher_round.png
├── mipmap-xxhdpi/        # 144x144 px
│   ├── ic_launcher.png
│   └── ic_launcher_round.png
├── mipmap-xxxhdpi/       # 192x192 px
│   ├── ic_launcher.png
│   └── ic_launcher_round.png
├── mipmap-anydpi-v26/    # Android 8.0+ (Adaptive)
│   └── ic_launcher.xml
└── values/
    └── ic_launcher_background.xml
```

### Método 2: Herramientas Online

#### Android Asset Studio (Web)

**URL**: https://romannurik.github.io/AndroidAssetStudio/icons-launcher.html

1. Sube tu imagen (1024x1024 recomendado)
2. Ajusta:
   - Padding
   - Background color
   - Foreground scaling
3. Click en `Download .zip`
4. Extrae el ZIP y copia las carpetas `mipmap-*` a `app/src/main/res/`

#### Icon Kitchen

**URL**: https://icon.kitchen/

1. Sube tu imagen
2. Personaliza:
   - Background color/gradient
   - Shape (adaptive icon)
   - Effects
3. Genera y descarga
4. Extrae en `res/`

#### App Icon Generator

**URL**: https://appicon.co/

1. Sube imagen de 1024x1024
2. Selecciona plataforma: **Android**
3. Genera todos los tamaños
4. Descarga y extrae en `res/`

### Método 3: Manual (Avanzado)

Si quieres crear iconos manualmente:

| Densidad | Tamaño | Uso |
|----------|--------|-----|
| MDPI | 48x48 | Dispositivos básicos |
| HDPI | 72x72 | Pantallas medianas |
| XHDPI | 96x96 | Pantallas HD |
| XXHDPI | 144x144 | Pantallas Full HD |
| XXXHDPI | 192x192 | Pantallas 4K |

**Herramientas para crear iconos**:
- **Figma** (gratuito, online)
- **Adobe Illustrator** (vectorial)
- **Inkscape** (gratuito, vectorial)
- **GIMP** (gratuito, rasterizado)
- **Photoshop**

### Adaptive Icons (Android 8.0+)

**Estructura de Adaptive Icon**:

```xml
<!-- res/mipmap-anydpi-v26/ic_launcher.xml -->
<adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
    <background android:drawable="@color/ic_launcher_background"/>
    <foreground android:drawable="@drawable/ic_launcher_foreground"/>
</adaptive-icon>
```

**Dimensiones importantes**:
- Canvas total: **108x108 dp**
- Safe zone (siempre visible): **72x72 dp** (centro)
- Elementos importantes deben estar dentro del círculo central de **66x66 dp**

**Ejemplo visual**:
```
┌─────────────────────┐
│  108x108 (canvas)   │
│   ┌─────────────┐   │
│   │  72x72 dp   │   │  ← Área segura
│   │   ┌─────┐   │   │
│   │   │66x66│   │   │  ← Logo principal
│   │   └─────┘   │   │
│   └─────────────┘   │
└─────────────────────┘
```

### Verificar los Iconos

1. **Ejecutar la app**
   - Los iconos se actualizan automáticamente

2. **Revisar en diferentes launchers**
   - Pixel Launcher (círculo)
   - Samsung One UI (squircle)
   - Custom launchers

3. **Verificar en AndroidManifest.xml**

```xml
<application
    android:icon="@mipmap/ic_launcher"
    android:roundIcon="@mipmap/ic_launcher_round"
    android:label="@string/app_name"
    ...>
```

### Tips para Buenos Iconos

✅ **Hacer**:
- Mantener diseño simple y limpio
- Usar colores contrastantes
- Hacer que sea reconocible a tamaño pequeño
- Considerar el "safe zone" en adaptive icons
- Probar en dispositivos reales

❌ **Evitar**:
- Demasiados detalles pequeños
- Texto (no se lee bien a tamaño pequeño)
- Fotos realistas complejas
- Iconos muy similares a apps populares
- Usar logos con marca registrada de terceros

---

## Crear Splash Screen

El **Splash Screen** es la pantalla que aparece mientras tu app se está iniciando. Desde Android 12, hay una API oficial para splash screens.

### Tipos de Splash Screen

1. **Android 12+ (API 31+)**: Sistema nativo (SplashScreen API)
2. **Android 11 y anterior**: Implementación personalizada

### Método 1: Splash Screen API (Android 12+)

Este es el método recomendado y más simple.

#### Paso 1: Agregar Dependencia

Abre **`app/build.gradle.kts`**:

```kotlin
dependencies {
    implementation("androidx.core:core-splashscreen:1.0.1")
    // ... otras dependencias
}
```

Sincroniza el proyecto: `File` → `Sync Project with Gradle Files`

#### Paso 2: Crear Icono del Splash Screen

**Opción A: Usar el mismo icono de la app** (recomendado para simplicidad)

```xml
<!-- res/values/themes.xml -->
<style name="Theme.App.Starting" parent="Theme.SplashScreen">
    <!-- Icono del splash -->
    <item name="windowSplashScreenAnimatedIcon">@mipmap/ic_launcher</item>
    <!-- Duración de animación (máximo 1000ms) -->
    <item name="windowSplashScreenAnimationDuration">500</item>
    <!-- Color de fondo -->
    <item name="windowSplashScreenBackground">@color/purple_500</item>
    <!-- Post-splash theme -->
    <item name="postSplashScreenTheme">@style/Theme.BaseProject</item>
</style>
```

**Opción B: Crear un logo personalizado**

1. Crea un vector drawable: `res/drawable/splash_logo.xml`

```xml
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="288dp"
    android:height="288dp"
    android:viewportWidth="288"
    android:viewportHeight="288">
    <!-- Tu diseño aquí -->
    <path
        android:fillColor="#FFFFFF"
        android:pathData="M144,72 L216,144 L144,216 L72,144 Z"/>
</vector>
```

2. Usa el logo en el theme:

```xml
<style name="Theme.App.Starting" parent="Theme.SplashScreen">
    <item name="windowSplashScreenAnimatedIcon">@drawable/splash_logo</item>
    <item name="windowSplashScreenBackground">@color/purple_500</item>
    <item name="postSplashScreenTheme">@style/Theme.BaseProject</item>
</style>
```

#### Paso 3: Configurar Color de Fondo

Edita **`res/values/colors.xml`**:

```xml
<resources>
    <!-- Colores existentes -->
    <color name="purple_500">#FF6200EE</color>

    <!-- Color del splash screen -->
    <color name="splash_background">#2196F3</color>
</resources>
```

Úsalo en el theme:

```xml
<item name="windowSplashScreenBackground">@color/splash_background</item>
```

#### Paso 4: Actualizar AndroidManifest.xml

Abre **`app/src/main/AndroidManifest.xml`**:

```xml
<application
    android:icon="@mipmap/ic_launcher"
    android:label="@string/app_name"
    android:theme="@style/Theme.App.Starting"
    ...>

    <activity
        android:name=".MainActivity"
        android:exported="true"
        android:theme="@style/Theme.App.Starting">
        <intent-filter>
            <action android:name="android.intent.action.MAIN" />
            <category android:name="android.intent.category.LAUNCHER" />
        </intent-filter>
    </activity>
</application>
```

#### Paso 5: Instalar Splash Screen en MainActivity

Abre **`MainActivity.kt`**:

```kotlin
package com.tuempresa.tuapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.baseproject.ui.theme.BaseProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Instalar splash screen ANTES de super.onCreate()
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setContent {
            BaseProjectTheme {
                // Tu composable principal
            }
        }
    }
}
```

#### Paso 6: (Opcional) Mantener Splash Screen más Tiempo

Si necesitas cargar datos antes de mostrar la app:

```kotlin
class MainActivity : ComponentActivity() {
    private var keepSplashScreen = true

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        // Mantener splash hasta que esté listo
        splashScreen.setKeepOnScreenCondition { keepSplashScreen }

        super.onCreate(savedInstanceState)

        // Simular carga de datos
        lifecycleScope.launch {
            // Cargar datos, configuración, etc.
            delay(2000) // Ejemplo: esperar 2 segundos
            keepSplashScreen = false
        }

        setContent {
            BaseProjectTheme {
                // Tu UI
            }
        }
    }
}
```

### Método 2: Splash Screen Personalizado (Android 11 y anterior)

Para apps que deben soportar versiones antiguas de Android.

#### Paso 1: Crear Layout del Splash Screen

Crea **`res/layout/activity_splash.xml`**:

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/splash_background">

    <ImageView
        android:id="@+id/splash_logo"
        android:layout_width="200dp"
        android:layout_height="200dp"
        android:src="@mipmap/ic_launcher"
        android:contentDescription="@string/app_name"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/app_name"
        android:textSize="24sp"
        android:textColor="@android:color/white"
        android:layout_marginTop="24dp"
        app:layout_constraintTop_toBottomOf="@id/splash_logo"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

#### Paso 2: Crear SplashActivity

Crea **`SplashActivity.kt`**:

```kotlin
package com.tuempresa.tuapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    private val splashTimeOut: Long = 2000 // 2 segundos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Navegar a MainActivity después del delay
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, splashTimeOut)
    }
}
```

#### Paso 3: Actualizar AndroidManifest.xml

```xml
<application ...>

    <!-- SplashActivity como launcher -->
    <activity
        android:name=".SplashActivity"
        android:exported="true"
        android:theme="@style/Theme.App.Starting">
        <intent-filter>
            <action android:name="android.intent.action.MAIN" />
            <category android:name="android.intent.category.LAUNCHER" />
        </intent-filter>
    </activity>

    <!-- MainActivity normal -->
    <activity
        android:name=".MainActivity"
        android:exported="false" />

</application>
```

### Mejores Prácticas para Splash Screens

✅ **Hacer**:
- Mantenerlo simple: solo logo y color de fondo
- Duración corta: máximo 1-2 segundos
- Usar colores de tu marca
- Usar la SplashScreen API en Android 12+
- Cargar recursos críticos durante el splash

❌ **Evitar**:
- Animaciones complejas (retrasan el inicio)
- Duración excesiva (frustra al usuario)
- Múltiples pantallas de splash
- Cargar datos pesados (hacerlo en background después)
- Splash screens para apps que inician rápido

### Diseño del Splash Screen

**Elementos recomendados**:
1. **Logo de la app** (centrado)
2. **Color de fondo** (color principal de tu marca)
3. **Opcional**: Nombre de la app (texto simple)

**Dimensiones del logo**:
- Tamaño recomendado: **192x192 dp** a **288x288 dp**
- Safe zone: **66x66 dp** del centro

**Colores**:
```xml
<!-- Ejemplos de combinaciones -->

<!-- Material Blue -->
<color name="splash_background">#2196F3</color>

<!-- Dark Mode Friendly -->
<color name="splash_background">#1F1F1F</color>

<!-- Gradiente (requiere drawable) -->
<drawable name="splash_background">
    <gradient
        android:startColor="#667eea"
        android:endColor="#764ba2"
        android:angle="135" />
</drawable>
```

---

## Convenciones de Nomenclatura

Seguir convenciones ayuda a mantener el código organizado y profesional.

### Application ID

**Formato**: `com.empresa.categoria.aplicacion`

**Ejemplos por tipo de desarrollador**:

```kotlin
// Estudiante / Personal
com.tunombre.nombreapp
com.juanperez.tareas
com.marialopez.finanzas

// Empresa pequeña
com.nombreempresa.nombreapp
com.miemprendimiento.ventas
com.consultoraxyz.clientes

// Organización
com.duoc.mobile.pokemongame
org.fundacion.nombreapp

// País específico (opcional)
cl.duoc.pokemongame      // Chile
ar.miempresa.ventas      // Argentina
mx.startup.finanzas      // México
```

**Reglas**:
- Todo en minúsculas
- Solo letras, números y puntos
- Mínimo 2 segmentos, recomendado 3
- No usar palabras reservadas de Java/Kotlin
- No comenzar con números
- Evitar guiones o guiones bajos

### Nombres de Archivos

#### Activities

```kotlin
// Formato: [Nombre]Activity.kt
MainActivity.kt
LoginActivity.kt
ProfileActivity.kt
SettingsActivity.kt
```

#### Composables

```kotlin
// Formato: [Nombre]Screen.kt o [Nombre]Component.kt
HomeScreen.kt
DetailScreen.kt
UserCard.kt
CustomButton.kt
```

#### ViewModels

```kotlin
// Formato: [Nombre]ViewModel.kt
HomeViewModel.kt
UserProfileViewModel.kt
```

#### Repositories

```kotlin
// Formato: [Nombre]Repository.kt
PokemonRepository.kt
UserRepository.kt
```

#### Layouts (XML)

```xml
<!-- Formato: [tipo]_[nombre].xml -->

<!-- Activities -->
activity_main.xml
activity_login.xml

<!-- Fragments -->
fragment_home.xml
fragment_profile.xml

<!-- List items -->
item_pokemon.xml
item_user.xml

<!-- Layouts incluidos -->
layout_toolbar.xml
layout_bottom_nav.xml
```

#### Drawables

```xml
<!-- Íconos -->
ic_home.xml
ic_search.xml
ic_back_arrow.xml

<!-- Backgrounds -->
bg_rounded_button.xml
bg_card.xml

<!-- Imágenes -->
img_placeholder.xml
img_logo.xml
```

#### Colores

```xml
<resources>
    <!-- Primarios -->
    <color name="primary">#2196F3</color>
    <color name="primary_dark">#1976D2</color>
    <color name="primary_light">#BBDEFB</color>

    <!-- Secundarios -->
    <color name="secondary">#FFC107</color>
    <color name="secondary_dark">#FFA000</color>

    <!-- Superficie -->
    <color name="background">#FFFFFF</color>
    <color name="surface">#FFFFFF</color>

    <!-- Estado -->
    <color name="error">#F44336</color>
    <color name="success">#4CAF50</color>
    <color name="warning">#FF9800</color>

    <!-- Texto -->
    <color name="text_primary">#212121</color>
    <color name="text_secondary">#757575</color>
    <color name="text_hint">#BDBDBD</color>
</resources>
```

#### Strings

```xml
<resources>
    <!-- General -->
    <string name="app_name">Mi App</string>
    <string name="ok">Aceptar</string>
    <string name="cancel">Cancelar</string>

    <!-- Login -->
    <string name="login_title">Iniciar Sesión</string>
    <string name="login_email_hint">Correo electrónico</string>
    <string name="login_password_hint">Contraseña</string>
    <string name="login_button">Entrar</string>

    <!-- Errores -->
    <string name="error_network">Error de conexión</string>
    <string name="error_empty_field">Este campo es obligatorio</string>

    <!-- Formato con parámetros -->
    <string name="welcome_message">¡Hola, %1$s!</string>
    <string name="items_count">%1$d elementos</string>
</resources>
```

### Convenciones de Código Kotlin

#### Variables y Funciones

```kotlin
// camelCase para variables y funciones
val userName = "Juan"
var itemCount = 0

fun getUserData() { }
fun calculateTotalPrice() { }
```

#### Clases e Interfaces

```kotlin
// PascalCase para clases
class UserProfile { }
data class Pokemon(val name: String)
interface ClickListener { }
```

#### Constantes

```kotlin
// UPPER_SNAKE_CASE para constantes
companion object {
    const val MAX_RETRY_COUNT = 3
    const val BASE_URL = "https://api.example.com"
    const val TIMEOUT_SECONDS = 30
}
```

#### Composables

```kotlin
// PascalCase, como funciones normales pero con mayúscula
@Composable
fun HomeScreen() { }

@Composable
fun UserProfileCard(user: User) { }

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit
) { }
```

---

## Checklist de Personalización

Usa esta lista para asegurarte de personalizar todos los aspectos importantes.

### Identidad Básica

- [ ] **Nombre de la app** cambiado en `strings.xml`
- [ ] **Application ID** único y apropiado
- [ ] Estructura de paquetes refactorizada
- [ ] **Version code** y **version name** configurados

### Iconos y Visuales

- [ ] **Icono launcher** personalizado generado
- [ ] Iconos en todas las densidades (mdpi, hdpi, xhdpi, xxhdpi, xxxhdpi)
- [ ] **Adaptive icon** configurado correctamente
- [ ] **Round icon** incluido
- [ ] Iconos probados en diferentes launchers
- [ ] **Splash screen** configurado
- [ ] Colores de marca definidos en `colors.xml`

### Configuración de Build

- [ ] **Namespace** actualizado en `build.gradle.kts`
- [ ] **Min SDK** y **Target SDK** apropiados
- [ ] Firma de release configurada (si aplica)
- [ ] ProGuard rules verificadas

### Manifest

- [ ] Permisos necesarios declarados
- [ ] Permisos innecesarios removidos
- [ ] Theme del splash screen aplicado
- [ ] Orientación de pantalla configurada

### Recursos Multiidioma (Opcional)

- [ ] Strings en español (`values/strings.xml`)
- [ ] Strings en inglés (`values-en/strings.xml`)
- [ ] Otros idiomas según necesidad

### Limpieza

- [ ] Código de ejemplo/demo removido
- [ ] Comentarios TODO atendidos o removidos
- [ ] Assets no utilizados eliminados
- [ ] Logs de debug removidos

### Testing

- [ ] App ejecutada en emulador
- [ ] App ejecutada en dispositivo físico
- [ ] Splash screen funciona correctamente
- [ ] Iconos se ven bien en diferentes launchers
- [ ] Nombre de app visible correctamente

### Documentación

- [ ] README actualizado con nombre del proyecto
- [ ] Instrucciones de build documentadas
- [ ] Información de keystore guardada de forma segura

---

## Recursos Útiles

### Herramientas de Diseño

**Generadores de Iconos**:
- [Android Asset Studio](https://romannurik.github.io/AndroidAssetStudio/) - Oficial de Google
- [Icon Kitchen](https://icon.kitchen/) - Moderna y completa
- [App Icon Generator](https://appicon.co/) - Múltiples plataformas

**Diseño Gráfico**:
- [Figma](https://figma.com) - Gratuito, excelente para UI/UX
- [Canva](https://canva.com) - Templates de iconos de app
- [Inkscape](https://inkscape.org/) - Gratuito, vectorial

**Paletas de Colores**:
- [Material Design Colors](https://materialui.co/colors/) - Colores Material Design
- [Coolors](https://coolors.co/) - Generador de paletas
- [Adobe Color](https://color.adobe.com/) - Rueda de colores

### Guías de Diseño

- [Material Design 3](https://m3.material.io/) - Guía oficial de Android
- [Android Design Guidelines](https://developer.android.com/design)
- [Iconografía Material](https://material.io/design/iconography)

### Verificadores

- [Package Name Checker](https://play.google.com/store) - Verifica disponibilidad en Play Store
- [App Manifest Validator](https://developer.android.com/studio/debug/apk-analyzer) - En Android Studio

---

## Ejemplo Completo: Personalizar desde Cero

Supongamos que quieres crear una app de recetas llamada "RecetasFácil".

### 1. Cambiar Application ID

```kotlin
// app/build.gradle.kts
android {
    namespace = "com.miempresa.recetasfacil"

    defaultConfig {
        applicationId = "com.miempresa.recetasfacil"
        versionCode = 1
        versionName = "1.0.0"
    }
}
```

### 2. Refactorizar Paquete

```
Antes: app/src/main/java/com/example/baseproject/
Después: app/src/main/java/com/miempresa/recetasfacil/
```

### 3. Cambiar Nombre

```xml
<!-- app/src/main/res/values/strings.xml -->
<resources>
    <string name="app_name">RecetasFácil</string>
</resources>
```

### 4. Crear Icono

**Diseño del icono**:
- Imagen: Un gorro de chef o tenedor/cuchara cruzados
- Colores: Rojo (#E53935) y blanco
- Estilo: Flat, simple, moderno

**Generación**:
1. Ir a [Icon Kitchen](https://icon.kitchen/)
2. Subir el diseño (1024x1024)
3. Background: #E53935 (rojo)
4. Descargar y extraer en `res/`

### 5. Configurar Splash Screen

```xml
<!-- res/values/themes.xml -->
<style name="Theme.RecetasFacil.Starting" parent="Theme.SplashScreen">
    <item name="windowSplashScreenAnimatedIcon">@mipmap/ic_launcher</item>
    <item name="windowSplashScreenBackground">@color/red_primary</item>
    <item name="postSplashScreenTheme">@style/Theme.RecetasFacil</item>
</style>
```

```xml
<!-- res/values/colors.xml -->
<color name="red_primary">#E53935</color>
<color name="red_dark">#C62828</color>
<color name="red_light">#FFCDD2</color>
```

### 6. Actualizar MainActivity

```kotlin
package com.miempresa.recetasfacil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.miempresa.recetasfacil.ui.theme.RecetasFacilTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            RecetasFacilTheme {
                // Tu app aquí
            }
        }
    }
}
```

### 7. Sincronizar y Ejecutar

```bash
# Limpiar build anterior
./gradlew clean

# Reconstruir
./gradlew build

# Ejecutar
./gradlew installDebug
```

### 8. Verificar

- ✅ Nombre "RecetasFácil" visible en launcher
- ✅ Icono personalizado de chef
- ✅ Splash screen rojo con logo
- ✅ Package `com.miempresa.recetasfacil`

---

¡Tu app ahora tiene identidad propia! 🎉
