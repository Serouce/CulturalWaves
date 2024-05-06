package com.example.android.culturalwaves.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.android.culturalwaves.R

@Composable
fun ArtworkImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    SubcomposeAsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        modifier = modifier
            .size(width = 308.dp, height = 247.dp),// Задаем размеры изображения
        contentScale = ContentScale.Crop, // Обрезаем изображение, чтобы оно заполнило контейнер
        loading = {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        },
        error = {
            Box(modifier = Modifier.fillMaxSize()) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Ошибка загрузки",
                    modifier = Modifier.align(Alignment.Center)
                )
                Text("Ошибка загрузки", modifier = Modifier.align(Alignment.Center))
            }
        }
    )
}
