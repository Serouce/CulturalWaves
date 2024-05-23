package com.example.android.culturalwaves.ui.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.android.culturalwaves.ui.navigation.Screen
import com.example.android.culturalwaves.ui.components.SearchContent
import com.example.android.culturalwaves.viewmodel.FavoriteViewModel
import com.example.android.culturalwaves.viewmodel.SearchViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(navController: NavHostController) {
    val searchViewModel: SearchViewModel = koinViewModel()
    val favoriteViewModel: FavoriteViewModel = koinViewModel()
    var searchText by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val searchResults = searchViewModel.searchResults.collectAsLazyPagingItems()
    val suggestions by searchViewModel.searchSuggestions.collectAsState()
    val artworkLoadStates by searchViewModel.artworkLoadStates.collectAsState()
    val error by searchViewModel.error.collectAsState()
    val isLoading by searchViewModel.isLoading.collectAsState()

    val isDarkTheme = isSystemInDarkTheme()
    val buttonColor = if (isDarkTheme) Color(0xFF455A64) else Color(0xFF90A4AE)

    Scaffold(
        content = { paddingValues ->
            SearchContent(
                paddingValues = paddingValues,
                isLoading = isLoading,
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
                suggestions = suggestions,
                error = error,
                searchResults = searchResults,
                artworkLoadStates = artworkLoadStates,
                onSuggestionClicked = { suggestion ->
                    searchText = suggestion
                    coroutineScope.launch { searchViewModel.searchArtworks(suggestion) }
                },
                buttonColor = buttonColor,
                onArtworkSelected = { objectId ->
                    navController.navigate(Screen.DetailScreen(objectId).createRoute())
                },
                favoriteViewModel = favoriteViewModel,
                searchViewModel = searchViewModel
            )
        }
    )
}