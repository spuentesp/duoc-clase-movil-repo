package cl.duoc.navegacion

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextReplacement
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
import cl.duoc.navegacion.ui.theme.TemaNavegacion
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Recorre cada lección como lo haría un usuario: toca botones y verifica que
 * se llegue a la pantalla correcta con los argumentos correctos.
 * Corre en la JVM gracias a Robolectric (no necesita emulador).
 */
@OptIn(ExperimentalTestApi::class)
@RunWith(RobolectricTestRunner::class)
class NavegacionLeccionesTest {

    @get:Rule
    val compose = createComposeRule()

    private fun abrir(contenido: @androidx.compose.runtime.Composable () -> Unit) =
        compose.setContent { TemaNavegacion { contenido() } }

    private fun tocar(texto: String, substring: Boolean = false) {
        compose.onNodeWithText(texto, substring = substring).performScrollTo().performClick()
        compose.waitForIdle()
    }

    private fun existe(texto: String, substring: Boolean = false) {
        compose.onNodeWithText(texto, substring = substring).assertExists()
    }

    @Test
    fun indice_abre_una_leccion_y_vuelve() {
        abrir { IndiceLecciones() }
        tocar("4. Navegación type-safe")
        existe("L4 · Carta (type-safe)")
        compose.onNodeWithContentDescription("Volver").performClick()
        compose.waitForIdle()
        existe("Navegación en Compose")
    }

    @Test
    fun leccion01_navega_y_retrocede() {
        abrir { Leccion01NavegacionBasica(onSalir = {}) }
        tocar("Ir a Perfil")
        existe("Pantalla: Perfil")
        tocar("Ir a Ajustes")
        existe("Pantalla: Ajustes")
        tocar("Volver directo a Inicio")
        existe("Pantalla: Inicio")
    }

    @Test
    fun leccion02_recibe_argumentos_incluso_con_caracteres_especiales() {
        abrir { Leccion02Argumentos(onSalir = {}) }
        tocar("Café / té", substring = true)
        existe("id recibido = 3")
        existe("nombre recibido = Café / té")
    }

    @Test
    fun leccion03_usa_valores_por_defecto() {
        abrir { Leccion03ArgumentosOpcionales(onSalir = {}) }
        tocar("Solo ofertas", substring = true)
        existe("texto = null (valor por defecto)")
        existe("soloOfertas = true")
    }

    @Test
    fun leccion04_type_safe_con_enum_y_opcionales() {
        abrir { Leccion04TypeSafe(onSalir = {}) }
        tocar("Leche asada", substring = true)
        existe("nombre = Leche asada")
        existe("categoria = POSTRE")
        existe("descuento = 0.2")
    }

    @Test
    fun leccion05_popUpTo_y_launchSingleTop() {
        abrir { Leccion05PilaDeNavegacion(onSalir = {}) }
        existe("Pila: Login")
        tocar("Iniciar sesión")
        existe("Pila: Home")                       // Login salió de la pila
        tocar("Home otra vez (apila un duplicado)")
        existe("Pila: Home → Home")
        tocar("Home otra vez con launchSingleTop")
        existe("Pila: Home → Home")                // no se agregó otro
        tocar("Ir a Perfil")
        tocar("Ir a Ajustes")
        existe("Pila: Home → Home → Perfil → Ajustes")
        tocar("Cerrar sesión")
        existe("Pila: Login")                      // se borró todo
    }

    @Test
    fun leccion06_devuelve_resultados() {
        abrir { Leccion06DevolverResultados(onSalir = {}) }
        tocar("Elegir salsa")
        tocar("Pebre")
        existe("Salsa: Pebre")

        tocar("Cambiar nombre")
        compose.onNode(hasSetTextAction()).performTextReplacement("Sofía")
        tocar("Guardar")
        existe("Cliente: Sofía")
        existe("Salsa: Pebre")                     // el resultado anterior se mantiene
    }

    @Test
    fun leccion07_viewmodel_lee_el_argumento() {
        abrir { Leccion07ViewModelYArgumentos(onSalir = {}) }
        tocar("Pedido #102", substring = true)
        existe("Cliente: Bruno")
        tocar("Agregar $500 de propina")
        tocar("Agregar $500 de propina")
        existe("Total: $13990")
    }

    @Test
    fun leccion08_viewmodel_compartido_en_grafo_anidado() {
        abrir { Leccion08GrafosAnidados(onSalir = {}) }
        tocar("Crear cuenta")
        compose.onNode(hasSetTextAction()).performTextReplacement("Ana")
        tocar("Siguiente")
        existe("Hola Ana, ¿cuál es tu correo?")
        compose.onNode(hasSetTextAction()).performTextReplacement("ana@duoc.cl")
        tocar("Siguiente")
        existe("Nombre: Ana")
        existe("Correo: ana@duoc.cl")
        tocar("Confirmar registro")
        existe("¡Bienvenido/a, Ana!")
    }

    @Test
    fun leccion09_cada_pestana_conserva_su_estado() {
        abrir { Leccion09BottomNavigation(onSalir = {}) }
        tocar("Noticia 2")
        existe("Detalle de la noticia 2")
        compose.onNode(hasText("Buscar")).performClick()
        compose.waitForIdle()
        existe("Pestaña Buscar")
        compose.onNode(hasText("Inicio")).performClick()
        compose.waitForIdle()
        existe("Detalle de la noticia 2")          // restoreState
    }

    @Test
    fun leccion10_factory_inyecta_repositorio_y_argumento() {
        abrir { Leccion10InyeccionManual(onSalir = {}) }
        compose.waitUntil(timeoutMillis = 5_000) {
            compose.onAllNodes(hasText("DSY1105", substring = true)).fetchSemanticsNodes().isNotEmpty()
        }
        tocar("DSY1105", substring = true)
        compose.waitUntil(timeoutMillis = 5_000) {
            compose.onAllNodes(hasText("Créditos: 10")).fetchSemanticsNodes().isNotEmpty()
        }
        existe("Desarrollo de Aplicaciones Móviles")
    }
}
