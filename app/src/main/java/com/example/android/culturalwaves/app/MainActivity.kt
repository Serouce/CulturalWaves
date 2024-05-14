package com.example.android.culturalwaves.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.navigation.compose.rememberNavController
import com.example.android.culturalwaves.ui.navigation.SetupNavGraph
import com.example.android.culturalwaves.ui.components.BottomNavigationBar
import com.example.android.culturalwaves.ui.theme.CulturalWavesTheme
import com.example.android.culturalwaves.ui.theme.DarkBackgroundEnd
import com.example.android.culturalwaves.ui.theme.DarkBackgroundStart
import com.example.android.culturalwaves.ui.theme.LightBackgroundEnd
import com.example.android.culturalwaves.ui.theme.LightBackgroundStart

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CulturalWavesTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = if (isSystemInDarkTheme()) {
                                    listOf(DarkBackgroundStart, DarkBackgroundEnd)
                                } else {
                                    listOf(LightBackgroundStart, LightBackgroundEnd)
                                }
                            )
                        )
                ) {
                    val navController = rememberNavController()

                    Scaffold(
                        bottomBar = { BottomNavigationBar(navController) }
                    ) { innerPadding ->
                        Box(modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                        ) {
                            SetupNavGraph(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

