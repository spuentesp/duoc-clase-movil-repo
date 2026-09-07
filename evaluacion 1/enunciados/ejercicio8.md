# Ejercicio 8 - Figuras geometricas (Constructores secundarios y propiedades computadas)

## Contexto

Un programa de dibujo necesita modelar figuras geométricas básicas (círculo, rectángulo, triángulo equilátero) con un comportamiento común (color, área, perímetro) y constructores convenientes para los casos típicos (por ejemplo, un cuadrado es un rectángulo con lados iguales).

## Objetivos de aprendizaje (IL 1.3, IL 1.2)

- Declarar una `sealed class` con subclases anidadas.
- Definir constructores secundarios que delegan con `this(...)`.
- Implementar propiedades computadas con `get()`.
- Sobrescribir `toString()` para depuración.
- Validar argumentos con `require` en un bloque `init`.

## Requisitos

Crea en el paquete `ejercicio8`:

- `sealed class Figura` con `abstract val color: String`, `abstract fun area(): Double`, `abstract fun perimetro(): Double`.
- `class Circulo(override val color: String, val radio: Double) : Figura()` con constructor `constructor(color: String) : this(color, radio = 1.0)`.
- `class Rectangulo(override val color: String, val ancho: Double, val alto: Double) : Figura()` con constructor `constructor(color: String, lado: Double) : this(color, lado, lado)` y propiedad computada `val esCuadrado: Boolean get() = ancho == alto`.
- `class TrianguloEquilatero(override val color: String, val lado: Double) : Figura()` con `init { require(lado > 0) }`.

## Enfoque paso a paso

1. Declara `sealed class Figura` con los miembros abstractos.
2. Crea las subclases dentro de la misma declaración usando `class X(...) : Figura()`.
3. Añade los constructores secundarios delegando con `this(...)`.
4. Implementa `area()`, `perimetro()` y `toString()` en cada subclase.
5. En `TrianguloEquilatero` añade `init` con `require` para validar el lado.

## Criterios de aceptación

- `Figura.Circulo("rojo")` tiene radio 1.0.
- `Figura.Rectangulo("verde", lado = 5.0)` es cuadrado y su área es 25.
- `Figura.TrianguloEquilatero("negro", lado = 0.0)` lanza `IllegalArgumentException`.
- `toString()` de cada figura incluye el nombre del tipo y sus propiedades.
- Los 7 tests en `FiguraTest` pasan en verde.
