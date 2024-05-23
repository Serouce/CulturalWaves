package com.example.android.culturalwaves.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.example.android.culturalwaves.ui.navigation.SetupNavGraph
import com.example.android.culturalwaves.ui.components.BottomNavigationBar
import com.example.android.culturalwaves.ui.theme.CulturalWavesTheme
import com.example.android.culturalwaves.ui.theme.GradientType
import com.example.android.culturalwaves.ui.theme.getGradientBrush

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CulturalWavesTheme {
                val gradientType = if (isSystemInDarkTheme()) GradientType.DARK_THEME
                else GradientType.LIGHT_THEME
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(getGradientBrush(gradientType))
                ) {
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = { BottomNavigationBar(navController) },
                        containerColor = Color.Transparent
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize()
                                .background(Color.Transparent)
                        ) {
                            SetupNavGraph(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

