package cl.duoc.navegacion.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AmarilloDuoc = Color(0xFFFFB800)
private val AzulOscuro = Color(0xFF1B2A41)

private val ColoresClaros = lightColorScheme(
    primary = AzulOscuro,
    secondary = AmarilloDuoc,
    secondaryContainer = Color(0xFFFFF1CC)
)

private val ColoresOscuros = darkColorScheme(
    primary = AmarilloDuoc,
    secondary = AmarilloDuoc,
    secondaryContainer = Color(0xFF4A3A00)
)

@Composable
fun TemaNavegacion(contenido: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) ColoresOscuros else ColoresClaros,
        content = contenido
    )
}
