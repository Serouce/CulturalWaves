package com.example.android.culturalwaves.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Velocity
import com.example.android.culturalwaves.R
import com.example.android.culturalwaves.data.entities.FavoriteArtwork
import com.example.android.culturalwaves.ui.components.AnimatedLogo
import com.example.android.culturalwaves.ui.components.CategoryCard
import com.example.android.culturalwaves.ui.components.ErrorView
import com.example.android.culturalwaves.ui.components.LoadingView
import com.example.android.culturalwaves.ui.components.TopAppBarTitle
import com.example.android.culturalwaves.utils.CategoryUtils
import com.example.android.culturalwaves.viewmodel.FavoriteViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.size
import androidx.compose.ui.res.painterResource

@Composable
fun MainScreen(onArtworkSelected: (Int) -> Unit) {
    val mainViewModel: MainViewModel = koinViewModel()
    val favoriteViewModel: FavoriteViewModel = koinViewModel()
    val artworks: LazyPagingItems<Artwork> = mainViewModel.artworks.collectAsLazyPagingItems()
    val artworkLoadStates by mainViewModel.artworkLoadStates.collectAsState()
    val currentClassification by mainViewModel.currentClassification.collectAsState()
    val isLoading by mainViewModel.isLoading.collectAsState()
    val error by mainViewModel.error.collectAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Состояние для управления видимостью кнопки
    var showButton by remember { mutableStateOf(false) }
    var previousIndex by remember { mutableStateOf(0) }

    // Следим за изменениями состояния прокрутки и обновляем видимость кнопки
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { index ->
                if (index < previousIndex) {
                    showButton = true
                } else {
                    showButton = false
                }
                previousIndex = index
            }
    }

    Scaffold(
        floatingActionButton = {
            if (showButton) {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            listState.scrollToItem(0) // Без анимации
                        }
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .size(48.dp) // Уменьшенный размер кнопки
                        .alpha(0.8f),
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_arrow_upward), contentDescription = "Scroll to top")
                }
            }
        }
    ) { padding ->
        SwipeRefresh(
            state = rememberSwipeRefreshState(isLoading),
            onRefresh = { mainViewModel.refreshArtworks() }
        ) {
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(
                    top = 0.dp,
                    bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            coroutineScope.launch {
                                listState.scrollBy(dragAmount.y * 0.5f) // Ограничение скорости скроллинга
                            }
                        }
                    }
            ) {
                item {
                    TopAppBarTitle(title = "Artworks")
                }

                item {
                    Text(
                        text = "Choose a Category",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

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

                if (error != null) {
                    item {
                        ErrorView(errorMessage = error)
                    }
                } else {
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
                                                        FavoriteArtwork(
                                                            id,
                                                            artwork.title ?: "",
                                                            artwork.imageUrl ?: "",
                                                            "",
                                                            System.currentTimeMillis() // Установить текущее время как дату добавления
                                                        )
                                                    )
                                                }
                                            } else {
                                                favoriteViewModel.addFavorite(
                                                    FavoriteArtwork(
                                                        artwork.objectId ?: 0,
                                                        artwork.title ?: "",
                                                        artwork.imageUrl ?: "",
                                                        "",
                                                        System.currentTimeMillis() // Установить текущее время как дату добавления
                                                    )
                                                )
                                            }
                                            isFavorite.value = !isFavorite.value
                                        },
                                        onCardClick = onArtworkSelected,
                                        onError = { mainViewModel.setArtworkLoadState(artwork.objectId ?: 0, false) },
                                        cardWidth = 350.dp,
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
}







