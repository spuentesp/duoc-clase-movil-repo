package cl.duoc.navegacion

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import cl.duoc.navegacion.leccion01.Leccion01NavegacionBasica
import cl.duoc.navegacion.leccion02.Leccion02Argumentos
import cl.duoc.navegacion.leccion03.Leccion03ArgumentosOpcionales
import cl.duoc.navegacion.leccion04.Leccion04TypeSafe
import cl.duoc.navegacion.leccion05.Leccion05PilaDeNavegacion
import cl.duoc.navegacion.leccion06.Leccion06DevolverResultados
import cl.duoc.navegacion.leccion07.Leccion07ViewModelYArgumentos
import cl.duoc.navegacion.leccion08.Leccion08GrafosAnidados
import cl.duoc.navegacion.leccion09.Leccion09BottomNavigation
import cl.duoc.navegacion.leccion10.Leccion10InyeccionManual
import cl.duoc.navegacion.leccion11.Leccion11Hilt
import cl.duoc.navegacion.ui.PantallaBase
import kotlinx.serialization.Serializable

// =====================================================================
// Índice de lecciones (es a su vez un ejemplo de navegación type-safe).
//
// Cada lección tiene SU PROPIO NavHost y NavController: es un ejemplo
// independiente que se puede copiar tal cual a un proyecto nuevo.
// Este NavHost "externo" solo sirve para entrar y salir de cada lección.
// =====================================================================

@Serializable
data object Indice

@Serializable
data class AbrirLeccion(val numero: Int)

/** Datos de cada lección para dibujar el índice. */
data class InfoLeccion(
    val numero: Int,
    val titulo: String,
    val resumen: String,
    val contenido: @Composable (onSalir: () -> Unit) -> Unit
)

val LECCIONES: List<InfoLeccion> = listOf(
    InfoLeccion(1, "Navegación básica", "NavController, NavHost, navigate() y popBackStack()") { Leccion01NavegacionBasica(it) },
    InfoLeccion(2, "Argumentos obligatorios", "Pasar un id y un texto en la ruta: \"detalle/{id}/{nombre}\"") { Leccion02Argumentos(it) },
    InfoLeccion(3, "Argumentos opcionales", "Parámetros ?clave=valor con valores por defecto") { Leccion03ArgumentosOpcionales(it) },
    InfoLeccion(4, "Navegación type-safe", "Rutas como clases @Serializable y toRoute() (forma recomendada)") { Leccion04TypeSafe(it) },
    InfoLeccion(5, "Pila de navegación", "popUpTo, inclusive y launchSingleTop (login / cerrar sesión)") { Leccion05PilaDeNavegacion(it) },
    InfoLeccion(6, "Devolver resultados", "Enviar datos a la pantalla anterior con savedStateHandle") { Leccion06DevolverResultados(it) },
    InfoLeccion(7, "ViewModel + argumentos", "Leer los argumentos de la ruta dentro del ViewModel") { Leccion07ViewModelYArgumentos(it) },
    InfoLeccion(8, "Grafos anidados", "Agrupar pantallas y compartir un ViewModel entre ellas") { Leccion08GrafosAnidados(it) },
    InfoLeccion(9, "Bottom Navigation", "Barra inferior con pestañas que conservan su estado") { Leccion09BottomNavigation(it) },
    InfoLeccion(10, "Inyección de dependencias manual", "Repositorio + contenedor + ViewModel Factory, sin librerías") { Leccion10InyeccionManual(it) },
    InfoLeccion(11, "Inyección de dependencias con Hilt", "@HiltViewModel, @Inject, @Module y hiltViewModel()") { Leccion11Hilt(it) }
)

@Composable
fun IndiceLecciones() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Indice) {
        composable<Indice> {
            PantallaIndice(onAbrir = { numero -> navController.navigate(AbrirLeccion(numero)) })
        }
        composable<AbrirLeccion> { entrada ->
            val numero = entrada.toRoute<AbrirLeccion>().numero
            val leccion = LECCIONES.first { it.numero == numero }
            leccion.contenido { navController.popBackStack() }
        }
    }
}

@Composable
private fun PantallaIndice(onAbrir: (Int) -> Unit) {
    PantallaBase(titulo = "Navegación en Compose", onVolver = null) {
        Text(
            "Lecciones progresivas: de lo más básico a inyección de dependencias.",
            style = MaterialTheme.typography.bodyLarge
        )
        LECCIONES.forEach { leccion ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAbrir(leccion.numero) }
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("${leccion.numero}. ${leccion.titulo}", style = MaterialTheme.typography.titleMedium)
                    Text(leccion.resumen, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
