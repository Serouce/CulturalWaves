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
import com.example.android.culturalwaves.data.entities.Artwork
import com.example.android.culturalwaves.ui.components.CardTemplate
import com.example.android.culturalwaves.viewmodel.ArtViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.android.culturalwaves.data.entities.FavoriteArtwork
import com.example.android.culturalwaves.viewmodel.FavoriteViewModel


//@Composable
//fun MainScreen(onArtworkSelected: (Int) -> Unit) {
//    val artViewModel: ArtViewModel = koinViewModel()
//    val favoriteViewModel: FavoriteViewModel = koinViewModel()
//    val artworks: LazyPagingItems<Artwork> = artViewModel.artworks.collectAsLazyPagingItems()
//
//    MaterialTheme {
//        Scaffold { padding ->
//            LazyVerticalGrid(
//                columns = GridCells.Adaptive(minSize = 180.dp),
//                contentPadding = PaddingValues(
//                    top = padding.calculateTopPadding() + WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
//                    bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding(),
//                    start = 16.dp,
//                    end = 16.dp
//                ),
//                verticalArrangement = Arrangement.spacedBy(16.dp),
//                horizontalArrangement = Arrangement.spacedBy(16.dp)
//            ) {
//                items(artworks.itemCount) { index ->
//                    artworks[index]?.let { artwork ->
//                        val isFavorite = remember { mutableStateOf(false) }
//
//                        LaunchedEffect(key1 = artwork.objectId) {
//                            isFavorite.value = favoriteViewModel.isFavorite(artwork.objectId ?: 0)
//                        }
//
//                        CardTemplate(
//                            imageUrl = artwork.imageUrl ?: "",
//                            title = artwork.title ?: "No Title",
//                            artist = artwork.people?.joinToString(separator = ", ") { artist -> artist.name ?: "Unknown Artist" } ?: "Unknown Artist",
//                            objectId = artwork.objectId ?: 0,
//                            isFavorite = isFavorite.value,
//                            onFavoriteClick = {
//                                if (isFavorite.value) {
//                                    artwork.objectId?.let { id ->
//                                        favoriteViewModel.removeFavorite(FavoriteArtwork(id, artwork.title ?: "", artwork.imageUrl ?: "", ""))
//                                    }
//                                } else {
//                                    favoriteViewModel.addFavorite(FavoriteArtwork(artwork.objectId ?: 0, artwork.title ?: "", artwork.imageUrl ?: "", ""))
//                                }
//                                isFavorite.value = !isFavorite.value
//                            },
//                            onCardClick = onArtworkSelected,
//                            onError =
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//
//

@Composable
fun MainScreen(onArtworkSelected: (Int) -> Unit) {
    val artViewModel: ArtViewModel = koinViewModel()
    val favoriteViewModel: FavoriteViewModel = koinViewModel()
    val artworks: LazyPagingItems<Artwork> = artViewModel.artworks.collectAsLazyPagingItems()

    MaterialTheme {
        Scaffold { padding ->
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 180.dp),
                contentPadding = PaddingValues(
                    top = padding.calculateTopPadding() + WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                    bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(artworks.itemCount) { index ->
                    artworks[index]?.let { artwork ->
                        val isFavorite = remember { mutableStateOf(false) }
                        var showCard by remember { mutableStateOf(true) } // Добавлен флаг для управления видимостью карточки

                        LaunchedEffect(key1 = artwork.objectId) {
                            isFavorite.value = favoriteViewModel.isFavorite(artwork.objectId ?: 0)
                        }

                        if (showCard) { // Проверяем флаг перед отображением карточки
                            CardTemplate(
                                imageUrl = artwork.imageUrl ?: "",
                                title = artwork.title ?: "No Title",
                                artist = artwork.people?.joinToString(separator = ", ") { artist -> artist.name ?: "Unknown Artist" } ?: "Unknown Artist",
                                objectId = artwork.objectId ?: 0,
                                isFavorite = isFavorite.value,
                                onFavoriteClick = {
                                    if (isFavorite.value) {
                                    artwork.objectId?.let { id ->
                                        favoriteViewModel.removeFavorite(FavoriteArtwork(id, artwork.title ?: "", artwork.imageUrl ?: "", ""))
                                    }
                                } else {
                                    favoriteViewModel.addFavorite(FavoriteArtwork(artwork.objectId ?: 0, artwork.title ?: "", artwork.imageUrl ?: "", ""))
                                }
                                isFavorite.value = !isFavorite.value
                                },
                                onCardClick = onArtworkSelected,
                                onError = { showCard = false } // Скрываем карточку при ошибке загрузки изображения
                            )
                        }
                    }
                }
            }
        }
    }
}



