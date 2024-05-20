package com.example.android.culturalwaves.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.android.culturalwaves.data.entities.Artwork
import com.example.android.culturalwaves.data.repositories.ArtRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.android.culturalwaves.utils.Result

//class SearchViewModel(private val repository: ArtRepository) : ViewModel() {
//    private val _searchResults = MutableStateFlow<PagingData<Artwork>>(PagingData.empty())
//    private val _searchSuggestions = MutableStateFlow<List<String>>(emptyList())
//    val searchResults: StateFlow<PagingData<Artwork>> = _searchResults.asStateFlow()
//    val searchSuggestions: StateFlow<List<String>> = _searchSuggestions.asStateFlow()
//
//    private val _isLoading = MutableStateFlow(false)
//    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
//
//    fun searchArtworks(query: String, filterOptions: Map<String, String>) {
//        _isLoading.value = true
//        val queryParams = mutableMapOf<String, String>().apply {
//            put("title", query)
//            filterOptions.forEach { (key, value) ->
//                put(key, value)
//            }
//        }
//
//        viewModelScope.launch {
//            repository.getArtworksStream(queryParams).cachedIn(viewModelScope).collect {
//                _searchResults.value = it
//                _isLoading.value = false
//            }
//        }
//    }
//
//    fun fetchSearchSuggestions(query: String) {
//        if (query.length < 3) return // Optionally limit to minimum character count
//
//        viewModelScope.launch {
//            val response = repository.fetchArtworksForSuggestions(query)
//            if (response.isSuccessful) {
//                // Прямо присваиваем список заголовков переменной _searchSuggestions
//                // Заменяем null заголовки на "No Title" или другую подходящую строку
//                _searchSuggestions.value = response.body()?.records?.map { it.title ?: "No Title" } ?: emptyList()
//            }
//        }
//    }
//
//}

class SearchViewModel(private val repository: ArtRepository) : ViewModel() {

    // StateFlow для хранения результатов поиска
    private val _searchResults = MutableStateFlow<PagingData<Artwork>>(PagingData.empty())
    val searchResults: StateFlow<PagingData<Artwork>> = _searchResults.asStateFlow()

    // StateFlow для хранения предложений по поисковому запросу
    private val _searchSuggestions = MutableStateFlow<List<String>>(emptyList())
    val searchSuggestions: StateFlow<List<String>> = _searchSuggestions.asStateFlow()

    // StateFlow для отслеживания состояния загрузки
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // StateFlow для отслеживания статуса загрузки произведений искусства
    private val _artworkLoadStates = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
    val artworkLoadStates: StateFlow<Map<Int, Boolean>> = _artworkLoadStates

    // StateFlow для отслеживания ошибок
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // Функция для установки состояния загрузки произведения искусства
    fun setArtworkLoadState(objectId: Int, isLoaded: Boolean) {
        _artworkLoadStates.value = _artworkLoadStates.value.toMutableMap().apply {
            this[objectId] = isLoaded
        }
    }

    // Функция для поиска произведений искусства по названию
    fun searchArtworks(query: String) {
        _isLoading.value = true // Устанавливаем состояние загрузки в true
        val queryParams = mutableMapOf<String, String>().apply {
            put("title", query) // Добавляем параметр заголовка для поиска
        }

        viewModelScope.launch {
            // Получаем поток данных произведений искусства из репозитория
            repository.getArtworksStream(queryParams).cachedIn(viewModelScope).collect { pagingData ->
                _searchResults.value = pagingData // Обновляем состояние результатов поиска
                _isLoading.value = false // Устанавливаем состояние загрузки в false
            }
        }
    }

    // Функция для получения предложений по поисковому запросу
    fun fetchSearchSuggestions(query: String) {
        if (query.length < 3) return // Ограничиваем минимальное количество символов для предложений

        viewModelScope.launch {
            // Обработка результата через репозиторий
            when (val result = repository.fetchArtworksForSuggestions(query)) {
                is Result.Success -> {
                    _searchSuggestions.value = result.data.records.map { it.title ?: "No Title" } ?: emptyList()
                    _error.value = null // Обнуляем ошибку
                }
                is Result.Error -> {
                    _searchSuggestions.value = emptyList() // Очистка предложений при ошибке
                    _error.value = result.exception.message // Устанавливаем сообщение об ошибке
                }
            }
        }
    }
}
