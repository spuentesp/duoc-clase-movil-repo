# Diseño — Evaluación 1 (Kotlin, IntelliJ)

**Fecha:** 2026-09-07
**Origen:** a partir de la rúbrica de "Evaluación Formativa N°1 — DSY1105" (`docs/Evaluación Formativa 1_Estudiante_SAY1105 (1).docx`).

## Propósito

Construir una carpeta-proyecto **`evaluacion 1/`** en la raíz del repo, autocontenida, ejecutable en IntelliJ IDEA (no Android Studio), escrita **solo en Kotlin**, con **5 ejercicios** que cubren los mismos indicadores de logro que la evaluación formativa:

- **IL 1.1** Tipos de datos, operadores aritméticos y condicionales.
- **IL 1.2** Funciones, colecciones, ciclos e iteraciones.
- **IL 1.3** POO (clases, herencia, polimorfismo), manejo de excepciones, verificación con pruebas.

Cada ejercicio viene con:

1. **`enunciados/ejercicioN.md`** — enunciado, objetivos, enfoque paso a paso, criterios de aceptación (todo en español).
2. **`src/main/kotlin/ejercicioN/Solucion.kt`** — solución completa que pasa los tests.
3. **`src/test/kotlin/ejercicioN/SolucionTest.kt`** — 5–10 casos de prueba que verifican la solución.

## Estructura de carpetas

```
evaluacion 1/
├── .gitignore
├── README.md
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradlew, gradlew.bat
├── gradle/wrapper/...            # Gradle wrapper generado
├── enunciados/
│   ├── ejercicio1.md
│   ├── ejercicio2.md
│   ├── ejercicio3.md
│   ├── ejercicio4.md
│   └── ejercicio5.md
└── src/
    ├── main/kotlin/
    │   ├── ejercicio1/Solucion.kt
    │   ├── ejercicio2/Solucion.kt
    │   ├── ejercicio3/Solucion.kt
    │   ├── ejercicio4/Solucion.kt
    │   └── ejercicio5/Solucion.kt
    └── test/kotlin/
        ├── ejercicio1/SolucionTest.kt
        ├── ejercicio2/SolucionTest.kt
        ├── ejercicio3/SolucionTest.kt
        ├── ejercicio4/SolucionTest.kt
        └── ejercicio5/SolucionTest.kt
```

## Stack y dependencias

- **Lenguaje:** Kotlin 1.9.20
- **Toolchain JVM:** Java 17
- **Build:** Gradle (wrapper generado para coincidir con el resto del repo)
- **Tests:** JUnit 5 (`junit-bom 5.10.1`) + `kotlinx-coroutines-test 1.7.3`
- **Sin dependencias de Android** — proyecto JVM puro.

`build.gradle.kts` replica exactamente la configuración de `exercises/build.gradle.kts`:

```kotlin
plugins { kotlin("jvm") version "1.9.20" }
repositories { mavenCentral() }
dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    testImplementation(platform("org.junit:junit-bom:5.10.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
}
kotlin { jvmToolchain(17) }
tasks.test {
    useJUnitPlatform()
    testLogging { events("passed", "failed", "skipped") }
}
```

## Los 5 ejercicios

| # | Dominio | Concepto principal | Concepto adicional |
|---|---------|-------------------|--------------------|
| 1 | **Biblioteca** | POO: `open class Publicacion` + `Libro` / `Revista`, polimorfismo en `mostrarDetalle()` | Constructores secundarios, propiedades inmutables |
| 2 | **Tienda online** | Colecciones: `List<Producto>`, `filter`, `map`, `groupBy`, `sumOf` | `data class`, `sortedBy` |
| 3 | **Pagos hospitalarios** | Corrutinas: `sealed class EstadoPago`, `suspend fun procesarPago(id)` con `delay` y `runBlocking` | `runTest` y control de tiempo virtual en tests |
| 4 | **Reservas de hotel** | POO + excepciones personalizadas (`HabitacionNoDisponibleException`, `FechaInvalidaException`) | `try/catch`, validaciones |
| 5 | **Cuentas bancarias** | Lambdas/HOF + colecciones: `aplicarOperacion(cuentas, op: (Cuenta) -> Cuenta)` | `fold`, `sortedByDescending` |

### Bosquejo de las soluciones

**Ejercicio 1 — Biblioteca**

```kotlin
open class Publicacion(val id: Int, val titulo: String, val anio: Int) {
    open fun mostrarDetalle(): String = "[$id] $titulo ($anio)"
}
class Libro(id: Int, titulo: String, anio: Int, val autor: String, val paginas: Int)
    : Publicacion(id, titulo, anio) {
    override fun mostrarDetalle() = "${super.mostrarDetalle()} — $autor, $paginas págs."
}
class Revista(id: Int, titulo: String, anio: Int, val editor: String, val numero: Int)
    : Publicacion(id, titulo, anio) {
    override fun mostrarDetalle() = "${super.mostrarDetalle()} — Revista $editor #$numero"
}
```

**Ejercicio 2 — Tienda online**

```kotlin
data class Producto(val id: Int, val nombre: String, val precio: Int, val categoria: String, val stock: Int)

class Inventario(val productos: List<Producto>) {
    fun valorTotal(): Int = productos.sumOf { it.precio * it.stock }
    fun conStockBajo(umbral: Int = 5): List<Producto> = productos.filter { it.stock < umbral }
    fun agruparPorCategoria(): Map<String, List<Producto>> = productos.groupBy { it.categoria }
    fun topMasCaros(n: Int): List<Producto> = productos.sortedByDescending { it.precio }.take(n)
}
```

**Ejercicio 3 — Pagos hospitalarios**

```kotlin
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
        else EstadoPago.Rechazado(id, "Monto inválido")
    }
}

fun main() = runBlocking {
    val p = ProcesadorPagos()
    when (val r = p.procesarPago(1, 1500)) {
        EstadoPago.Pendiente    -> println("Pendiente")
        is EstadoPago.Procesando -> println("Procesando ${r.id}")
        is EstadoPago.Aprobado   -> println("Aprobado #${r.id} por ${r.monto}")
        is EstadoPago.Rechazado  -> println("Rechazado: ${r.motivo}")
    }
}
```

**Ejercicio 4 — Reservas de hotel**

```kotlin
class FechaInvalidaException(msg: String) : Exception(msg)
class HabitacionNoDisponibleException(msg: String) : Exception(msg)

data class Habitacion(val numero: Int, val tipo: String, val precioNoche: Int)
data class Reserva(val huesped: String, val habitacion: Habitacion, val noches: Int)

class GestorReservas {
    private val habitaciones = mutableListOf<Habitacion>()
    private val reservas = mutableListOf<Reserva>()
    fun registrarHabitacion(h: Habitacion) { habitaciones += h }
    fun reservar(huesped: String, numero: Int, noches: Int): Reserva {
        require(noches > 0) { throw FechaInvalidaException("Noches debe ser > 0") }
        val h = habitaciones.firstOrNull { it.numero == numero }
            ?: throw HabitacionNoDisponibleException("Habitación $numero no existe")
        return Reserva(huesped, h, noches).also { reservas += it }
    }
    fun reservasPorHuesped(nombre: String) = reservas.filter { it.huesped == nombre }
}
```

**Ejercicio 5 — Cuentas bancarias**

```kotlin
enum class TipoCuenta { CORRIENTE, AHORRO, CREDITO }

data class Cuenta(val titular: String, val saldo: Int, val tipo: TipoCuenta)

class Banco(val cuentas: List<Cuenta>) {
    fun aplicarOperacion(op: (Cuenta) -> Cuenta): List<Cuenta> = cuentas.map(op)
    fun saldoTotal(): Int = cuentas.sumOf { it.saldo }
    fun cuentasPorTipo(): Map<TipoCuenta, List<Cuenta>> = cuentas.groupBy { it.tipo }
    fun saldosAcumuladosPorTipo(): Map<TipoCuenta, Int> =
        cuentas.groupBy { it.tipo }.mapValues { (_, lista) -> lista.sumOf { it.saldo } }
}
```

## Enunciados (Markdown por ejercicio)

Cada `enunciados/ejercicioN.md` debe incluir, en español:

1. **Contexto / caso de negocio** (1–2 párrafos).
2. **Objetivos de aprendizaje** (mapeados a IL 1.1 / 1.2 / 1.3).
3. **Requisitos** (firmas de clases/funciones, contratos).
4. **Enfoque paso a paso** (cómo pensarlo).
5. **Criterios de aceptación** (qué tiene que pasar para considerarlo resuelto).

## Estilo del código

- Nombres en español para clases/métodos (`mostrarDetalle`, `procesarPago`, `reservar`).
- Comentarios explicativos mínimos (uno por bloque importante, no por línea).
- Tests con `@DisplayName("…")` en español para que el reporte sea legible.
- Sin dependencias más allá de Kotlin stdlib + coroutines + JUnit.

## .gitignore local

`evaluacion 1/.gitignore`:

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

Y se añade al `.gitignore` raíz:

```
evaluacion 1/build/
evaluacion 1/.gradle/
```

(Esto evita ruido si el usuario llega a buildear desde la raíz antes de entrar a la carpeta.)

## Verificación de aceptación

1. `cd "evaluacion 1" && ./gradlew test` → todos los tests en verde, reporte muestra los 5 ejercicios.
2. `cd "evaluacion 1" && ./gradlew build` → compila sin warnings.
3. El proyecto abre limpio en IntelliJ IDEA (importar como Gradle existente).
4. Cada `enunciados/ejercicioN.md` existe y está en español.
5. Cada `SolucionTest.kt` tiene al menos 5 casos.
6. `git push origin main` deja la carpeta visible en el repo.

## Riesgos y notas

- **Espacio en el nombre de carpeta** — puede requerir comillas en shells. Gradle y Git lo aceptan sin problema. Si más adelante causa fricción, se puede renombrar a `evaluacion-1/`.
- **Gradle wrapper** — se genera localmente con `gradle wrapper` desde la raíz o copiando `gradlew*` y `gradle/wrapper/` desde `exercises/`.
- **JDK 17** requerido localmente (toolchain lo descargará si no está).