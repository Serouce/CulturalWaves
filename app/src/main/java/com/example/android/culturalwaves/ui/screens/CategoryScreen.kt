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


@Composable
fun CategoryScreen() {
    val quizViewModel: QuizViewModel = koinViewModel()
    var question by remember { mutableStateOf("") }
    val result by quizViewModel.quizResult.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Генерация текста", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = question,
            onValueChange = { question = it },
            label = { Text("Введите текст") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                quizViewModel.generateQuizQuestion(question)
            }
        ) {
            Text("Сгенерировать текст")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Ответ: $result", style = MaterialTheme.typography.bodyLarge)
    }
}