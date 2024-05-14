package com.example.android.culturalwaves.di

import com.example.android.culturalwaves.domain.repository.ArtRepository
import com.example.android.culturalwaves.data.network.RetrofitClient
import com.example.android.culturalwaves.domain.repository.FavoriteArtRepository
import com.example.android.culturalwaves.viewmodel.ArtViewModel
import com.example.android.culturalwaves.viewmodel.ArtworkDetailViewModel
import com.example.android.culturalwaves.viewmodel.FavoriteViewModel
import com.example.android.culturalwaves.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single { RetrofitClient.instance }  // Предоставляет инстанс ArtMuseumApiService
}

val repositoryModule = module {
    single { ArtRepository(get()) }  // Использует ArtMuseumApiService для создания репозитория
    single { FavoriteArtRepository(get()) }  // Использует FavoriteArtworkDao для создания репозитория избранного
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

val viewModelFavoriteModule = module {
    viewModel { FavoriteViewModel(get()) }
}




val appModules = listOf(
    networkModule,
    databaseModule,
    repositoryModule,
    viewModelModule,
    viewModelDetailModule,
    viewModelSearchModule,
    viewModelFavoriteModule
)