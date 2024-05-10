package com.example.android.culturalwaves.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.android.culturalwaves.model.Artwork
import com.example.android.culturalwaves.ui.components.CardTemplate
import com.example.android.culturalwaves.viewmodel.ArtViewModel

@Composable
fun MainScreen(artViewModel: ArtViewModel, onArtworkSelected: (Int) -> Unit) {
    val artworks: LazyPagingItems<Artwork> = artViewModel.artworks.collectAsLazyPagingItems()

    // Определяем стиль для темы Material 3
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
                            artist = artwork.people?.joinToString { artist -> artist.name ?: "Неизвестный художник" } ?: "Неизвестный художник",
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

