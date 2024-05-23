package com.example.android.culturalwaves.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SimpleDetailSection(label: String, content: String?) {
    content?.let {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text(
                label,
                style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.outline))
            Text(it, style = MaterialTheme.typography.bodyMedium)
        }
    }
}