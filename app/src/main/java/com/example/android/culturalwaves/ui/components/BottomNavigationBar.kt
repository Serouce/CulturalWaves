package com.example.android.culturalwaves.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.android.culturalwaves.ui.navigation.Screen

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        Screen.MainScreen,
        Screen.FavoriteScreen,
        Screen.SearchScreen,
        Screen.CategoryScreen
    )

    NavigationBar { // Использование NavigationBar из Material Design 3
        val currentRoute = currentRoute(navController)
        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(screen.label) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        // Очистка стека навигации до начального экрана, гарантируем, что возврат ведет на главный экран
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Предотвращение множественного запуска одного и того же экрана
                        launchSingleTop = true
                        // Восстановление состояния предыдущего экрана, если это необходимо
                        restoreState = true
                    }
                }
            )
        }
    }


}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
