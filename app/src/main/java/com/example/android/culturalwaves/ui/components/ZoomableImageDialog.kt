package com.example.android.culturalwaves.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage

//@Composable
//fun ZoomableImageDialog(
//    imageUrl: String,
//    onDismissRequest: () -> Unit
//) {
//    var scale by remember { mutableFloatStateOf(1f) }
//    var offsetX by remember { mutableFloatStateOf(0f) }
//    var offsetY by remember { mutableFloatStateOf(0f) }
//
//    Dialog(onDismissRequest = onDismissRequest) {
//        Box(modifier = Modifier.fillMaxSize()) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.Black)
//                    .pointerInput(Unit) {
//                        detectTransformGestures { _, pan, zoom, _ ->
//                            scale *= zoom
//                            offsetX += pan.x
//                            offsetY += pan.y
//                        }
//                    }
//                    .graphicsLayer(
//                        scaleX = scale,
//                        scaleY = scale,
//                        translationX = offsetX,
//                        translationY = offsetY
//                    )
//            ) {
//                AsyncImage(
//                    model = imageUrl,
//                    contentDescription = null,
//                    contentScale = ContentScale.Fit,
//                    modifier = Modifier.fillMaxSize()
//                )
//            }
//            IconButton(
//                onClick = { onDismissRequest() },
//                modifier = Modifier
//                    .align(Alignment.TopStart)
//                    .padding(16.dp)
//                    .background(Color.Black.copy(alpha = 0.5f), CircleShape)
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Close,
//                    contentDescription = "Close",
//                    tint = Color.White
//                )
//            }
//        }
//    }
//}


@Composable
fun ZoomableImageScreen(
    imageUrl: String,
    onDismissRequest: () -> Unit
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale *= zoom
                    offsetX += pan.x
                    offsetY += pan.y
                }
            }
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                translationX = offsetX,
                translationY = offsetY
            )
            .clickable { onDismissRequest() } // Закрытие по клику на изображение
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}