package com.example.android.culturalwaves.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.android.culturalwaves.data.entities.FavoriteArtwork
import com.example.android.culturalwaves.ui.navigation.Screen
import com.example.android.culturalwaves.ui.components.CardTemplate
import com.example.android.culturalwaves.viewmodel.FavoriteViewModel
import com.example.android.culturalwaves.viewmodel.SearchViewModel
import org.koin.androidx.compose.koinViewModel



@Composable
fun SearchScreen(navController: NavHostController, searchViewModel: SearchViewModel = koinViewModel(), favoriteViewModel: FavoriteViewModel = koinViewModel()) {
    var searchText by remember { mutableStateOf("") }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    val focusManager = remember { FocusRequester() } // Получаем текущий менеджер фокуса

    Column {
        TextField(
            value = searchText,
            onValueChange = {
                searchText = it
                isDropdownExpanded = if (it.length >= 3) {
                    searchViewModel.fetchSearchSuggestions(it)
                    true
                } else {
                    false
                }
            },
            label = { Text("Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .focusRequester(focusManager),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                searchViewModel.searchArtworks(searchText, emptyMap())
                searchText = ""
                isDropdownExpanded = false
            })
        )

        LaunchedEffect(key1 = searchText) {
            // Автоматически запрашиваем фокус обратно к TextField, когда текст изменяется
            focusManager.requestFocus()
        }

        // Отображение предложений поиска
        val suggestions = searchViewModel.searchSuggestions.collectAsState().value
        DropdownMenu(
            expanded = isDropdownExpanded && suggestions.isNotEmpty(),
            onDismissRequest = { isDropdownExpanded = false }
        ) {
            suggestions.forEach { suggestion ->
                Text(
                    text = suggestion,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            searchText = suggestion
                            searchViewModel.searchArtworks(suggestion, emptyMap())
                            searchText = ""
                            isDropdownExpanded = false
                        })
                        .padding(16.dp)
                )
            }
        }

        // Отображение результатов поиска
        val searchResults = searchViewModel.searchResults.collectAsLazyPagingItems()
        LazyColumn {
            items(searchResults.itemCount) { index ->
                searchResults[index]?.let { artwork ->
                    val isFavorite = remember { mutableStateOf(false) }

                    LaunchedEffect(key1 = artwork.objectId) {
                        isFavorite.value = favoriteViewModel.isFavorite(artwork.objectId ?: 0)
                    }

                    CardTemplate(
                        imageUrl = artwork.imageUrl ?: "",
                        title = artwork.title ?: "No Title",
                        artist = artwork.people?.joinToString(separator = ", ") { it.name ?: "Unknown" } ?: "Unknown",
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
                        onCardClick = { objectId ->
                            navController.navigate(Screen.DetailScreen(objectId).createRoute())
                        },
                        onError = {
                            // Логика обработки ошибки загрузки изображения
                            // Например, можно удалить карточку из избранного или показать уведомление
                        }
                    )
                }
            }
        }
    }
}
