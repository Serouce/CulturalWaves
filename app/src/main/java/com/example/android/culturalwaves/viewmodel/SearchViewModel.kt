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
    private val _searchResults = MutableStateFlow<PagingData<Artwork>>(PagingData.empty()) // State for search results
    private val _searchSuggestions = MutableStateFlow<List<String>>(emptyList()) // State for search suggestions
    val searchResults: StateFlow<PagingData<Artwork>> = _searchResults.asStateFlow()
    val searchSuggestions: StateFlow<List<String>> = _searchSuggestions.asStateFlow()

    private val _isLoading = MutableStateFlow(false) // Loading state
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _artworkLoadStates = MutableStateFlow<Map<Int, Boolean>>(emptyMap()) // State for artwork load statuses
    val artworkLoadStates: StateFlow<Map<Int, Boolean>> = _artworkLoadStates

    // Function to set artwork load state
    fun setArtworkLoadState(objectId: Int, isLoaded: Boolean) {
        _artworkLoadStates.value = _artworkLoadStates.value.toMutableMap().apply {
            this[objectId] = isLoaded
        }
    }

    // Function to search artworks by title
    fun searchArtworks(query: String) {
        _isLoading.value = true // Set loading state to true
        val queryParams = mutableMapOf<String, String>().apply {
            put("title", query) // Add title parameter for search
        }

        viewModelScope.launch {
            repository.getArtworksStream(queryParams).cachedIn(viewModelScope).collect {
                _searchResults.value = it // Update search results state
                _isLoading.value = false // Set loading state to false
            }
        }
    }

    // Function to fetch search suggestions based on title
    fun fetchSearchSuggestions(query: String) {
        if (query.length < 3) return // Limit minimum character count for suggestions

        viewModelScope.launch {
            val response = repository.fetchArtworksForSuggestions(query)
            if (response.isSuccessful) {
                _searchSuggestions.value = response.body()?.records?.map { it.title ?: "No Title" } ?: emptyList() // Update suggestions state
            }
        }
    }
}
