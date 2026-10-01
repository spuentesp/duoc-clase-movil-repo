package cl.duoc.navegacion

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Abre la app completa (MainActivity con @AndroidEntryPoint) para comprobar que
 * Hilt construye de verdad los ViewModels y comparte el repositorio @Singleton.
 */
@RunWith(RobolectricTestRunner::class)
class AppConHiltTest {

    @get:Rule
    val compose = createAndroidComposeRule<MainActivity>()

    @Test
    fun leccion11_hilt_inyecta_y_comparte_el_repositorio() {
        compose.onNodeWithText("11. Inyección de dependencias con Hilt").performScrollTo().performClick()
        compose.waitForIdle()
        // El título viene de ConfiguracionTareas (@Provides)
        compose.onNodeWithText("L11 · Mis tareas Duoc").assertExists()

        compose.onAllNodesWithText("Ver")[0].performScrollTo().performClick()
        compose.waitForIdle()
        compose.onNodeWithText("L11 · Tarea #1").assertExists()
        compose.onNodeWithText("Marcar como completada").performScrollTo().performClick()
        compose.waitForIdle()
        compose.onNodeWithText("Estado: completada ✔").assertExists()

        // Volvemos a la lista y entramos de nuevo: el estado viene del repositorio singleton
        compose.onNodeWithContentDescription("Volver").performClick()
        compose.waitForIdle()
        compose.onAllNodesWithText("Ver")[0].performScrollTo().performClick()
        compose.waitForIdle()
        compose.onNodeWithText("Estado: completada ✔").assertExists()
    }
}
