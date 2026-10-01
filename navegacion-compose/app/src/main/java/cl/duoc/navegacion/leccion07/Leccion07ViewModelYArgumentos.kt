package cl.duoc.navegacion.leccion07

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import cl.duoc.navegacion.ui.BotonPrincipal
import cl.duoc.navegacion.ui.BotonSecundario
import cl.duoc.navegacion.ui.CodigoClave
import cl.duoc.navegacion.ui.Explicacion
import cl.duoc.navegacion.ui.PantallaBase
import cl.duoc.navegacion.ui.ValorRecibido
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

// =====================================================================
// LECCIÓN 7 — Leer los argumentos de la ruta dentro del ViewModel
//
// Cada pantalla de la pila (NavBackStackEntry) tiene su propio ViewModelStore:
//   - viewModel() dentro de composable<...> crea un ViewModel PARA ESA PANTALLA.
//   - El ViewModel vive mientras la pantalla esté en la pila y se destruye
//     (onCleared) cuando se hace popBackStack.
//
// El ViewModel recibe automáticamente un SavedStateHandle que YA CONTIENE
// los argumentos de la ruta. Con toRoute<T>() los leemos tipados.
// La pantalla no necesita saber nada de argumentos: solo usa su ViewModel.
// =====================================================================

data class Pedido(val id: Int, val cliente: String, val detalle: String, val total: Int)

object PedidosDePrueba {
    val todos = listOf(
        Pedido(101, "Ana", "2 completos + bebida", 6_200),
        Pedido(102, "Bruno", "1 chorrillana", 12_990),
        Pedido(103, "Carla", "3 empanadas", 6_600)
    )

    fun buscar(id: Int): Pedido? = todos.firstOrNull { it.id == id }
}

@Serializable data object ListaPedidos
@Serializable data class DetallePedido(val pedidoId: Int)

/**
 * ViewModel de la pantalla de detalle.
 * Su constructor recibe SavedStateHandle: la factory por defecto de Navigation
 * se lo entrega ya relleno con los argumentos de la ruta.
 */
class DetallePedidoViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    // 1. Leemos el argumento tipado
    val pedidoId: Int = savedStateHandle.toRoute<DetallePedido>().pedidoId

    // 2. Con el id cargamos los datos (aquí de una lista; en la vida real, de un repositorio)
    val pedido: Pedido? = PedidosDePrueba.buscar(pedidoId)

    // 3. El SavedStateHandle también sirve para guardar estado que sobrevive
    //    a la rotación e incluso a que Android mate el proceso en segundo plano.
    val propina: StateFlow<Int> = savedStateHandle.getStateFlow(CLAVE_PROPINA, 0)

    fun agregarPropina() {
        savedStateHandle[CLAVE_PROPINA] = propina.value + 500
    }

    private companion object {
        const val CLAVE_PROPINA = "propina"
    }
}

@Composable
fun Leccion07ViewModelYArgumentos(onSalir: () -> Unit) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ListaPedidos) {
        composable<ListaPedidos> {
            PantallaListaPedidos(
                onAbrir = { id -> navController.navigate(DetallePedido(pedidoId = id)) },
                onSalir = onSalir
            )
        }
        composable<DetallePedido> {
            // Fíjate: NO leemos argumentos aquí. El ViewModel lo hace.
            PantallaDetallePedido(onVolver = { navController.popBackStack() })
        }
    }
}

@Composable
private fun PantallaListaPedidos(onAbrir: (Int) -> Unit, onSalir: () -> Unit) {
    PantallaBase(titulo = "L7 · Pedidos", onVolver = onSalir) {
        Explicacion("Al abrir un pedido solo se envía su id. El ViewModel del detalle lo lee y carga los datos.")
        CodigoClave(
            """
            class DetallePedidoViewModel(
                savedStateHandle: SavedStateHandle
            ) : ViewModel() {
                val pedidoId = savedStateHandle
                    .toRoute<DetallePedido>().pedidoId
            }
            """
        )
        PedidosDePrueba.todos.forEach { pedido ->
            BotonPrincipal("Pedido #${pedido.id} — ${pedido.cliente}") { onAbrir(pedido.id) }
        }
    }
}

@Composable
private fun PantallaDetallePedido(
    onVolver: () -> Unit,
    // viewModel() crea (o recupera) el ViewModel asociado a ESTA entrada de la pila
    viewModel: DetallePedidoViewModel = viewModel()
) {
    val propina by viewModel.propina.collectAsStateWithLifecycle()
    val pedido = viewModel.pedido

    PantallaBase(titulo = "L7 · Pedido #${viewModel.pedidoId}", onVolver = onVolver) {
        if (pedido == null) {
            Text("No existe el pedido ${viewModel.pedidoId}")
        } else {
            ValorRecibido("Cliente: ${pedido.cliente}")
            Text("Detalle: ${pedido.detalle}")
            ValorRecibido("Propina: $$propina")
            ValorRecibido("Total: $${pedido.total + propina}")
        }
        Explicacion(
            "Gira el teléfono: la propina se mantiene porque está en el ViewModel/SavedStateHandle.\n\n" +
                "Vuelve atrás y abre de nuevo el pedido: la propina vuelve a 0, porque al salir de la " +
                "pantalla su ViewModel se destruyó."
        )
        BotonPrincipal("Agregar $500 de propina", onClick = viewModel::agregarPropina)
        BotonSecundario("Volver", onVolver)
    }
}
