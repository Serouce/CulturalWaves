package com.example.android.culturalwaves.data.network

import com.example.android.culturalwaves.data.entities.models.GenerateContentRequest
import com.example.android.culturalwaves.data.entities.models.GenerateContentResponse
import com.google.ai.client.generativeai.GenerativeModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface GeminiApiService {
    @POST("v1beta2/models/gemini-1.5-flash:generateText")
    suspend fun generateText(
        @Header("Authorization") token: String,
        @Body request: GenerateContentRequest
    ): Response<GenerateContentResponse>
}


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
