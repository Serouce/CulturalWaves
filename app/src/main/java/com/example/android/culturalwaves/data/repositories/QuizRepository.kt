package com.example.android.culturalwaves.data.repositories

interface QuizRepository {
    suspend fun generateQuizQuestion(prompt: String): String
    suspend fun checkQuizAnswer(question: String, userAnswer: String): String
}