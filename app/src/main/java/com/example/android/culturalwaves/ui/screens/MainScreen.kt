package com.example.android.culturalwaves.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.android.culturalwaves.model.Artwork
import com.example.android.culturalwaves.ui.components.ArtworkImage
import com.example.android.culturalwaves.ui.components.CardTemplate
import com.example.android.culturalwaves.viewmodel.ArtViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(onArtworkSelected: (Int) -> Unit) {
    // Используем koinViewModel для создания или получения уже существующей ViewModel
    val artViewModel: ArtViewModel = koinViewModel()

    // Собираем арт-объекты в LazyPagingItems для пагинации
    val artworks: LazyPagingItems<Artwork> = artViewModel.artworks.collectAsLazyPagingItems()

    MaterialTheme {
        Scaffold { _ ->
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 180.dp),  // Адаптивные колонки в зависимости от размера экрана
                contentPadding = PaddingValues(16.dp),  // Общий отступ вокруг сетки
                verticalArrangement = Arrangement.spacedBy(16.dp),  // Расстояние между элементами по вертикали
                horizontalArrangement = Arrangement.spacedBy(16.dp)  // Расстояние между элементами по горизонтали
            ) {
                items(artworks.itemCount) { index ->
                    artworks[index]?.let { artwork ->
                        CardTemplate(
                            imageUrl = artwork.imageUrl ?: "",
                            title = artwork.title ?: "Нет названия",
                            artist = artwork.people?.joinToString(separator = ", ") { artist -> artist.name ?: "Неизвестный художник" } ?: "Неизвестный художник",
                            objectId = artwork.objectId ?: 0,
                            isFavorite = false,  // Значение можно обновить, используя состояние из ViewModel
                            onFavoriteClick = { /* TODO: Добавить обработку добавления в избранное */ },
                            onCardClick = onArtworkSelected
                        )
                    }
                }
            }
        }
    }
}


