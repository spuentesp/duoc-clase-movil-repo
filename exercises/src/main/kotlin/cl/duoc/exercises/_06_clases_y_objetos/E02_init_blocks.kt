package cl.duoc.exercises._06_clases_y_objetos

/**
 * EJERCICIO: Init blocks y constructores secundarios
 *
 * OBJETIVO: Bloques init se ejecutan en orden; constructor secundario
 * delega al primario con `: this(...)`.
 *
 * INSTRUCCIONES:
 * 1. Implementa `CuentaBancaria` con `saldo: Double` (no puede ser negativo).
 *    Init block debe validar y lanzar IllegalArgumentException si saldo < 0.
 *    Constructor secundario sin argumentos que inicializa saldo = 0.
 */
class CuentaBancaria(saldoInicial: Double) {
    val saldo: Double = saldoInicial.also {
        require(it >= 0) { "El saldo inicial no puede ser negativo" }
    }

    constructor() : this(0.0)
}