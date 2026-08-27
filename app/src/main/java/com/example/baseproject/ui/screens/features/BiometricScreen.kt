package com.example.baseproject.ui.screens.features

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BiometricScreen(onBackClick: () -> Unit = {}) {
    val context = LocalContext.current
    var authResult by remember { mutableStateOf("") }
    var biometricAvailability by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val biometricManager = BiometricManager.from(context)
        biometricAvailability = when (biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG or
            BiometricManager.Authenticators.DEVICE_CREDENTIAL
        )) {
            BiometricManager.BIOMETRIC_SUCCESS -> "Autenticación biométrica disponible"
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> "Sin hardware biométrico"
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> "Hardware biométrico no disponible"
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> "Sin datos biométricos registrados"
            else -> "Estado desconocido"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Autenticación Biométrica") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Autenticación Biométrica",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Usa huella digital o reconocimiento facial para autenticación.",
                style = MaterialTheme.typography.bodyMedium
            )

            Divider()

            // Tarjeta de estado
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Estado del Dispositivo:",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = biometricAvailability,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            // Botón autenticar
            Button(
                onClick = {
                    authenticateWithBiometric(
                        context as FragmentActivity,
                        onSuccess = { authResult = "✓ ¡Autenticación exitosa!" },
                        onError = { error -> authResult = "✗ Error: $error" },
                        onFailed = { authResult = "✗ Autenticación falló" }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Autenticar con Biométrica")
            }

            // Mostrar resultado
            if (authResult.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (authResult.startsWith("✓"))
                            MaterialTheme.colorScheme.primaryContainer
                        else
                            MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = authResult,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Divider()

            // Ejemplo de código
            Text(
                text = "Ejemplo de Código:",
                style = MaterialTheme.typography.titleMedium
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(
                    text = """
// 1. Verificar disponibilidad biométrica
val biometricManager = BiometricManager.from(context)
val canAuthenticate = biometricManager.canAuthenticate(
    BiometricManager.Authenticators.BIOMETRIC_STRONG
)

// 2. Crear información del prompt
val promptInfo = BiometricPrompt.PromptInfo.Builder()
    .setTitle("Biometric Authentication")
    .setSubtitle("Confirm your identity")
    .setNegativeButtonText("Cancel")
    .build()

// 3. Crear callback
val callback = object : BiometricPrompt.AuthenticationCallback() {
    override fun onAuthenticationSucceeded(
        result: BiometricPrompt.AuthenticationResult
    ) {
        // Autenticación exitosa
    }

    override fun onAuthenticationFailed() {
        // Autenticación falló
    }

    override fun onAuthenticationError(
        errorCode: Int,
        errString: CharSequence
    ) {
        // Error de autenticación
    }
}

// 4. Mostrar prompt biométrico
val biometricPrompt = BiometricPrompt(
    activity,
    executor,
    callback
)
biometricPrompt.authenticate(promptInfo)
                    """.trimIndent(),
                    modifier = Modifier.padding(16.dp),
                    fontFamily = FontFamily.Monospace,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Nota de dependencia
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Dependencia Requerida:",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Text(
                        text = "implementation(\"androidx.biometric:biometric:1.2.0-alpha05\")",
                        fontFamily = FontFamily.Monospace,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            }
        }
    }
}

private fun authenticateWithBiometric(
    activity: FragmentActivity,
    onSuccess: () -> Unit,
    onError: (String) -> Unit,
    onFailed: () -> Unit
) {
    val executor = ContextCompat.getMainExecutor(activity)

    val biometricPrompt = BiometricPrompt(
        activity,
        executor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onSuccess()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                onError(errString.toString())
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                onFailed()
            }
        }
    )

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Biometric Authentication")
        .setSubtitle("Confirm your identity to continue")
        .setNegativeButtonText("Cancel")
        .build()

    biometricPrompt.authenticate(promptInfo)
}
