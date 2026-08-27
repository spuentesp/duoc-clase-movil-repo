package cl.duoc.exercises._26_android_compose_preview

// ============================================================
// BRIDGE: Conceptos Compose (solo lectura)
// ============================================================
// Este archivo NO se ejecuta porque requiere AndroidX.
// Su propósito es mostrar cómo los conceptos de Kotlin que ya
// practicaste se traducen a Compose.

/*
@Composable
fun SaludoComposable() {
    // remember + mutableStateOf = estado local que sobrevive recomposiciones
    var nombre by remember { mutableStateOf("Mundo") }

    // LaunchedEffect = coroutine que arranca al primer compose y se cancela al salir
    LaunchedEffect(Unit) {
        delay(1000)
        nombre = "Compose"
    }

    Column {
        Text("Hola, ${'$'}nombre!")
        Button(onClick = { nombre = "click!" }) { Text("Cambiar") }
    }
}

// Equivalencias:
//   val nombre = "Ana"           -> remember { mutableStateOf("Ana") }
//   Thread.sleep(1000)            -> delay(1000) en LaunchedEffect
//   .setText() / .setOnClickListener -> recomposición declarativa
*/
