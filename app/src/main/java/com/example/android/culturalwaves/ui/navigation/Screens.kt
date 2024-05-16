package com.example.android.culturalwaves.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object FavoriteScreen : Screen("favorite_screen")
    object SearchScreen : Screen("search_screen")
    object QuizScreen : Screen("category_screen")
    class DetailScreen(val objectId: Int) : Screen("detail_screen/{objectId}") {
        fun createRoute() = "detail_screen/$objectId"
    }

    val icon: ImageVector
        get() = when (this) {
            is MainScreen -> Icons.Filled.Home
            is FavoriteScreen -> Icons.Filled.Favorite
            is SearchScreen -> Icons.Filled.Search
            is QuizScreen -> Icons.Filled.Info
            else -> throw IllegalArgumentException("No icon for this screen")
        }

    val label: String
        get() = when (this) {
            is MainScreen -> "Main"
            is FavoriteScreen -> "Favorites"
            is SearchScreen -> "Search"
            is QuizScreen -> "Categories"
            else -> throw IllegalArgumentException("No label for this screen")
        }
}


