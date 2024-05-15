package com.example.android.culturalwaves.di

import com.example.android.culturalwaves.data.network.GeminiRetrofitClient
import com.example.android.culturalwaves.domain.repository.ArtRepository
import com.example.android.culturalwaves.data.network.RetrofitClient
import com.example.android.culturalwaves.domain.repository.FavoriteArtRepository
import com.example.android.culturalwaves.domain.repository.QuizRepository
import com.example.android.culturalwaves.viewmodel.MainViewModel
import com.example.android.culturalwaves.viewmodel.ArtworkDetailViewModel
import com.example.android.culturalwaves.viewmodel.FavoriteViewModel
import com.example.android.culturalwaves.viewmodel.QuizViewModel
import com.example.android.culturalwaves.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// Network module
val networkModule = module {
    single { RetrofitClient.instance }
    single { GeminiRetrofitClient.instance }
}

val repositoryModule = module {
    single { ArtRepository(get()) }
    single { FavoriteArtRepository(get()) }
    single { QuizRepository() }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}

val viewModelDetailModule = module {
    viewModel { (objectId: Int) -> ArtworkDetailViewModel(get(), objectId) }
}

val viewModelSearchModule = module {
    viewModel { SearchViewModel(get()) }
}

val viewModelFavoriteModule = module {
    viewModel { FavoriteViewModel(get()) }
}

val quizModule = module {
    viewModel { QuizViewModel(get()) }
}

val appModules = listOf(
    networkModule,
    databaseModule,
    repositoryModule,
    viewModelModule,
    viewModelDetailModule,
    viewModelSearchModule,
    viewModelFavoriteModule,
    quizModule
)

