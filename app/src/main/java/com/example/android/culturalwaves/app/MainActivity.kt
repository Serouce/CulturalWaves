package com.example.android.culturalwaves.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.android.culturalwaves.ui.navigation.SetupNavGraph
import com.example.android.culturalwaves.ui.components.BottomNavigationBar
import com.example.android.culturalwaves.ui.theme.CulturalWavesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CulturalWavesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    Scaffold(
                        bottomBar = { BottomNavigationBar(navController) }
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            SetupNavGraph(navController = navController)
                        }
                    }

                }
            }
        }
    }
}

