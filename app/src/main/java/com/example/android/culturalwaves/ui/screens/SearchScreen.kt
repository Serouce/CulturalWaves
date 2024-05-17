package com.example.android.culturalwaves.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
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
    var searchText by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val searchResults = searchViewModel.searchResults.collectAsLazyPagingItems()
    val suggestions by searchViewModel.searchSuggestions.collectAsState()

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Search", style = MaterialTheme.typography.titleLarge) },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = searchText,
                    onValueChange = {
                        searchText = it
                        if (it.length >= 3) {
                            coroutineScope.launch {
                                searchViewModel.fetchSearchSuggestions(it)
                            }
                        }
                    },
                    label = { Text("Search") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp)),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        coroutineScope.launch {
                            searchViewModel.searchArtworks(searchText, emptyMap())
                        }
                        searchText = ""
                    }),
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.primary
                    )
                )

                val limitedSuggestions = suggestions.take(3)



                // Отображение предложений ниже текстового поля
                if (limitedSuggestions.isNotEmpty() && searchText.isNotEmpty()) {
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
                                            searchViewModel.searchArtworks(suggestion, emptyMap())
                                        }
                                    }
                                    .padding(8.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

                // Отображение результатов поиска
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
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
                                },
                                cardWidth = 350.dp, // Увеличение ширины карточки
                                cardHeight = 350.dp // Увеличение высоты карточки
                            )
                        }
                    }
                }
            }
        }
    )
}





