package com.example.android.culturalwaves.utils

object StringUtils {
    fun truncateString(text: String, maxLength: Int): String {
        if (text.length <= maxLength) return text
        return text.substring(0, maxLength - 3) + "..."
    }
}