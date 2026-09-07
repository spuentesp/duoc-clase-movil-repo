# Ejercicio 2 - Tienda online (Colecciones)

## Contexto

Una tienda online mantiene un catálogo de productos. Necesita consultas rápidas: valor total del inventario, productos con stock bajo, agrupación por categoría y ranking de productos más caros.

## Objetivos de aprendizaje (IL 1.2)

- Trabajar con `List` y operaciones funcionales: `filter`, `map`, `groupBy`, `sumOf`, `sortedByDescending`, `take`.
- Usar `data class` para modelar entidades inmutables.
- Definir una clase (`Inventario`) que encapsula operaciones sobre una colección.

## Requisitos

- `data class Producto(val id: Int, val nombre: String, val precio: Int, val categoria: String, val stock: Int)`.
- `class Inventario(val productos: List<Producto>)` con:
  - `fun valorTotal(): Int` - suma `precio * stock` de cada producto.
  - `fun conStockBajo(umbral: Int = 5): List<Producto>`.
  - `fun agruparPorCategoria(): Map<String, List<Producto>>`.
  - `fun topMasCaros(n: Int): List<Producto>`.

## Enfoque paso a paso

1. Modela el producto con `data class` para tener `equals`, `hashCode` y `copy` gratis.
2. Construye `Inventario` con la lista de productos como dependencia inyectada por constructor.
3. Implementa cada método usando una sola expresión con las funciones de extensión de la stdlib.
4. Verifica que el inventario vacío no rompe (caso borde).

## Criterios de aceptación

- `valorTotal` ignora productos con stock 0.
- `conStockBajo` retorna una lista, no `null`.
- `agruparPorCategoria` produce un `Map` inmutable.
- `topMasCaros(n)` respeta `n` y ordena de mayor a menor precio.
- Los 5 tests en `ProductoTest` pasan en verde.
