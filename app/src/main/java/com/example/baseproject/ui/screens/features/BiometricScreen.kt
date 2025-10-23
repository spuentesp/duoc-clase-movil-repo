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
            BiometricManager.BIOMETRIC_SUCCESS -> "Biometric authentication available"
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> "No biometric hardware"
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> "Biometric hardware unavailable"
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> "No biometric enrolled"
            else -> "Unknown status"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Biometric Authentication") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
                text = "Biometric Authentication",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Use fingerprint or face recognition for authentication.",
                style = MaterialTheme.typography.bodyMedium
            )

            Divider()

            // Status card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Device Status:",
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

            // Authenticate button
            Button(
                onClick = {
                    authenticateWithBiometric(
                        context as FragmentActivity,
                        onSuccess = { authResult = "✓ Authentication successful!" },
                        onError = { error -> authResult = "✗ Error: $error" },
                        onFailed = { authResult = "✗ Authentication failed" }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Authenticate with Biometric")
            }

            // Result display
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

            // Code example
            Text(
                text = "Example Code:",
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
// 1. Check biometric availability
val biometricManager = BiometricManager.from(context)
val canAuthenticate = biometricManager.canAuthenticate(
    BiometricManager.Authenticators.BIOMETRIC_STRONG
)

// 2. Create prompt info
val promptInfo = BiometricPrompt.PromptInfo.Builder()
    .setTitle("Biometric Authentication")
    .setSubtitle("Confirm your identity")
    .setNegativeButtonText("Cancel")
    .build()

// 3. Create callback
val callback = object : BiometricPrompt.AuthenticationCallback() {
    override fun onAuthenticationSucceeded(
        result: BiometricPrompt.AuthenticationResult
    ) {
        // Authentication successful
    }

    override fun onAuthenticationFailed() {
        // Authentication failed
    }

    override fun onAuthenticationError(
        errorCode: Int,
        errString: CharSequence
    ) {
        // Authentication error
    }
}

// 4. Show biometric prompt
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

            // Dependency note
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Required Dependency:",
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
