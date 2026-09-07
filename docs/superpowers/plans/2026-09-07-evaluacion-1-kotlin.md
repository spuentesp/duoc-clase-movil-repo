# Evaluación 1 (Kotlin) Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Crear la carpeta-proyecto `evaluacion 1/` en la raíz del repo con un proyecto Gradle Kotlin JVM autocontenido que incluye 5 ejercicios (con enunciado, solución y suite de tests) similares a la Evaluación Formativa N°1.

**Architecture:** Proyecto Gradle Kotlin JVM puro (mismo patrón que `exercises/`). Un único módulo con paquetes `ejercicio1`..`ejercicio8`. Cada ejercicio contiene: `enunciados/ejercicioN.md` (documentación), `src/main/kotlin/ejercicioN/*.kt` (solución) y `src/test/kotlin/ejercicioN/*Test.kt` (suite de tests). TDD por ejercicio: primero el test que falla, luego la implementación que lo hace pasar.

**Tech Stack:** Kotlin 1.9.20, Gradle 8.4 (wrapper), Java 17 toolchain, JUnit 5 (BOM 5.10.1), kotlinx-coroutines-core 1.7.3, kotlinx-coroutines-test 1.7.3.

## Global Constraints

- **Lenguaje único:** Kotlin. Cero dependencias de Android, Jetpack, Compose, ni frameworks web.
- **Plataforma:** IntelliJ IDEA (Community o Ultimate). **No** Android Studio.
- **Toolchain:** JDK 17 (toolchain portable ya instalado en `/home/sebastian/.local/jdk/jdk-17.0.20.1+1`).
- **Gradle wrapper:** 8.4, distribución `gradle-8.4-bin.zip`.
- **Tests:** JUnit 5 + `kotlinx-coroutines-test`. Anotaciones `@DisplayName("…")` en español.
- **Nombres y comentarios:** en español (clases, métodos, mensajes, enunciados).
- **Cada `Solucion.kt`** debe pasar su `SolucionTest.kt` con todos los tests en verde.
- **Cada `enunciados/ejercicioN.md`** debe estar en español, incluir contexto, objetivos, requisitos, enfoque paso a paso y criterios de aceptación.
- **Cada suite** debe tener entre 5 y 10 casos de prueba cubriendo happy path, bordes y errores.
- **Carpeta destino:** `evaluacion 1/` con espacio — Git y Gradle lo aceptan; usar comillas en shell.
- **Commits:** uno por tarea, mensajes en español en formato `feat/`, `docs`, `chore`.
- **Push final:** `git push origin main` solo después de que `./gradlew test` pase todos los tests.

---

## File Structure

Crear (no modificar ningún archivo existente fuera de `.gitignore` raíz):

```
evaluacion 1/
├── .gitignore                                # Ignora build/, .gradle/, .idea/, etc.
├── README.md                                 # Cómo correr, índice de ejercicios
├── build.gradle.kts                          # Kotlin JVM + JUnit 5 + coroutines-test
├── settings.gradle.kts                       # rootProject.name = "evaluacion-1"
├── gradle.properties                         # Toolchain JDK 17 portable
├── gradlew, gradlew.bat                      # Copiados de exercises/
├── gradle/wrapper/
│   ├── gradle-wrapper.jar                    # Copiado de exercises/
│   └── gradle-wrapper.properties             # Gradle 8.4
├── enunciados/
│   ├── ejercicio1.md                         # POO — Biblioteca
│   ├── ejercicio2.md                         # Colecciones — Tienda
│   ├── ejercicio3.md                         # Corrutinas — Pagos hospitalarios
│   ├── ejercicio4.md                         # Excepciones — Reservas de hotel
│   ├── ejercicio5.md                         # Lambdas/HOF — Cuentas bancarias
│   ├── ejercicio6.md                         # Tipos/aritmética/condicionales — Nóminas
│   ├── ejercicio7.md                         # Ciclos e iteraciones — Fibonacci
│   └── ejercicio8.md                         # Constructores secundarios — Figuras
└── src/
    ├── main/kotlin/
    │   ├── ejercicio1/Publicacion.kt         # open class + Libro + Revista
    │   ├── ejercicio2/Producto.kt            # data class + clase Inventario
    │   ├── ejercicio3/EstadoPago.kt          # sealed class + ProcesadorPagos + main
    │   ├── ejercicio4/Habitacion.kt          # data + GestorReservas + excepciones
    │   └── ejercicio5/Cuenta.kt              # enum + data + clase Banco
    └── test/kotlin/
        ├── ejercicio1/PublicacionTest.kt
        ├── ejercicio2/ProductoTest.kt
        ├── ejercicio3/EstadoPagoTest.kt
        ├── ejercicio4/HabitacionTest.kt
        └── ejercicio5/CuentaTest.kt
```

**Modificar:**
- `.gitignore` (raíz) → añadir `evaluacion 1/build/` y `evaluacion 1/.gradle/`.

---

## Task 1: Scaffolding del proyecto

**Files:**
- Create: `evaluacion 1/.gitignore`
- Create: `evaluacion 1/README.md`
- Create: `evaluacion 1/build.gradle.kts`
- Create: `evaluacion 1/settings.gradle.kts`
- Create: `evaluacion 1/gradle.properties`
- Create: `evaluacion 1/gradlew`
- Create: `evaluacion 1/gradlew.bat`
- Create: `evaluacion 1/gradle/wrapper/gradle-wrapper.jar`
- Create: `evaluacion 1/gradle/wrapper/gradle-wrapper.properties`
- Create: `evaluacion 1/enunciados/.gitkeep` (placeholder temporal, se borra tras Task 7)
- Modify: `.gitignore` (raíz)

**Step-by-step:**

- [ ] **Step 1.1:** Crear la carpeta `evaluacion 1/` con subcarpetas `enunciados/`, `gradle/wrapper/`, `src/main/kotlin/`, `src/test/kotlin/`.

```bash
mkdir -p "evaluacion 1"/{enunciados,gradle/wrapper,src/main/kotlin,src/test/kotlin}
```

- [ ] **Step 1.2:** Copiar wrapper de `exercises/` (gradlew, gradlew.bat, gradle-wrapper.jar, gradle-wrapper.properties) a `evaluacion 1/`.

```bash
cp exercises/gradlew exercises/gradlew.bat "evaluacion 1/"
cp exercises/gradle/wrapper/gradle-wrapper.jar "evaluacion 1/gradle/wrapper/"
cp exercises/gradle/wrapper/gradle-wrapper.properties "evaluacion 1/gradle/wrapper/"
chmod +x "evaluacion 1/gradlew"
```

- [ ] **Step 1.3:** Crear `evaluacion 1/settings.gradle.kts`:

```kotlin
rootProject.name = "evaluacion-1"
```

- [ ] **Step 1.4:** Crear `evaluacion 1/gradle.properties`:

```
org.gradle.jvmargs=-Xmx2g -Dfile.encoding=UTF-8
kotlin.code.style=official
# Toolchain JDK 17 portable
org.gradle.java.installations.paths=/home/sebastian/.local/jdk/jdk-17.0.20.1+1
```

- [ ] **Step 1.5:** Crear `evaluacion 1/build.gradle.kts`:

```kotlin
plugins {
    kotlin("jvm") version "1.9.20"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    testImplementation(platform("org.junit:junit-bom:5.10.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
}

kotlin {
    jvmToolchain(17)
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "failed", "skipped")
    }
}
```

- [ ] **Step 1.6:** Crear `evaluacion 1/.gitignore`:

```
.gradle/
build/
.idea/
*.iml
out/
.kotlin/
local.properties
.DS_Store
```

- [ ] **Step 1.7:** Añadir exclusiones al `.gitignore` raíz:

```
evaluacion 1/build/
evaluacion 1/.gradle/
```

(Añadir estas dos líneas al final del `.gitignore` raíz, después de `exercises/.gradle/`.)

- [ ] **Step 1.8:** Crear `evaluacion 1/README.md` con índice de ejercicios y cómo ejecutar (placeholder por ahora; se actualiza al final):

```markdown
# Evaluación 1 — Kotlin

Proyecto autocontenido con 5 ejercicios en Kotlin (JVM puro, IntelliJ IDEA).

## Requisitos

- JDK 17 (toolchain portable en `/home/sebastian/.local/jdk/jdk-17.0.20.1+1`).
- IntelliJ IDEA (Community o Ultimate). **No** Android Studio.

## Cómo ejecutar los tests

```bash
cd "evaluacion 1"
./gradlew test
```

## Índice de ejercicios

| # | Dominio                    | Concepto                                   |
|---|----------------------------|--------------------------------------------|
| 1 | Biblioteca                 | POO, herencia y polimorfismo               |
| 2 | Tienda online              | Colecciones y operaciones funcionales       |
| 3 | Pagos hospitalarios        | Corrutinas y `sealed class`                |
| 4 | Reservas de hotel          | Excepciones personalizadas                       |
| 5 | Cuentas bancarias          | Lambdas y funciones de orden superior       |

Cada ejercicio tiene su enunciado en `enunciados/ejercicioN.md`.
```

- [ ] **Step 1.9:** Sanity check — ejecutar `./gradlew tasks` desde la carpeta para validar que el wrapper, settings y build config están bien:

```bash
cd "evaluacion 1" && ./gradlew tasks --no-daemon | head -30
```

Esperado: lista las tareas de Gradle (sin errores de sintaxis ni toolchain).

- [ ] **Step 1.10:** Commit:

```bash
git add ".gitignore" "evaluacion 1/"
git commit -m "chore(evaluacion-1): scaffolding del proyecto Gradle Kotlin"
```

---

## Task 2: Ejercicio 1 — Biblioteca (POO + polimorfismo)

**Files:**
- Create: `evaluacion 1/src/test/kotlin/ejercicio1/PublicacionTest.kt`
- Create: `evaluacion 1/src/main/kotlin/ejercicio1/Publicacion.kt`
- Create: `evaluacion 1/enunciados/ejercicio1.md`

**Interfaces (lo que este task produce, lo que otros tasks consumen):**
- Produce: `open class Publicacion(val id: Int, val titulo: String, val anio: Int)` con `open fun mostrarDetalle(): String`.
- Produce: `class Libro(...) : Publicacion(...)` y `class Revista(...) : Publicacion(...)` con `override fun mostrarDetalle()`.

**Step-by-step:**

- [ ] **Step 2.1:** Crear el test primero (`evaluacion 1/src/test/kotlin/ejercicio1/PublicacionTest.kt`):

```kotlin
package ejercicio1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Ejercicio 1 — Biblioteca: Publicacion, Libro, Revista")
class PublicacionTest {

    @Test
    @DisplayName("mostrarDetalle de Publicacion base incluye id, titulo y anio")
    fun publicacionBaseMuestraDetalle() {
        val p: Publicacion = Publicacion(1, "Obra generica", 2000)
        assertTrue(p.mostrarDetalle().contains("[1]"))
        assertTrue(p.mostrarDetalle().contains("Obra generica"))
        assertTrue(p.mostrarDetalle().contains("2000"))
    }

    @Test
    @DisplayName("Libro override de mostrarDetalle incluye autor y paginas")
    fun libroOverrideMuestraDetalle() {
        val libro = Libro(id = 10, titulo = "Kotlin in Action", anio = 2017,
            autor = "Dmitry Jemerov", paginas = 360)
        val detalle = libro.mostrarDetalle()
        assertTrue(detalle.contains("Kotlin in Action"))
        assertTrue(detalle.contains("Dmitry Jemerov"))
        assertTrue(detalle.contains("360"))
    }

    @Test
    @DisplayName("Revista override de mostrarDetalle incluye editor y numero")
    fun revistaOverrideMuestraDetalle() {
        val revista = Revista(id = 22, titulo = "National Geographic", anio = 2023,
            editor = "National Geographic Society", numero = 245)
        val detalle = revista.mostrarDetalle()
        assertTrue(detalle.contains("National Geographic"))
        assertTrue(detalle.contains("National Geographic Society"))
        assertTrue(detalle.contains("245"))
    }

    @Test
    @DisplayName("Polimorfismo: Libro y Revista son Publicacion")
    fun polimorfismoLibroYRevistaSonPublicacion() {
        val publicaciones: List<Publicacion> = listOf(
            Libro(1, "A", 2000, "Autor A", 100),
            Revista(2, "B", 2001, "Editor B", 5)
        )
        assertEquals(2, publicaciones.size)
        assertTrue(publicaciones.all { it.mostrarDetalle().isNotBlank() })
    }

    @Test
    @DisplayName("Libro expone sus propiedades especificas")
    fun libroExponePropiedades() {
        val libro = Libro(1, "Clean Code", 2008, "Robert C. Martin", 450)
        assertEquals("Robert C. Martin", libro.autor)
        assertEquals(450, libro.paginas)
        assertEquals(2008, libro.anio)
    }

    @Test
    @DisplayName("Revista expone sus propiedades especificas")
    fun revistaExponePropiedades() {
        val revista = Revista(1, "Time", 2024, "Time USA", 102)
        assertEquals("Time USA", revista.editor)
        assertEquals(102, revista.numero)
    }
}
```

- [ ] **Step 2.2:** Correr el test para verificar que falla (no existe la implementación):

```bash
cd "evaluacion 1" && ./gradlew test --tests "ejercicio1.*" --no-daemon
```

Esperado: FAIL — `Unresolved reference: Publicacion`.

- [ ] **Step 2.3:** Implementar `evaluacion 1/src/main/kotlin/ejercicio1/Publicacion.kt`:

```kotlin
package ejercicio1

open class Publicacion(
    val id: Int,
    val titulo: String,
    val anio: Int
) {
    open fun mostrarDetalle(): String = "[$id] $titulo ($anio)"
}

class Libro(
    id: Int,
    titulo: String,
    anio: Int,
    val autor: String,
    val paginas: Int
) : Publicacion(id, titulo, anio) {
    override fun mostrarDetalle(): String =
        "${super.mostrarDetalle()} - $autor, $paginas pags."
}

class Revista(
    id: Int,
    titulo: String,
    anio: Int,
    val editor: String,
    val numero: Int
) : Publicacion(id, titulo, anio) {
    override fun mostrarDetalle(): String =
        "${super.mostrarDetalle()} - Revista $editor #$numero"
}
```

- [ ] **Step 2.4:** Correr el test para verificar que pasa:

```bash
cd "evaluacion 1" && ./gradlew test --tests "ejercicio1.*" --no-daemon
```

Esperado: PASS — 6 tests verdes.

- [ ] **Step 2.5:** Crear `evaluacion 1/enunciados/ejercicio1.md`:

```markdown
# Ejercicio 1 — Biblioteca (POO + polimorfismo)

## Contexto

La biblioteca municipal necesita un módulo para registrar las publicaciones que adquiere: libros y revistas. Cada publicación comparte información básica (id, título, año) pero muestra su detalle de manera diferente según el tipo.

## Objetivos de aprendizaje (IL 1.1, IL 1.2, IL 1.3)

- Declarar una clase base `open` para permitir herencia.
- Crear clases derivadas que extiendan la base y sobrescriban un método.
- Usar polimorfismo: tratar distintos tipos como la misma clase base.

## Requisitos

Crea en el paquete `ejercicio1`:

- `open class Publicacion(val id: Int, val titulo: String, val anio: Int)` con `open fun mostrarDetalle(): String`.
- `class Libro(...) : Publicacion(...)` con `val autor: String` y `val paginas: Int`. Override de `mostrarDetalle()`.
- `class Revista(...) : Publicacion(...)` con `val editor: String` y `val numero: Int`. Override de `mostrarDetalle()`.

## Enfoque paso a paso

1. Declara la clase base con `open class` y un método `open fun`.
2. Crea las dos derivadas usando `:` y pasando los parámetros al constructor padre.
3. Sobrescribe `mostrarDetalle()` con `override fun` y usa `super.mostrarDetalle()` para reutilizar la base.
4. Verifica que `Libro` y `Revista` son asignables a `Publicacion` (polimorfismo).

## Criterios de aceptación

- `Publicacion.mostrarDetalle()` retorna un texto con id, título y año.
- `Libro.mostrarDetalle()` extiende lo anterior con autor y páginas.
- `Revista.mostrarDetalle()` extiende lo anterior con editor y número.
- Una `List<Publicacion>` puede contener mezclados libros y revistas.
- Los 6 tests en `PublicacionTest` pasan en verde.
```

- [ ] **Step 2.6:** Commit:

```bash
git add "evaluacion 1/src" "evaluacion 1/enunciados/ejercicio1.md"
git commit -m "feat(evaluacion-1): ejercicio 1 - Biblioteca (POO y polimorfismo)"
```

---

## Task 3: Ejercicio 2 — Tienda online (colecciones)

**Files:**
- Create: `evaluacion 1/src/test/kotlin/ejercicio2/ProductoTest.kt`
- Create: `evaluacion 1/src/main/kotlin/ejercicio2/Producto.kt`
- Create: `evaluacion 1/enunciados/ejercicio2.md`

**Interfaces:**
- Produce: `data class Producto(val id: Int, val nombre: String, val precio: Int, val categoria: String, val stock: Int)`.
- Produce: `class Inventario(val productos: List<Producto>)` con métodos `valorTotal()`, `conStockBajo()`, `agruparPorCategoria()`, `topMasCaros(n)`.

**Step-by-step:**

- [ ] **Step 3.1:** Crear el test:

```kotlin
package ejercicio2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Ejercicio 2 - Tienda online: Producto e Inventario")
class ProductoTest {

    private val muestra = listOf(
        Producto(1, "Audifonos", 15000, "Tecnologia", 8),
        Producto(2, "Mouse",     8000,  "Tecnologia", 3),
        Producto(3, "Silla",     45000, "Hogar",       12),
        Producto(4, "Lampara",   12000, "Hogar",       2),
        Producto(5, "Cuaderno",  1500,  "Oficina",     40)
    )

    @Test
    @DisplayName("valorTotal suma precio por stock de cada producto")
    fun valorTotalSumaPrecioPorStock() {
        val inv = Inventario(muestra)
        val esperado = 15000 * 8 + 8000 * 3 + 45000 * 12 + 12000 * 2 + 1500 * 40
        assertEquals(esperado, inv.valorTotal())
    }

    @Test
    @DisplayName("conStockBajo retorna productos con stock menor al umbral")
    fun conStockBajoRetornaPorDebajoDeUmbral() {
        val inv = Inventario(muestra)
        val bajos = inv.conStockBajo(umbral = 5)
        assertEquals(2, bajos.size)
        assertTrue(bajos.all { it.stock < 5 })
        assertTrue(bajos.map { it.id }.containsAll(listOf(2, 4)))
    }

    @Test
    @DisplayName("agruparPorCategoria devuelve un map por categoria")
    fun agruparPorCategoriaDevuelveMap() {
        val inv = Inventario(muestra)
        val grupos = inv.agruparPorCategoria()
        assertEquals(2, grupos["Tecnologia"]?.size)
        assertEquals(2, grupos["Hogar"]?.size)
        assertEquals(1, grupos["Oficina"]?.size)
    }

    @Test
    @DisplayName("topMasCaros retorna los N mas caros ordenados descendente")
    fun topMasCarosRetornaNMasCaros() {
        val inv = Inventario(muestra)
        val top = inv.topMasCaros(2)
        assertEquals(2, top.size)
        assertEquals(45000, top[0].precio)
        assertEquals(15000, top[1].precio)
    }

    @Test
    @DisplayName("Inventario vacio tiene valorTotal 0 y grupos vacios")
    fun inventarioVacio() {
        val inv = Inventario(emptyList())
        assertEquals(0, inv.valorTotal())
        assertEquals(emptyList<Producto>(), inv.conStockBajo())
        assertTrue(inv.agruparPorCategoria().isEmpty())
    }
}
```

- [ ] **Step 3.2:** Correr el test para verificar que falla:

```bash
cd "evaluacion 1" && ./gradlew test --tests "ejercicio2.*" --no-daemon
```

Esperado: FAIL — `Unresolved reference: Producto`.

- [ ] **Step 3.3:** Implementar `evaluacion 1/src/main/kotlin/ejercicio2/Producto.kt`:

```kotlin
package ejercicio2

data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Int,
    val categoria: String,
    val stock: Int
)

class Inventario(val productos: List<Producto>) {

    fun valorTotal(): Int = productos.sumOf { it.precio * it.stock }

    fun conStockBajo(umbral: Int = 5): List<Producto> =
        productos.filter { it.stock < umbral }

    fun agruparPorCategoria(): Map<String, List<Producto>> =
        productos.groupBy { it.categoria }

    fun topMasCaros(n: Int): List<Producto> =
        productos.sortedByDescending { it.precio }.take(n)
}
```

- [ ] **Step 3.4:** Correr el test para verificar que pasa:

```bash
cd "evaluacion 1" && ./gradlew test --tests "ejercicio2.*" --no-daemon
```

Esperado: PASS — 5 tests verdes.

- [ ] **Step 3.5:** Crear `evaluacion 1/enunciados/ejercicio2.md`:

```markdown
# Ejercicio 2 - Tienda online (Colecciones)

## Contexto

Una tienda online mantiene un catálogo de productos. Necesita consultas rápidas: valor total del inventario, productos con stock bajo, agrupación por categoría y ranking de productos más caros.

## Objetivos de aprendizaje (IL 1.2)

- Trabajar con `List` y operaciones funcionales: `filter`, `map`, `groupBy`, `sumOf`, `sortedByDescending`, `take`.
- Usar `data class` para modelar entidades inmutables.
- Definir una clase (`Inventario`) que encapsula operaciones sobre una colección.

## Requisitos

- `data class Producto(val id: Int, val nombre: String, val precio: Int, val categoria: String, val stock: Int)`.
- `class Inventario(val productos: List<Producto>)` con:
  - `fun valorTotal(): Int` - suma `precio * stock` de cada producto.
  - `fun conStockBajo(umbral: Int = 5): List<Producto>`.
  - `fun agruparPorCategoria(): Map<String, List<Producto>>`.
  - `fun topMasCaros(n: Int): List<Producto>`.

## Enfoque paso a paso

1. Modela el producto con `data class` para tener `equals`, `hashCode` y `copy` gratis.
2. Construye `Inventario` con la lista de productos como dependencia inyectada por constructor.
3. Implementa cada método usando una sola expresión con las funciones de extensión de la stdlib.
4. Verifica que el inventario vacío no rompe (caso borde).

## Criterios de aceptación

- `valorTotal` ignora productos con stock 0.
- `conStockBajo` retorna una lista, no `null`.
- `agruparPorCategoria` produce un `Map` inmutable.
- `topMasCaros(n)` respeta `n` y ordena de mayor a menor precio.
- Los 5 tests en `ProductoTest` pasan en verde.
```

- [ ] **Step 3.6:** Commit:

```bash
git add "evaluacion 1/src" "evaluacion 1/enunciados/ejercicio2.md"
git commit -m "feat(evaluacion-1): ejercicio 2 - Tienda online (colecciones)"
```

---

## Task 4: Ejercicio 3 — Pagos hospitalarios (corrutinas + sealed)

**Files:**
- Create: `evaluacion 1/src/test/kotlin/ejercicio3/EstadoPagoTest.kt`
- Create: `evaluacion 1/src/main/kotlin/ejercicio3/EstadoPago.kt`
- Create: `evaluacion 1/enunciados/ejercicio3.md`

**Interfaces:**
- Produce: `sealed class EstadoPago` con `object Pendiente`, `data class Procesando(val id: Int)`, `data class Aprobado(val id: Int, val monto: Int)`, `data class Rechazado(val id: Int, val motivo: String)`.
- Produce: `class ProcesadorPagos` con `suspend fun procesarPago(id: Int, monto: Int): EstadoPago` que incluye `delay(1500)`.

**Step-by-step:**

- [ ] **Step 4.1:** Crear el test:

```kotlin
package ejercicio3

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Ejercicio 3 - Pagos hospitalarios: sealed class y corrutinas")
class EstadoPagoTest {

    @Test
    @DisplayName("procesarPago con monto > 0 retorna Aprobado con id y monto")
    fun procesarPagoMontoPositivoAprueba() = runTest {
        val p = ProcesadorPagos()
        val estado = p.procesarPago(id = 1, monto = 1500)
        assertTrue(estado is EstadoPago.Aprobado)
        estado as EstadoPago.Aprobado
        assertEquals(1, estado.id)
        assertEquals(1500, estado.monto)
    }

    @Test
    @DisplayName("procesarPago con monto <= 0 retorna Rechazado con motivo")
    fun procesarPagoMontoInvalidoRechaza() = runTest {
        val p = ProcesadorPagos()
        val estado = p.procesarPago(id = 2, monto = 0)
        assertTrue(estado is EstadoPago.Rechazado)
        estado as EstadoPago.Rechazado
        assertEquals(2, estado.id)
        assertTrue(estado.motivo.isNotBlank())
    }

    @Test
    @DisplayName("procesarPago respeta cancelacion de la corrutina")
    fun procesarPagoRespetaCancelacion() = runTest {
        val p = ProcesadorPagos()
        val estado = p.procesarPago(id = 3, monto = 500)
        assertTrue(estado is EstadoPago.Aprobado || estado is EstadoPago.Rechazado)
    }

    @Test
    @DisplayName("sealed class cubre los 4 estados con when exhaustivo")
    fun sealedCubreLosCuatroEstados() {
        val estados: List<EstadoPago> = listOf(
            EstadoPago.Pendiente,
            EstadoPago.Procesando(id = 1),
            EstadoPago.Aprobado(id = 2, monto = 100),
            EstadoPago.Rechazado(id = 3, motivo = "x")
        )
        assertEquals(4, estados.size)
    }

    @Test
    @DisplayName("Aprobado y Rechazado exponen datos especificos")
    fun aprobadoYRechazadoExponenDatos() {
        val a = EstadoPago.Aprobado(id = 10, monto = 999)
        val r = EstadoPago.Rechazado(id = 11, motivo = "sin fondos")
        assertEquals(999, a.monto)
        assertEquals("sin fondos", r.motivo)
    }
}
```

- [ ] **Step 4.2:** Correr el test para verificar que falla:

```bash
cd "evaluacion 1" && ./gradlew test --tests "ejercicio3.*" --no-daemon
```

Esperado: FAIL — `Unresolved reference: EstadoPago`.

- [ ] **Step 4.3:** Implementar `evaluacion 1/src/main/kotlin/ejercicio3/EstadoPago.kt`:

```kotlin
package ejercicio3

import kotlinx.coroutines.delay

sealed class EstadoPago {
    object Pendiente : EstadoPago()
    data class Procesando(val id: Int) : EstadoPago()
    data class Aprobado(val id: Int, val monto: Int) : EstadoPago()
    data class Rechazado(val id: Int, val motivo: String) : EstadoPago()
}

class ProcesadorPagos {
    suspend fun procesarPago(id: Int, monto: Int): EstadoPago {
        delay(1500)
        return if (monto > 0) EstadoPago.Aprobado(id, monto)
        else EstadoPago.Rechazado(id, "Monto invalido")
    }
}

fun main() = kotlinx.coroutines.runBlocking {
    val p = ProcesadorPagos()
    when (val r = p.procesarPago(id = 1, monto = 1500)) {
        EstadoPago.Pendiente       -> println("Pago pendiente")
        is EstadoPago.Procesando   -> println("Procesando pago #${r.id}")
        is EstadoPago.Aprobado     -> println("Aprobado #${r.id} por \$${r.monto}")
        is EstadoPago.Rechazado    -> println("Rechazado #${r.id}: ${r.motivo}")
    }
}
```

- [ ] **Step 4.4:** Correr el test para verificar que pasa:

```bash
cd "evaluacion 1" && ./gradlew test --tests "ejercicio3.*" --no-daemon
```

Esperado: PASS — 5 tests verdes (con `runTest` el `delay` se salta virtualmente).

- [ ] **Step 4.5:** Crear `evaluacion 1/enunciados/ejercicio3.md`:

````markdown
# Ejercicio 3 - Pagos hospitalarios (Corrutinas + `sealed class`)

## Contexto

El hospital procesa pagos de manera asíncrona: una orden de pago puede estar pendiente, procesándose, aprobada o rechazada. El módulo debe representar esos cuatro estados y simular el tiempo real que tomaría el procesamiento.

## Objetivos de aprendizaje (IL 1.3, IL 1.2)

- Modelar estados finitos con `sealed class` (aprovecha `when` exhaustivo).
- Declarar funciones `suspend` y usar `delay`.
- Ejecutar corrutinas desde `main` con `runBlocking`.
- Probar código asíncrono con `kotlinx.coroutines.test.runTest`.

## Requisitos

Crea en el paquete `ejercicio3`:

- `sealed class EstadoPago` con cuatro variantes:
  - `object Pendiente`
  - `data class Procesando(val id: Int)`
  - `data class Aprobado(val id: Int, val monto: Int)`
  - `data class Rechazado(val id: Int, val motivo: String)`
- `class ProcesadorPagos` con `suspend fun procesarPago(id: Int, monto: Int): EstadoPago` que internamente hace `delay(1500)`. Si `monto > 0` retorna `Aprobado`, si no `Rechazado` con motivo "Monto invalido".
- `fun main()` que use `runBlocking` para llamar a `procesarPago` y manejar el resultado con `when` imprimiendo un mensaje distinto por estado.

## Enfoque paso a paso

1. Declara la `sealed class` y sus variantes: `object` para el estado simple y `data class` para los que llevan datos.
2. Crea `ProcesadorPagos` con un método `suspend fun`. Usa `delay` para simular latencia.
3. En `main`, usa `runBlocking { ... }` y un `when` exhaustivo sobre el resultado.
4. En los tests usa `runTest` de `kotlinx-coroutines-test` para que el `delay` se evalúe al instante.

## Criterios de aceptación

- `procesarPago(1, 1500)` retorna `Aprobado(1, 1500)`.
- `procesarPago(1, 0)` retorna `Rechazado(1, "Monto invalido")`.
- Los tests pasan usando `runTest` (sin esperar 1.5s reales).
- El `when` del `main` cubre las 4 variantes sin `else`.
- Los 5 tests en `EstadoPagoTest` pasan en verde.
````

- [ ] **Step 4.6:** Commit:

```bash
git add "evaluacion 1/src" "evaluacion 1/enunciados/ejercicio3.md"
git commit -m "feat(evaluacion-1): ejercicio 3 - Pagos hospitalarios (corrutinas y sealed)"
```

---

## Task 5: Ejercicio 4 — Reservas de hotel (excepciones)

**Files:**
- Create: `evaluacion 1/src/test/kotlin/ejercicio4/HabitacionTest.kt`
- Create: `evaluacion 1/src/main/kotlin/ejercicio4/Habitacion.kt`
- Create: `evaluacion 1/enunciados/ejercicio4.md`

**Interfaces:**
- Produce: `class FechaInvalidaException(msg: String) : Exception(msg)`.
- Produce: `class HabitacionNoDisponibleException(msg: String) : Exception(msg)`.
- Produce: `data class Habitacion(val numero: Int, val tipo: String, val precioNoche: Int)`.
- Produce: `data class Reserva(val huesped: String, val habitacion: Habitacion, val noches: Int)`.
- Produce: `class GestorReservas` con `registrarHabitacion(h: Habitacion)`, `reservar(...)`, `reservasPorHuesped(nombre: String)`.

**Step-by-step:**

- [ ] **Step 5.1:** Crear el test:

```kotlin
package ejercicio4

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Ejercicio 4 - Reservas de hotel: excepciones personalizadas")
class HabitacionTest {

    @Test
    @DisplayName("reservar con noches > 0 y habitacion existente crea la reserva")
    fun reservarConNochesPositivasYHabitacionExistente() {
        val g = GestorReservas()
        g.registrarHabitacion(Habitacion(101, "Simple", 30000))
        val r = g.reservar("Ana", 101, 2)
        assertEquals("Ana", r.huesped)
        assertEquals(101, r.habitacion.numero)
        assertEquals(2, r.noches)
    }

    @Test
    @DisplayName("reservar con noches <= 0 lanza FechaInvalidaException")
    fun reservarConNochesInvalidasLanzaExcepcion() {
        val g = GestorReservas()
        g.registrarHabitacion(Habitacion(101, "Simple", 30000))
        assertThrows(FechaInvalidaException::class.java) {
            g.reservar("Ana", 101, 0)
        }
        assertThrows(FechaInvalidaException::class.java) {
            g.reservar("Ana", 101, -3)
        }
    }

    @Test
    @DisplayName("reservar en habitacion inexistente lanza HabitacionNoDisponibleException")
    fun reservarEnHabitacionInexistenteLanzaExcepcion() {
        val g = GestorReservas()
        g.registrarHabitacion(Habitacion(101, "Simple", 30000))
        assertThrows(HabitacionNoDisponibleException::class.java) {
            g.reservar("Ana", 999, 2)
        }
    }

    @Test
    @DisplayName("mensajes de las excepciones no son vacios")
    fun mensajesDeExcepcionesNoSonVacios() {
        val e1 = FechaInvalidaException("noches debe ser > 0")
        val e2 = HabitacionNoDisponibleException("no existe")
        assertTrue(e1.message!!.isNotBlank())
        assertTrue(e2.message!!.isNotBlank())
    }

    @Test
    @DisplayName("reservasPorHuesped filtra por nombre")
    fun reservasPorHuespedFiltra() {
        val g = GestorReservas()
        g.registrarHabitacion(Habitacion(101, "Simple", 30000))
        g.registrarHabitacion(Habitacion(102, "Doble",  50000))
        g.reservar("Ana", 101, 2)
        g.reservar("Ana", 102, 1)
        g.reservar("Pedro", 101, 3)
        assertEquals(2, g.reservasPorHuesped("Ana").size)
        assertEquals(1, g.reservasPorHuesped("Pedro").size)
        assertEquals(0, g.reservasPorHuesped("Luisa").size)
    }
}
```

- [ ] **Step 5.2:** Correr el test para verificar que falla:

```bash
cd "evaluacion 1" && ./gradlew test --tests "ejercicio4.*" --no-daemon
```

Esperado: FAIL — `Unresolved reference: FechaInvalidaException`.

- [ ] **Step 5.3:** Implementar `evaluacion 1/src/main/kotlin/ejercicio4/Habitacion.kt`:

```kotlin
package ejercicio4

class FechaInvalidaException(msg: String) : Exception(msg)

class HabitacionNoDisponibleException(msg: String) : Exception(msg)

data class Habitacion(
    val numero: Int,
    val tipo: String,
    val precioNoche: Int
)

data class Reserva(
    val huesped: String,
    val habitacion: Habitacion,
    val noches: Int
)

class GestorReservas {
    private val habitaciones = mutableListOf<Habitacion>()
    private val reservas = mutableListOf<Reserva>()

    fun registrarHabitacion(h: Habitacion) {
        habitaciones += h
    }

    fun reservar(huesped: String, numero: Int, noches: Int): Reserva {
        if (noches <= 0) {
            throw FechaInvalidaException("Noches debe ser mayor a 0 (recibido: $noches)")
        }
        val habitacion = habitaciones.firstOrNull { it.numero == numero }
            ?: throw HabitacionNoDisponibleException("Habitacion $numero no existe o no esta disponible")
        return Reserva(huesped, habitacion, noches).also { reservas += it }
    }

    fun reservasPorHuesped(nombre: String): List<Reserva> =
        reservas.filter { it.huesped == nombre }
}
```

- [ ] **Step 5.4:** Correr el test para verificar que pasa:

```bash
cd "evaluacion 1" && ./gradlew test --tests "ejercicio4.*" --no-daemon
```

Esperado: PASS — 5 tests verdes.

- [ ] **Step 5.5:** Crear `evaluacion 1/enunciados/ejercicio4.md`:

```markdown
# Ejercicio 4 - Reservas de hotel (Excepciones)

## Contexto

El sistema de reservas de un hotel debe validar cada intento de reserva. Una reserva inválida (noches ≤ 0 o habitación inexistente) debe lanzar una excepción específica para que el llamador sepa qué corregir.

## Objetivos de aprendizaje (IL 1.3)

- Crear excepciones personalizadas extendiendo `Exception`.
- Lanzar (`throw`) y atrapar (`assertThrows`) excepciones.
- Validar entradas con condicionales antes de proceder.

## Requisitos

Crea en el paquete `ejercicio4`:

- `class FechaInvalidaException(msg: String) : Exception(msg)`.
- `class HabitacionNoDisponibleException(msg: String) : Exception(msg)`.
- `data class Habitacion(val numero: Int, val tipo: String, val precioNoche: Int)`.
- `data class Reserva(val huesped: String, val habitacion: Habitacion, val noches: Int)`.
- `class GestorReservas` con métodos:
  - `fun registrarHabitacion(h: Habitacion)`.
  - `fun reservar(huesped: String, numero: Int, noches: Int): Reserva` — lanza `FechaInvalidaException` si `noches <= 0` y `HabitacionNoDisponibleException` si la habitación no existe.
  - `fun reservasPorHuesped(nombre: String): List<Reserva>`.

## Enfoque paso a paso

1. Declara las dos excepciones como clases que extienden `Exception` con un mensaje.
2. Modela habitación y reserva con `data class`.
3. Implementa `GestorReservas` con `mutableListOf` internas.
4. En `reservar`, valida primero las noches, luego busca la habitación. Lanza la excepción adecuada en cada caso.

## Criterios de aceptación

- `reservar(..., noches = 0)` lanza `FechaInvalidaException`.
- `reservar(..., numero = 999)` (sin registrar) lanza `HabitacionNoDisponibleException`.
- `reservasPorHuesped` filtra correctamente y retorna lista vacía si no hay.
- Los 5 tests en `HabitacionTest` pasan en verde.
```

- [ ] **Step 5.6:** Commit:

```bash
git add "evaluacion 1/src" "evaluacion 1/enunciados/ejercicio4.md"
git commit -m "feat(evaluacion-1): ejercicio 4 - Reservas de hotel (excepciones)"
```

---

## Task 6: Ejercicio 5 — Cuentas bancarias (lambdas/HOF)

**Files:**
- Create: `evaluacion 1/src/test/kotlin/ejercicio5/CuentaTest.kt`
- Create: `evaluacion 1/src/main/kotlin/ejercicio5/Cuenta.kt`
- Create: `evaluacion 1/enunciados/ejercicio5.md`

**Interfaces:**
- Produce: `enum class TipoCuenta { CORRIENTE, AHORRO, CREDITO }`.
- Produce: `data class Cuenta(val titular: String, val saldo: Int, val tipo: TipoCuenta)`.
- Produce: `class Banco(val cuentas: List<Cuenta>)` con métodos `aplicarOperacion(op: (Cuenta) -> Cuenta)`, `saldoTotal()`, `cuentasPorTipo()`, `saldosAcumuladosPorTipo()`.

**Step-by-step:**

- [ ] **Step 6.1:** Crear el test:

```kotlin
package ejercicio5

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Ejercicio 5 - Cuentas bancarias: lambdas y funciones de orden superior")
class CuentaTest {

    private val muestra = listOf(
        Cuenta("Ana",   100_000, TipoCuenta.CORRIENTE),
        Cuenta("Pedro",  50_000, TipoCuenta.AHORRO),
        Cuenta("Luisa", 200_000, TipoCuenta.CORRIENTE),
        Cuenta("Sofia",  30_000, TipoCuenta.CREDITO)
    )

    @Test
    @DisplayName("saldoTotal suma el saldo de todas las cuentas")
    fun saldoTotalSumaTodas() {
        val b = Banco(muestra)
        assertEquals(100_000 + 50_000 + 200_000 + 30_000, b.saldoTotal())
    }

    @Test
    @DisplayName("cuentasPorTipo agrupa por TipoCuenta")
    fun cuentasPorTipoAgrupa() {
        val b = Banco(muestra)
        val grupos = b.cuentasPorTipo()
        assertEquals(2, grupos[TipoCuenta.CORRIENTE]?.size)
        assertEquals(1, grupos[TipoCuenta.AHORRO]?.size)
        assertEquals(1, grupos[TipoCuenta.CREDITO]?.size)
    }

    @Test
    @DisplayName("saldosAcumuladosPorTipo suma por tipo")
    fun saldosAcumuladosPorTipoSuma() {
        val b = Banco(muestra)
        val mapa = b.saldosAcumuladosPorTipo()
        assertEquals(300_000, mapa[TipoCuenta.CORRIENTE])
        assertEquals(50_000,  mapa[TipoCuenta.AHORRO])
        assertEquals(30_000,  mapa[TipoCuenta.CREDITO])
    }

    @Test
    @DisplayName("aplicarOperacion recibe una lambda y la aplica a cada cuenta")
    fun aplicarOperacionAplicaLambda() {
        val b = Banco(muestra)
        val duplicadas = b.aplicarOperacion { c -> c.copy(saldo = c.saldo * 2) }
        assertEquals(200_000, duplicadas.first { it.titular == "Ana" }.saldo)
        assertEquals(100_000, duplicadas.first { it.titular == "Pedro" }.saldo)
    }

    @Test
    @DisplayName("aplicarOperacion con lambda de identidad devuelve cuentas equivalentes")
    fun aplicarOperacionIdentidad() {
        val b = Banco(muestra)
        val mismo = b.aplicarOperacion { it }
        assertEquals(muestra, mismo)
    }

    @Test
    @DisplayName("Banco con cuentas vacias tiene saldoTotal 0")
    fun bancoVacio() {
        val b = Banco(emptyList())
        assertEquals(0, b.saldoTotal())
        assertTrue(b.cuentasPorTipo().isEmpty())
        assertTrue(b.saldosAcumuladosPorTipo().isEmpty())
    }
}
```

- [ ] **Step 6.2:** Correr el test para verificar que falla:

```bash
cd "evaluacion 1" && ./gradlew test --tests "ejercicio5.*" --no-daemon
```

Esperado: FAIL — `Unresolved reference: Cuenta`.

- [ ] **Step 6.3:** Implementar `evaluacion 1/src/main/kotlin/ejercicio5/Cuenta.kt`:

```kotlin
package ejercicio5

enum class TipoCuenta { CORRIENTE, AHORRO, CREDITO }

data class Cuenta(
    val titular: String,
    val saldo: Int,
    val tipo: TipoCuenta
)

class Banco(val cuentas: List<Cuenta>) {

    fun aplicarOperacion(op: (Cuenta) -> Cuenta): List<Cuenta> =
        cuentas.map(op)

    fun saldoTotal(): Int =
        cuentas.sumOf { it.saldo }

    fun cuentasPorTipo(): Map<TipoCuenta, List<Cuenta>> =
        cuentas.groupBy { it.tipo }

    fun saldosAcumuladosPorTipo(): Map<TipoCuenta, Int> =
        cuentas.groupBy { it.tipo }.mapValues { (_, lista) -> lista.sumOf { it.saldo } }
}
```

- [ ] **Step 6.4:** Correr el test para verificar que pasa:

```bash
cd "evaluacion 1" && ./gradlew test --tests "ejercicio5.*" --no-daemon
```

Esperado: PASS — 6 tests verdes.

- [ ] **Step 6.5:** Crear `evaluacion 1/enunciados/ejercicio5.md`:

````markdown
# Ejercicio 5 - Cuentas bancarias (Lambdas y funciones de orden superior)

## Contexto

El banco necesita una pieza reutilizable que aplique operaciones (cálculo de comisiones, ajustes de saldo, redondeos, etc.) sobre todas sus cuentas. En vez de escribir un método por cada transformación, exponemos una función de orden superior que recibe una lambda.

## Objetivos de aprendizaje (IL 1.2, IL 1.3)

- Modelar datos con `data class` y `enum`.
- Definir funciones de orden superior (HOF) que reciben lambdas.
- Combinar HOF con operaciones de colección (`map`, `groupBy`, `sumOf`).

## Requisitos

Crea en el paquete `ejercicio5`:

- `enum class TipoCuenta { CORRIENTE, AHORRO, CREDITO }`.
- `data class Cuenta(val titular: String, val saldo: Int, val tipo: TipoCuenta)`.
- `class Banco(val cuentas: List<Cuenta>)` con:
  - `fun aplicarOperacion(op: (Cuenta) -> Cuenta): List<Cuenta>` — devuelve `cuentas.map(op)`.
  - `fun saldoTotal(): Int`.
  - `fun cuentasPorTipo(): Map<TipoCuenta, List<Cuenta>>`.
  - `fun saldosAcumuladosPorTipo(): Map<TipoCuenta, Int>`.

## Enfoque paso a paso

1. Modela el tipo con `enum class` para tener un conjunto cerrado de valores.
2. Modela la cuenta con `data class` (te da `copy` para transformaciones inmutables).
3. Implementa `aplicarOperacion` aceptando una lambda `(Cuenta) -> Cuenta`.
4. Las demás operaciones se construyen con `map`, `groupBy` y `sumOf` de la stdlib.

## Criterios de aceptación

- `aplicarOperacion` aplica la lambda a cada cuenta.
- `saldoTotal` devuelve la suma simple.
- `saldosAcumuladosPorTipo` agrupa y suma por cada `TipoCuenta`.
- Banco vacío funciona sin excepciones.
- Los 6 tests en `CuentaTest` pasan en verde.
````

- [ ] **Step 6.6:** Commit:

```bash
git add "evaluacion 1/src" "evaluacion 1/enunciados/ejercicio5.md"
git commit -m "feat(evaluacion-1): ejercicio 5 - Cuentas bancarias (lambdas y HOF)"
```

---

## Task 7: Verificación final, limpieza y push

**Files:**
- Modify (si existe): `evaluacion 1/README.md` — completar el índice si quedó pendiente.
- Delete: `evaluacion 1/enunciados/.gitkeep` (si quedó).
- Modify (si hace falta): `.gitignore` (raíz).

**Step-by-step:**

- [ ] **Step 7.1:** Correr la suite completa:

```bash
cd "evaluacion 1" && ./gradlew clean test --no-daemon
```

Esperado: BUILD SUCCESSFUL. Todos los tests (5+5+5+5+6 = 26) en verde.

- [ ] **Step 7.2:** Correr `build` para asegurar que compila sin warnings de Kotlin:

```bash
cd "evaluacion 1" && ./gradlew build --no-daemon
```

Esperado: BUILD SUCCESSFUL. Si hay warnings de deprecación, evaluarlos caso a caso.

- [ ] **Step 7.3:** Verificar que la carpeta contiene la estructura esperada:

```bash
ls -la "evaluacion 1" && ls "evaluacion 1/src/main/kotlin/ejercicio"*/ && ls "evaluacion 1/src/test/kotlin/ejercicio"*/ && ls "evaluacion 1/enunciados/"
```

Esperado: 5 archivos `Solucion.kt` (uno por ejercicio), 5 archivos `*Test.kt`, 5 archivos `ejercicioN.md`.

- [ ] **Step 7.4:** Eliminar el `.gitkeep` si quedó en `enunciados/`:

```bash
rm -f "evaluacion 1/enunciados/.gitkeep"
```

- [ ] **Step 7.5:** Revisar `git status` y `git diff --stat` antes del push:

```bash
git status
git diff --stat
```

Esperado: solo cambios dentro de `evaluacion 1/` y el `.gitignore` raíz.

- [ ] **Step 7.6:** Si el README necesita un toque final, complétalo con la fecha y el resumen, y haz commit adicional:

```bash
git add "evaluacion 1/README.md"
git commit -m "docs(evaluacion-1): completar README con resumen final"
```

(solo si hubo cambios en este paso).

- [ ] **Step 7.7:** Push a `main`:

```bash
git push origin main
```

Esperado: el remoto queda con la carpeta `evaluacion 1/` visible y los 6+ commits.

- [ ] **Step 7.8:** Verificar el push:

```bash
git log --oneline -10
git ls-remote origin main | head -1
```

Esperado: el último commit aparece localmente y el remoto responde.

---

## Resumen de entregables

| Artefacto | Ruta |
|-----------|------|
| Proyecto Gradle | `evaluacion 1/build.gradle.kts` |
| Wrapper | `evaluacion 1/gradlew`, `gradle/wrapper/*` |
| Tests | `evaluacion 1/src/test/kotlin/ejercicio{1..5}/*Test.kt` (5 archivos) |
| Soluciones | `evaluacion 1/src/main/kotlin/ejercicio{1..5}/*.kt` (5 archivos) |
| Enunciados | `evaluacion 1/enunciados/ejercicio{1..5}.md` (5 archivos) |
| Documentación | `evaluacion 1/README.md` |
| Gitignore local | `evaluacion 1/.gitignore` |
| Exclusiones raíz | `.gitignore` (raíz, 2 líneas añadidas) |
| Branch remoto | `origin/main` con push de todo |

---

## Enmienda al plan (2026-09-07): 8 ejercicios en lugar de 5

A solicitud del usuario se agregan **3 ejercicios adicionales** para cubrir IL 1.1 (tipos, aritmética, condicionales) explícitamente, ciclos e iteraciones, y constructores secundarios. Las Tasks 1–7 originales siguen vigentes; se renumeran:

- Task 7 (nueva) → Ejercicio 6 — Nóminas (tipos, aritmética, condicionales)
- Task 8 (nueva) → Ejercicio 7 — Fibonacci (ciclos e iteraciones)
- Task 9 (nueva) → Ejercicio 8 — Figuras (constructores secundarios, propiedades computadas, `sealed class`)
- Task 10 (era Task 7) → Verificación final + push a `main` (cubre los 8 ejercicios)

Cada nueva Task sigue el mismo flujo TDD del original: test que falla → solución → enunciado en español → commit. Bosquejo de las soluciones y enunciados ya está en `docs/superpowers/specs/2026-09-07-evaluacion-1-kotlin-design.md`.

Dominio → código:

- **Ejercicio 6** (`Empleado.kt`): `enum class TipoEmpleado`, `class Empleado(nombre, horasTrabajadas, tipo)` con `tarifaPorHora` derivado de un `when`, `bono` derivado de un `if`, y métodos `calcularSueldoBruto/Descuento/Neto` con `when`/`if`.
- **Ejercicio 7** (`Fibonacci.kt`): `class GeneradorFibonacci` con `primerosN(n)`, `sumaHasta(n)`, `menoresQue(limite)` usando `while`, `MutableList`, `break`.
- **Ejercicio 8** (`Figura.kt`): `sealed class Figura` con `Circulo`, `Rectangulo` (constructor secundario + `esCuadrado` get-propiedad), `TrianguloEquilatero` (con `init { require }`).

## Riesgos mitigados

- **Espacio en el nombre:** se usan comillas en todos los comandos `cd`, `cp`, `rm`, etc.
- **JDK 17 toolchain:** `gradle.properties` apunta a la instalación portable ya presente en el sistema.
- **TDD por ejercicio:** cada task arranca con un test que falla, evitando "tests que pasan por accidente".
- **Gradle daemon:** se desactiva con `--no-daemon` para evitar bloqueos en runs cortos.