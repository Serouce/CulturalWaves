package com.example.android.culturalwaves.di

import com.example.android.culturalwaves.repository.ArtRepository
import com.example.android.culturalwaves.network.RetrofitClient
import com.example.android.culturalwaves.viewmodel.ArtViewModel
import com.example.android.culturalwaves.viewmodel.ArtworkDetailViewModel
import com.example.android.culturalwaves.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single { RetrofitClient.instance }  // Предоставляет инстанс ArtMuseumApiService
}

val repositoryModule = module {
    single { ArtRepository(get()) }  // Использует ArtMuseumApiService для создания репозитория
}

val viewModelModule = module {
    viewModel { ArtViewModel(get()) }  // Использует ArtRepository для создания ViewModel
}
val viewModelDetailModule = module {
    viewModel { (objectId: Int) -> ArtworkDetailViewModel(get(), objectId) }
}

val viewModelSearchModule = module {
    viewModel { SearchViewModel(get()) }
}




// Объединяем все модули в один список для Koin
val appModules = listOf(networkModule, repositoryModule, viewModelModule, viewModelDetailModule, viewModelSearchModule)