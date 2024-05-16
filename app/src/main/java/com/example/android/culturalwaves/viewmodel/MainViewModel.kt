package com.example.android.culturalwaves.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.android.culturalwaves.data.entities.Artwork
import com.example.android.culturalwaves.domain.repository.ArtRepository
import com.example.android.culturalwaves.utils.CategoryUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn




//class MainViewModel(private val artRepository: ArtRepository) : ViewModel() {
//    val artworks: StateFlow<PagingData<Artwork>> = artRepository.getArtworksStream()
//        .cachedIn(viewModelScope)
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())
//
//    private val _artworkLoadStates = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
//    val artworkLoadStates: StateFlow<Map<Int, Boolean>> = _artworkLoadStates
//
//    fun setArtworkLoadState(objectId: Int, isLoaded: Boolean) {
//        _artworkLoadStates.value = _artworkLoadStates.value.toMutableMap().apply {
//            this[objectId] = isLoaded
//        }
//    }
//
//
//}


class MainViewModel(private val artRepository: ArtRepository) : ViewModel() {

    // MutableStateFlow для хранения текущей классификации
    private val _currentClassification = MutableStateFlow<String?>(null)
    val currentClassification: StateFlow<String?> get() = _currentClassification

    // Список доступных категорий, полученный из Utils
    val categories = CategoryUtils.getCategories().map { it.first }

    // Обновляем поток данных произведений искусства при изменении классификации
    @OptIn(ExperimentalCoroutinesApi::class)
    val artworks: StateFlow<PagingData<Artwork>> = _currentClassification
        .flatMapLatest { classification ->
            // Передаем queryParams в виде карты
            val queryParams = classification?.let { mapOf("classification" to it) } ?: emptyMap()
            artRepository.getArtworksStream(queryParams)
        }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())

    private val _artworkLoadStates = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
    val artworkLoadStates: StateFlow<Map<Int, Boolean>> = _artworkLoadStates

    fun setArtworkLoadState(objectId: Int, isLoaded: Boolean) {
        _artworkLoadStates.value = _artworkLoadStates.value.toMutableMap().apply {
            this[objectId] = isLoaded
        }
    }

    // Функция для установки текущей классификации
    fun setClassification(classification: String?) {
        _currentClassification.value = classification
    }
}

