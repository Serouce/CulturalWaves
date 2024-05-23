package com.example.android.culturalwaves.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.android.culturalwaves.data.entities.ImageDetail

@Composable
fun ImageGallery(images: List<ImageDetail>?, onImageClick: (String) -> Unit) {
    images?.let {
        if (it.isNotEmpty()) {
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(it) { imageDetail ->
                    ImageThumbnail(imageDetail, onImageClick)
                }
            }
        }
    }
}