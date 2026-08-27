package cl.duoc.exercises._30_kata_integrador

/**
 * EJERCICIO KATA: Parser de CSV inmutable con sealed Result
 *
 * OBJETIVO: Integrar varios temas en un solo ejercicio:
 *   - data class para modelar filas (con destructuring)
 *   - sealed class para resultados (éxito/header inválido/celda vacía)
 *   - extension functions (String.splitCsvLine)
 *   - lambdas/operaciones funcionales
 *   - null safety con Elvis
 *
 * INSTRUCCIONES:
 * 1. Implementa la data class `CsvRow(val cells: List<String>)`.
 * 2. Implementa extension `String.splitCsvLine(): List<String>` que
 *    separa por comas y descarta celdas vacías.
 * 3. Implementa `parsearCsv(texto: String): ResultadoCsv` que retorna:
 *    - Exito(rows: List<CsvRow>) si todo OK.
 *    - Error(mensaje: String) si la primera línea (header) no tiene
 *      al menos 2 columnas.
 *    Usa sealed class ResultadoCsv.
 */
data class CsvRow(val cells: List<String>)

sealed class ResultadoCsv {
    data class Exito(val rows: List<CsvRow>) : ResultadoCsv()
    data class Error(val mensaje: String) : ResultadoCsv()
}

fun String.splitCsvLine(): List<String> = TODO()

fun parsearCsv(texto: String): ResultadoCsv = TODO()
