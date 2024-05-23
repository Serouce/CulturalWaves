package com.example.android.culturalwaves.ui.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.example.android.culturalwaves.ui.components.QuizContent
import com.example.android.culturalwaves.viewmodel.QuizViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen() {
    val quizViewModel: QuizViewModel = koinViewModel()
    val quizQuestion by quizViewModel.quizQuestion.collectAsState()
    val quizResult by quizViewModel.quizResult.collectAsState()
    val isLoading by quizViewModel.isLoading.collectAsState()
    val error by quizViewModel.error.collectAsState()
    var userAnswer by remember { mutableStateOf("") }

    val isDarkTheme = isSystemInDarkTheme()
    val buttonColor = if (isDarkTheme) Color(0xFF455A64) else Color(0xFF90A4AE)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Culture quiz") },
            )
        },
        content = { padding ->
            QuizContent(
                padding = padding,
                isLoading = isLoading,
                quizQuestion = quizQuestion,
                quizResult = quizResult,
                error = error,
                userAnswer = userAnswer,
                onAnswerSelected = { answer ->
                    userAnswer = answer
                    quizViewModel.checkQuizAnswer(quizQuestion, answer)
                },
                onGenerateNewQuestion = {
                    quizViewModel.generateQuizQuestion(
                        "Задай квиз-вопрос по теме культуры и эстетики с 4 вариантами ответов: A, B, C и D.")
                    userAnswer = ""
                },
                buttonColor = buttonColor,
                isDarkTheme = isDarkTheme
            )
        }
    )
}