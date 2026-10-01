package cl.duoc.navegacion.leccion01

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.duoc.navegacion.ui.BotonPrincipal
import cl.duoc.navegacion.ui.BotonSecundario
import cl.duoc.navegacion.ui.CodigoClave
import cl.duoc.navegacion.ui.Explicacion
import cl.duoc.navegacion.ui.PantallaBase
import cl.duoc.navegacion.ui.ValorRecibido

// =====================================================================
// LECCIÓN 1 — Navegación básica
//
// Las 3 piezas de Navigation Compose:
//   1. NavController -> el "control remoto": cambia de pantalla y recuerda el historial.
//   2. NavHost       -> el "contenedor": dibuja la pantalla que corresponde a la ruta actual.
//   3. Ruta          -> el "nombre" de cada pantalla (aquí, un simple String).
//
// Dependencia necesaria (build.gradle.kts):
//   implementation("androidx.navigation:navigation-compose:<versión>")
// =====================================================================

/** Las rutas como constantes: evita errores de tipeo al escribir el mismo String en varios lugares. */
object Rutas01 {
    const val INICIO = "inicio"
    const val PERFIL = "perfil"
    const val AJUSTES = "ajustes"
}

@Composable
fun Leccion01NavegacionBasica(onSalir: () -> Unit) {
    // 1. Creamos (y recordamos entre recomposiciones) el NavController.
    val navController = rememberNavController()

    // 2. El NavHost define el "mapa" de pantallas (el grafo de navegación)
    //    y cuál es la primera (startDestination).
    NavHost(navController = navController, startDestination = Rutas01.INICIO) {

        // 3. Cada composable(ruta) { ... } registra una pantalla.
        composable(Rutas01.INICIO) {
            // Buena práctica: la pantalla NO recibe el navController,
            // recibe lambdas ("eventos"). Así es reutilizable y fácil de probar.
            PantallaInicio(
                onIrAPerfil = { navController.navigate(Rutas01.PERFIL) },
                onSalir = onSalir
            )
        }

        composable(Rutas01.PERFIL) {
            PantallaPerfil(
                onIrAAjustes = { navController.navigate(Rutas01.AJUSTES) },
                onVolver = { navController.popBackStack() }
            )
        }

        composable(Rutas01.AJUSTES) {
            PantallaAjustes(
                onVolver = { navController.popBackStack() },
                // popBackStack(ruta, inclusive) retrocede HASTA esa ruta.
                // inclusive = false -> la ruta "inicio" se mantiene y queda visible.
                onVolverAlInicio = { navController.popBackStack(Rutas01.INICIO, inclusive = false) }
            )
        }
    }
}

@Composable
private fun PantallaInicio(onIrAPerfil: () -> Unit, onSalir: () -> Unit) {
    PantallaBase(titulo = "L1 · Inicio", onVolver = onSalir) {
        ValorRecibido("Pantalla: Inicio")
        Explicacion(
            "Esta es la startDestination del NavHost: la primera pantalla que se muestra. " +
                "Al tocar el botón se llama a navController.navigate(\"perfil\"), que agrega " +
                "Perfil ENCIMA de Inicio en la pila de navegación (back stack)."
        )
        CodigoClave(
            """
            val navController = rememberNavController()
            NavHost(navController, startDestination = "inicio") {
                composable("inicio") { PantallaInicio(...) }
                composable("perfil") { PantallaPerfil(...) }
            }
            """
        )
        BotonPrincipal("Ir a Perfil", onClick = onIrAPerfil)
    }
}

@Composable
private fun PantallaPerfil(onIrAAjustes: () -> Unit, onVolver: () -> Unit) {
    PantallaBase(titulo = "L1 · Perfil", onVolver = onVolver) {
        ValorRecibido("Pantalla: Perfil")
        Explicacion(
            "Pila actual: Inicio → Perfil.\n\n" +
                "La flecha de arriba y el botón \"atrás\" del teléfono llaman a " +
                "navController.popBackStack(): sacan la pantalla de arriba de la pila " +
                "y vuelve a verse Inicio."
        )
        CodigoClave("""navController.navigate("ajustes")""")
        BotonPrincipal("Ir a Ajustes", onClick = onIrAAjustes)
        BotonSecundario("Volver (popBackStack)", onVolver)
    }
}

@Composable
private fun PantallaAjustes(onVolver: () -> Unit, onVolverAlInicio: () -> Unit) {
    PantallaBase(titulo = "L1 · Ajustes", onVolver = onVolver) {
        ValorRecibido("Pantalla: Ajustes")
        Explicacion(
            "Pila actual: Inicio → Perfil → Ajustes.\n\n" +
                "popBackStack() vuelve solo una pantalla (a Perfil). " +
                "popBackStack(\"inicio\", inclusive = false) saca todo lo que está sobre Inicio."
        )
        CodigoClave("""navController.popBackStack("inicio", inclusive = false)""")
        BotonPrincipal("Volver directo a Inicio", onClick = onVolverAlInicio)
        BotonSecundario("Volver una pantalla", onVolver)
    }
}
