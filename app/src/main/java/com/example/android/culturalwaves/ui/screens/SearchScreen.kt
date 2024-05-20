package com.example.android.culturalwaves.ui.screens

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
import com.example.android.culturalwaves.ui.navigation.Screen
import com.example.android.culturalwaves.ui.components.CardTemplate
import com.example.android.culturalwaves.viewmodel.FavoriteViewModel
import com.example.android.culturalwaves.viewmodel.SearchViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
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

    val isDarkTheme = isSystemInDarkTheme()
    val buttonColor = if (isDarkTheme) Color(0xFF455A64) else Color(0xFF90A4AE) // Button color based on theme

    Scaffold(
        content = { paddingValues ->
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
                item {
                    Text(
                        text = "Search",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }

                item {
                    // Text field for search input
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = {
                            searchText = it
                            if (it.length >= 3) {
                                coroutineScope.launch {
                                    searchViewModel.fetchSearchSuggestions(it) // Fetch search suggestions
                                }
                            }
                        },
                        label = { Text("Search") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = {
                            if (searchText.isNotBlank()) { // Check if search text is not blank before executing search
                                coroutineScope.launch {
                                    searchViewModel.searchArtworks(searchText) // Execute search
                                }
                                searchText = ""
                            }
                        }),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = buttonColor, // Border color when focused
                            unfocusedBorderColor = buttonColor, // Border color when not focused
                            cursorColor = MaterialTheme.colorScheme.primary
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Modern search button
                    Button(
                        onClick = {
                            if (searchText.isNotBlank()) { // Check if search text is not blank before executing search
                                coroutineScope.launch {
                                    searchViewModel.searchArtworks(searchText) // Execute search on button click
                                }
                                searchText = ""
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = buttonColor,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(12.dp) // Rounded corners for modern look
                    ) {
                        Text(
                            "Search",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }

                val limitedSuggestions = suggestions.take(3) // Limit number of suggestions

                // Display suggestions below text field
                if (limitedSuggestions.isNotEmpty() && searchText.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
                                .padding(8.dp)
                        ) {
                            limitedSuggestions.forEach { suggestion ->
                                Text(
                                    text = suggestion,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            searchText = suggestion
                                            coroutineScope.launch {
                                                searchViewModel.searchArtworks(suggestion) // Search by selected suggestion
                                            }
                                        }
                                        .padding(8.dp),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }

                // Display error message if exists
                if (error != null) {
                    item {
                        Text(
                            text = error ?: "",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                }

                // Display "Search Results" header
                if (searchResults.itemCount > 0) {
                    item {
                        Text(
                            text = "Search Results",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                }

                // Display search results using LazyVerticalGrid
                items(searchResults.itemCount) { index ->
                    searchResults[index]?.let { artwork ->
                        val isFavorite = remember { mutableStateOf(false) }
                        val showCard = artworkLoadStates[artwork.objectId ?: 0] ?: true

                        LaunchedEffect(key1 = artwork.objectId) {
                            isFavorite.value = favoriteViewModel.isFavorite(artwork.objectId ?: 0) // Check if artwork is favorite
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
                                                favoriteViewModel.removeFavorite(FavoriteArtwork(id, artwork.title ?: "", artwork.imageUrl ?: "", "")) // Remove from favorites
                                            }
                                        } else {
                                            favoriteViewModel.addFavorite(FavoriteArtwork(artwork.objectId ?: 0, artwork.title ?: "", artwork.imageUrl ?: "", "")) // Add to favorites
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
    )
}
