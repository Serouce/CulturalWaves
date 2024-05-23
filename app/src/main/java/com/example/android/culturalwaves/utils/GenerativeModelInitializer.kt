package com.example.android.culturalwaves.utils

import com.google.ai.client.generativeai.GenerativeModel

class GenerativeModelInitializer {
    companion object {
        fun initializeModel(): GenerativeModel {
            return GenerativeModel(
                modelName = "gemini-pro",
                apiKey = GEMINI_API_KEY
            )
        }
    }
}

