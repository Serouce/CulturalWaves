package com.example.android.culturalwaves.data.network

import com.example.android.culturalwaves.data.entities.GenerateContentRequest
import com.example.android.culturalwaves.data.entities.GenerateContentResponse
import com.google.ai.client.generativeai.GenerativeModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface GeminiApiService {
    @POST("v1beta2/models/gemini-1.5-flash:generateText")
    suspend fun generateText(
        @Header("Authorization") token: String,
        @Body request: GenerateContentRequest
    ): Response<GenerateContentResponse>
}


