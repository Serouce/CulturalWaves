package com.example.android.culturalwaves.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable

// CategoryCard.kt
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale


@Composable
fun CategoryCard(
    category: String,
    imageRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.inverseOnSurface else MaterialTheme.colorScheme.surfaceVariant
    val elevation by animateDpAsState(if (isSelected) 12.dp else 4.dp, label = "")
    val scale by animateFloatAsState(if (isSelected) 1.15f else 1f, label = "")

    Card(
        modifier = Modifier
            .padding(4.dp) // Уменьшение отступа
            .clickable(onClick = onClick)
            .size(120.dp) // Уменьшение размера
            .scale(scale)
            .clip(RoundedCornerShape(12.dp))
            .shadow(elevation, RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Box {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = category,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp) // Уменьшение высоты изображения
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.4f)), // Уменьшение интенсивности градиента
                                startY = 50f
                            )
                        )
                )
            }
            Text(
                text = category,
                style = MaterialTheme.typography.bodySmall, // Уменьшение размера текста
                color = Color.White,
                modifier = Modifier.padding(4.dp) // Уменьшение отступа
            )
        }
    }
}

