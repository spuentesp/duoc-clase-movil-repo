package cl.duoc.exercises._12_properties_delegates

import kotlin.properties.Delegates

/**
 * EJERCICIO: Property delegates
 *
 * OBJETIVO: `lazy`, `observable`, `vetoable`, `Delegates.notNull()`.
 *
 * INSTRUCCIONES:
 * 1. `valorGrande: String` lazy que retorna "computado" solo en el primer acceso.
 * 2. `contador: Int` con vetoable que solo acepta valores positivos.
 * 3. `nombre: String` con observable que registra cada cambio en `log`.
 */
val valorGrande: String by lazy { TODO() }

class Config {
    var contador: Int by Delegates.vetoable(0) { _, _, nuevo -> TODO() }
    var nombre: String by Delegates.observable("<sin nombre>") { _, _, nuevo -> log.add(nuevo) }
    val log: MutableList<String> = mutableListOf()
}
