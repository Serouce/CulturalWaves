package com.example.android.culturalwaves.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.android.culturalwaves.R

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object FavoriteScreen : Screen("favorite_screen")
    object SearchScreen : Screen("search_screen")
    object QuizScreen : Screen("category_screen")
    class DetailScreen(val objectId: Int) : Screen("detail_screen/{objectId}") {
        fun createRoute() = "detail_screen/$objectId"
    }

    val icon: ImageVector
        @Composable
        get() = when (this) {
            is MainScreen -> Icons.Filled.Home
            is FavoriteScreen -> Icons.Filled.Favorite
            is SearchScreen -> Icons.Filled.Search
            is QuizScreen -> ImageVector.vectorResource(id = R.drawable.quiz)
            else -> throw IllegalArgumentException("No icon for this screen")
        }

    val label: String
        get() = when (this) {
            is MainScreen -> "Main"
            is FavoriteScreen -> "Favorites"
            is SearchScreen -> "Search"
            is QuizScreen -> "Quiz"
            else -> throw IllegalArgumentException("No label for this screen")
        }
}
