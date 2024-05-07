package com.example.android.culturalwaves

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android.culturalwaves.network.RetrofitClient
import com.example.android.culturalwaves.ui.ArtworkListScreen
import com.example.android.culturalwaves.ui.theme.CulturalWavesTheme
import com.example.android.culturalwaves.viewmodel.ArtViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

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


                    val artViewModel: ArtViewModel = getViewModel()
                    ArtworkListScreen(artViewModel)


                }
            }
        }
    }
}

