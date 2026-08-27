package com.example.baseproject.ui.screens.textfields

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.baseproject.ui.screens.buttons.ComponentSection
import com.example.baseproject.ui.theme.BaseProjectTheme
import androidx.compose.material.icons.outlined.VisibilityOff

/**
 * Pantalla que muestra todos los tipos de campos de texto en Material Design 3.
 * Incluye: TextField, OutlinedTextField, y sus diferentes configuraciones
 * con iconos, labels, placeholders, y validaciones.
 *
 * @param onBackClick Callback para volver a la pantalla anterior
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldsScreen(
    onBackClick: () -> Unit
) {
    // Estados para los diferentes text fields
    var simpleText by remember { mutableStateOf("") }
    var outlinedText by remember { mutableStateOf("") }
    var labelText by remember { mutableStateOf("") }
    var placeholderText by remember { mutableStateOf("") }
    var leadingIconText by remember { mutableStateOf("") }
    var trailingIconText by remember { mutableStateOf("") }
    var errorText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var multilineText by remember { mutableStateOf("") }
    var emailText by remember { mutableStateOf("") }
    var numberText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Text Fields") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Sección: TextField básico
            item {
                ComponentSection(title = "TextField - Filled") {
                    TextField(
                        value = simpleText,
                        onValueChange = { simpleText = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("TextField Simple") }
                    )

                    TextField(
                        value = labelText,
                        onValueChange = { labelText = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Con Label") },
                        supportingText = { Text("Texto de ayuda debajo del campo") }
                    )

                    TextField(
                        value = placeholderText,
                        onValueChange = { placeholderText = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Con Placeholder") },
                        placeholder = { Text("Escribe algo aquí...") }
                    )
                }
            }

            // Sección: OutlinedTextField
            item {
                ComponentSection(title = "OutlinedTextField") {
                    OutlinedTextField(
                        value = outlinedText,
                        onValueChange = { outlinedText = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("OutlinedTextField") },
                        supportingText = { Text("Variante con borde exterior") }
                    )

                    OutlinedTextField(
                        value = leadingIconText,
                        onValueChange = { leadingIconText = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Con Ícono Leading") },
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = "Usuario")
                        }
                    )

                    OutlinedTextField(
                        value = trailingIconText,
                        onValueChange = { trailingIconText = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Con Ícono Trailing") },
                        trailingIcon = {
                            IconButton(onClick = { trailingIconText = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Limpiar")
                            }
                        }
                    )
                }
            }

            // Sección: Estados y validación
            item {
                ComponentSection(title = "Validación y Estados") {
                    val isError = errorText.isNotEmpty() && errorText.length < 3

                    OutlinedTextField(
                        value = errorText,
                        onValueChange = { errorText = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Campo con Validación") },
                        isError = isError,
                        supportingText = {
                            if (isError) {
                                Text("Debe tener al menos 3 caracteres")
                            } else {
                                Text("Mínimo 3 caracteres")
                            }
                        },
                        trailingIcon = {
                            if (isError) {
                                Icon(
                                    Icons.Default.Error,
                                    contentDescription = "Error",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    )

                    OutlinedTextField(
                        value = "Campo deshabilitado",
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Deshabilitado") },
                        enabled = false
                    )

                    OutlinedTextField(
                        value = "Campo de solo lectura",
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Solo Lectura") },
                        readOnly = true
                    )
                }
            }

            // Sección: Password field
            item {
                ComponentSection(title = "Password TextField") {
                    OutlinedTextField(
                        value = passwordText,
                        onValueChange = { passwordText = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Contraseña") },
                        visualTransformation = if (passwordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = "Contraseña")
                        },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    if (passwordVisible)
                                        Icons.Default.Visibility
                                    else
                                        Icons.Outlined.VisibilityOff,
                                    contentDescription = if (passwordVisible)
                                        "Ocultar contraseña"
                                    else
                                        "Mostrar contraseña"
                                )
                            }
                        }
                    )
                }
            }

            // Sección: Tipos de teclado
            item {
                ComponentSection(title = "Tipos de Teclado") {
                    OutlinedTextField(
                        value = emailText,
                        onValueChange = { emailText = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Email") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        leadingIcon = {
                            Icon(Icons.Default.Email, contentDescription = "Email")
                        }
                    )

                    OutlinedTextField(
                        value = numberText,
                        onValueChange = { numberText = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Número") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        leadingIcon = {
                            Icon(Icons.Default.Phone, contentDescription = "Teléfono")
                        }
                    )
                }
            }

            // Sección: Multiline
            item {
                ComponentSection(title = "TextField Multilinea") {
                    OutlinedTextField(
                        value = multilineText,
                        onValueChange = { multilineText = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Texto Largo") },
                        placeholder = { Text("Escribe un texto largo aquí...") },
                        minLines = 4,
                        maxLines = 6,
                        supportingText = {
                            Text("${multilineText.length} caracteres")
                        }
                    )
                }
            }

            // Sección: Ejemplo de formulario
            item {
                ComponentSection(title = "Ejemplo de Formulario") {
                    var nombre by remember { mutableStateOf("") }
                    var apellido by remember { mutableStateOf("") }
                    var correo by remember { mutableStateOf("") }

                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Nombre") },
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null)
                        }
                    )

                    OutlinedTextField(
                        value = apellido,
                        onValueChange = { apellido = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Apellido") },
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null)
                        }
                    )

                    OutlinedTextField(
                        value = correo,
                        onValueChange = { correo = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Correo Electrónico") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        leadingIcon = {
                            Icon(Icons.Default.Email, contentDescription = null)
                        }
                    )

                    Button(
                        onClick = { /* Acción de envío */ },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = nombre.isNotBlank() && apellido.isNotBlank() && correo.isNotBlank()
                    ) {
                        Text("Enviar Formulario")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldsScreenPreview() {
    BaseProjectTheme {
        TextFieldsScreen(onBackClick = {})
    }
}
