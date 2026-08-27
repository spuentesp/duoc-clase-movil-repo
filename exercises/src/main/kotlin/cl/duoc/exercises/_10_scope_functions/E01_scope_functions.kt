package cl.duoc.exercises._10_scope_functions

/**
 * EJERCICIO: Scope functions
 *
 * OBJETIVO: `let`, `apply`, `also`, `run`, `with`.
 *   - let:   it como argumento, retorna lambda. Para null-safe.
 *   - apply: this como receptor, retorna this. Para configurar.
 *   - also:  it como argumento, retorna this. Para side-effects.
 *   - run:   this como receptor, retorna lambda. Para computar.
 *   - with: igual que run pero no extension.
 *
 * INSTRUCCIONES:
 * 1. `configurar(): StringBuilder` retorna un StringBuilder con "Hola", " ", "Mundo" usando apply.
 * 2. `safeLength(s: String?): Int` retorna s?.let { it.length } ?: 0.
 */
fun configurar(): StringBuilder = TODO()
fun safeLength(s: String?): Int = TODO()
