package com.example.android.culturalwaves.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.android.culturalwaves.R


//@Composable
//fun ArtworkImage(
//    imageUrl: String,
//    contentDescription: String?,
//    modifier: Modifier = Modifier
//) {
//    SubcomposeAsyncImage(
//        model = imageUrl,
//        contentDescription = contentDescription,
//        modifier = modifier
//            .fillMaxWidth() // Устанавливаем ширину изображения на максимальную доступную
//            .aspectRatio(1f), // Сохраняем соотношение сторон, можно адаптировать под нужные пропорции
//        contentScale = ContentScale.Crop,
//        loading = {
//            Box(modifier = Modifier.matchParentSize()) {
//                CircularProgressIndicator(
//                    modifier = Modifier.align(Alignment.Center),
//                    color = MaterialTheme.colorScheme.primary
//                )
//            }
//        },
//        error = {
//            Box(modifier = Modifier.matchParentSize()) {
//                Icon(
//                    imageVector = Icons.Filled.Close,
//                    contentDescription = "Ошибка загрузки",
//                    modifier = Modifier.align(Alignment.Center),
//                    tint = MaterialTheme.colorScheme.error
//                )
//                Text(
//                    "Ошибка загрузки",
//                    color = MaterialTheme.colorScheme.error,
//                    style = MaterialTheme.typography.bodyMedium,
//                    modifier = Modifier.align(Alignment.Center) // Правильно центрируем текст внутри Box
//                )
//            }
//        }
//    )
//}
//

@Composable
fun ArtworkImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    onError: () -> Unit // Добавлен параметр onError
) {
    var hasError by remember { mutableStateOf(false) }

    SubcomposeAsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        modifier = modifier
            .fillMaxWidth() // Устанавливаем ширину изображения на максимальную доступную
            .aspectRatio(1f), // Сохраняем соотношение сторон, можно адаптировать под нужные пропорции
        contentScale = ContentScale.Crop,
        loading = {
            Box(modifier = Modifier.matchParentSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        error = {
            hasError = true
            onError() // Вызов onError при ошибке загрузки изображения
            Box(modifier = Modifier.matchParentSize()) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Ошибка загрузки",
                    modifier = Modifier.align(Alignment.Center),
                    tint = MaterialTheme.colorScheme.error
                )
                Text(
                    "Ошибка загрузки",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.Center) // Правильно центрируем текст внутри Box
                )
            }
        }
    )
}


