package com.example.baseproject.ui.screens.features

import android.content.Context
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
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

// Extension property for DataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalStorageScreen(onBackClick: () -> Unit = {}) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var inputText by remember { mutableStateOf("") }
    var storedValue by remember { mutableStateOf("") }

    // Load stored value on composition
    LaunchedEffect(Unit) {
        storedValue = readFromDataStore(context)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Local Storage") },
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
                text = "Local Storage (DataStore)",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Save and retrieve data locally using DataStore Preferences.",
                style = MaterialTheme.typography.bodyMedium
            )

            Divider()

            // Input field
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                label = { Text("Enter text to save") },
                modifier = Modifier.fillMaxWidth()
            )

            // Save button
            Button(
                onClick = {
                    scope.launch {
                        saveToDataStore(context, inputText)
                        storedValue = inputText
                        inputText = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save to DataStore")
            }

            // Display stored value
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Stored Value:",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = storedValue.ifEmpty { "No data saved yet" },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            // Read button
            Button(
                onClick = {
                    scope.launch {
                        storedValue = readFromDataStore(context)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Reload from DataStore")
            }

            // Clear button
            OutlinedButton(
                onClick = {
                    scope.launch {
                        clearDataStore(context)
                        storedValue = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Clear DataStore")
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
// 1. Create DataStore extension
val Context.dataStore: DataStore<Preferences> by
    preferencesDataStore(name = "settings")

// 2. Define preference key
val USER_NAME_KEY = stringPreferencesKey("user_name")

// 3. Write data
suspend fun saveData(context: Context, value: String) {
    context.dataStore.edit { preferences ->
        preferences[USER_NAME_KEY] = value
    }
}

// 4. Read data
suspend fun readData(context: Context): String {
    val preferences = context.dataStore.data.first()
    return preferences[USER_NAME_KEY] ?: "default"
}

// 5. Or use Flow for reactive updates
val userNameFlow: Flow<String> = context.dataStore.data
    .map { preferences ->
        preferences[USER_NAME_KEY] ?: "default"
    }

// 6. Clear data
suspend fun clearData(context: Context) {
    context.dataStore.edit { preferences ->
        preferences.clear()
    }
}
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
                        text = "implementation(\"androidx.datastore:datastore-preferences:1.0.0\")",
                        fontFamily = FontFamily.Monospace,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            }
        }
    }
}

// DataStore helper functions
private val SAMPLE_KEY = stringPreferencesKey("sample_text")

private suspend fun saveToDataStore(context: Context, value: String) {
    context.dataStore.edit { preferences ->
        preferences[SAMPLE_KEY] = value
    }
}

private suspend fun readFromDataStore(context: Context): String {
    val preferences = context.dataStore.data.first()
    return preferences[SAMPLE_KEY] ?: ""
}

private suspend fun clearDataStore(context: Context) {
    context.dataStore.edit { preferences ->
        preferences.clear()
    }
}
