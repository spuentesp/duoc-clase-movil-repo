# Ejercicio 6 - Calculadora de nóminas (Tipos, aritmética y condicionales)

## Contexto

El área de RR.HH. necesita calcular el sueldo mensual de cada empleado considerando su tipo (junior, senior, gerente), las horas trabajadas y un bono por horas extra. Adicionalmente, se aplica un descuento por tramos según el sueldo bruto.

## Objetivos de aprendizaje (IL 1.1)

- Usar `enum class` para modelar un conjunto cerrado de valores.
- Aplicar operadores aritméticos (`*`, `+`, `-`, `/`, `%`) sobre enteros.
- Usar `when` como expresión para mapear tipos a tarifas.
- Usar `if` como expresión para decisiones simples.
- Validar entradas con `require` (que lanza `IllegalArgumentException`).

## Requisitos

Crea en el paquete `ejercicio6`:

- `enum class TipoEmpleado { JUNIOR, SENIOR, GERENTE }`.
- `class Empleado(val nombre: String, val horasTrabajadas: Int, val tipo: TipoEmpleado)` con:
  - `val tarifaPorHora: Int` (8_000, 15_000, 25_000 según tipo).
  - `val bono: Int` (5_000 por hora extra sobre 160).
  - `fun calcularSueldoBruto(): Int`.
  - `fun calcularDescuento(): Int` (0% si bruto < 500k, 8% si < 1M, 15% si ≥ 1M).
  - `fun calcularSueldoNeto(): Int`.
  - Si `horasTrabajadas < 0`, lanzar `IllegalArgumentException` desde un bloque `init`.

## Enfoque paso a paso

1. Modela los tipos de empleado con `enum class` para tener un conjunto cerrado.
2. En el `init` valida las horas (negativas → excepción).
3. Calcula `tarifaPorHora` y `bono` como propiedades inmutables usando `when` e `if`.
4. Implementa los métodos usando los valores almacenados y aritmética entera.
5. Para el descuento usa `when` con rangos.

## Criterios de aceptación

- `tarifaPorHora` se mapea correctamente por tipo.
- `bono` es 0 cuando hay ≤ 160 horas; 5_000 por hora extra sobre 160.
- `calcularDescuento` aplica el tramo correcto según el bruto.
- `Empleado("Ana", -1, JUNIOR)` lanza `IllegalArgumentException`.
- Los 9 tests en `EmpleadoTest` pasan en verde.
