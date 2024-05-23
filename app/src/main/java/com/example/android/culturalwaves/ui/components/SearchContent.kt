package com.example.android.culturalwaves.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.android.culturalwaves.data.entities.Artwork
import com.example.android.culturalwaves.data.entities.FavoriteArtwork
import com.example.android.culturalwaves.viewmodel.FavoriteViewModel
import com.example.android.culturalwaves.viewmodel.SearchViewModel

@Composable
fun SearchContent(
    paddingValues: PaddingValues,
    isLoading: Boolean,
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onSearchTriggered: () -> Unit,
    suggestions: List<String>,
    error: String?,
    searchResults: LazyPagingItems<Artwork>,
    artworkLoadStates: Map<Int, Boolean>,
    onSuggestionClicked: (String) -> Unit,
    buttonColor: Color,
    onArtworkSelected: (Int) -> Unit,
    favoriteViewModel: FavoriteViewModel,
    searchViewModel: SearchViewModel
) {
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center) {
            AnimatedLogo()
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding()
                        + WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding(),
                start = 16.dp,
                end = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Text(
                    text = "Search Artworks",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
            item {
                SearchHeader()
            }
            item {
                SearchInputField(
                    searchText = searchText,
                    onSearchTextChanged = onSearchTextChanged,
                    onSearchTriggered = onSearchTriggered,
                    buttonColor = buttonColor
                )
            }
            item {
                SuggestionsList(
                    suggestions = suggestions,
                    searchText = searchText,
                    onSuggestionClicked = onSuggestionClicked
                )
            }
            if (error != null) {
                item {
                    ErrorView(errorMessage = error)
                }
            }
            if (searchResults.itemCount > 0) {
                item {
                    Text(
                        text = "Search Results",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
            items(searchResults.itemCount) { index ->
                searchResults[index]?.let { artwork ->
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
                                .wrapContentSize(Alignment.Center)
                        ) {
                            CardTemplate(
                                imageUrl = artwork.imageUrl ?: "",
                                title = artwork.title ?: "No Title",
                                artist = artwork.people?.joinToString(separator = ", ")
                                { it.name ?: "Unknown" } ?: "Unknown",
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
                                                    System.currentTimeMillis()
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
                                                System.currentTimeMillis()
                                            )
                                        )
                                    }
                                    isFavorite.value = !isFavorite.value
                                },
                                onCardClick = onArtworkSelected,
                                onError = { searchViewModel.setArtworkLoadState(
                                    artwork.objectId ?: 0, false) },
                                cardWidth = 250.dp,
                                cardHeight = 250.dp
                            )
                        }
                    }
                }
            }
        }
    }
}