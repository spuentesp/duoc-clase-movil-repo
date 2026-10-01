# Guía de Robolectric: qué es y cómo se usa

## 1. Los dos tipos de test en Android

| | Test **unitario** (`src/test/`) | Test **instrumentado** (`src/androidTest/`) |
| --- | --- | --- |
| Dónde corre | En tu computador (la JVM) | En un emulador o teléfono |
| Velocidad | Segundos | Minutos (hay que instalar la app) |
| ¿Tiene Android? | **No**: clases como `Bundle`, `Context` o `Activity` no funcionan | Sí, el Android real |

El problema: muchas cosas que queremos probar sí usan Android. Por ejemplo,
`toRoute()` lee los argumentos desde un `Bundle`, y una pantalla Compose necesita
una `Activity`. En un test unitario normal eso falla con errores como
`Method ... not mocked`.

## 2. ¿Qué es Robolectric?

**Robolectric** es una librería que **simula Android dentro de la JVM**. Trae una
versión de las clases del sistema (`Bundle`, `Context`, `Activity`, recursos…) que
funciona en tu computador.

Resultado: puedes probar código que usa Android **como si fuera un test unitario**,
rápido y sin emulador. Por eso este proyecto puede tocar botones y comprobar la
navegación con `./gradlew testDebugUnitTest`.

> No reemplaza del todo al emulador: no es un teléfono real (rendimiento, cámara,
> sensores). Pero para lógica, navegación y UI básica es muy útil.

## 3. Cómo se configura

Ya está configurado en este proyecto (`app/build.gradle.kts`):

```kotlin
android {
    testOptions {
        // Robolectric necesita acceder a los recursos (strings, temas) de la app
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    testImplementation("org.robolectric:robolectric:<versión>")

    // Solo para probar pantallas Compose:
    testImplementation(platform("androidx.compose:compose-bom:<versión>"))
    testImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
```

La primera vez que corre, Robolectric descarga una imagen de Android (pesa
bastante); después queda guardada y es rápido.

## 4. Cómo se usa: lo único obligatorio es `@RunWith`

Un test con Robolectric es un test JUnit normal con **una línea extra**:

```kotlin
@RunWith(RobolectricTestRunner::class)   // ← activa el Android simulado
class TareasViewModelTest {
    @Test
    fun `el detalle lee el id de la ruta`() {
        val handle = SavedStateHandle(route = DetalleTarea(tareaId = 2)) // usa Bundle → necesita Robolectric
        val vm = DetalleTareaViewModel(RepositorioTareasEnMemoria(), handle)
        assertEquals(2, vm.tareaId)
    }
}
```

Sin `@RunWith(RobolectricTestRunner::class)` ese mismo test falla, porque en la
JVM no existe `Bundle`.

## 5. Probar pantallas Compose (tocar botones)

Con Robolectric + `createComposeRule()` se dibuja la pantalla y se interactúa
con ella. Así está hecho `NavegacionLeccionesTest.kt`:

```kotlin
@RunWith(RobolectricTestRunner::class)
class NavegacionLeccionesTest {

    @get:Rule
    val compose = createComposeRule()           // crea una Activity vacía para dibujar

    @Test
    fun leccion01_navega_y_retrocede() {
        // 1. Dibujar la pantalla
        compose.setContent { Leccion01NavegacionBasica(onSalir = {}) }

        // 2. Buscar un elemento por su texto y tocarlo
        compose.onNodeWithText("Ir a Perfil").performScrollTo().performClick()
        compose.waitForIdle()                    // esperar a que termine la animación de navegación

        // 3. Comprobar que llegamos a la pantalla correcta
        compose.onNodeWithText("Pantalla: Perfil").assertExists()
    }
}
```

Funciones más usadas:

| Función | Para qué |
| --- | --- |
| `setContent { ... }` | Dibuja el composable a probar |
| `onNodeWithText("...")` | Busca un elemento por su texto (`substring = true` para buscar una parte) |
| `onNodeWithContentDescription("Volver")` | Busca íconos por su descripción |
| `onNode(hasSetTextAction())` | Busca un campo de texto |
| `performClick()` | Lo toca |
| `performScrollTo()` | Hace scroll hasta el elemento antes de tocarlo |
| `performTextReplacement("Ana")` | Escribe en un campo de texto |
| `assertExists()` | Comprueba que el elemento está en pantalla |
| `waitForIdle()` | Espera a que terminen animaciones y recomposiciones |
| `waitUntil { ... }` | Espera algo asíncrono (por ejemplo, datos que cargan con `delay`) |

Para abrir **la app completa** (con su `MainActivity` y Hilt) se usa
`createAndroidComposeRule<MainActivity>()`. Ver `AppConHiltTest.kt`.

## 6. Cómo correr los tests

- **Terminal:** `./gradlew testDebugUnitTest`
  (reporte en `app/build/reports/tests/testDebugUnitTest/index.html`)
- **Android Studio:** abre un archivo de `app/src/test/` y toca el ▶ verde junto a la clase o al `@Test`.

## 7. Errores comunes

| Error | Causa | Solución |
| --- | --- | --- |
| `Method ... in android.os.Bundle not mocked` | Falta `@RunWith(RobolectricTestRunner::class)` | Agrégalo a la clase de test |
| `Unable to resolve activity for Intent ... ComponentActivity` | Falta `ui-test-manifest` | `debugImplementation("androidx.compose.ui:ui-test-manifest")` |
| `Resources$NotFoundException` | No se incluyen los recursos | `unitTests.isIncludeAndroidResources = true` |
| `No compose hierarchies found` / no encuentra el nodo | El texto no coincide exacto o falta esperar | Usa `substring = true` y `waitForIdle()` |
| Error de versión de Java | Android 35+ en Robolectric requiere JDK 21 | Usa el JDK que trae Android Studio |
