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