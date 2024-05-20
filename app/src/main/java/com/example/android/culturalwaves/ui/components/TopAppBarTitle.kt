package com.example.android.culturalwaves.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarTitle(title: String) {
    TopAppBar(
        title = { Text(text = title) }
    )
}