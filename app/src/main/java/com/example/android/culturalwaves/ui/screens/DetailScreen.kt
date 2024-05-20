package com.example.android.culturalwaves.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.android.culturalwaves.data.entities.Artist
import com.example.android.culturalwaves.data.entities.ImageDetail
import com.example.android.culturalwaves.ui.components.ArtistSection
import com.example.android.culturalwaves.ui.components.ArtworkImage
import com.example.android.culturalwaves.ui.components.ImageGallery
import com.example.android.culturalwaves.ui.components.SimpleDetailSection
import com.example.android.culturalwaves.ui.components.ZoomableImageScreen
import com.example.android.culturalwaves.ui.navigation.Screen
import com.example.android.culturalwaves.viewmodel.ArtworkDetailViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DetailScreen(navController: NavHostController, objectId: Int) {
    val viewModel: ArtworkDetailViewModel = koinViewModel { parametersOf(objectId) }
    val artworkDetailState by viewModel.artworkDetails.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var selectedImageUrl by remember { mutableStateOf<String?>(null) }

    Scaffold { padding ->
        if (isLoading) {
            // Отображение индикатора загрузки, если данные загружаются
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else if (error != null) {
            // Отображение сообщения об ошибке, если произошла ошибка
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = error ?: "Unknown Error",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            // Отображение деталей произведения искусства, если данные успешно загружены
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
                                .clickable {
                                    selectedImageUrl = imageUrl
                                    showDialog = true
                                }
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
        }
    }

    selectedImageUrl?.let { imageUrl ->
        if (showDialog) {
            ZoomableImageScreen(imageUrl = imageUrl, onDismissRequest = { showDialog = false })
        }
    }
}


