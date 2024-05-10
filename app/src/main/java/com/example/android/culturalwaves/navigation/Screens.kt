package com.example.android.culturalwaves.navigation

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    class DetailScreen(val objectId: Int) : Screen("detail_screen/{objectId}") {
        fun createRoute() = "detail_screen/$objectId"
    }
}


