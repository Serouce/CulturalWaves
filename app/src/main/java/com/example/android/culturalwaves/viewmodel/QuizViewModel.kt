package com.example.android.culturalwaves.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.culturalwaves.domain.repository.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Пакет: com.example.android.culturalwaves.viewmodel

//class QuizViewModel(private val quizRepository: QuizRepository) : ViewModel() {
//    private val _quizResult = MutableStateFlow("")
//    val quizResult: StateFlow<String> = _quizResult
//
//    fun generateQuizQuestion(prompt: String) {
//        viewModelScope.launch {
//            val result = quizRepository.generateQuizQuestion(prompt)
//            _quizResult.value = result
//        }
//    }
//}


class QuizViewModel(private val quizRepository: QuizRepository) : ViewModel() {
    private val _quizQuestion = MutableStateFlow("")
    val quizQuestion: StateFlow<String> = _quizQuestion

    private val _quizResult = MutableStateFlow("")
    val quizResult: StateFlow<String> = _quizResult

    fun generateQuizQuestion(prompt: String) {
        viewModelScope.launch {
            val question = quizRepository.generateQuizQuestion(prompt)
            _quizQuestion.value = question
        }
    }

    fun checkQuizAnswer(question: String, userAnswer: String) {
        viewModelScope.launch {
            val result = quizRepository.checkQuizAnswer(question, userAnswer)
            _quizResult.value = result
        }
    }
}

