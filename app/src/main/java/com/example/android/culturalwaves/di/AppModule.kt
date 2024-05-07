package com.example.android.culturalwaves.di

import com.example.android.culturalwaves.network.ArtMuseumApiService
import com.example.android.culturalwaves.network.ArtRepository
import com.example.android.culturalwaves.network.RetrofitClient
import com.example.android.culturalwaves.viewmodel.ArtViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { RetrofitClient.instance }  // Предоставляет инстанс ArtMuseumApiService
}

val repositoryModule = module {
    single { ArtRepository(get()) }  // Использует ArtMuseumApiService для создания репозитория
}

val viewModelModule = module {
    viewModel { ArtViewModel(get()) }  // Использует ArtRepository для создания ViewModel
}

// Объединяем все модули в один список для Koin
val appModules = listOf(networkModule, repositoryModule, viewModelModule)