package com.example.android.culturalwaves.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.android.culturalwaves.data.entities.Artist

@Composable
fun ArtistSection(people: List<Artist>?) {
    people?.let {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Artists and Roles:", style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.outline))
            it.forEach { person ->
                Text(
                    "${person.name ?: "Unknown"} - ${person.role ?: "Unknown role"}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}