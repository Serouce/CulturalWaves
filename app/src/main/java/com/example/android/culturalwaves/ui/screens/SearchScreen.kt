package com.example.android.culturalwaves.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.android.culturalwaves.data.entities.FavoriteArtwork
import com.example.android.culturalwaves.ui.components.AnimatedLogo
import com.example.android.culturalwaves.ui.navigation.Screen
import com.example.android.culturalwaves.ui.components.CardTemplate
import com.example.android.culturalwaves.ui.components.ErrorView
import com.example.android.culturalwaves.ui.components.LoadingView
import com.example.android.culturalwaves.ui.components.SearchHeader
import com.example.android.culturalwaves.ui.components.SearchInputField
import com.example.android.culturalwaves.ui.components.SuggestionsList
import com.example.android.culturalwaves.ui.components.TopAppBarTitle
import com.example.android.culturalwaves.viewmodel.FavoriteViewModel
import com.example.android.culturalwaves.viewmodel.SearchViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    navController: NavHostController,
    searchViewModel: SearchViewModel = koinViewModel(),
    favoriteViewModel: FavoriteViewModel = koinViewModel()
) {
    var searchText by remember { mutableStateOf("") } // State for search text
    val coroutineScope = rememberCoroutineScope() // Coroutine scope for async operations
    val searchResults = searchViewModel.searchResults.collectAsLazyPagingItems() // Search results
    val suggestions by searchViewModel.searchSuggestions.collectAsState() // Search suggestions
    val artworkLoadStates by searchViewModel.artworkLoadStates.collectAsState() // Artwork load states
    val error by searchViewModel.error.collectAsState() // Error state
    val isLoading by searchViewModel.isLoading.collectAsState()

    val isDarkTheme = isSystemInDarkTheme()
    val buttonColor = if (isDarkTheme) Color(0xFF455A64) else Color(0xFF90A4AE) // Button color based on theme

    Scaffold(
        content = { paddingValues ->
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    AnimatedLogo()
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(
                        top = paddingValues.calculateTopPadding() + WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                        bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding(),
                        start = 16.dp,
                        end = 16.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Добавляем заголовок в начало LazyColumn
                    item {
                        Text(
                            text = "Search Artworks",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }
                    item { SearchHeader() } // Используем наш компонент SearchHeader
                    item {
                        SearchInputField(
                            searchText = searchText,
                            onSearchTextChanged = { newText ->
                                searchText = newText
                                if (newText.length >= 3) {
                                    coroutineScope.launch { searchViewModel.fetchSearchSuggestions(newText) }
                                }
                            },
                            onSearchTriggered = {
                                if (searchText.isNotBlank()) {
                                    coroutineScope.launch { searchViewModel.searchArtworks(searchText) }
                                    searchText = ""
                                }
                            },
                            buttonColor = buttonColor
                        )
                    }
                    item {
                        SuggestionsList(
                            suggestions = suggestions,
                            searchText = searchText,
                            onSuggestionClicked = { suggestion ->
                                searchText = suggestion
                                coroutineScope.launch { searchViewModel.searchArtworks(suggestion) }
                            }
                        )
                    }
                    if (error != null) {
                        item {
                            ErrorView(errorMessage = error) // Используем ErrorView для отображения сообщения об ошибке
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
                                        artist = artwork.people?.joinToString(separator = ", ") { it.name ?: "Unknown" } ?: "Unknown",
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
                                        onCardClick = { objectId ->
                                            navController.navigate(Screen.DetailScreen(objectId).createRoute()) // Navigate to artwork details
                                        },
                                        onError = { searchViewModel.setArtworkLoadState(artwork.objectId ?: 0, false) },
                                        cardWidth = 250.dp, // Card width
                                        cardHeight = 250.dp // Card height
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

