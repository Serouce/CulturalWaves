package com.example.android.culturalwaves.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.android.culturalwaves.repository.ArtRepository
import com.example.android.culturalwaves.ui.screens.DetailScreen
import com.example.android.culturalwaves.ui.screens.MainScreen
import com.example.android.culturalwaves.viewmodel.ArtViewModel
import com.example.android.culturalwaves.viewmodel.ArtworkDetailViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(Screen.MainScreen.route) {
            val artViewModel: ArtViewModel = koinViewModel()
            MainScreen(artViewModel = artViewModel) { objectId ->
                navController.navigate(Screen.DetailScreen(objectId).createRoute())
            }
        }
        composable(
            route = Screen.DetailScreen(0).route, // Используем шаблонный route для подстановки
            arguments = listOf(navArgument("objectId") { type = NavType.IntType })
        ) { backStackEntry ->
            val objectId = backStackEntry.arguments?.getInt("objectId") ?: return@composable
            val detailViewModel: ArtworkDetailViewModel = koinViewModel(parameters = { parametersOf(objectId) })
            DetailScreen(viewModel = detailViewModel)
        }
    }
}

