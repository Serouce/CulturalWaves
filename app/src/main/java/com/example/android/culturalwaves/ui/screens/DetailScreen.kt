package com.example.android.culturalwaves.ui.screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.example.android.culturalwaves.ui.components.ErrorView
import com.example.android.culturalwaves.ui.components.LoadingView
import com.example.android.culturalwaves.ui.components.SuccessView
import com.example.android.culturalwaves.viewmodel.ArtworkDetailViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DetailScreen(navController: NavHostController, objectId: Int) {
    val viewModel: ArtworkDetailViewModel = koinViewModel { parametersOf(objectId) }
    val artworkDetailState by viewModel.artworkDetails.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var selectedImageUrl by remember { mutableStateOf<String?>(null) }

    Scaffold { padding ->
        when {
            isLoading -> {
                LoadingView()
            }
            error != null -> {
                ErrorView(errorMessage = error)
            }
            else -> {
                SuccessView(
                    padding = padding,
                    artworkDetailState = artworkDetailState,
                    showDialog = showDialog,
                    selectedImageUrl = selectedImageUrl,
                    onImageClick = { imageUrl ->
                        selectedImageUrl = imageUrl
                        showDialog = true
                    },
                    onDismissRequest = { showDialog = false }
                )
            }
        }
    }
}


