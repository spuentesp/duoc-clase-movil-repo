package cl.duoc.exercises._20_arquitectura_separacion

// ============================================================
// POR QUÉ separar UI de lógica
// ============================================================
// Una pantalla que mezcla UI con lógica de negocio es:
//   - Difícil de testear (necesitas emulador o Robolectric).
//   - Imposible de reusar (la lógica vive atada a la pantalla).
//   - Frágil: refactors de UI rompen reglas de negocio.
// La solución: la pantalla emite eventos y observa estado desde un
// ViewModel. La pantalla no sabe cómo se computa el estado.
//
// DEMO: contador mal hecho vs bien hecho. Ejecuta `main()`.

// --- ❌ MAL: lógica en el composable (esquemático) ---
// @Composable
// fun ContadorMalHecho() {
//     var count by remember { mutableStateOf(0) }
//     Button(onClick = {
//         count++
//         if (count == 10) println("🎉")  // regla de negocio en UI
//     }) { Text("$count") }
// }

// --- ✅ BIEN: composable pasivo + ViewModel ---
class CounterViewModel {
    var count = 0; private set
    fun increment() {
        count++
        if (count == 10) println("🎉")  // regla testeable
    }
}

// @Composable
// fun ContadorBienHecho(vm: CounterViewModel) {
//     Button(onClick = vm::increment) { Text("${vm.count}") }
// }

fun main() {
    val vm = CounterViewModel()
    repeat(11) { vm.increment() }
    println("count final: ${vm.count}")
}
