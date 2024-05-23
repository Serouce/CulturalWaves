package com.example.android.culturalwaves.ui.components

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.android.culturalwaves.data.entities.Artwork
import com.example.android.culturalwaves.data.entities.FavoriteArtwork
import com.example.android.culturalwaves.utils.CategoryUtils
import com.example.android.culturalwaves.viewmodel.FavoriteViewModel
import com.example.android.culturalwaves.viewmodel.MainViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainContent(
    padding: PaddingValues,
    isLoading: Boolean,
    artworks: LazyPagingItems<Artwork>,
    artworkLoadStates: Map<Int, Boolean>,
    currentClassification: String?,
    error: String?,
    onArtworkSelected: (Int) -> Unit,
    mainViewModel: MainViewModel,
    favoriteViewModel: FavoriteViewModel,
    listState: LazyListState,
    coroutineScope: CoroutineScope
) {
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
                        coroutineScope.launch { listState.scrollBy(dragAmount.y * 0.5f) }
                    }
                }
        ) {
            item { TopAppBarTitle(title = "Artworks") }
            item {
                Text(
                text = "Choose a Category",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)) }
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(vertical = 8.dp)) {
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
                item { ErrorView(errorMessage = error) }
            } else {
                items(artworks.itemCount) { index ->
                    artworks[index]?.let { artwork ->
                        val isFavorite = remember { mutableStateOf(false) }
                        val showCard = artworkLoadStates[artwork.objectId ?: 0] ?: true

                        LaunchedEffect(key1 = artwork.objectId) {
                            isFavorite.value = favoriteViewModel
                                .isFavorite(artwork.objectId ?: 0)
                        }

                        if (showCard) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentSize(Alignment.Center)) {
                                CardTemplate(
                                    imageUrl = artwork.imageUrl ?: "",
                                    title = artwork.title ?: "No Title",
                                    artist = artwork.people?.joinToString(separator = ", ")
                                    { artist -> artist.name ?: "Unknown Artist" } ?: "Unknown Artist",
                                    objectId = artwork.objectId ?: 0,
                                    isFavorite = isFavorite.value,
                                    onFavoriteClick = {
                                        if (isFavorite.value) {
                                            artwork.objectId?.let { id ->
                                                favoriteViewModel.removeFavorite(
                                                    FavoriteArtwork(id, artwork.title ?: "",
                                                        artwork.imageUrl ?: "",
                                                        "",
                                                        System.currentTimeMillis())
                                                )
                                            }
                                        } else {
                                            favoriteViewModel.addFavorite(
                                                FavoriteArtwork(artwork.objectId ?: 0,
                                                    artwork.title ?: "",
                                                    artwork.imageUrl ?: "",
                                                    "",
                                                    System.currentTimeMillis())
                                            )
                                        }
                                        isFavorite.value = !isFavorite.value
                                    },
                                    onCardClick = onArtworkSelected,
                                    onError = { mainViewModel.setArtworkLoadState(
                                        artwork.objectId ?: 0, false
                                    ) },
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