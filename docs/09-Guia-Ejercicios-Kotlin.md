# 09 — Guía de Ejercicios Kotlin

Los 33 ejercicios del módulo `exercises/` están diseñados para progresar
de Kotlin básico a Android. Esta guía explica cómo correrlos, cómo están
organizados y cómo aprovechar al máximo el aprendizaje.

## Cómo correr

Desde la carpeta `exercises/`:

```bash
cd exercises
./gradlew test                            # todos los tests (muchos fallarán — esperado)
./gradlew test --tests "*E01*"            # un ejercicio específico
./gradlew test --tests "*_14_*"           # todos los de un bloque (coroutines basic)
./gradlew test --tests "*_20_*"           # todos los de un bloque (arquitectura)
```

Los tests fallan hasta que implementas la función. **Esto es esperado**:
el ciclo es: leer el enunciado → implementar → correr el test → iterar.

No necesitas Android Studio, emulador ni un dispositivo. Es un proyecto
JVM puro.

## Mapa de bloques

| # | Bloque | Carpetas | Ejercicios | Dificultad |
|---|--------|----------|-----------:|-----------:|
| 1 | Kotlin básico | `_00_` a `_05_` | 7 | 🟢 |
| 2 | Kotlin intermedio | `_06_` a `_09_` | 5 | 🟡 |
| 3 | Kotlin avanzado | `_10_` a `_13_` | 4 | 🔴 |
| 4 | Coroutines | `_14_` a `_19_` | 6 | 🔴 |
| 5 | Arquitectura | `_20_` a `_25_` | 6 | 🟡 |
| 6 | Bridge Android | `_26_` a `_29_` | 4 | 🟢 (lectura) |
| 7 | Kata integrador | `_30_` | 1 | 🔴 |

Total: 33 ejercicios.

## Ruta sugerida

1. **Bloque 1 → 3** (semanas 1-3): dominar el lenguaje.
2. **Bloque 4 — Coroutines** (semanas 4-5): corazón de Android moderno.
3. **Bloque 5 — Arquitectura** (semana 6): leer cada cápsula y ejecutar su `main()`.
4. **Bloque 6** (lectura, semana 7): conectar lo anterior con Android.
5. **Kata integrador** (semana 8): integrar todo.

## Convención por ejercicio

Cada `.kt` de ejercicio sigue este formato:

```kotlin
package cl.duoc.exercises._XX_tema

/**
 * EJERCICIO: <título>
 *
 * OBJETIVO: <qué>
 * POR QUÉ IMPORTA: <motivación>
 * INSTRUCCIONES: <pasos>
 * DIFICULTAD: 🟢 / 🟡 / 🔴
 * TEST: ./gradlew :test --tests "*<id>*"
 */
fun <nombre>(<args>): <ReturnType> = TODO()
```

El `TODO()` es un placeholder que lanza `NotImplementedError` al invocarse.
Reemplázalo por tu implementación. El test te dice cuándo vas bien.

## Glosario

- **`TODO()`**: marca de "implementar aquí". Lanza si lo ejecutas.
- **`suspend fun`**: función que puede suspenderse sin bloquear el thread.
- **`Flow`**: stream de valores cold (cada collect() arranca el producer).
- **`StateFlow`**: stream hot con un valor actual; perfecto para UI state.
- **Sealed class**: jerarquía cerrada; el compilador chequea exhaustividad.
- **Repository**: encapsula la fuente de datos para no acoplar UI a API/DB.

## Recursos adicionales

- [Kotlin Docs](https://kotlinlang.org/docs/home.html)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Kotlin Flow](https://kotlinlang.org/docs/flow.html)
- [Android Architecture Guide](https://developer.android.com/topic/architecture)
