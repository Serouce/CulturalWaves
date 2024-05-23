package com.example.android.culturalwaves.data.entities

data class GenerateContentRequest(
    val prompt: String,
    val temperature: Double = 0.7,
    val topK: Int = 40,
    val topP: Double = 0.95,
    val maxOutputTokens: Int = 1024
)

data class GenerateContentResponse(
    val candidates: List<Candidate>
)

data class Candidate(
    val content: String
)
