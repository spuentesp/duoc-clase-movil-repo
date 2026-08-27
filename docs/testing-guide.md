# Guía de Pruebas Unitarias en Android con Kotlin

## Tabla de Contenidos
1. [Introducción](#introducción)
2. [¿Por qué hacer pruebas?](#por-qué-hacer-pruebas)
3. [Tipos de Pruebas](#tipos-de-pruebas)
4. [Configuración Inicial](#configuración-inicial)
5. [Estructura de Pruebas](#estructura-de-pruebas)
6. [Escribir tu Primera Prueba](#escribir-tu-primera-prueba)
7. [Patrones Comunes](#patrones-comunes)
8. [Ejecutar Pruebas](#ejecutar-pruebas)
9. [Mejores Prácticas](#mejores-prácticas)
10. [Ejemplos del Proyecto](#ejemplos-del-proyecto)
11. [Recursos Adicionales](#recursos-adicionales)

---

## Introducción

Las pruebas unitarias son fragmentos de código que verifican automáticamente que tu código funciona correctamente. En Android con Kotlin, las pruebas te ayudan a:

- ✅ Detectar bugs antes de que lleguen a producción
- ✅ Documentar cómo se usa tu código
- ✅ Facilitar refactorizaciones futuras
- ✅ Mejorar el diseño de tu código
- ✅ Ahorrar tiempo en testing manual

---

## ¿Por qué hacer pruebas?

Imagina que tienes una calculadora en tu app. Sin pruebas, cada vez que haces un cambio, necesitas:
1. Compilar la app
2. Instalarla en un dispositivo
3. Navegar a la calculadora
4. Probar manualmente cada operación

**Con pruebas automáticas:** Ejecutas un comando y en segundos sabes si todo funciona. ⚡

---

## Tipos de Pruebas

### 1. Pruebas Unitarias (Unit Tests)
- **Qué prueban:** Funciones y clases individuales aisladas
- **Dónde están:** `app/src/test/`
- **Cuándo usarlas:** Para lógica de negocio, validaciones, cálculos
- **Ventajas:** Rápidas, no necesitan dispositivo Android

**Ejemplo:** Probar que `2 + 2 = 4` en una clase Calculator

### 2. Pruebas de Integración (Integration Tests)
- **Qué prueban:** Cómo interactúan múltiples componentes
- **Dónde están:** `app/src/test/` o `app/src/androidTest/`
- **Cuándo usarlas:** Para probar repositorios, bases de datos, APIs
- **Ventajas:** Detectan problemas en la comunicación entre componentes

### 3. Pruebas de UI (UI/Instrumented Tests)
- **Qué prueban:** La interfaz de usuario en un dispositivo real o emulador
- **Dónde están:** `app/src/androidTest/`
- **Cuándo usarlas:** Para probar flujos completos de usuario
- **Desventajas:** Lentas, necesitan dispositivo Android

---

## Configuración Inicial

### 1. Dependencias en `build.gradle.kts`

Asegúrate de tener estas dependencias en tu archivo `app/build.gradle.kts`:

```kotlin
dependencies {
    // Testing básico
    testImplementation("junit:junit:4.13.2")

    // Kotlin test (recomendado para Kotlin)
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.9.20")

    // Coroutines test (para probar código asíncrono)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // MockK (mocking library para Kotlin)
    testImplementation("io.mockk:mockk:1.13.8")

    // Para pruebas de UI (opcional)
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
}
```

### 2. Estructura de Carpetas

```
app/
├── src/
│   ├── main/
│   │   └── java/com/example/baseproject/
│   │       ├── utils/
│   │       │   └── Calculator.kt          # Código de producción
│   │       └── viewmodel/
│   │           └── CounterViewModel.kt
│   │
│   └── test/
│       └── java/com/example/baseproject/  # Misma estructura de paquetes
│           ├── utils/
│           │   └── CalculatorTest.kt      # Pruebas unitarias
│           └── viewmodel/
│               └── CounterViewModelTest.kt
```

**Regla importante:** Los archivos de prueba deben estar en el mismo paquete que las clases que prueban.

---

## Estructura de Pruebas

### Anatomía de una Prueba

```kotlin
@Test
fun `nombre descriptivo de la prueba`() {
    // GIVEN (Dado) - Preparación
    val calculator = Calculator()
    val a = 5
    val b = 3

    // WHEN (Cuando) - Acción
    val result = calculator.add(a, b)

    // THEN (Entonces) - Verificación
    assertEquals(8, result)
}
```

### Componentes Clave

#### 1. Anotaciones

```kotlin
@Before
fun setUp() {
    // Se ejecuta ANTES de cada prueba
    // Usa para inicializar objetos
}

@After
fun tearDown() {
    // Se ejecuta DESPUÉS de cada prueba
    // Usa para limpiar recursos
}

@Test
fun nombreDeLaPrueba() {
    // Una prueba individual
}
```

#### 2. Assertions (Afirmaciones)

```kotlin
import org.junit.Assert.*

// Verifica que dos valores sean iguales
assertEquals(esperado, actual)
assertEquals("Mensaje si falla", esperado, actual)

// Verifica que dos valores NO sean iguales
assertNotEquals(noEsperado, actual)

// Verifica que algo sea verdadero/falso
assertTrue(condicion)
assertFalse(condicion)

// Verifica que algo sea null/no null
assertNull(objeto)
assertNotNull(objeto)

// Verifica que se lance una excepción
assertThrows(IllegalArgumentException::class.java) {
    // código que debería lanzar la excepción
}
```

---

## Escribir tu Primera Prueba

### Paso 1: Crear una clase a probar

Archivo: `app/src/main/java/com/example/baseproject/utils/Calculator.kt`

```kotlin
package com.example.baseproject.utils

class Calculator {
    fun add(a: Int, b: Int): Int {
        return a + b
    }
}
```

### Paso 2: Crear el archivo de prueba

1. En Android Studio, haz clic derecho en la clase `Calculator`
2. Selecciona **Generate** → **Test**
3. O crea manualmente: `app/src/test/java/com/example/baseproject/utils/CalculatorTest.kt`

### Paso 3: Escribir la prueba

```kotlin
package com.example.baseproject.utils

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CalculatorTest {

    private lateinit var calculator: Calculator

    @Before
    fun setUp() {
        calculator = Calculator()
    }

    @Test
    fun `suma de dos numeros positivos`() {
        // Given
        val a = 5
        val b = 3

        // When
        val result = calculator.add(a, b)

        // Then
        assertEquals(8, result)
    }
}
```

---

## Patrones Comunes

### 1. Probar Data Classes

```kotlin
data class User(val name: String, val age: Int)

class UserTest {
    @Test
    fun `crear usuario con parametros correctos`() {
        val user = User("Juan", 25)

        assertEquals("Juan", user.name)
        assertEquals(25, user.age)
    }

    @Test
    fun `dos usuarios con mismos datos son iguales`() {
        val user1 = User("Juan", 25)
        val user2 = User("Juan", 25)

        assertEquals(user1, user2)
    }

    @Test
    fun `copy funciona correctamente`() {
        val original = User("Juan", 25)
        val modified = original.copy(age = 26)

        assertEquals("Juan", modified.name)
        assertEquals(26, modified.age)
    }
}
```

### 2. Probar Funciones de Validación

```kotlin
object Validator {
    fun isValidEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }
}

class ValidatorTest {
    @Test
    fun `email valido retorna true`() {
        assertTrue(Validator.isValidEmail("test@example.com"))
    }

    @Test
    fun `email sin arroba retorna false`() {
        assertFalse(Validator.isValidEmail("testexample.com"))
    }

    @Test
    fun `email vacio retorna false`() {
        assertFalse(Validator.isValidEmail(""))
    }
}
```

### 3. Probar ViewModels

```kotlin
@OptIn(ExperimentalCoroutinesApi::class)
class MyViewModelTest {
    private lateinit var viewModel: MyViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        // Configura el dispatcher para coroutines
        Dispatchers.setMain(testDispatcher)
        viewModel = MyViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `estado inicial es correcto`() {
        assertEquals(0, viewModel.count.value)
    }

    @Test
    fun `increment aumenta el contador`() {
        viewModel.increment()
        assertEquals(1, viewModel.count.value)
    }
}
```

### 4. Probar Excepciones

```kotlin
class Calculator {
    fun divide(a: Int, b: Int): Int {
        if (b == 0) throw IllegalArgumentException("División por cero")
        return a / b
    }
}

class CalculatorTest {
    @Test
    fun `dividir por cero lanza excepcion`() {
        val calculator = Calculator()

        assertThrows(IllegalArgumentException::class.java) {
            calculator.divide(10, 0)
        }
    }

    @Test
    fun `division normal funciona`() {
        val calculator = Calculator()
        val result = calculator.divide(10, 2)
        assertEquals(5, result)
    }
}
```

---

## Ejecutar Pruebas

### Opción 1: Desde Android Studio (Recomendado)

#### Ejecutar todas las pruebas:
1. Abre el panel **Project**
2. Haz clic derecho en la carpeta `test`
3. Selecciona **Run 'All Tests'**

#### Ejecutar una clase de prueba:
1. Abre el archivo de prueba (ej: `CalculatorTest.kt`)
2. Haz clic en el ícono verde ▶️ junto al nombre de la clase
3. O clic derecho → **Run 'CalculatorTest'**

#### Ejecutar una sola prueba:
1. Haz clic en el ícono verde ▶️ junto al nombre de la función `@Test`

### Opción 2: Desde la Terminal

```bash
# Ejecutar TODAS las pruebas unitarias
./gradlew test

# Ejecutar pruebas de un módulo específico
./gradlew :app:test

# Ver reporte en HTML
# El reporte se genera en: app/build/reports/tests/testDebugUnitTest/index.html
```

### Opción 3: Con Gradle Task

En Android Studio:
1. Abre el panel **Gradle** (derecha)
2. Navega a: `app` → `Tasks` → `verification` → `test`
3. Doble clic en `test`

---

## Mejores Prácticas

### 1. Nombres Descriptivos

❌ **Mal:**
```kotlin
@Test
fun test1() { }
```

✅ **Bien:**
```kotlin
@Test
fun `suma de dos numeros positivos retorna resultado correcto`() { }
```

### 2. Una Verificación por Prueba

❌ **Mal:**
```kotlin
@Test
fun testCalculator() {
    assertEquals(8, calculator.add(5, 3))
    assertEquals(2, calculator.subtract(5, 3))
    assertEquals(15, calculator.multiply(5, 3))
}
```

✅ **Bien:**
```kotlin
@Test
fun `suma funciona correctamente`() {
    assertEquals(8, calculator.add(5, 3))
}

@Test
fun `resta funciona correctamente`() {
    assertEquals(2, calculator.subtract(5, 3))
}
```

### 3. Usar Given-When-Then

```kotlin
@Test
fun `ejemplo con estructura clara`() {
    // GIVEN - Estado inicial
    val usuario = Usuario("Juan", 25)

    // WHEN - Acción que estamos probando
    val resultado = usuario.cumplirAnos()

    // THEN - Verificación del resultado
    assertEquals(26, resultado.edad)
}
```

### 4. Probar Casos Edge (Borde)

```kotlin
class StringUtilsTest {
    @Test
    fun `string vacio`() {
        assertTrue(StringUtils.isEmpty(""))
    }

    @Test
    fun `string solo con espacios`() {
        assertTrue(StringUtils.isEmpty("   "))
    }

    @Test
    fun `string null`() {
        assertTrue(StringUtils.isEmpty(null))
    }

    @Test
    fun `string valido`() {
        assertFalse(StringUtils.isEmpty("texto"))
    }
}
```

### 5. Mantener Pruebas Rápidas

- ✅ Las pruebas unitarias deben ejecutarse en milisegundos
- ❌ Si una prueba tarda más de 1 segundo, probablemente esté haciendo demasiado
- ✅ Evita operaciones de red, archivos, o bases de datos en pruebas unitarias

---

## Ejemplos del Proyecto

Este proyecto incluye varios ejemplos de pruebas:

### 1. Calculator - Clase Utilitaria
📁 **Código:** `app/src/main/java/com/example/baseproject/utils/Calculator.kt`
📁 **Pruebas:** `app/src/test/java/com/example/baseproject/utils/CalculatorTest.kt`

**Qué demuestra:**
- Pruebas básicas de funciones
- Verificación de excepciones
- Múltiples casos de prueba

### 2. StringValidator - Validaciones
📁 **Código:** `app/src/main/java/com/example/baseproject/utils/StringValidator.kt`
📁 **Pruebas:** `app/src/test/java/com/example/baseproject/utils/StringValidatorTest.kt`

**Qué demuestra:**
- Pruebas de regex
- Casos válidos e inválidos
- Validaciones del mundo real (emails, RUT, teléfonos)

### 3. ShowcaseCategory - Data Class
📁 **Código:** `app/src/main/java/com/example/baseproject/ui/screens/ShowcaseCategory.kt`
📁 **Pruebas:** `app/src/test/java/com/example/baseproject/ui/screens/ShowcaseCategoryTest.kt`

**Qué demuestra:**
- Pruebas de data classes
- Verificación de igualdad
- Función `copy()`
- Pruebas de listas

### 4. CounterViewModel - ViewModel
📁 **Código:** `app/src/main/java/com/example/baseproject/viewmodel/CounterViewModel.kt`
📁 **Pruebas:** `app/src/test/java/com/example/baseproject/viewmodel/CounterViewModelTest.kt`

**Qué demuestra:**
- Pruebas de ViewModels
- Testing de StateFlow
- Configuración de coroutines para tests
- Verificación de cambios de estado

---

## Comandos Útiles

```bash
# Ejecutar todas las pruebas
./gradlew test

# Ejecutar pruebas con reporte detallado
./gradlew test --info

# Limpiar y ejecutar pruebas
./gradlew clean test

# Ejecutar pruebas en modo continuo (re-ejecuta al cambiar código)
./gradlew test --continuous

# Ver cobertura de código (requiere configuración adicional)
./gradlew testDebugUnitTestCoverage
```

---

## Solución de Problemas Comunes

### Problema 1: "Cannot resolve symbol 'assertEquals'"

**Solución:** Importa las funciones de assert:
```kotlin
import org.junit.Assert.*
```

### Problema 2: "lateinit property has not been initialized"

**Solución:** Asegúrate de inicializar en `@Before`:
```kotlin
private lateinit var calculator: Calculator

@Before
fun setUp() {
    calculator = Calculator()  // ← No olvides esto
}
```

### Problema 3: Las pruebas no se encuentran

**Solución:**
- Verifica que el archivo esté en `src/test/` (no en `src/main/`)
- Asegúrate de que el paquete sea correcto
- Sincroniza Gradle: **File → Sync Project with Gradle Files**

### Problema 4: "Method ... not mocked"

**Solución:** Estás intentando usar APIs de Android en una prueba unitaria. Opciones:
1. Usa `@RunWith(RobolectricTestRunner::class)` para simular Android
2. Mueve la prueba a `androidTest/` si necesita Android real
3. Extrae la lógica a una función pura sin dependencias de Android

---

## Recursos Adicionales

### Documentación Oficial
- [Testing en Android](https://developer.android.com/training/testing)
- [JUnit 4](https://junit.org/junit4/)
- [Kotlin Test](https://kotlinlang.org/api/latest/kotlin.test/)

### Tutoriales Recomendados
- [Codelab: Testing Basics](https://developer.android.com/codelabs/advanced-android-kotlin-training-testing-basics)
- [Testing ViewModels](https://developer.android.com/codelabs/android-room-with-a-view-kotlin#9)

### Librerías Útiles
- **JUnit 4**: Framework de testing
- **MockK**: Mocking para Kotlin
- **Truth**: Assertions más legibles de Google
- **Turbine**: Testing de Flows
- **Robolectric**: Android framework en JVM

---

## Siguiente Paso: ¡Escribe tu Primera Prueba! 🚀

1. Abre el proyecto en Android Studio
2. Navega a `CalculatorTest.kt`
3. Haz clic en el ícono verde ▶️ junto a la clase
4. ¡Observa cómo todas las pruebas pasan! ✅

**Desafío:** Ahora intenta:
- Agregar una nueva función a `Calculator.kt`
- Escribir pruebas para esa función
- Ejecutar las pruebas y verificar que pasen

---

## Resumen

- ✅ Las pruebas unitarias te ahorran tiempo y detectan bugs temprano
- ✅ Están en `src/test/` y no necesitan dispositivo
- ✅ Usa `@Test`, `@Before`, `@After` para estructurar
- ✅ Usa `assertEquals`, `assertTrue`, etc. para verificar
- ✅ Sigue el patrón Given-When-Then
- ✅ Escribe nombres descriptivos en español
- ✅ Ejecuta con `./gradlew test` o desde Android Studio

**¡Feliz Testing!** 🎉
