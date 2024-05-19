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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


//class MainViewModel(private val artRepository: ArtRepository) : ViewModel() {
//
//    // MutableStateFlow для хранения текущей классификации
//    private val _currentClassification = MutableStateFlow<String?>(null)
//    val currentClassification: StateFlow<String?> get() = _currentClassification
//
//    // Список доступных категорий, полученный из Utils
//    val categories = CategoryUtils.getCategories().map { it.first }
//
//    // Обновляем поток данных произведений искусства при изменении классификации
//    @OptIn(ExperimentalCoroutinesApi::class)
//    val artworks: StateFlow<PagingData<Artwork>> = _currentClassification
//        .flatMapLatest { classification ->
//            // Передаем queryParams в виде карты
//            val queryParams = classification?.let { mapOf("classification" to it) } ?: emptyMap()
//            artRepository.getArtworksStream(queryParams)
//        }
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
//    // Функция для установки текущей классификации
//    fun setClassification(classification: String?) {
//        _currentClassification.value = classification
//    }
//
//    // Функция для переключения классификации и сброса фильтра при повторном нажатии
//    fun toggleClassification(classification: String) {
//        if (_currentClassification.value == classification) {
//            _currentClassification.value = null // Сбросить фильтр
//        } else {
//            _currentClassification.value = classification // Установить новую классификацию
//        }
//    }
//
//
//}
//

class MainViewModel(private val artRepository: ArtRepository) : ViewModel() {

    // MutableStateFlow для хранения текущей классификации
    private val _currentClassification = MutableStateFlow<String?>(null)
    val currentClassification: StateFlow<String?> get() = _currentClassification

    // Состояние для хранения данных о произведениях искусства
    private val _artworks = MutableStateFlow<PagingData<Artwork>>(PagingData.empty())
    val artworks: StateFlow<PagingData<Artwork>> get() = _artworks

    // Состояние для отслеживания обновления данных
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> get() = _isRefreshing

    // Состояние загрузки изображений
    private val _artworkLoadStates = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
    val artworkLoadStates: StateFlow<Map<Int, Boolean>> get() = _artworkLoadStates

    // Обновляем поток данных произведений искусства при изменении классификации
    init {
        viewModelScope.launch {
            currentClassification.collectLatest { classification ->
                refreshArtworks(classification)
            }
        }
    }

    // Функция для обновления данных произведений искусства
    fun refreshArtworks(classification: String? = _currentClassification.value) {
        viewModelScope.launch {
            _isRefreshing.value = true
            val queryParams = classification?.let { mapOf("classification" to it) } ?: emptyMap()
            artRepository.getArtworksStream(queryParams).cachedIn(viewModelScope).collect {
                _artworks.value = it
                _isRefreshing.value = false
            }
        }
    }

    // Функция для установки состояния загрузки изображения
    fun setArtworkLoadState(objectId: Int, isLoaded: Boolean) {
        _artworkLoadStates.value = _artworkLoadStates.value.toMutableMap().apply {
            this[objectId] = isLoaded
        }
    }

    // Функция для установки текущей классификации
    fun setClassification(classification: String?) {
        _currentClassification.value = classification
    }

    // Функция для переключения классификации и сброса фильтра при повторном нажатии
    fun toggleClassification(classification: String) {
        if (_currentClassification.value == classification) {
            _currentClassification.value = null // Сбросить фильтр
        } else {
            _currentClassification.value = classification // Установить новую классификацию
        }
    }
}