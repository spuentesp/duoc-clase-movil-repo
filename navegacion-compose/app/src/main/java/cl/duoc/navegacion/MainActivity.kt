package cl.duoc.navegacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cl.duoc.navegacion.ui.theme.TemaNavegacion
import dagger.hilt.android.AndroidEntryPoint

/**
 * Única Activity de la app ("single activity"): en Compose no se crea una Activity
 * por pantalla. Las pantallas son funciones @Composable y la navegación entre ellas
 * la maneja Navigation Compose.
 *
 * @AndroidEntryPoint es obligatorio para que las pantallas dentro de esta Activity
 * puedan pedir ViewModels a Hilt con hiltViewModel() (Lección 11).
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TemaNavegacion {
                IndiceLecciones()
            }
        }
    }
}
