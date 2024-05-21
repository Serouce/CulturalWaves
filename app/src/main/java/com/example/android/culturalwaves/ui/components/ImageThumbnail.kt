package com.example.android.culturalwaves.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.android.culturalwaves.data.entities.ImageDetail

//@Composable
//fun ImageThumbnail(imageDetail: ImageDetail) {
//    SubcomposeAsyncImage(
//        model = imageDetail.baseImageUrl,
//        contentDescription = imageDetail.description,
//        modifier = Modifier
//            .size(100.dp)
//            .clip(RoundedCornerShape(8.dp))
//            .border(1.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp)),
//        contentScale = ContentScale.Crop,
//        loading = {
//            Box(modifier = Modifier.matchParentSize()) {
//                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
//            }
//        },
//        error = {
//            Icon(
//                Icons.Default.Close,
//                contentDescription = "Load error",
//                modifier = Modifier.matchParentSize()
//            )
//        }
//    )
//}

@Composable
fun ImageThumbnail(imageDetail: ImageDetail, onImageClick: (String) -> Unit) {
    val imageUrl = imageDetail.baseImageUrl?.toString() ?: ""

    SubcomposeAsyncImage(
        model = imageUrl,
        contentDescription = imageDetail.description,
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp))
            .clickable { onImageClick(imageUrl) }, // Добавляем обработчик клика
        contentScale = ContentScale.Crop,
        loading = {
            Box(modifier = Modifier.matchParentSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        },
        error = {
            Icon(
                Icons.Default.Close,
                contentDescription = "Load error",
                modifier = Modifier.matchParentSize()
            )
        }
    )
}
