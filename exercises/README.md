# Ejercicios Kotlin — Recursos de Estudio

Módulo hermano con 33 ejercicios Kotlin autocontenidos, diseñados para
progresar de Kotlin básico a Android. No requiere Android Studio ni
emulador: corre con Gradle en JVM.

## Requisitos

- JDK 17+
- Gradle 8.4 (provisto por el wrapper)

## Cómo correr

Desde esta carpeta (`exercises/`):

```bash
./gradlew test                          # todos los tests
./gradlew test --tests "*E01*"          # un ejercicio específico
./gradlew test --tests "*_14_*"          # un bloque completo (coroutines basic)
./gradlew test --tests "*_20_*"          # bloque de Arquitectura
```

## Convención

- Un paquete por tema: `_NN_tema/`.
- Cada ejercicio tiene dos archivos:
  - `E##_nombre.kt`: contiene el `TODO()` a implementar.
  - `E##_nombreTest.kt`: test JUnit 5 que valida el comportamiento.
- Los tests fallan hasta que el alumno implementa la función. Esto es
  **esperado** y parte del aprendizaje.

## Ruta de aprendizaje

| Bloque | Carpeta | Ejercicios |
|--------|---------|-----------|
| Kotlin básico | `_00_` a `_05_` | 7 |
| Kotlin intermedio | `_06_` a `_09_` | 5 |
| Kotlin avanzado | `_10_` a `_13_` | 4 |
| Coroutines en profundidad | `_14_` a `_19_` | 6 |
| Arquitectura de Software | `_20_` a `_25_` | 6 |
| Bridge a Android | `_26_` a `_29_` | 4 |
| Kata integrador | `_30_` | 1 |

Total: 33 ejercicios.
