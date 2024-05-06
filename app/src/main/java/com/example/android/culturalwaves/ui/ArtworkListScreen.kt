package com.example.android.culturalwaves.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.android.culturalwaves.model.Artwork
import com.example.android.culturalwaves.ui.components.CardTemplate
import com.example.android.culturalwaves.viewmodel.ArtViewModel

@Composable
fun ArtworkListScreen(artViewModel: ArtViewModel) {
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
                            isFavorite = false,  // Значение можно обновить, используя состояние из ViewModel
                            onFavoriteClick = { /* TODO: Добавить обработку добавления в избранное */ },
                            onCardClick = { /* TODO: Добавить обработку навигации на детальный экран */ }
                        )
                    }
                }
            }
        }
    }
}


