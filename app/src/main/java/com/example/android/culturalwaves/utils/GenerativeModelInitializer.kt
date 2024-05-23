package com.example.android.culturalwaves.utils

import com.example.android.culturalwaves.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel

class GenerativeModelInitializer {
    companion object {
        fun initializeModel(): GenerativeModel {
            return GenerativeModel(
                modelName = "gemini-pro",
                apiKey = BuildConfig.GEMINI_API_KEY
            )
        }
    }
}

