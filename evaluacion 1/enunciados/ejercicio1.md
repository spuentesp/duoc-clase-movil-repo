# Ejercicio 1 — Biblioteca (POO + polimorfismo)

## Contexto

La biblioteca municipal necesita un módulo para registrar las publicaciones que adquiere: libros y revistas. Cada publicación comparte información básica (id, título, año) pero muestra su detalle de manera diferente según el tipo.

## Objetivos de aprendizaje (IL 1.1, IL 1.2, IL 1.3)

- Declarar una clase base `open` para permitir herencia.
- Crear clases derivadas que extiendan la base y sobrescriban un método.
- Usar polimorfismo: tratar distintos tipos como la misma clase base.

## Requisitos

Crea en el paquete `ejercicio1`:

- `open class Publicacion(val id: Int, val titulo: String, val anio: Int)` con `open fun mostrarDetalle(): String`.
- `class Libro(...) : Publicacion(...)` con `val autor: String` y `val paginas: Int`. Override de `mostrarDetalle()`.
- `class Revista(...) : Publicacion(...)` con `val editor: String` y `val numero: Int`. Override de `mostrarDetalle()`.

## Enfoque paso a paso

1. Declara la clase base con `open class` y un método `open fun`.
2. Crea las dos derivadas usando `:` y pasando los parámetros al constructor padre.
3. Sobrescribe `mostrarDetalle()` con `override fun` y usa `super.mostrarDetalle()` para reutilizar la base.
4. Verifica que `Libro` y `Revista` son asignables a `Publicacion` (polimorfismo).

## Criterios de aceptación

- `Publicacion.mostrarDetalle()` retorna un texto con id, título y año.
- `Libro.mostrarDetalle()` extiende lo anterior con autor y páginas.
- `Revista.mostrarDetalle()` extiende lo anterior con editor y número.
- Una `List<Publicacion>` puede contener mezclados libros y revistas.
- Los 6 tests en `PublicacionTest` pasan en verde.
