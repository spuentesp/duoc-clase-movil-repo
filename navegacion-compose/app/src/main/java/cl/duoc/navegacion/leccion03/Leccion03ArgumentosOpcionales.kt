package cl.duoc.navegacion.leccion03

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cl.duoc.navegacion.ui.BotonPrincipal
import cl.duoc.navegacion.ui.CodigoClave
import cl.duoc.navegacion.ui.Explicacion
import cl.duoc.navegacion.ui.PantallaBase
import cl.duoc.navegacion.ui.ValorRecibido

// =====================================================================
// LECCIÓN 3 — Argumentos OPCIONALES con valores por defecto
//
// Obligatorios -> van en el camino:     "detalle/{id}"
// Opcionales   -> van como parámetros:  "busqueda?texto={texto}&soloOfertas={soloOfertas}"
//
// Un argumento opcional DEBE tener defaultValue (o ser nullable).
// Si al navegar no se envía, la pantalla recibe el valor por defecto.
// =====================================================================

data class Plato(val nombre: String, val enOferta: Boolean)

private val MENU = listOf(
    Plato("Pastel de choclo", enOferta = true),
    Plato("Cazuela de vacuno", enOferta = false),
    Plato("Porotos granados", enOferta = true),
    Plato("Charquicán", enOferta = false)
)

object Rutas03 {
    const val MENU_FILTROS = "filtros"
    const val BUSQUEDA = "busqueda?texto={texto}&soloOfertas={soloOfertas}"

    /** Arma la ruta incluyendo solo los parámetros que realmente se envían. */
    fun busqueda(texto: String? = null, soloOfertas: Boolean? = null): String {
        val parametros = buildList {
            if (texto != null) add("texto=${Uri.encode(texto)}")
            if (soloOfertas != null) add("soloOfertas=$soloOfertas")
        }
        return if (parametros.isEmpty()) "busqueda" else "busqueda?" + parametros.joinToString("&")
    }
}

@Composable
fun Leccion03ArgumentosOpcionales(onSalir: () -> Unit) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Rutas03.MENU_FILTROS) {
        composable(Rutas03.MENU_FILTROS) {
            PantallaFiltros(
                onBuscar = { texto, soloOfertas -> navController.navigate(Rutas03.busqueda(texto, soloOfertas)) },
                onSalir = onSalir
            )
        }

        composable(
            route = Rutas03.BUSQUEDA,
            arguments = listOf(
                navArgument("texto") {
                    type = NavType.StringType
                    nullable = true        // puede no venir
                    defaultValue = null
                },
                navArgument("soloOfertas") {
                    type = NavType.BoolType
                    defaultValue = false   // si no viene, vale false
                }
            )
        ) { entry ->
            PantallaBusqueda(
                texto = entry.arguments?.getString("texto"),
                soloOfertas = entry.arguments?.getBoolean("soloOfertas") ?: false,
                onVolver = { navController.popBackStack() }
            )
        }
    }
}

@Composable
private fun PantallaFiltros(onBuscar: (String?, Boolean?) -> Unit, onSalir: () -> Unit) {
    PantallaBase(titulo = "L3 · Filtros", onVolver = onSalir) {
        Explicacion("Cada botón navega a la MISMA pantalla, enviando distintos parámetros opcionales.")
        CodigoClave(
            """
            navArgument("texto") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
            """
        )
        BotonPrincipal("Sin parámetros → \"busqueda\"") { onBuscar(null, null) }
        BotonPrincipal("Solo ofertas → \"busqueda?soloOfertas=true\"") { onBuscar(null, true) }
        BotonPrincipal("Texto \"pa\" → \"busqueda?texto=pa\"") { onBuscar("pa", null) }
        BotonPrincipal("Ambos → \"?texto=po&soloOfertas=true\"") { onBuscar("po", true) }
    }
}

@Composable
private fun PantallaBusqueda(texto: String?, soloOfertas: Boolean, onVolver: () -> Unit) {
    val resultados = MENU
        .filter { texto == null || it.nombre.contains(texto, ignoreCase = true) }
        .filter { !soloOfertas || it.enOferta }

    PantallaBase(titulo = "L3 · Resultados", onVolver = onVolver) {
        ValorRecibido("texto = ${texto ?: "null (valor por defecto)"}")
        ValorRecibido("soloOfertas = $soloOfertas")
        Explicacion("Resultados filtrados con los argumentos recibidos (${resultados.size}):")
        resultados.forEach { Text("• ${it.nombre}" + if (it.enOferta) "  (oferta)" else "") }
    }
}
