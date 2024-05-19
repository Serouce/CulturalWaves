package com.example.android.culturalwaves.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.android.culturalwaves.ui.components.CardTemplate
import com.example.android.culturalwaves.ui.navigation.Screen
import com.example.android.culturalwaves.viewmodel.FavoriteViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(navController: NavHostController) {
    val favoriteViewModel: FavoriteViewModel = koinViewModel()
    val favoriteArtworks by favoriteViewModel.favoriteArtworks.collectAsState()

    Scaffold { padding ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 180.dp),
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding(),
                start = 16.dp,
                end = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            // Элемент заголовка "Favorite"
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    text = "Favorites",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(16.dp)
                        .background(MaterialTheme.colorScheme.background)
                )
            }

            // Элементы карточек
            items(favoriteArtworks.size) { index ->
                val artwork = favoriteArtworks[index]
                CardTemplate(
                    imageUrl = artwork.imageUrl,
                    title = artwork.title,
                    artist = "",
                    objectId = artwork.objectId,
                    isFavorite = true,
                    onFavoriteClick = {
                        favoriteViewModel.removeFavorite(artwork)
                    },
                    onCardClick = {
                        navController.navigate(Screen.DetailScreen(artwork.objectId).createRoute())
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
