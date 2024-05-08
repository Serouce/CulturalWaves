package com.example.android.culturalwaves.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.android.culturalwaves.model.Artwork
import com.example.android.culturalwaves.repository.ArtRepository
import kotlinx.coroutines.flow.Flow


class ArtViewModel(private val artRepository: ArtRepository) : ViewModel() {
    // Вместо использования MutableStateFlow, определяем artworks как Flow, который
    // получается напрямую из Pager, который уже поддерживает корутин и лучше интегрируется с PagingLibrary.
    val artworks: Flow<PagingData<Artwork>> = Pager(
        config = PagingConfig(
            pageSize = 20,        // Определяем количество элементов на страницу
            enablePlaceholders = false // Отключаем плейсхолдеры для более простой работы с UI
        ),
        pagingSourceFactory = { artRepository.getArtPagingSource() } // Используем PagingSource из репозитория
    ).flow
        .cachedIn(viewModelScope) // Кэшируем данные в области ViewModel для управления жизненным циклом
}


