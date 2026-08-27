package com.example.baseproject.ui.screens.exercises.e04_navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.baseproject.ui.screens.exercises.CodeBlock
import com.example.baseproject.ui.screens.exercises.MiniAppScaffold

private const val ENUNCIADO = """
    Crea una navegación entre dos pantallas:
      - ListaScreen: muestra una LazyColumn de 5 ítems ficticios.
      - DetalleScreen: recibe el id del ítem como argumento y lo muestra.

    Conceptos: NavHost, composable(), navArgument, NavType.IntType.
"""

private val SOLUCION = """
    @Composable
    fun EjemploNavegacion() {
        val nav = rememberNavController()
        NavHost(nav, startDestination = "lista") {
            composable("lista") {
                LazyColumn {
                    items(5) { id ->
                        Button(onClick = { nav.navigate("detalle/${'$'}id") }) {
                            Text("Item ${'$'}id")
                        }
                    }
                }
            }
            composable(
                "detalle/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { entry ->
                val id = entry.arguments?.getInt("id") ?: 0
                Text("Detalle del item ${'$'}id")
            }
        }
    }
""".trimIndent()

@Composable
fun NavigationExerciseScreen() {
    MiniAppScaffold(
        titulo = "4. Navegación",
        enunciado = ENUNCIADO,
        contenidoSolucion = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Demostración interna:", fontWeight = FontWeight.SemiBold)
                DemoNavInterno()
                Text("Código:", fontWeight = FontWeight.SemiBold)
                CodeBlock(codigo = SOLUCION)
            }
        },
    )
}

@Composable
private fun DemoNavInterno() {
    val nav = rememberNavController()
    NavHost(nav, startDestination = "lista") {
        composable("lista") {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                items(5) { id ->
                    Button(onClick = { nav.navigate("detalle/$id") }) { Text("Item $id") }
                }
            }
        }
        composable(
            "detalle/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType }),
        ) { entry ->
            val id = entry.arguments?.getInt("id") ?: 0
            Column {
                Text("Detalle del item $id", fontWeight = FontWeight.Bold)
                Button(onClick = { nav.popBackStack() }) { Text("Volver") }
            }
        }
    }
}
