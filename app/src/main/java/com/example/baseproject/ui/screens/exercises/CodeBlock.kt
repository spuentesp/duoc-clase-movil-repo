package com.example.baseproject.ui.screens.exercises

import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape

/**
 * Pinta un bloque de código con scroll vertical y fondo oscuro.
 * Usado para mostrar la solución de cada mini-app.
 */
@Composable
fun CodeBlock(codigo: String, modifier: Modifier = Modifier) {
    Text(
        text = codigo,
        style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(12.dp),
    )
}
