package com.example.android.culturalwaves.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.android.culturalwaves.navigation.Screen
import com.example.android.culturalwaves.ui.components.CardTemplate
import com.example.android.culturalwaves.viewmodel.SearchViewModel
import org.koin.androidx.compose.koinViewModel

//@Composable
//fun SearchScreen() {
//    var textState by remember { mutableStateOf(TextFieldValue("")) }
//
//    MaterialTheme {
//        Scaffold { _ ->
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
//                BasicTextField(
//                    value = textState,
//                    onValueChange = { textState = it },
//                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
//                    decorationBox = { innerTextField ->
//                        if (textState.text.isEmpty()) {
//                            Text("Введите запрос", style = MaterialTheme.typography.bodyLarge)
//                        }
//                        innerTextField()
//                    }
//                )
//            }
//        }
//    }
//}


@Composable
fun SearchScreen(searchViewModel: SearchViewModel = koinViewModel()) {
    var searchText by remember { mutableStateOf("") }

    Column {
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                searchViewModel.searchArtworks(searchText, emptyMap())  // Вызываем поиск
            })
        )

        // Display search results
        val searchResults = searchViewModel.searchResults.collectAsLazyPagingItems()
        LazyColumn {
            items(searchResults.itemCount) { index ->
                searchResults[index]?.let { artwork ->
                    CardTemplate(
                        imageUrl = artwork.imageUrl ?: "",
                        title = artwork.title ?: "No Title",
                        artist = artwork.people?.joinToString(separator = ", ") { it.name ?: "Unknown" } ?: "Unknown",
                        objectId = artwork.objectId ?: 0,
                        isFavorite = false,  // Assuming you will handle favorites later
                        onFavoriteClick = { /* Handle favorite */ },
                        onCardClick = { objectId ->
                            // Navigate to detail screen
                        }
                    )
                }
            }
        }
    }
}



@Composable
fun SearchBar(onQueryChanged: (String) -> Unit) {
    var text by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onQueryChanged(it)
        },
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Поиск") },
        trailingIcon = {
            if (text.isNotBlank()) {
                IconButton(onClick = { text = ""; onQueryChanged("") }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Очистить")
                }
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search)
    )
}

