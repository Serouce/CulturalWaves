package com.example.android.culturalwaves.utils

// Utils.kt
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.android.culturalwaves.R

object CategoryUtils {
    // Функция для получения списка категорий и соответствующих изображений
    fun getCategories(): List<Pair<String, Int>> {
        return listOf(
            "Paintings" to R.drawable.paintings,
            "Drawings" to R.drawable.drawings,
            "Photographs" to R.drawable.photographs,
            "Architectural Elements" to R.drawable.architectural_elements,
            "Jewelry" to R.drawable.jewelry,
            "Manuscripts" to R.drawable.manuscripts,
            "Stained Glass" to R.drawable.stained_glass,
            "Prints" to R.drawable.prints,
            "Sculpture" to R.drawable.sculpture,
            "Cameos" to R.drawable.cameos,
            "Vessels" to R.drawable.vessels,
            "Textile Arts" to R.drawable.textile_arts
        )
    }

    // Функция для получения изображения по названию категории
    fun getCategoryImage(category: String): Int {
        return getCategories().find { it.first == category }?.second ?: R.drawable.artwork_image
    }
}