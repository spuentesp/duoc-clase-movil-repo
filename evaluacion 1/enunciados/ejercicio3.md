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