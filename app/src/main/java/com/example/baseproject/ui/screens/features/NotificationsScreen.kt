package com.example.baseproject.ui.screens.features

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(onBackClick: () -> Unit = {}) {
    val context = LocalContext.current
    var hasNotificationPermission by remember {
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            } else {
                true
            }
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasNotificationPermission = isGranted
    }

    LaunchedEffect(Unit) {
        createNotificationChannel(context)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notificaciones") },
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
                text = "Notificaciones Locales",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Envía notificaciones push locales al usuario.",
                style = MaterialTheme.typography.bodyMedium
            )

            Divider()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !hasNotificationPermission) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Se requiere permiso de notificación (Android 13+)")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = {
                            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }) {
                            Text("Otorgar Permiso")
                        }
                    }
                }
            }

            Button(
                onClick = { sendSimpleNotification(context) },
                modifier = Modifier.fillMaxWidth(),
                enabled = hasNotificationPermission
            ) {
                Text("Enviar Notificación Simple")
            }

            Button(
                onClick = { sendNotificationWithAction(context) },
                modifier = Modifier.fillMaxWidth(),
                enabled = hasNotificationPermission
            ) {
                Text("Enviar Notificación con Acción")
            }

            Divider()

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
// 1. Crear canal de notificación (Android 8.0+)
val channelId = "my_channel"
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    val channel = NotificationChannel(
        channelId,
        "Channel Name",
        NotificationManager.IMPORTANCE_DEFAULT
    )
    val notificationManager = context.getSystemService(
        Context.NOTIFICATION_SERVICE
    ) as NotificationManager
    notificationManager.createNotificationChannel(channel)
}

// 2. Construir notificación
val notification = NotificationCompat.Builder(context, channelId)
    .setSmallIcon(R.drawable.ic_launcher_foreground)
    .setContentTitle("Notification Title")
    .setContentText("This is the notification content")
    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    .build()

// 3. Mostrar notificación
NotificationManagerCompat.from(context)
    .notify(notificationId, notification)
                    """.trimIndent(),
                    modifier = Modifier.padding(16.dp),
                    fontFamily = FontFamily.Monospace,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Required Permission (Android 13+):",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = "<uses-permission android:name=\"android.permission.POST_NOTIFICATIONS\" />",
                        fontFamily = FontFamily.Monospace,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

private const val CHANNEL_ID = "base_project_channel"

private fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Base Project Notifications"
        val descriptionText = "Notifications from Base Project"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

private fun sendSimpleNotification(context: Context) {
    val notification = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle("Simple Notification")
        .setContentText("This is a simple notification from the app!")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()

    NotificationManagerCompat.from(context).notify(1, notification)
}

private fun sendNotificationWithAction(context: Context) {
    val notification = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle("Notification with Action")
        .setContentText("This notification has an action button!")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .addAction(
            android.R.drawable.ic_menu_view,
            "Action",
            null
        )
        .build()

    NotificationManagerCompat.from(context).notify(2, notification)
}
