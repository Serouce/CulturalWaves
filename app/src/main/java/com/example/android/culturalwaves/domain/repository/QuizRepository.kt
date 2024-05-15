package com.example.android.culturalwaves.domain.repository

import android.util.Log
import com.example.android.culturalwaves.data.entities.models.GenerateContentRequest
import com.example.android.culturalwaves.data.entities.models.GenerateContentResponse
import com.example.android.culturalwaves.data.network.GeminiApiService
import com.example.android.culturalwaves.data.network.GenerativeModelInitializer
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class QuizRepository {
    private val generativeModel: GenerativeModel = GenerativeModelInitializer.initializeModel()

    suspend fun generateQuizQuestion(prompt: String): String {
        val inputContent = content { text(prompt) }
        return withContext(Dispatchers.IO) {
            try {
                val response = generativeModel.generateContent(inputContent)
                response.text
            } catch (e: Exception) {
                "Error: ${e.message}"
            }.toString()
        }
    }
}


