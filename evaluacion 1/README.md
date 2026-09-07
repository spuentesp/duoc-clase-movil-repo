# Evaluación 1 — Kotlin

Proyecto autocontenido con 8 ejercicios en Kotlin (JVM puro, IntelliJ IDEA). Cada ejercicio cubre un indicador de logro de la Evaluación Formativa N°1 del ramo DSY1105.

## Requisitos

- JDK 17 (toolchain portable en `/home/sebastian/.local/jdk/jdk-17.0.20.1+1`).
- IntelliJ IDEA (Community o Ultimate). **No** Android Studio.

## Cómo ejecutar los tests

```bash
cd "evaluacion 1"
JAVA_HOME=/home/sebastian/.local/jdk/jdk-17.0.20.1+1 ./gradlew test
```

> Si tu `JAVA_HOME` ya apunta a un JDK 17, el prefijo es opcional.

## Índice de ejercicios

| #  | Dominio                  | Concepto                                          | IL      |
|----|--------------------------|---------------------------------------------------|---------|
| 1  | Biblioteca               | POO: `open class`, herencia y polimorfismo        | 1.3     |
| 2  | Tienda online            | Colecciones (`filter`, `map`, `groupBy`, `sumOf`) | 1.2     |
| 3  | Pagos hospitalarios      | Corrutinas + `sealed class` (`runBlocking`)       | 1.2/1.3 |
| 4  | Reservas de hotel        | Excepciones personalizadas                        | 1.3     |
| 5  | Cuentas bancarias        | Lambdas y funciones de orden superior             | 1.2     |
| 6  | Calculadora de nóminas   | Tipos, aritmética, condicionales (`when`, `if`)   | 1.1     |
| 7  | Secuencia de Fibonacci   | Ciclos e iteraciones (`while`, `break`)           | 1.2     |
| 8  | Figuras geométricas      | Constructores secundarios + propiedades computadas | 1.3     |

Cada ejercicio tiene:
- **Enunciado** en `enunciados/ejercicioN.md` (contexto, objetivos, requisitos, enfoque, criterios).
- **Solución** en `src/main/kotlin/ejercicioN/*.kt`.
- **Suite de tests** en `src/test/kotlin/ejercicioN/*Test.kt`.

Total: 8 ejercicios, 8 soluciones, 8 suites con **51 tests** que pasan en verde con `./gradlew test`.

## Cómo importar en IntelliJ IDEA

1. **File → Open…** y selecciona la carpeta `evaluacion 1` (no un `.kts` adentro).
2. IDEA detecta el `build.gradle.kts` y sincroniza Gradle automáticamente.
3. Una vez sincronizado, abre cualquier `*Test.kt` y usa el ícono ▶︎ junto a cada `@Test`.