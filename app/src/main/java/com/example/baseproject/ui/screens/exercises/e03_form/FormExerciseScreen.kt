package com.example.baseproject.ui.screens.exercises.e03_form

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.baseproject.ui.screens.exercises.CodeBlock
import com.example.baseproject.ui.screens.exercises.MiniAppScaffold

private const val ENUNCIADO = """
    Crea un formulario con dos campos: email y password. Muestra un
    mensaje de error bajo cada campo si:
      - email no contiene '@'
      - password tiene menos de 6 caracteres
    Habilita un botón 'Enviar' solo cuando ambos campos son válidos.

    Conceptos: TextField, validación, isError, enabled.
"""

private val SOLUCION = """
    @Composable
    fun LoginForm() {
        var email by remember { mutableStateOf("") }
        var pass by remember { mutableStateOf("") }
        val emailError = email.isNotEmpty() && !email.contains('@')
        val passError = pass.isNotEmpty() && pass.length < 6
        val puedeEnviar = email.contains('@') && pass.length >= 6
        Column {
            OutlinedTextField(
                value = email, onValueChange = { email = it },
                label = { Text("Email") }, isError = emailError,
                supportingText = { if (emailError) Text("Email inválido") },
            )
            OutlinedTextField(
                value = pass, onValueChange = { pass = it },
                label = { Text("Password") }, isError = passError,
                supportingText = { if (passError) Text("Mínimo 6 caracteres") },
            )
            Button(onClick = { /* enviar */ }, enabled = puedeEnviar) {
                Text("Enviar")
            }
        }
    }
""".trimIndent()

@Composable
fun FormExerciseScreen() {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    val emailError = email.isNotEmpty() && !email.contains('@')
    val passError = pass.isNotEmpty() && pass.length < 6
    val puedeEnviar = email.contains('@') && pass.length >= 6

    MiniAppScaffold(
        titulo = "3. Formulario",
        enunciado = ENUNCIADO,
        contenidoSolucion = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Demostración:", fontWeight = FontWeight.SemiBold)
                OutlinedTextField(
                    value = email, onValueChange = { email = it },
                    label = { Text("Email") }, isError = emailError,
                    supportingText = { if (emailError) Text("Email inválido") },
                )
                OutlinedTextField(
                    value = pass, onValueChange = { pass = it },
                    label = { Text("Password") }, isError = passError,
                    supportingText = { if (passError) Text("Mínimo 6 caracteres") },
                )
                Button(onClick = {}, enabled = puedeEnviar) { Text("Enviar") }
                Text("Código:", fontWeight = FontWeight.SemiBold)
                CodeBlock(codigo = SOLUCION)
            }
        },
    )
}
