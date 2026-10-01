package cl.duoc.navegacion.leccion09

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import cl.duoc.navegacion.ui.BotonPrincipal
import cl.duoc.navegacion.ui.CodigoClave
import cl.duoc.navegacion.ui.Explicacion
import cl.duoc.navegacion.ui.ValorRecibido
import kotlin.reflect.KClass
import kotlinx.serialization.Serializable

// =====================================================================
// LECCIÓN 9 — Bottom Navigation (barra inferior con pestañas)
//
// Claves:
//   1. La barra (NavigationBar) va en el Scaffold, FUERA del NavHost.
//   2. La pestaña seleccionada se calcula desde la ruta actual:
//        currentBackStackEntryAsState() + destination.hierarchy.any { it.hasRoute(...) }
//   3. Al cambiar de pestaña se usan 3 opciones para que cada pestaña
//      conserve su propio historial y estado:
//        popUpTo(startDestination) { saveState = true }
//        launchSingleTop = true
//        restoreState = true
//   4. Si una pestaña tiene varias pantallas (lista -> detalle), se agrupan
//      en un grafo anidado (lección 8).
// =====================================================================

// Pestaña "Inicio": grafo con 2 pantallas
@Serializable data object GrafoInicio
@Serializable data object Noticias
@Serializable data class DetalleNoticia(val id: Int)

// Pestañas simples
@Serializable data object Buscar
@Serializable data object Cuenta

/** Describe cada pestaña de la barra. */
data class Pestana(val etiqueta: String, val icono: ImageVector, val ruta: Any, val claseRuta: KClass<*>)

private val PESTANAS = listOf(
    Pestana("Inicio", Icons.Filled.Home, GrafoInicio, GrafoInicio::class),
    Pestana("Buscar", Icons.Filled.Search, Buscar, Buscar::class),
    Pestana("Cuenta", Icons.Filled.Person, Cuenta, Cuenta::class)
)

@Composable
fun Leccion09BottomNavigation(onSalir: () -> Unit) {
    val navController = rememberNavController()

    // Ruta actual (se recompone cada vez que se navega)
    val entradaActual by navController.currentBackStackEntryAsState()
    val destinoActual = entradaActual?.destination

    PantallaBaseConBarra(
        onSalir = onSalir,
        barraInferior = {
            NavigationBar {
                PESTANAS.forEach { pestana ->
                    // hierarchy incluye el destino y todos sus grafos padres:
                    // así "Inicio" queda marcada también en DetalleNoticia.
                    val seleccionada = destinoActual?.hierarchy?.any { it.hasRoute(pestana.claseRuta) } == true
                    NavigationBarItem(
                        selected = seleccionada,
                        onClick = {
                            navController.navigate(pestana.ruta) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(pestana.icono, contentDescription = null) },
                        label = { Text(pestana.etiqueta) }
                    )
                }
            }
        }
    ) { modifier ->
        NavHost(navController = navController, startDestination = GrafoInicio, modifier = modifier) {

            navigation<GrafoInicio>(startDestination = Noticias) {
                composable<Noticias> {
                    PantallaNoticias(onAbrir = { id -> navController.navigate(DetalleNoticia(id)) })
                }
                composable<DetalleNoticia> { entry ->
                    PantallaDetalleNoticia(id = entry.toRoute<DetalleNoticia>().id, onVolver = { navController.popBackStack() })
                }
            }
            composable<Buscar> { PantallaBuscar() }
            composable<Cuenta> { PantallaCuenta() }
        }
    }
}

/**
 * Scaffold de esta lección: barra superior + barra inferior + contenido.
 * El contenido recibe un Modifier con el espacio que dejan libre las barras.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PantallaBaseConBarra(
    onSalir: () -> Unit,
    barraInferior: @Composable () -> Unit,
    contenido: @Composable (Modifier) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("L9 · Bottom Navigation") },
                navigationIcon = {
                    IconButton(onClick = onSalir) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        bottomBar = barraInferior
    ) { padding ->
        contenido(Modifier.padding(padding))
    }
}

/** Contenido de cada pestaña: columna con scroll y margen. */
@Composable
private fun ColumnaPestana(contenido: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        content = contenido
    )
}

@Composable
private fun PantallaNoticias(onAbrir: (Int) -> Unit) {
    ColumnaPestana {
        ValorRecibido("Pestaña Inicio · Noticias")
        Explicacion(
            "1) Abre una noticia.  2) Cambia a la pestaña Buscar.  3) Vuelve a Inicio:\n" +
                "la noticia sigue abierta gracias a saveState / restoreState."
        )
        CodigoClave(
            """
            navController.navigate(pestana.ruta) {
                popUpTo(navController.graph
                    .findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
            """
        )
        (1..3).forEach { id -> BotonPrincipal("Noticia $id") { onAbrir(id) } }
    }
}

@Composable
private fun PantallaDetalleNoticia(id: Int, onVolver: () -> Unit) {
    ColumnaPestana {
        ValorRecibido("Detalle de la noticia $id")
        Text("Esta pantalla está dentro del grafo de la pestaña Inicio.")
        BotonPrincipal("Volver a Noticias", onClick = onVolver)
    }
}

@Composable
private fun PantallaBuscar() {
    // rememberSaveable + restoreState: el texto se conserva al cambiar de pestaña y volver
    var texto by rememberSaveable { mutableStateOf("") }
    ColumnaPestana {
        ValorRecibido("Pestaña Buscar")
        OutlinedTextField(value = texto, onValueChange = { texto = it }, label = { Text("Buscar…") })
        Explicacion("Escribe algo, cambia de pestaña y vuelve: el texto sigue ahí.")
    }
}

@Composable
private fun PantallaCuenta() {
    var visitas by rememberSaveable { mutableIntStateOf(0) }
    ColumnaPestana {
        ValorRecibido("Pestaña Cuenta")
        BotonPrincipal("Contador: $visitas") { visitas++ }
        Explicacion("Sin restoreState = true este contador volvería a 0 cada vez que regresas a la pestaña.")
    }
}
