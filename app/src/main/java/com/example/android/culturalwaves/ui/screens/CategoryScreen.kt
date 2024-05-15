package com.example.android.culturalwaves.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android.culturalwaves.R
import com.example.android.culturalwaves.viewmodel.CategoryViewModel
import com.example.android.culturalwaves.viewmodel.QuizViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel


//@Composable
//fun CategoryScreen() {
//    val showDialog = rememberSaveable { mutableStateOf(false) }
//
//    if (showDialog.value) {
//        Dialog(onDismissRequest = { showDialog.value = false }) {
//            Surface(
//                modifier = Modifier.padding(16.dp),
//                shape = RoundedCornerShape(8.dp),
//                color = MaterialTheme.colorScheme.surface
//            ) {
//                Column(
//                    modifier = Modifier.padding(16.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text(
//                        text = "Выберите категории",
//                        style = MaterialTheme.typography.bodyLarge
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//                    Button(onClick = { showDialog.value = false }) {
//                        Text("Закрыть")
//                    }
//                }
//            }
//        }
//    }
//
//    // Здесь остальной UI компонент
//    MaterialTheme {
//        Scaffold { _ ->
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                Button(onClick = { showDialog.value = true }) {
//                    Text("Показать категории")
//                }
//            }
//        }
//    }
//}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen() {
    val quizViewModel: QuizViewModel = koinViewModel()
    val quizQuestion by quizViewModel.quizQuestion.collectAsState()
    val quizResult by quizViewModel.quizResult.collectAsState()
    var userAnswer by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Квиз по культуре") },
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = quizQuestion,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = userAnswer,
                    onValueChange = { userAnswer = it },
                    label = { Text("Ваш ответ") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        quizViewModel.checkQuizAnswer(quizQuestion, userAnswer)
                    }
                ) {
                    Text("Проверить ответ")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Результат: $quizResult",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        quizViewModel.generateQuizQuestion("Задай квиз-вопрос по теме культуры и эстетики с 4 вариантами ответов: A, B, C и D.")
                        userAnswer = ""
                    }
                ) {
                    Text("Новый вопрос")
                }
            }
        }
    )
}