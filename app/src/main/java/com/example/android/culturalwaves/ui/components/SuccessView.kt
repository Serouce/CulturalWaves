package com.example.android.culturalwaves.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.android.culturalwaves.data.entities.ArtworkDetailResponse

@Composable
fun SuccessView(
    padding: PaddingValues,
    artworkDetailState: ArtworkDetailResponse?,
    showDialog: Boolean,
    selectedImageUrl: String?,
    onImageClick: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    artworkDetailState?.let { artworkDetail ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(WindowInsets.navigationBars.asPaddingValues())
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Заголовок
            Text(
                text = artworkDetail.title ?: "Artwork Details",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.background)
            )

            // Изображение
            artworkDetail.imageUrl?.let { imageUrl ->
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = artworkDetail.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.5f)
                        .clip(RoundedCornerShape(12.dp))
                        .padding(horizontal = 16.dp)
                        .clickable { onImageClick(imageUrl) }
                )
            }

            // Описание и другие детали
            SimpleDetailSection("Description:", artworkDetail.description)
            SimpleDetailSection("Technique:", artworkDetail.technique)
            SimpleDetailSection("Provenance:", artworkDetail.provenance)
            SimpleDetailSection("Period:", artworkDetail.period)
            SimpleDetailSection("Dimensions:", artworkDetail.dimensions)

            ArtistSection(artworkDetail.people)
            ImageGallery(artworkDetail.images)
        }
    }

    selectedImageUrl?.let { imageUrl ->
        if (showDialog) {
            ZoomableImageScreen(imageUrl = imageUrl, onDismissRequest = onDismissRequest)
        }
    }
}