package com.example.android.culturalwaves.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun SearchScreen() {
    var textState by remember { mutableStateOf(TextFieldValue("")) }

    MaterialTheme {
        Scaffold { _ ->
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                BasicTextField(
                    value = textState,
                    onValueChange = { textState = it },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
                    decorationBox = { innerTextField ->
                        if (textState.text.isEmpty()) {
                            Text("Введите запрос", style = MaterialTheme.typography.bodyLarge)
                        }
                        innerTextField()
                    }
                )
            }
        }
    }
}
