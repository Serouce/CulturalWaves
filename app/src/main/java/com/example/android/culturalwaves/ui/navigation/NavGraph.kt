package com.example.android.culturalwaves.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.android.culturalwaves.ui.screens.CategoryScreen
import com.example.android.culturalwaves.ui.screens.DetailScreen
import com.example.android.culturalwaves.ui.screens.FavoriteScreen
import com.example.android.culturalwaves.ui.screens.MainScreen
import com.example.android.culturalwaves.ui.screens.SearchScreen


@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(Screen.MainScreen.route) {
            MainScreen() { objectId ->
                navController.navigate(Screen.DetailScreen(objectId).createRoute()) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
        composable(Screen.FavoriteScreen.route) {
            FavoriteScreen()
        }
        composable(Screen.SearchScreen.route) {
            SearchScreen(navController = navController)
        }
        composable(Screen.CategoryScreen.route) {
            CategoryScreen()
        }
        composable(
            route = Screen.DetailScreen(0).route, // Используем шаблонный route для подстановки
            arguments = listOf(navArgument("objectId") { type = NavType.IntType })
        ) { backStackEntry ->
            val objectId = backStackEntry.arguments?.getInt("objectId") ?: return@composable
            DetailScreen(objectId)
        }
    }
}