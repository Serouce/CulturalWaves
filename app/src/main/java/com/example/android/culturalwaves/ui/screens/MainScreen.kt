package com.example.android.culturalwaves.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
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
import com.example.android.culturalwaves.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.android.culturalwaves.R
import com.example.android.culturalwaves.data.entities.FavoriteArtwork
import com.example.android.culturalwaves.ui.components.CategoryCard
import com.example.android.culturalwaves.utils.CategoryUtils
import com.example.android.culturalwaves.viewmodel.FavoriteViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


//@Composable
//fun MainScreen(onArtworkSelected: (Int) -> Unit) {
//    val mainViewModel: MainViewModel = koinViewModel()
//    val favoriteViewModel: FavoriteViewModel = koinViewModel()
//    val artworks: LazyPagingItems<Artwork> = mainViewModel.artworks.collectAsLazyPagingItems()
//    val artworkLoadStates by mainViewModel.artworkLoadStates.collectAsState()
//
//    Box(
//        modifier = Modifier
//            .background(MaterialTheme.colorScheme.background)
//            .fillMaxSize()
//    ) {
//        Scaffold { padding ->
//            LazyColumn(
//                contentPadding = PaddingValues(
//                    top = padding.calculateTopPadding() + WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
//                    bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding(),
//                    start = 16.dp,
//                    end = 16.dp
//                ),
//                verticalArrangement = Arrangement.spacedBy(16.dp),
//                modifier = Modifier.fillMaxSize()
//            ) {
//                items(artworks.itemCount) { index ->
//                    artworks[index]?.let { artwork ->
//                        val isFavorite = remember { mutableStateOf(false) }
//                        val showCard = artworkLoadStates[artwork.objectId ?: 0] ?: true
//
//                        LaunchedEffect(key1 = artwork.objectId) {
//                            isFavorite.value = favoriteViewModel.isFavorite(
//                                artwork.objectId ?: 0)
//                        }
//
//                        if (showCard) {
//                            Box(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .wrapContentSize(Alignment.Center)
//                            ) {
//                                CardTemplate(
//                                    imageUrl = artwork.imageUrl ?: "",
//                                    title = artwork.title ?: "No Title",
//                                    artist = artwork.people?.joinToString(separator = ", ")
//                                    { artist -> artist.name ?: "Unknown Artist" } ?: "Unknown Artist",
//                                    objectId = artwork.objectId ?: 0,
//                                    isFavorite = isFavorite.value,
//                                    onFavoriteClick = {
//                                        if (isFavorite.value) {
//                                            artwork.objectId?.let { id ->
//                                                favoriteViewModel.removeFavorite(FavoriteArtwork(
//                                                    id, artwork.title ?: "",
//                                                    artwork.imageUrl ?: "",
//                                                    "")
//                                                )
//                                            }
//                                        } else {
//                                            favoriteViewModel.addFavorite(FavoriteArtwork(
//                                                artwork.objectId ?: 0, artwork.title ?: "",
//                                                artwork.imageUrl ?: "", ""))
//                                        }
//                                        isFavorite.value = !isFavorite.value
//                                    },
//                                    onCardClick = onArtworkSelected,
//                                    onError = { mainViewModel.setArtworkLoadState(
//                                        artwork.objectId ?: 0, false) }, // Обновление состояния в ViewModel
//                                    cardWidth = 350.dp, // Увеличение ширины карточки
//                                    cardHeight = 350.dp // Увеличение высоты карточки
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}


@Composable
fun MainScreen(onArtworkSelected: (Int) -> Unit) {
    val mainViewModel: MainViewModel = koinViewModel()
    val favoriteViewModel: FavoriteViewModel = koinViewModel()
    val artworks: LazyPagingItems<Artwork> = mainViewModel.artworks.collectAsLazyPagingItems()
    val artworkLoadStates by mainViewModel.artworkLoadStates.collectAsState()
    val currentClassification by mainViewModel.currentClassification.collectAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize() // Убираем фоновый цвет
    ) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(0)
                        }
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .alpha(0.7f), // Устанавливаем полупрозрачность
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_arrow_upward), contentDescription = "Scroll to top")
                }
            }
        ) { padding ->
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(
                    top = padding.calculateTopPadding() + WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                    bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                // Добавляем горизонтальный список для категорий
                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        items(CategoryUtils.getCategories()) { (category, imageRes) ->
                            CategoryCard(
                                category = category,
                                imageRes = imageRes,
                                isSelected = currentClassification == category,
                                onClick = { mainViewModel.toggleClassification(category) }
                            )
                        }
                    }
                }

                items(artworks.itemCount) { index ->
                    artworks[index]?.let { artwork ->
                        val isFavorite = remember { mutableStateOf(false) }
                        val showCard = artworkLoadStates[artwork.objectId ?: 0] ?: true

                        LaunchedEffect(key1 = artwork.objectId) {
                            isFavorite.value = favoriteViewModel.isFavorite(artwork.objectId ?: 0)
                        }

                        if (showCard) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentSize(Alignment.Center)
                            ) {
                                CardTemplate(
                                    imageUrl = artwork.imageUrl ?: "",
                                    title = artwork.title ?: "No Title",
                                    artist = artwork.people?.joinToString(separator = ", ") { artist -> artist.name ?: "Unknown Artist" } ?: "Unknown Artist",
                                    objectId = artwork.objectId ?: 0,
                                    isFavorite = isFavorite.value,
                                    onFavoriteClick = {
                                        if (isFavorite.value) {
                                            artwork.objectId?.let { id ->
                                                favoriteViewModel.removeFavorite(
                                                    FavoriteArtwork(id, artwork.title ?: "", artwork.imageUrl ?: "", "")
                                                )
                                            }
                                        } else {
                                            favoriteViewModel.addFavorite(
                                                FavoriteArtwork(artwork.objectId ?: 0, artwork.title ?: "", artwork.imageUrl ?: "", "")
                                            )
                                        }
                                        isFavorite.value = !isFavorite.value
                                    },
                                    onCardClick = onArtworkSelected,
                                    onError = { mainViewModel.setArtworkLoadState(artwork.objectId ?: 0, false) },
                                    cardWidth = 300.dp,
                                    cardHeight = 301.dp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
