package com.example.android.culturalwaves.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.culturalwaves.data.repositories.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuizViewModel(
    private val quizRepository: QuizRepository
) : BaseViewModel() {
    private val _quizQuestion = MutableStateFlow("")
    val quizQuestion: StateFlow<String> get() = _quizQuestion

    private val _quizResult = MutableStateFlow("")
    val quizResult: StateFlow<String> get() = _quizResult

    fun generateQuizQuestion(prompt: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val question = quizRepository.generateQuizQuestion(prompt)
                _quizQuestion.value = question
                _quizResult.value = ""
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun checkQuizAnswer(question: String, userAnswer: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = quizRepository.checkQuizAnswer(question, userAnswer)
                _quizResult.value = result
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}