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
