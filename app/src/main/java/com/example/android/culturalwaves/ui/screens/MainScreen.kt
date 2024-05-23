package com.example.android.culturalwaves.ui.screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.android.culturalwaves.data.entities.Artwork
import com.example.android.culturalwaves.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import com.example.android.culturalwaves.viewmodel.FavoriteViewModel
import kotlinx.coroutines.launch
import com.example.android.culturalwaves.ui.components.MainContent
import com.example.android.culturalwaves.ui.components.ScrollToTopButton

@Composable
fun MainScreen(onArtworkSelected: (Int) -> Unit) {
    val mainViewModel: MainViewModel = koinViewModel()
    val favoriteViewModel: FavoriteViewModel = koinViewModel()
    val artworks: LazyPagingItems<Artwork> = mainViewModel.artworks.collectAsLazyPagingItems()
    val artworkLoadStates by mainViewModel.artworkLoadStates.collectAsState()
    val currentClassification by mainViewModel.currentClassification.collectAsState()
    val isLoading by mainViewModel.isLoading.collectAsState()
    val error by mainViewModel.error.collectAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    var showButton by remember { mutableStateOf(false) }
    var previousIndex by remember { mutableStateOf(0) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { index ->
                showButton = index < previousIndex
                previousIndex = index
            }
    }

    Scaffold(
        floatingActionButton = {
            if (showButton) {
                ScrollToTopButton { coroutineScope.launch { listState.scrollToItem(0) } }
            }
        }
    ) { padding ->
        MainContent(
            padding = padding,
            isLoading = isLoading,
            artworks = artworks,
            artworkLoadStates = artworkLoadStates,
            currentClassification = currentClassification,
            error = error,
            onArtworkSelected = onArtworkSelected,
            mainViewModel = mainViewModel,
            favoriteViewModel = favoriteViewModel,
            listState = listState,
            coroutineScope = coroutineScope
        )
    }
}


