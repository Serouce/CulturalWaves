package com.example.android.culturalwaves.viewmodel

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

class SearchViewModel(
    private val repository: ArtRepository
) : BaseViewModel() {
    private val _searchResults
    = MutableStateFlow<PagingData<Artwork>>(PagingData.empty())
    val searchResults: StateFlow<PagingData<Artwork>> get() = _searchResults.asStateFlow()

    private val _searchSuggestions
    = MutableStateFlow<List<String>>(emptyList())
    val searchSuggestions: StateFlow<List<String>> get() = _searchSuggestions.asStateFlow()

    private val _artworkLoadStates
    = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
    val artworkLoadStates: StateFlow<Map<Int, Boolean>> get() = _artworkLoadStates.asStateFlow()

    fun setArtworkLoadState(objectId: Int, isLoaded: Boolean) {
        _artworkLoadStates.value = _artworkLoadStates.value.toMutableMap().apply {
            this[objectId] = isLoaded
        }
    }

    fun searchArtworks(query: String) {
        _isLoading.value = true
        val queryParams = mutableMapOf<String, String>().apply {
            put("title", query)
        }

        viewModelScope.launch {
            repository.getArtworksStream(queryParams)
                .cachedIn(viewModelScope).collect { pagingData ->
                _searchResults.value = pagingData
                _isLoading.value = false
            }
        }
    }

    fun fetchSearchSuggestions(query: String) {
        if (query.length < 3) return

        viewModelScope.launch {
            when (val result = repository.fetchArtworksForSuggestions(query)) {
                is Result.Success -> {
                    _searchSuggestions.value = result.data.records.map { it.title ?: "No Title" }
                    _error.value = null
                }
                is Result.Error -> {
                    _searchSuggestions.value = emptyList()
                    _error.value = result.exception.message
                }
            }
        }
    }
}