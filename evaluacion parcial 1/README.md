# FoodExpress — Solución de referencia (Evaluación Parcial 1, DSY1105)

Implementación completa del caso de estudio **"Sistema de Gestión de Delivery FoodExpress"**,
cubriendo todos los requisitos obligatorios del enunciado y los 10 indicadores de la rúbrica.

## Ejecutar

```bash
cd "evaluacion parcial 1"
./gradlew run -q --console=plain      # aplicación interactiva
./gradlew test                        # 11 pruebas unitarias
```

En IntelliJ IDEA: **File → Open…** y seleccionar la carpeta `evaluacion parcial 1` (proyecto Gradle independiente, Kotlin 2.2, JDK 17+).

## Estructura (la exigida por el enunciado)

| Archivo | Contenido |
| --- | --- |
| `src/main/kotlin/Main.kt` | Punto de entrada (`runBlocking`), lectura de datos con reintentos y toda la presentación en consola |
| `src/main/kotlin/Producto.kt` | `open class Producto` y derivadas `Comida` y `Bebida`; enums `Categoria` y `Tamano` |
| `src/main/kotlin/EstadoPedido.kt` | `sealed class EstadoPedido` (Pendiente, EnPreparacion, Listo, Error) + `when` exhaustivo |
| `src/main/kotlin/GestorPedidos.kt` | Lógica de negocio: catálogo, cálculos, reglas, corutina, consultas y reportes (sin `println`) |
| `src/test/kotlin/GestorPedidosTest.kt` | Pruebas que verifican el ejemplo del enunciado, reglas y errores |

## Flujo del programa

1. Muestra el catálogo
2. Pide los productos (`1,3`) y el tipo de cliente (`vip`)
3. Calcula subtotal → descuento por cliente → promoción → IVA → total
4. Procesa el pedido de forma asíncrona (`delay(3000)`) mostrando los estados
5. Muestra el resumen con el desglose de precios
6. Permite hacer más pedidos y al final muestra consultas del catálogo y el reporte de ventas

Salida para el ejemplo del enunciado (`1,3` + `vip`):

```
=== RESUMEN DEL PEDIDO ===
- Hamburguesa Clásica: $8,990
- Coca Cola (Mediano): $2,289
Subtotal: $11,279
Descuento VIP (10%): -$1,128
IVA (19%): $1,929
TOTAL: $12,080
```

## Reglas de negocio implementadas

| Regla | Implementación |
| --- | --- |
| Descuento por cliente | Regular 5%, VIP 10%, Premium 15% (`TipoCliente`, `when`) |
| Tamaño de bebida | Pequeño ×1.00, Mediano ×1.15, Grande ×1.30 (deducido del ejemplo: $1,990→$2,289 y $2,990→$3,887) |
| Plato premium | Se marca "(Premium)" y suma 10 min de preparación; su precio diferenciado viene del catálogo ($15,990), igual que en el ejemplo |
| Pedido mínimo | Subtotal ≥ $5,000, si no el pedido termina en `EstadoPedido.Error` |
| Promoción especial | 5% adicional si el subtotal es ≥ $25,000 |
| IVA | 19% sobre el monto neto (después de descuentos) |
| Redondeo | Pesos chilenos sin decimales: cada monto se redondea al peso, así el desglose siempre cuadra |

## Mapeo a la rúbrica

| Indicador | Dónde se evidencia |
| --- | --- |
| **IE 1.1.1** Tipos y variables (10%) | Precios y porcentajes `Double` (nunca `Int`), cantidades/minutos `Int`, `Boolean` para premium, `const val` para reglas de negocio, `val` en propiedades del modelo y `var`/listas mutables solo donde cambian |
| **IE 1.1.2** Operadores aritméticos (10%) | `calcularSubtotal`, `calcularDescuentoCliente`, `calcularDescuentoPromocion`, `calcularIva`, `calcularResumen` |
| **IE 1.1.3** Condicionales (10%) | `when` en `TipoCliente.desdeTexto`, `crearProducto`, `EstadoPedido.etiqueta`; `if` en promoción, premium y validaciones (`check`/`require`) |
| **IE 1.2.1** Funciones modulares (10%) | Una responsabilidad por función; lógica (`GestorPedidos.kt`) separada de presentación (`Main.kt`) |
| **IE 1.2.2** Colecciones (10%) | `List<Producto>` para catálogo; `Pedido` encapsula una `MutableList` y expone `List` inmutable; `Map` en reportes |
| **IE 1.2.3** Orden superior (15%) | `map`, `mapNotNull`, `filter`, `filterIsInstance`, `sumOf`, `groupBy`, `groupingBy().eachCount()`, `flatMap`, `sortedByDescending`, `mapValues`, `joinToString` |
| **IE 1.2.4** Iteración y presentación (5%) | `forEachIndexed` para el catálogo, `for` en el resumen, `forEach` en consultas y reportes |
| **IE 1.3.1** Herencia (10%) | `open class Producto`; `Comida` y `Bebida` pasan parámetros al constructor primario de la superclase |
| **IE 1.3.2** Polimorfismo (5%) | `open fun calcularPrecioFinal()` (override en `Bebida`), `descripcion()` y `tiempoPreparacion()` (override en ambas); se invocan sobre `List<Producto>` |
| **IE 1.3.3** Corutinas + sealed class (10%) | `suspend fun procesarPedido` con `delay(3000)`, ejecutada desde `runBlocking`; resultado `EstadoPedido` evaluado con `when` exhaustivo |
| **IE 1.3.4** Excepciones (5%) | Precio negativo (`require` en `init` → descartado al crear el catálogo), entrada no numérica (`NumberFormatException`), índice inexistente (`IndexOutOfBoundsException`), tipo de cliente inválido, pedido bajo el mínimo (`IllegalStateException` → estado Error), promedio sin datos (`ArithmeticException`). El programa nunca se detiene: reintenta o informa y continúa |

> El catálogo incluye a propósito un producto con precio negativo ("Plato Fantasma") para demostrar
> en cada ejecución que el dato inválido se descarta y la aplicación sigue funcionando.
