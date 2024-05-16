package com.example.android.culturalwaves.data.network

import com.google.ai.client.generativeai.GenerativeModel

class GenerativeModelInitializer {
    companion object {
        fun initializeModel(): GenerativeModel {
            return GenerativeModel(
                modelName = "gemini-pro",
                apiKey = "AIzaSyCyjwBNUDGV9sJayyjUsk2XeJPJqn_5Zww"
            )
        }
    }
}
