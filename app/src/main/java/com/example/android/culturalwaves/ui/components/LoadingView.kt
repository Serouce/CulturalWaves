package com.example.android.culturalwaves.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LoadingView() {
    // Box для покрытия всего экрана затемненным фоном и логотипом загрузки
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.3f)), // Полупрозрачный черный фон
        contentAlignment = Alignment.Center // Центрирование контента
    ) {
        AnimatedLogo() // Вставляем анимированный логотип
    }
}