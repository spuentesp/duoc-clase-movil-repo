# Ejercicio 7 - Secuencia de Fibonacci (Ciclos e iteraciones)

## Contexto

Una clase de matemáticas quiere generar y analizar la secuencia de Fibonacci: 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, ... Cada término es la suma de los dos anteriores. Se necesita un módulo que entregue los primeros N términos, su suma, y todos los términos menores que un límite dado.

## Objetivos de aprendizaje (IL 1.2)

- Usar `while` con condición de salida.
- Acumular valores en una `MutableList` y retornar `List<Long>` inmutable.
- Aplicar `break` para cortar un bucle infinito controlado.
- Validar argumentos con `require`.

## Requisitos

Crea en el paquete `ejercicio7`:

- `class GeneradorFibonacci` con:
  - `fun primerosN(n: Int): List<Long>` — los primeros `n` términos; `n < 0` lanza `IllegalArgumentException`.
  - `fun sumaHasta(n: Int): Long` — suma de los primeros `n` términos.
  - `fun menoresQue(limite: Long): List<Long>` — todos los términos `< limite`.

## Enfoque paso a paso

1. Arranca con una lista semilla `[0, 1]`.
2. Para `primerosN`, usa `while (lista.size < n)` y agrega el siguiente término.
3. Para `menoresQue`, usa un bucle infinito con `break` cuando el siguiente ya no cumple la condición.
4. Valida entradas con `require`.

## Criterios de aceptación

- `primerosN(7)` retorna `[0, 1, 1, 2, 3, 5, 8]`.
- `sumaHasta(7)` retorna `20`.
- `menoresQue(100)` retorna `[0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89]`.
- `primerosN(-1)` lanza `IllegalArgumentException`.
- Los 8 tests en `FibonacciTest` pasan en verde.
