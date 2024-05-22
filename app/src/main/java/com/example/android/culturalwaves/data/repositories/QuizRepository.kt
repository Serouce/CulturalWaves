package com.example.android.culturalwaves.data.repositories

import com.example.android.culturalwaves.utils.GenerativeModelInitializer
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext



//class QuizRepository {
//    private val generativeModel: GenerativeModel = GenerativeModelInitializer.initializeModel()
//
//    suspend fun generateQuizQuestion(prompt: String): String {
//        val inputContent = content { text(prompt) }
//        return withContext(Dispatchers.IO) {
//            try {
//                val response = generativeModel.generateContent(inputContent)
//                response.text
//            } catch (e: Exception) {
//                "Error: ${e.message}"
//            }.toString()
//        }
//    }
//
//    suspend fun checkQuizAnswer(question: String, userAnswer: String): String {
//        val inputContent = content { text("$question\nUser answer: $userAnswer\nIs this correct? Provide a brief explanation if the answer is incorrect.") }
//        return withContext(Dispatchers.IO) {
//            try {
//                val response = generativeModel.generateContent(inputContent)
//                response.text
//            } catch (e: Exception) {
//                "Error: ${e.message}"
//            }.toString()
//        }
//    }
//}


interface QuizRepository {
    suspend fun generateQuizQuestion(prompt: String): String
    suspend fun checkQuizAnswer(question: String, userAnswer: String): String
}