package com.example.android.culturalwaves.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush


//val Purple80 = Color(0xFFD0BCFF)
//val PurpleGrey80 = Color(0xFFCCC2DC)
//val Pink80 = Color(0xFFEFB8C8)
//
//val Purple40 = Color(0xFF6650a4)
//val PurpleGrey40 = Color(0xFF625b71)
//val Pink40 = Color(0xFF7D5260)
//
//// Определяем цвета для градиента
//val LightBackgroundStart = Color(0xFFF8F8F8)
//val LightBackgroundEnd = Color(0xFFFFFFFF)
//val DarkBackgroundStart = Color(0xFF121212)
//val DarkBackgroundEnd = Color(0xFF000000)


// Цвета для светлой темы
// Определение цветов
val LightPrimary = Color(0xFFFAEBD7) // Светлый бежевый
val LightSecondary = Color(0xFFCCCCCC) // Светло-серый
val LightTertiary = Color(0xFFF5F5F5) // Светлый серый
val LightBackgroundStart = Color(0xFFFAEBD7) // Начало градиента светлой темы
val LightBackgroundEnd = Color(0xFFFFFFFF) // Конец градиента светлой темы
val ColorForCard = Color(0xFF37474F)
val DarkPrimary = Color(0xFF333333) // Тёмно-серый
val DarkSecondary = Color(0xFF111111) // Глубокий тёмный серый
val DarkTertiary = Color(0xFF000080) // Глубокий тёмный синий
val DarkBackgroundStart = Color(0xFF333333) // Начало градиента тёмной темы
val DarkBackgroundEnd = Color(0xFF000000) // Конец градиента тёмной темы


//enum class GradientType(val colors: List<Color>) {
//    LIGHT_THEME(listOf(Color(0xFFF8F9FA), Color(0xFFB0C4DE))),  // Очень светлый серый к светло-голубому
//    DARK_THEME(listOf(Color(0xFF3A3A3A), Color(0xFF1C1C1C)))   // Темный графит к угольному
//}

enum class GradientType(val colors: List<Color>) {
    LIGHT_THEME(listOf(Color(0xFFFAF9F6), Color(0xFFB0BEC5))),  // Кремовый к светло-серому
    DARK_THEME(listOf(Color(0xFF263238), Color(0xFF37474F)))   // Темный серо-синий к серо-синему
}






fun getGradientBrush(gradientType: GradientType): Brush {
    return Brush.verticalGradient(colors = gradientType.colors)
}

