# Evaluación 1 — Kotlin

Proyecto autocontenido con 8 ejercicios en Kotlin (JVM puro, IntelliJ IDEA). Cada ejercicio cubre un indicador de logro de la Evaluación Formativa N°1 del ramo DSY1105.

## Requisitos

- **JDK 17** — toolchain portable en `/home/sebastian/.local/jdk/jdk-17.0.20.1+1` (ya configurado en `gradle.properties`).
- **IntelliJ IDEA** Community o Ultimate. **No** Android Studio.
- **Gradle wrapper** ya viene incluido — no necesitas instalar Gradle.

## Cómo correr los tests

### 1. Desde la terminal (CLI)

```bash
cd "evaluacion 1"
JAVA_HOME=/home/sebastian/.local/jdk/jdk-17.0.20.1+1 ./gradlew test
```

Resultado esperado:
```
BUILD SUCCESSFUL in 27s
5 actionable tasks: 5 executed
51 tests completed, 0 failed
```

> Si tu `JAVA_HOME` ya apunta a un JDK 17 puedes omitir el prefijo.

### 2. Correr solo un ejercicio

Reemplaza `ejercicioN` por el número (1 a 8):

```bash
cd "evaluacion 1"
JAVA_HOME=/home/sebastian/.local/jdk/jdk-17.0.20.1+1 ./gradlew test --tests "ejercicio3.*"
```

### 3. Correr un test específico

```bash
cd "evaluacion 1"
JAVA_HOME=/home/sebastian/.local/jdk/jdk-17.0.20.1+1 ./gradlew test --tests "ejercicio3.EstadoPagoTest.procesarPagoMontoPositivoAprueba"
```

### 4. Desde IntelliJ IDEA

1. **File → Open…** y selecciona la carpeta `evaluacion 1` (no un `.kts` adentro).
2. IDEA detecta el `build.gradle.kts` y sincroniza Gradle automáticamente. Si te pregunta por el JDK 17, acepta el del toolchain.
3. Para correr todos los tests: panel lateral Gradle → **Tasks → verification → test** (doble clic).
4. Para correr un test puntual: abre cualquier `*Test.kt` y haz clic en el ícono ▶︎ verde junto a cada `@Test` (o clic derecho sobre la clase → **Run**).

### 5. Build completo (compilar + tests + jar)

```bash
cd "evaluacion 1"
JAVA_HOME=/home/sebastian/.local/jdk/jdk-17.0.20.1+1 ./gradlew build
```

## Índice de ejercicios

| #  | Dominio                  | Concepto                                          | IL      | Tests |
|----|--------------------------|---------------------------------------------------|---------|-------|
| 1  | Biblioteca               | POO: `open class`, herencia y polimorfismo        | 1.3     | 6     |
| 2  | Tienda online            | Colecciones (`filter`, `map`, `groupBy`, `sumOf`) | 1.2     | 5     |
| 3  | Pagos hospitalarios      | Corrutinas + `sealed class` (`runBlocking`)       | 1.2/1.3 | 5     |
| 4  | Reservas de hotel        | Excepciones personalizadas                        | 1.3     | 5     |
| 5  | Cuentas bancarias        | Lambdas y funciones de orden superior             | 1.2     | 6     |
| 6  | Calculadora de nóminas   | Tipos, aritmética, condicionales (`when`, `if`)   | 1.1     | 9     |
| 7  | Secuencia de Fibonacci   | Ciclos e iteraciones (`while`, `break`)           | 1.2     | 8     |
| 8  | Figuras geométricas      | Constructores secundarios + propiedades computadas| 1.3     | 7     |

Cada ejercicio tiene:
- **Enunciado** en `enunciados/ejercicioN.md` (contexto, objetivos, requisitos, enfoque, criterios).
- **Solución** en `src/main/kotlin/ejercicioN/*.kt`.
- **Suite de tests** en `src/test/kotlin/ejercicioN/*Test.kt`.

**Total: 8 ejercicios, 51 tests que pasan en verde.**

## Solución de problemas

### `ERROR: JAVA_HOME is not set`

`gradlew` necesita una JVM para arrancar aunque use toolchain después. Soluciones:

```bash
# Opción A: prefijar el comando (recomendado)
JAVA_HOME=/home/sebastian/.local/jdk/jdk-17.0.20.1+1 ./gradlew test

# Opción B: exportar la variable en tu sesión
export JAVA_HOME=/home/sebastian/.local/jdk/jdk-17.0.20.1+1
./gradlew test
```

Si tu JDK 17 vive en otra ruta, ajusta `org.gradle.java.installations.paths` en `gradle.properties`.

### `Daemon will be stopped at the end of the build`

Aparece cuando pasas `--no-daemon` (recomendado para runs cortos). Es informativo, no un error.

### Tests no aparecen o quedan en cache

Fuerza una nueva pasada:

```bash
./gradlew test --rerun-tasks
```

### Quiero limpiar antes de probar

```bash
./gradlew clean test
```

## Estructura

```
evaluacion 1/
├── build.gradle.kts          # Kotlin JVM 1.9.20 + JUnit 5 + coroutines-test
├── settings.gradle.kts       # rootProject.name = "evaluacion-1"
├── gradle.properties         # toolchain JDK 17 portable
├── gradlew, gradlew.bat      # Gradle wrapper (no requiere instalar Gradle)
├── gradle/wrapper/           # Gradle 8.4
├── .gitignore                # Ignora build/, .gradle/, .idea/, etc.
├── enunciados/               # Enunciados en español (1 archivo por ejercicio)
└── src/
    ├── main/kotlin/          # Soluciones
    │   └── ejercicio{1..8}/
    └── test/kotlin/          # Suites de tests
        └── ejercicio{1..8}/
```