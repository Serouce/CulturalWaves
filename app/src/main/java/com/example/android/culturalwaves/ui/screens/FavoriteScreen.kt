package com.example.android.culturalwaves.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.android.culturalwaves.ui.components.CardTemplate
import com.example.android.culturalwaves.ui.components.TopAppBarWithSortButton
import com.example.android.culturalwaves.ui.navigation.Screen
import com.example.android.culturalwaves.viewmodel.FavoriteViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoriteScreen(navController: NavHostController) {
    val favoriteViewModel: FavoriteViewModel = koinViewModel()
    val favoriteArtworks by favoriteViewModel.favoriteArtworks.collectAsState()
    val sortOrder by favoriteViewModel.sortOrder.collectAsState()

    Scaffold { padding ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 180.dp),
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding()
                        + WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding(),
                start = 16.dp,
                end = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                TopAppBarWithSortButton(sortOrder, onSortOrderChange = { newSortOrder ->
                    favoriteViewModel.setSortOrder(newSortOrder)
                })
            }

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

                    }
                )
            }
        }
    }
}


